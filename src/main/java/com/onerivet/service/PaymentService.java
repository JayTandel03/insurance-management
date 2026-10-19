package com.onerivet.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onerivet.exception.BadRequestException;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.PaymentRequestDto;
import com.onerivet.model.dto.PaymentResponseDto;
import com.onerivet.model.entity.Payment;
import com.onerivet.model.entity.Quote;
import com.onerivet.model.entity.Status;
import com.onerivet.model.enums.PaymentMethod;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.repository.PaymentRepository;
import com.onerivet.repository.QuoteRepository;
import com.onerivet.repository.StatusRepository;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

	private final PaymentRepository paymentRepository;
	private final QuoteRepository quoteRepository;
	private final StatusRepository statusRepository;

	public PaymentService(PaymentRepository paymentRepository, QuoteRepository quoteRepository,
			StatusRepository statusRepository) {

		this.paymentRepository = paymentRepository;
		this.quoteRepository = quoteRepository;
		this.statusRepository = statusRepository;
	}

	public PaymentResponseDto processPayment(String quoteId, PaymentRequestDto requestDto) {

		log.info("Processing payment for quoteId: {}", quoteId);

		String paymentMethodString = requestDto.getPaymentMethod().trim().replaceAll("\\s+", "").toUpperCase();

		validatePaymentMethod(paymentMethodString);
		validateExpiryDate(requestDto.getExpiryDate());

		Quote quote = quoteRepository.findById(quoteId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUOTE_NOT_FOUND));

		validatePaymentAlreadyCompleted(quoteId);

		if (!quote.getStatus().getName().equalsIgnoreCase(TemplateStatus.COMPLETED.name())) {

			return processFailedPayment(quote, quoteId, paymentMethodString);
		}

		return processSuccessfulPayment(quote, quoteId, paymentMethodString);
	}

	private void validatePaymentMethod(String paymentMethodString) {

		if (!paymentMethodString.equals(PaymentMethod.CREDITCARD.name())
				&& !paymentMethodString.equals(PaymentMethod.DEBITCARD.name())) {

			throw new BadRequestException(ErrorMessageConstants.INVALID_PAYMENT_METHOD);
		}
	}

	private void validatePaymentAlreadyCompleted(String quoteId) {

		boolean paymentAlreadyCompleted = paymentRepository.existsByQuoteQuoteIdAndStatusNameIgnoreCase(quoteId,
				TemplateStatus.SUCCESSFUL.name());

		if (paymentAlreadyCompleted) {

			throw new BadRequestException(ErrorMessageConstants.PAYMENT_ALREADY_COMPLETED);
		}
	}

	private PaymentResponseDto processSuccessfulPayment(Quote quote, String quoteId, String paymentMethodString) {

		BigDecimal totalPremium = quote.getTotalPremium();

		if (totalPremium == null) {

			throw new BadRequestException(ErrorMessageConstants.TOTAL_PREMIUM_NOT_CALCULATED);
		}

		Status successfulStatus = statusRepository.findByNameIgnoreCase(TemplateStatus.SUCCESSFUL.name())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.STATUS_NOT_FOUND));

		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);

		Payment payment = new Payment();

		payment.setQuote(quote);
		payment.setCreatedById(quote.getUser());
		payment.setPaymentMethod(paymentMethod.getDisplayName());
		payment.setStatus(successfulStatus);
		payment.setTransactionId(generateTransactionId());
		payment.setTotalPremium(totalPremium);

		paymentRepository.save(payment);

		log.info("Payment completed successfully for quoteId: {}", quoteId);

		return buildSuccessResponse(payment, quoteId, paymentMethod);
	}

	private PaymentResponseDto processFailedPayment(Quote quote, String quoteId, String paymentMethodString) {

		Status failedStatus = statusRepository.findByNameIgnoreCase(TemplateStatus.FAILED.name())
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.STATUS_NOT_FOUND));

		PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);

		Payment payment = new Payment();

		payment.setQuote(quote);
		payment.setCreatedById(quote.getUser());
		payment.setPaymentMethod(paymentMethod.getDisplayName());
		payment.setStatus(failedStatus);
		payment.setTransactionId(generateTransactionId());
		payment.setTotalPremium(quote.getTotalPremium());

		paymentRepository.save(payment);

		log.info("Payment failed for quoteId: {}", quoteId);

		PaymentResponseDto responseDto = new PaymentResponseDto();

		responseDto.setMessage(ErrorMessageConstants.PAYMENT_FAILED_QUOTE_NOT_COMPLETED);
		responseDto.setTransactionId(payment.getTransactionId());
		responseDto.setQuoteId(quoteId);
		responseDto.setPaymentMethod(paymentMethod.getDisplayName());
		responseDto.setPaymentStatus(payment.getStatus().getName());
		responseDto.setTotalPremium(payment.getTotalPremium());

		return responseDto;
	}

	private PaymentResponseDto buildSuccessResponse(Payment payment, String quoteId, PaymentMethod paymentMethod) {

		PaymentResponseDto responseDto = new PaymentResponseDto();

		responseDto.setMessage(Constants.PAYMENT_SUCCESS_MESSAGE);
		responseDto.setTransactionId(payment.getTransactionId());
		responseDto.setQuoteId(quoteId);
		responseDto.setPaymentMethod(paymentMethod.getDisplayName());
		responseDto.setPaymentStatus(payment.getStatus().getName());
		responseDto.setTotalPremium(payment.getTotalPremium());

		return responseDto;
	}

	private String generateTransactionId() {

		return Constants.TRANSACTION_ID_PREFIX_STRING + UUID.randomUUID().toString()
				.replace(Constants.HYPHEN, Constants.BLANK).substring(0, 10).toUpperCase();
	}

	private void validateExpiryDate(String expiryDate) {

		YearMonth cardExpiry = YearMonth.parse(expiryDate, DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
		YearMonth currentMonth = YearMonth.now();

		if (cardExpiry.isBefore(currentMonth)) {
			throw new BadRequestException(ErrorMessageConstants.CARD_HAS_EXPIRED);
		}
	}

}
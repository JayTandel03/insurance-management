package com.onerivet.service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.PremiumBreakdownResponseDto;
import com.onerivet.model.enums.VehicleAgeSlabEnum;
import com.onerivet.model.mapper.PremiumBreakdownMapper;
import com.onerivet.model.projection.AddOnPremiumProjection;
import com.onerivet.model.projection.CoveragePremiumProjection;
import com.onerivet.model.projection.PaymentProjection;
import com.onerivet.model.projection.PremiumBreakdownQuoteProjection;
import com.onerivet.model.projection.VehicleAgeFactorProjection;
import com.onerivet.repository.PaymentRepository;
import com.onerivet.repository.QuoteCoverageSelectionRepository;
import com.onerivet.repository.QuoteRepository;
import com.onerivet.repository.VehicleAgeFactorRepository;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class PremiumBreakdownService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PremiumBreakdownService.class);

	private final QuoteRepository quoteRepository;
	private final PaymentRepository paymentRepository;
	private final QuoteCoverageSelectionRepository quoteCoverageSelectionRepository;
	private final VehicleAgeFactorRepository vehicleAgeFactorRepository;
	private final PremiumBreakdownMapper premiumBreakdownMapper;

	public PremiumBreakdownService(QuoteRepository quoteRepository, PaymentRepository paymentRepository,
			QuoteCoverageSelectionRepository quoteCoverageSelectionRepository,
			VehicleAgeFactorRepository vehicleAgeFactorRepository, PremiumBreakdownMapper premiumBreakdownMapper) {

		this.quoteRepository = quoteRepository;
		this.paymentRepository = paymentRepository;
		this.quoteCoverageSelectionRepository = quoteCoverageSelectionRepository;
		this.vehicleAgeFactorRepository = vehicleAgeFactorRepository;
		this.premiumBreakdownMapper = premiumBreakdownMapper;
	}

	public PremiumBreakdownResponseDto getPremiumBreakdown(String quoteId) {

		LOGGER.info("Fetching premium breakdown for quoteId: {}", quoteId);

		PremiumBreakdownQuoteProjection quote = getQuoteDetails(quoteId);
		PaymentProjection payment = getPaymentDetails(quoteId);

		List<CoveragePremiumProjection> coverageProjections = getCoverageDetails(quoteId);
		List<AddOnPremiumProjection> addOnProjections = getAddOnDetails(quoteId);

		BigDecimal carAgeFactor = getVehicleAgeFactor(quote.getCarRegistrationYear());
		BigDecimal motorAgeFactor = getVehicleAgeFactor(quote.getMotorCycleRegistrationYear());

		return buildResponse(quoteId, quote, payment, carAgeFactor, motorAgeFactor, coverageProjections,
				addOnProjections);
	}

	private PremiumBreakdownQuoteProjection getQuoteDetails(String quoteId) {
		return quoteRepository.findPremiumBreakdownQuote(quoteId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.QUOTE_NOT_FOUND));
	}

	private PaymentProjection getPaymentDetails(String quoteId) {
		return paymentRepository.findPaymentDetailsByQuoteId(quoteId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.PAYMENT_NOT_FOUND));
	}

	private List<CoveragePremiumProjection> getCoverageDetails(String quoteId) {
		return quoteCoverageSelectionRepository.getSelectedCoveragePremiums(quoteId);
	}

	private List<AddOnPremiumProjection> getAddOnDetails(String quoteId) {
		return quoteCoverageSelectionRepository.getSelectedAddOns(quoteId);
	}

	private BigDecimal getVehicleAgeFactor(Integer registrationYear) {

		if (registrationYear == null) {
			return BigDecimal.ZERO;
		}

		int vehicleAge = Year.now().getValue() - registrationYear;

		String ageSlab = VehicleAgeSlabEnum.fromAge(vehicleAge);

		VehicleAgeFactorProjection projection = vehicleAgeFactorRepository.findByVehicleAge(ageSlab)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));

		return new BigDecimal(projection.getVehicleAgeFactor());
	}

	private PremiumBreakdownResponseDto buildResponse(String quoteId, PremiumBreakdownQuoteProjection quote,
			PaymentProjection payment, BigDecimal carAgeFactor, BigDecimal motorAgeFactor,
			List<CoveragePremiumProjection> coverageProjections, List<AddOnPremiumProjection> addOnProjections) {

		PremiumBreakdownResponseDto response = new PremiumBreakdownResponseDto();

		response.setQuoteId(quoteId);
		response.setTransactionId(payment.getTransactionId());
		response.setPlanFactor(quote.getPlanFactor());
		response.setCarVehicleVariantFactor(quote.getCarVariantFactor());
		response.setMotorVehicleVariantFactor(quote.getMotorVariantFactor());
		response.setCarAgeFactor(carAgeFactor);
		response.setMotorAgeFactor(motorAgeFactor);
		response.setTotalPremium(payment.getTotalPremium());
		response.setCoverages(coverageProjections.stream().map(premiumBreakdownMapper::mapCoverageDto).toList());
		response.setAddOns(addOnProjections.stream().map(premiumBreakdownMapper::mapAddOnDto).toList());

		return response;
	}
}
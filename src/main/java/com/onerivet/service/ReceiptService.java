package com.onerivet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.dto.ReceiptAddOnResponseDto;
import com.onerivet.model.dto.ReceiptCoverageResponseDto;
import com.onerivet.model.dto.ReceiptResponseDto;
import com.onerivet.model.projection.AddOnPremiumProjection;
import com.onerivet.model.projection.ReceiptCoverageProjection;
import com.onerivet.model.projection.ReceiptDetailsProjection;
import com.onerivet.model.projection.VehicleAgeFactorProjection;
import com.onerivet.repository.QuoteAnswerRepository;
import com.onerivet.repository.QuoteCoverageSelectionRepository;
import com.onerivet.repository.QuoteRepository;
import com.onerivet.repository.VehicleAgeFactorRepository;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;
import com.onerivet.util.ReceiptPdfConstants;

@Service
public class ReceiptService {

	private final QuoteRepository quoteRepository;
	private final QuoteAnswerRepository quoteAnswerRepository;
	private final QuoteCoverageSelectionRepository quoteCoverageSelectionRepository;
	private final VehicleAgeFactorRepository vehicleAgeFactorRepository;
	private final ReceiptPdfGenerator receiptPdfGenerator;

	public ReceiptService(QuoteRepository quoteRepository, QuoteAnswerRepository quoteAnswerRepository,
	                      QuoteCoverageSelectionRepository quoteCoverageSelectionRepository,
	                      VehicleAgeFactorRepository vehicleAgeFactorRepository, ReceiptPdfGenerator receiptPdfGenerator) {

		this.quoteRepository = quoteRepository;
		this.quoteAnswerRepository = quoteAnswerRepository;
		this.quoteCoverageSelectionRepository = quoteCoverageSelectionRepository;
		this.vehicleAgeFactorRepository = vehicleAgeFactorRepository;
		this.receiptPdfGenerator = receiptPdfGenerator;
	}

	public byte[] generateReceipt(String quoteId) throws Exception {
		ReceiptResponseDto responseDto = buildReceiptData(quoteId);
		return receiptPdfGenerator.generate(responseDto);
	}

	private ReceiptResponseDto buildReceiptData(String quoteId) {

		ReceiptDetailsProjection receiptDetailsProjection = getReceiptDetails(quoteId);

		if (receiptDetailsProjection.getTransactionId() == null) {
			throw new ResourceNotFoundException(ReceiptPdfConstants.RECEIPT_NOT_AVAILABLE_PAYMENT_PENDING);
		}
		ReceiptResponseDto responseDto = buildBasicReceiptData(receiptDetailsProjection);

		setVehicleAgeDetails(responseDto, receiptDetailsProjection);
		setVinNumber(responseDto, quoteId);
		setCoverageDetails(responseDto, quoteId);
		setAddOnDetails(responseDto, quoteId);

		return responseDto;
	}

	private ReceiptDetailsProjection getReceiptDetails(String quoteId) {
		return quoteRepository.getReceiptDetails(quoteId)
				.orElseThrow(() -> new ResourceNotFoundException(ReceiptPdfConstants.QUOTE_NOT_FOUND_IN_PAYMENT));
	}

	private ReceiptResponseDto buildBasicReceiptData(ReceiptDetailsProjection projection) {

		ReceiptResponseDto responseDto = new ReceiptResponseDto();

		responseDto.setInvoiceNumber(generateInvoiceNumber());
		responseDto.setInvoiceDate(LocalDateTime.now());
		responseDto.setPaymentDate(projection.getPaymentCreatedDate().toLocalDate());

		responseDto.setPolicyStartDate(projection.getPaymentCreatedDate().toLocalDate());
		responseDto.setPolicyEndDate(projection.getPaymentCreatedDate().toLocalDate().plusYears(1));

		responseDto.setTransactionId(projection.getTransactionId());
		responseDto.setPaymentMethod(projection.getPaymentMethod());
		responseDto.setPolicyNumber(projection.getQuoteId());
		responseDto.setTotalPremiumPaid(projection.getTotalPremium());

		responseDto.setCustomerName(
				projection.getCustomerFirstName() + Constants.SPACE + projection.getCustomerLastName());
		responseDto.setAddress(projection.getAddress());
		responseDto.setGender(projection.getGender());
		responseDto.setCustomerAge(Period.between(projection.getDateOfBirth(), LocalDate.now()).getYears());

		responseDto.setVehicleTypeId(projection.getVehicleTypeId());

		responseDto.setCarVariant(projection.getCarVariant());
		responseDto.setCarVariantFactor(projection.getCarVariantFactor());

		responseDto.setMotorcycleVariant(projection.getMotorcycleVariant());
		responseDto.setMotorcycleVariantFactor(projection.getMotorcycleVariantFactor());

		responseDto.setPlanName(projection.getPlanName());
		responseDto.setPlanFactor(projection.getPlanFactor());

		Integer vehicleTypeId = projection.getVehicleTypeId();

		if (vehicleTypeId != null) {
			if (vehicleTypeId == 1 || vehicleTypeId == 2) {
				responseDto.setInsuredVehicleCount(1);
			} else if (vehicleTypeId == 3) {
				responseDto.setInsuredVehicleCount(2);
			}
		}

		return responseDto;
	}

	private void setVehicleAgeDetails(ReceiptResponseDto responseDto, ReceiptDetailsProjection projection) {

		if (projection.getCarRegistrationYear() != null) {

			Integer carAge = LocalDate.now().getYear() - projection.getCarRegistrationYear();

			responseDto.setCarAge(carAge + ReceiptPdfConstants.YEARS);

			VehicleAgeFactorProjection factorProjection = getVehicleAgeFactor(carAge);

			responseDto.setCarVehicleAgeFactor(factorProjection.getVehicleAgeFactor());
		}

		if (projection.getMotorcycleRegistrationYear() != null) {

			Integer motorcycleAge = LocalDate.now().getYear() - projection.getMotorcycleRegistrationYear();

			responseDto.setMotorcycleAge(motorcycleAge + ReceiptPdfConstants.YEARS);

			VehicleAgeFactorProjection factorProjection = getVehicleAgeFactor(motorcycleAge);

			responseDto.setMotorcycleVehicleAgeFactor(factorProjection.getVehicleAgeFactor());
		}
	}

	private VehicleAgeFactorProjection getVehicleAgeFactor(Integer vehicleAge) {

		if (vehicleAge < 3) {
			return vehicleAgeFactorRepository.findByVehicleAge(ReceiptPdfConstants.VEHICLE_AGE_0_TO_3).orElseThrow(
					() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));

		} else if (vehicleAge < 5) {
			return vehicleAgeFactorRepository.findByVehicleAge(ReceiptPdfConstants.VEHICLE_AGE_3_TO_5).orElseThrow(
					() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));

		} else if (vehicleAge < 10) {
			return vehicleAgeFactorRepository.findByVehicleAge(ReceiptPdfConstants.VEHICLE_AGE_5_TO_10).orElseThrow(
					() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));
		}
		return vehicleAgeFactorRepository.findByVehicleAge(ReceiptPdfConstants.VEHICLE_AGE_10_PLUS)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));
	}

	private void setVinNumber(ReceiptResponseDto responseDto, String quoteId) {
		quoteAnswerRepository.findActiveByQuoteId(quoteId).stream()
				.filter(answer -> answer.getQuestion().getQuestion().toLowerCase().contains(ReceiptPdfConstants.VIN))
				.findFirst().ifPresent(answer -> responseDto.setVinNumber(answer.getAnswerText()));
	} 

	private void setCoverageDetails(ReceiptResponseDto responseDto, String quoteId) {

		List<ReceiptCoverageResponseDto> coverages = new ArrayList<>();
		BigDecimal basePremium = BigDecimal.ZERO;
		List<ReceiptCoverageProjection> coverageProjections = quoteCoverageSelectionRepository
				.getReceiptSelectedCoveragePremiums(quoteId);

		for (ReceiptCoverageProjection projection : coverageProjections) {

			ReceiptCoverageResponseDto coverageDto = new ReceiptCoverageResponseDto();
			coverageDto.setCoverageName(projection.getCoverageName());
			coverageDto.setCoverageLimit(projection.getCoverageTypeName());
			coverageDto.setPremium(projection.getPremium());
			basePremium = basePremium.add(projection.getPremium());
			coverages.add(coverageDto);
		}
		responseDto.setBasePremium(basePremium);
		responseDto.setCoverages(coverages);
	}

	private void setAddOnDetails(ReceiptResponseDto responseDto, String quoteId) {

		List<ReceiptAddOnResponseDto> addOns = new ArrayList<>();
		List<AddOnPremiumProjection> addOnProjections = quoteCoverageSelectionRepository.getSelectedAddOns(quoteId);

		for (AddOnPremiumProjection projection : addOnProjections) {

			ReceiptAddOnResponseDto addOnDto = new ReceiptAddOnResponseDto();
			addOnDto.setAddOnName(projection.getName());
			addOnDto.setPremium(projection.getBasePrice());
			addOns.add(addOnDto);
		}
		responseDto.setAddOns(addOns);
	}

	private String generateInvoiceNumber() {
		return Constants.INVOICE_ID_PREFIX_STRING + UUID.randomUUID().toString()
				.replace(Constants.HYPHEN, Constants.BLANK).substring(0, 10).toUpperCase();
	}
}
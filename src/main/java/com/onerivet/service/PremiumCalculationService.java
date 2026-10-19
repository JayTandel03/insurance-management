package com.onerivet.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.onerivet.exception.ResourceNotFoundException;
import com.onerivet.model.entity.Quote;
import com.onerivet.model.entity.QuoteCoverageSelection;
import com.onerivet.model.entity.VehicleVariant;
import com.onerivet.model.enums.VehicleAgeSlabEnum;
import com.onerivet.service.helper.CoverageRepositoryHelper;
import com.onerivet.service.helper.QuoteMasterRepositoryHelper;
import com.onerivet.service.helper.QuoteWriteRepositoryHelper;
import com.onerivet.service.helper.TemplateRepositoryHelper;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;

@Service
public class PremiumCalculationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PremiumCalculationService.class);

	private final QuoteWriteRepositoryHelper quoteWriteRepositoryHelper;
	private final CoverageRepositoryHelper coverageRepositoryHelper;
	private final QuoteMasterRepositoryHelper quoteMasterRepositoryHelper;
	private final TemplateRepositoryHelper templateRepositoryHelper;

	public PremiumCalculationService(QuoteWriteRepositoryHelper quoteWriteRepositoryHelper,
	                                 CoverageRepositoryHelper coverageRepositoryHelper, QuoteMasterRepositoryHelper quoteMasterRepositoryHelper,
	                                 TemplateRepositoryHelper templateRepositoryHelper) {

		this.quoteWriteRepositoryHelper = quoteWriteRepositoryHelper;
		this.coverageRepositoryHelper = coverageRepositoryHelper;
		this.quoteMasterRepositoryHelper = quoteMasterRepositoryHelper;
		this.templateRepositoryHelper = templateRepositoryHelper;
	}

	public BigDecimal calculateTotalPremium(Quote quote) {

		if (quote.getPlan() == null) {
			LOGGER.warn("Skipping premium calculation because plan is null for quoteId: {}", quote.getQuoteId());
			return BigDecimal.ZERO;
		}

		LOGGER.info("Premium calculation started for quoteId: {}", quote.getQuoteId());

		Integer vehicleTypeId = quote.getVehicleType().getVehicleTypeId();

		if (Constants.VEHICLE_TYPE_CAR.equals(vehicleTypeId)) {

			return calculateVehiclePremium(quote, quote.getCarRegistrationYear(), quote.getCarVariantId());
		}

		if (Constants.VEHICLE_TYPE_MOTORBIKE.equals(vehicleTypeId)) {

			return calculateVehiclePremium(quote, quote.getMotorCycleRegistrationYear(),
					quote.getMotorCycleVariantId());
		}

		if (Constants.VEHICLE_TYPE_ALL.equals(vehicleTypeId)) {

			BigDecimal carPremium = calculateVehiclePremium(quote, quote.getCarRegistrationYear(),
					quote.getCarVariantId());

			BigDecimal motorcyclePremium = calculateVehiclePremium(quote, quote.getMotorCycleRegistrationYear(),
					quote.getMotorCycleVariantId());

			BigDecimal totalPremium = carPremium.add(motorcyclePremium);

			LOGGER.info(
					"Combined Premium Calculation completed. carPremium: {}, motorcyclePremium: {}, totalPremium: {}",
					carPremium, motorcyclePremium, totalPremium);

			return totalPremium;
		}

		return BigDecimal.ZERO;
	}

	private BigDecimal calculateVehiclePremium(Quote quote, Integer registrationYear, VehicleVariant vehicleVariant) {

		if (registrationYear == null || vehicleVariant == null) {

			LOGGER.info("Skipping premium calculation because registrationYear or vehicleVariant is null");

			return BigDecimal.ZERO;
		}

		List<QuoteCoverageSelection> selections = getCoverageSelections(quote);

		BigDecimal basePremium = calculateBasePremium(quote, selections);

		BigDecimal addOnPremium = calculateAddOnPremium(quote, selections);

		return applyPremiumFormula(quote, registrationYear, vehicleVariant, basePremium, addOnPremium);
	}

	private List<QuoteCoverageSelection> getCoverageSelections(Quote quote) {

		return quoteWriteRepositoryHelper.quoteCoverageSelectionRepository.findCoveragesByQuote(quote.getQuoteId());
	}

	private BigDecimal calculateBasePremium(Quote quote, List<QuoteCoverageSelection> selections) {

		BigDecimal basePremium = BigDecimal.ZERO;

		Set<Integer> processedCoverageIds = new HashSet<>();

		for (QuoteCoverageSelection selection : selections) {

			Integer coverageId = selection.getCoverage().getCoverageId();

			if (processedCoverageIds.add(coverageId)) {

				BigDecimal coveragePrice = getCoverageBasePrice(quote, coverageId);

				basePremium = basePremium.add(coveragePrice);

				LOGGER.info("CoverageId: {}, basePrice: {}", coverageId, coveragePrice);
			}
		}

		LOGGER.info("Total base premium: {}", basePremium);

		return basePremium;
	}

	private BigDecimal getCoverageBasePrice(Quote quote, Integer coverageId) {

		return coverageRepositoryHelper.vehicleTypeCoveragePriceRepository
				.findBasePrice(quote.getTemplate().getTemplateId(), quote.getPlan().getPlanId(), coverageId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.COVERAGE_PRICE_NOT_FOUND));
	}

	private BigDecimal calculateAddOnPremium(Quote quote, List<QuoteCoverageSelection> selections) {

		BigDecimal addOnPremium = BigDecimal.ZERO;

		Set<Integer> processedAddOnIds = new HashSet<>();

		for (QuoteCoverageSelection selection : selections) {

			if (selection.getAddOn() == null) {
				continue;
			}

			Integer addOnId = selection.getAddOn().getAddOnId();

			if (processedAddOnIds.add(addOnId)) {

				BigDecimal addOnPrice = getAddOnBasePrice(quote, addOnId);

				addOnPremium = addOnPremium.add(addOnPrice);

				LOGGER.info("AddOnId: {}, addOnPrice: {}", addOnId, addOnPrice);
			}
		}

		LOGGER.info("Total add-on premium: {}", addOnPremium);

		return addOnPremium;
	}

	private BigDecimal getAddOnBasePrice(Quote quote, Integer addOnId) {

		return templateRepositoryHelper.templateAddOnPriceRepository
				.findBasePrice(quote.getTemplate().getTemplateId(), quote.getPlan().getPlanId(), addOnId)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.ADD_ON_PRICE_NOT_FOUND));
	}

	private BigDecimal applyPremiumFormula(Quote quote, Integer registrationYear, VehicleVariant vehicleVariant,
	                                       BigDecimal basePremium, BigDecimal addOnPremium) {

		BigDecimal ageFactor = getVehicleAgeFactor(registrationYear);

		BigDecimal variantFactor = vehicleVariant.getVehicleVariantFactor();

		BigDecimal planFactor = quote.getPlan().getPlanFactor();

		LOGGER.info(
				"Formula values -> basePremium: {}, ageFactor: {}, variantFactor: {}, planFactor: {}, addOnPremium: {}",
				basePremium, ageFactor, variantFactor, planFactor, addOnPremium);

		return basePremium.multiply(ageFactor).multiply(variantFactor).multiply(planFactor).add(addOnPremium)
				.setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getVehicleAgeFactor(Integer registrationYear) {

		int age = Year.now().getValue() - registrationYear;

		String ageRange = VehicleAgeSlabEnum.fromAge(age);

		return quoteMasterRepositoryHelper.vehicleAgeFactorRepository.findVehicleAgeFactorByVehicleAge(ageRange)
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessageConstants.VEHICLE_AGE_FACTOR_NOT_FOUND));
	}
}
package com.onerivet.model.mapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.onerivet.model.dto.PaymentDetailsResponse;
import com.onerivet.model.dto.QuoteResponseDto;
import com.onerivet.model.dto.TemplateDetailsResponseDto;
import com.onerivet.model.dto.VehicleDetailsResponseDto;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.model.projection.CompletedQuoteProjection;
import com.onerivet.model.projection.InCompleteQuoteProjection;
@Component
public class QuoteMapper {
    public List<QuoteResponseDto> toInCompleteQuoteResponses(List<InCompleteQuoteProjection> projections) {
        return projections.stream()
                .map(projection -> new QuoteResponseDto(
                        projection.getQuoteId(),
                        getVehicleDetails(
                                projection.getCarName(),
                                projection.getCarRegistrationYear(),
                                projection.getMotorCycleName(),
                                projection.getMotorCycleRegistrationYear()),
                        null,
                        new PaymentDetailsResponse(null, TemplateStatus.INCOMPLETE.name(), null, null)))
                .toList();
    }
    public List<QuoteResponseDto> toCompletedQuoteResponses(List<CompletedQuoteProjection> projections) {
        return projections.stream()
                .map(projection -> new QuoteResponseDto(
                        projection.getQuoteId(),
                        getVehicleDetails(
                                projection.getCarName(),
                                projection.getCarRegistrationYear(),
                                projection.getMotorCycleName(),
                                projection.getMotorCycleRegistrationYear()),
                        new TemplateDetailsResponseDto(
                                projection.getTemplateId(),
                                projection.getTemplateName(),
                                projection.getPlanId(),
                                projection.getPlanName()),
                        new PaymentDetailsResponse(
                                projection.getPaymentId(),
                                TemplateStatus.SUCCESSFUL.name(),
                                projection.getTotalPremium(),
                                projection.getTransactionId())))
                .toList();
    }
    private List<VehicleDetailsResponseDto> getVehicleDetails(String carName, Integer carRegistrationYear,
                                                              String motorCycleName, Integer motorCycleRegistrationYear) {
        List<VehicleDetailsResponseDto> vehicleDetails = new ArrayList<>();
        if (carName != null) {
            vehicleDetails.add(new VehicleDetailsResponseDto(carName, "Car", carRegistrationYear));
        }
        if (motorCycleName != null) {
            vehicleDetails.add(new VehicleDetailsResponseDto(motorCycleName, "Motorcycle", motorCycleRegistrationYear));
        }
        return vehicleDetails;
    }
}
package com.onerivet.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Component;
import com.onerivet.model.dto.ReceiptAddOnResponseDto;
import com.onerivet.model.dto.ReceiptCoverageResponseDto;
import com.onerivet.model.dto.ReceiptResponseDto;
import com.onerivet.service.helper.PdfHelper;
import com.onerivet.util.ReceiptPdfConstants;

@Component
public class ReceiptPdfGenerator {

	private static class PageContext {

		private final PDDocument document;
		private PDPage currentPage;
		private PDPageContentStream contentStream;
		private float y;

		PageContext(PDDocument document) throws Exception {
			this.document = document;
			addNewPage();
		}

		private void addNewPage() throws Exception {
			if (contentStream != null) {
				contentStream.close();
			}
			currentPage = new PDPage(PDRectangle.A4);
			document.addPage(currentPage);
			contentStream = new PDPageContentStream(document, currentPage);
			y = ReceiptPdfConstants.PAGE_TOP;
		}

		void ensureSpace(float requiredSpace) throws Exception {
			if (y - requiredSpace < ReceiptPdfConstants.PAGE_BOTTOM) {
				addNewPage();
			}
		}

		void moveDown(float delta) throws Exception {
			y -= delta;
			if (y < ReceiptPdfConstants.PAGE_BOTTOM) {
				addNewPage();
			}
		}

		PDPageContentStream cs() {
			return contentStream;
		}

		float getY() {
			return y;
		}

		void setY(float v) {
			this.y = v;
		}

		void close() throws Exception {
			if (contentStream != null) {
				contentStream.close();
			}
		}
	}

	public byte[] generate(ReceiptResponseDto dto) throws Exception {

		PDDocument document = new PDDocument();
		PDImageXObject logo = loadLogo(document);
		PageContext ctx = new PageContext(document);

		BigDecimal totalCoveragePrice = sumCoveragePremiums(dto);
		BigDecimal totalAddOnPrice = sumAddOnPremiums(dto);
		BigDecimal combinedBase = totalCoveragePrice;

		BigDecimal planFactor = dto.getPlanFactor() == null ? BigDecimal.ONE : dto.getPlanFactor();
		BigDecimal carAgeFactor = dto.getCarVehicleAgeFactor() == null ? BigDecimal.ONE
				: new BigDecimal(dto.getCarVehicleAgeFactor());
		BigDecimal motorcycleAgeFactor = dto.getMotorcycleVehicleAgeFactor() == null ? BigDecimal.ONE
				: new BigDecimal(dto.getMotorcycleVehicleAgeFactor());

		BigDecimal carVariantFactor = dto.getCarVariantFactor() == null ? BigDecimal.ONE : dto.getCarVariantFactor();
		BigDecimal motorcycleVariantFactor = dto.getMotorcycleVariantFactor() == null ? BigDecimal.ONE
				: dto.getMotorcycleVariantFactor();

		BigDecimal planPremium = combinedBase.multiply(planFactor).setScale(2, RoundingMode.HALF_UP);

		BigDecimal carAgePremium = combinedBase.multiply(carAgeFactor).setScale(2, RoundingMode.HALF_UP);
		BigDecimal motorcycleAgePremium = combinedBase.multiply(motorcycleAgeFactor).setScale(2, RoundingMode.HALF_UP);

		BigDecimal carVariantPremium = combinedBase.multiply(carVariantFactor).setScale(2, RoundingMode.HALF_UP);
		BigDecimal motorcycleVariantPremium = combinedBase.multiply(motorcycleVariantFactor).setScale(2,
				RoundingMode.HALF_UP);

		BigDecimal carPremium = BigDecimal.ZERO;

		if (dto.getCarVariant() != null) {

			carPremium = combinedBase.multiply(planFactor).multiply(carAgeFactor).multiply(carVariantFactor).setScale(2,
					RoundingMode.HALF_UP);
		}

		BigDecimal motorcyclePremium = BigDecimal.ZERO;

		if (dto.getMotorcycleVariant() != null) {

			motorcyclePremium = combinedBase.multiply(planFactor).multiply(motorcycleAgeFactor)
					.multiply(motorcycleVariantFactor).setScale(2, RoundingMode.HALF_UP);
		}

		BigDecimal totalPremium = carPremium.add(motorcyclePremium).add(totalAddOnPrice).setScale(2,
				RoundingMode.HALF_UP);

		drawTitle(ctx);
		drawCompanyHeader(ctx, logo, dto);
		drawSeparator(ctx);
		drawNameInsuredSection(ctx, dto);
		drawSeparator(ctx);
		drawTotalPolicyPremium(ctx, dto, totalPremium);
		drawSeparator(ctx);
		drawVehicleSection(ctx, dto);
		drawSeparator(ctx);
		drawDriverRatingSection(ctx, dto);
		drawSeparator(ctx);
		drawCoveragesTable(ctx, dto, totalCoveragePrice);
		drawAddOnsTable(ctx, dto, totalAddOnPrice);
		drawFactorsTable(ctx, dto, planPremium, carAgePremium, motorcycleAgePremium, carVariantPremium,
				motorcycleVariantPremium);
		drawSeparator(ctx);
		drawTotalPremiumPaid(ctx, dto, totalPremium);
		drawSeparator(ctx);
		drawFooter(ctx);

		ctx.close();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		document.close();
		return outputStream.toByteArray();
	}

	private PDImageXObject loadLogo(PDDocument document) throws Exception {
		InputStream is = getClass().getResourceAsStream(ReceiptPdfConstants.LOGO_RESOURCE_PATH);
		if (is == null) {
			System.err.println(ReceiptPdfConstants.WARN_LOGO_NOT_FOUND + ReceiptPdfConstants.LOGO_RESOURCE_PATH
					+ ReceiptPdfConstants.WARN_LOGO_SKIP);
			return null;
		}
		byte[] imageBytes = is.readAllBytes();
		is.close();
		return PDImageXObject.createFromByteArray(document, imageBytes, ReceiptPdfConstants.LOGO_RESOURCE_NAME);
	}

	private BigDecimal sumCoveragePremiums(ReceiptResponseDto dto) {
		BigDecimal total = BigDecimal.ZERO;
		for (ReceiptCoverageResponseDto coverage : dto.getCoverages()) {
			total = total.add(coverage.getPremium());
		}
		return total;
	}

	private BigDecimal sumAddOnPremiums(ReceiptResponseDto dto) {
		BigDecimal total = BigDecimal.ZERO;
		if (dto.getAddOns() != null) {
			for (ReceiptAddOnResponseDto addOn : dto.getAddOns()) {
				total = total.add(addOn.getPremium());
			}
		}
		return total;
	}

	private void drawSeparator(PageContext ctx) throws Exception {
		ctx.ensureSpace(30);
		PdfHelper.line(ctx.cs(), ReceiptPdfConstants.LINE_START_X, ctx.getY(), ReceiptPdfConstants.LINE_END_X,
				ctx.getY());
		ctx.moveDown(30);
	}

	private void drawTitle(PageContext ctx) throws Exception {
		ctx.ensureSpace(60);
		PdfHelper.title(ctx.cs(), ReceiptPdfConstants.TITLE_PAYMENT_INVOICE, ReceiptPdfConstants.TITLE_X, ctx.getY());
		ctx.moveDown(50);
	}

	private void drawCompanyHeader(PageContext ctx, PDImageXObject logo, ReceiptResponseDto dto) throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;
		float rightX = ReceiptPdfConstants.RIGHT_X;

		ctx.ensureSpace(100 + ReceiptPdfConstants.LOGO_HEIGHT);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_COMPANY_LOGO, leftX, ctx.getY());
		ctx.moveDown(8);

		if (logo != null) {
			ctx.cs().drawImage(logo, leftX, ctx.getY() - ReceiptPdfConstants.LOGO_HEIGHT,
					ReceiptPdfConstants.LOGO_WIDTH, ReceiptPdfConstants.LOGO_HEIGHT);
		}
		ctx.moveDown(ReceiptPdfConstants.LOGO_HEIGHT + 10);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_INSURE_EDGE, leftX, ctx.getY());
		ctx.moveDown(25);
		PdfHelper.text(ctx.cs(), ReceiptPdfConstants.HEADING_COVERAGE_PROVIDED_BY, leftX, ctx.getY());
		ctx.moveDown(18);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_COMPANY_ADDRESS, leftX, ctx.getY());

		float rightY = ctx.getY() + 90;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_INVOICE_NUMBER + dto.getInvoiceNumber(), rightX, rightY);
		rightY -= 22;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_INVOICE_DATE + dto.getInvoiceDate(), rightX, rightY);
		rightY -= 22;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_PAYMENT_DATE + dto.getPaymentDate(), rightX, rightY);
		rightY -= 22;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_DUE_DATE + dto.getPolicyEndDate(), rightX, rightY);
		rightY -= 22;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_PAYMENT_METHOD + dto.getPaymentMethod(), rightX, rightY);
		rightY -= 22;
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_TRANSACTION_ID + dto.getTransactionId(), rightX, rightY);

		ctx.setY(Math.min(ctx.getY(), rightY) - 30);
	}

	private void drawNameInsuredSection(PageContext ctx, ReceiptResponseDto dto) throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;
		float rightX = ReceiptPdfConstants.RIGHT_X;

		ctx.ensureSpace(80);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_NAME_INSURED, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_POLICY_NUMBER, rightX, ctx.getY());
		ctx.moveDown(30);

		PdfHelper.text(ctx.cs(), dto.getCustomerName(), leftX, ctx.getY());
		PdfHelper.text(ctx.cs(), dto.getPolicyNumber(), rightX, ctx.getY());
		ctx.moveDown(22);

		PdfHelper.text(ctx.cs(), dto.getAddress(), leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_POLICY_VALID, rightX, ctx.getY());
		ctx.moveDown(22);

		PdfHelper.text(ctx.cs(),
				dto.getPolicyStartDate() + ReceiptPdfConstants.LABEL_POLICY_VALID_TO + dto.getPolicyEndDate(), rightX,
				ctx.getY());
		ctx.moveDown(35);
	}

	private void drawTotalPolicyPremium(PageContext ctx, ReceiptResponseDto dto, BigDecimal totalPremium)
			throws Exception {
		ctx.ensureSpace(50);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_TOTAL_PREMIUM_PAID + totalPremium,
				ReceiptPdfConstants.LEFT_X, ctx.getY());
		ctx.moveDown(25);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.NOTE_NOT_A_BILL, ReceiptPdfConstants.LEFT_X, ctx.getY());
		ctx.moveDown(40);
	}

	private void drawVehicleSection(PageContext ctx, ReceiptResponseDto dto) throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;
		float rightX = ReceiptPdfConstants.RIGHT_X;

		ctx.ensureSpace(ReceiptPdfConstants.SPACE_REQUIRED_VEHICLE);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_VEHICLES_COVERED, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_VIN, rightX, ctx.getY());
		ctx.moveDown(ReceiptPdfConstants.Y_MOVE_HEADING_DOWN);

		if (dto.getVehicleTypeId() == ReceiptPdfConstants.VEHICLE_TYPE_CAR) {
			PdfHelper.text(ctx.cs(), ReceiptPdfConstants.PREFIX_CAR + dto.getCarVariant(), leftX, ctx.getY());
		} else if (dto.getVehicleTypeId() == ReceiptPdfConstants.VEHICLE_TYPE_MOTORCYCLE) {
			PdfHelper.text(ctx.cs(), ReceiptPdfConstants.PREFIX_MOTORCYCLE + dto.getMotorcycleVariant(), leftX,
					ctx.getY());
		} else {
			PdfHelper.text(ctx.cs(), ReceiptPdfConstants.PREFIX_CAR + dto.getCarVariant(), leftX, ctx.getY());
			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);
			PdfHelper.text(ctx.cs(), ReceiptPdfConstants.PREFIX_MOTORCYCLE + dto.getMotorcycleVariant(), leftX,
					ctx.getY());
		}

		PdfHelper.text(ctx.cs(), dto.getVinNumber(), rightX, ctx.getY());
		ctx.moveDown(ReceiptPdfConstants.Y_MOVE_SECTION_END_DOWN);
	}

	private void drawDriverRatingSection(PageContext ctx, ReceiptResponseDto dto) throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;

		ctx.ensureSpace(100);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.HEADING_DRIVER_RATING, leftX, ctx.getY());
		ctx.moveDown(30);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_DRIVERS_INCLUDED, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_AGE, ReceiptPdfConstants.COL_AGE_X, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_GENDER, ReceiptPdfConstants.COL_GENDER_X, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_VEHICLES, ReceiptPdfConstants.COL_VEHICLES_X, ctx.getY());
		ctx.moveDown(25);

		PdfHelper.text(ctx.cs(), dto.getCustomerName(), leftX, ctx.getY());
		PdfHelper.text(ctx.cs(), String.valueOf(dto.getCustomerAge()), ReceiptPdfConstants.COL_AGE_X, ctx.getY());
		PdfHelper.text(ctx.cs(), dto.getGender(), ReceiptPdfConstants.COL_GENDER_X, ctx.getY());
		PdfHelper.text(ctx.cs(), String.valueOf(dto.getInsuredVehicleCount()), ReceiptPdfConstants.COL_VEHICLES_X,
				ctx.getY());
		ctx.moveDown(40);

		PdfHelper.italic(ctx.cs(), ReceiptPdfConstants.DISCLAIMER_LINE_1, leftX, ctx.getY());
		ctx.moveDown(18);
		PdfHelper.italic(ctx.cs(), ReceiptPdfConstants.DISCLAIMER_LINE_2, leftX, ctx.getY());
		ctx.moveDown(30);
	}

	private void drawCoveragesTable(PageContext ctx, ReceiptResponseDto dto, BigDecimal totalCoveragePrice)
			throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;

		ctx.ensureSpace(60 + dto.getCoverages().size() * 25 + 40);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_COVERAGES, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_LIMITS, ReceiptPdfConstants.COL_AGE_X, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_PREMIUM, ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
		ctx.moveDown(30);

		for (ReceiptCoverageResponseDto coverage : dto.getCoverages()) {
			ctx.ensureSpace(30);
			PdfHelper.text(ctx.cs(), coverage.getCoverageName(), leftX, ctx.getY());
			PdfHelper.text(ctx.cs(), coverage.getCoverageLimit(), ReceiptPdfConstants.COL_AGE_X, ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + coverage.getPremium(),
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
			ctx.moveDown(25);
		}

		ctx.moveDown(10);
		ctx.ensureSpace(30);
		PdfHelper.line(ctx.cs(), ReceiptPdfConstants.LINE_START_X, ctx.getY(), ReceiptPdfConstants.LINE_END_X,
				ctx.getY());
		ctx.moveDown(25);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_TOTAL_BASE_PRICE, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + totalCoveragePrice,
				ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
		ctx.moveDown(40);
	}

	private void drawAddOnsTable(PageContext ctx, ReceiptResponseDto dto, BigDecimal totalAddOnPrice) throws Exception {
		if (dto.getAddOns() == null || dto.getAddOns().isEmpty()) {
			return;
		}

		float leftX = ReceiptPdfConstants.LEFT_X;

		ctx.ensureSpace(60 + dto.getAddOns().size() * 25 + 40);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_ADD_ONS, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_PREMIUM, ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
		ctx.moveDown(30);

		for (ReceiptAddOnResponseDto addOn : dto.getAddOns()) {
			ctx.ensureSpace(30);
			PdfHelper.text(ctx.cs(), addOn.getAddOnName(), leftX, ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + addOn.getPremium(),
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
			ctx.moveDown(25);
		}

		ctx.moveDown(10);
		ctx.ensureSpace(30);
		PdfHelper.line(ctx.cs(), ReceiptPdfConstants.LINE_START_X, ctx.getY(), ReceiptPdfConstants.LINE_END_X,
				ctx.getY());
		ctx.moveDown(25);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_TOTAL_ADD_ON_PRICE, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + totalAddOnPrice,
				ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
		ctx.moveDown(40);
	}

	private void drawFactorsTable(PageContext ctx, ReceiptResponseDto dto, BigDecimal planPremium,
	                              BigDecimal carAgePremium, BigDecimal motorcycleAgePremium, BigDecimal carVariantPremium,
	                              BigDecimal motorcycleVariantPremium) throws Exception {

		float leftX = ReceiptPdfConstants.LEFT_X;

		ctx.ensureSpace(ReceiptPdfConstants.SPACE_REQUIRED_TABLE);

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_PLAN, leftX, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_FACTORS, ReceiptPdfConstants.COL_FACTORS_X, ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.COL_PREMIUM, ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

		ctx.moveDown(ReceiptPdfConstants.Y_MOVE_HEADER_DOWN);

		PdfHelper.text(ctx.cs(), dto.getPlanName(), leftX, ctx.getY());
		PdfHelper.text(ctx.cs(), String.valueOf(dto.getPlanFactor()), ReceiptPdfConstants.COL_FACTORS_VALUE_X,
				ctx.getY());
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + planPremium,
				ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

		ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);

		if (dto.getCarAge() != null) {
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_CAR_AGE, leftX, ctx.getY());
			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_SUB_LABEL_DOWN);

			PdfHelper.text(ctx.cs(), dto.getCarAge(), leftX + ReceiptPdfConstants.X_OFFSET_SUB_TEXT, ctx.getY());
			PdfHelper.text(ctx.cs(), dto.getCarVehicleAgeFactor(), ReceiptPdfConstants.COL_FACTORS_VALUE_X, ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + carAgePremium,
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);
		}

		if (dto.getMotorcycleAge() != null) {
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_MOTORCYCLE_AGE, leftX, ctx.getY());
			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_SUB_LABEL_DOWN);

			PdfHelper.text(ctx.cs(), dto.getMotorcycleAge(), leftX + ReceiptPdfConstants.X_OFFSET_SUB_TEXT, ctx.getY());
			PdfHelper.text(ctx.cs(), dto.getMotorcycleVehicleAgeFactor(), ReceiptPdfConstants.COL_FACTORS_VALUE_X,
					ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + motorcycleAgePremium,
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);
		}

		if (dto.getCarVariant() != null) {
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_CAR_VARIANT, leftX, ctx.getY());
			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_SUB_LABEL_DOWN);

			PdfHelper.text(ctx.cs(), dto.getCarVariant(), leftX + ReceiptPdfConstants.X_OFFSET_SUB_TEXT, ctx.getY());
			PdfHelper.text(ctx.cs(), String.valueOf(dto.getCarVariantFactor()), ReceiptPdfConstants.COL_FACTORS_VALUE_X,
					ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + carVariantPremium,
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);
		}

		if (dto.getMotorcycleVariant() != null) {
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_MOTORCYCLE_VARIANT, leftX, ctx.getY());
			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_SUB_LABEL_DOWN);

			PdfHelper.text(ctx.cs(), dto.getMotorcycleVariant(), leftX + ReceiptPdfConstants.X_OFFSET_SUB_TEXT,
					ctx.getY());
			PdfHelper.text(ctx.cs(), String.valueOf(dto.getMotorcycleVariantFactor()),
					ReceiptPdfConstants.COL_FACTORS_VALUE_X, ctx.getY());
			PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + motorcycleVariantPremium,
					ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());

			ctx.moveDown(ReceiptPdfConstants.Y_MOVE_ROW_DOWN);
		}
	}

	private void drawTotalPremiumPaid(PageContext ctx, ReceiptResponseDto dto, BigDecimal totalPremium)
			throws Exception {
		ctx.ensureSpace(50);
		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.LABEL_TOTAL_PREMIUM_PAID, ReceiptPdfConstants.LEFT_X,
				ctx.getY());

		PdfHelper.italic(ctx.cs(),
				ReceiptPdfConstants.FORMULA_PREFIX + dto.getPlanFactor() + ReceiptPdfConstants.FORMULA_MIDDLE
						+ dto.getCarVariantFactor() + ReceiptPdfConstants.FORMULA_MIDDLE + dto.getMotorcycleVariantFactor()
						+ ReceiptPdfConstants.FORMULA_SUFFIX,
				ReceiptPdfConstants.FORMULA_X, ctx.getY());

		PdfHelper.boldText(ctx.cs(), ReceiptPdfConstants.CURRENCY_PREFIX + totalPremium,
				ReceiptPdfConstants.COL_PREMIUM_X, ctx.getY());
		ctx.moveDown(40);
	}

	private void drawFooter(PageContext ctx) throws Exception {
		float leftX = ReceiptPdfConstants.LEFT_X;

		ctx.ensureSpace(80);
		PdfHelper.italic(ctx.cs(), ReceiptPdfConstants.FOOTER_LINE_1, leftX, ctx.getY());
		ctx.moveDown(18);
		PdfHelper.italic(ctx.cs(), ReceiptPdfConstants.FOOTER_LINE_2, leftX, ctx.getY());
		ctx.moveDown(18);
		PdfHelper.italic(ctx.cs(), ReceiptPdfConstants.FOOTER_LINE_3, leftX, ctx.getY());
		ctx.moveDown(30);
		PdfHelper.text(ctx.cs(), ReceiptPdfConstants.FOOTER_CONTACT, leftX, ctx.getY());
	}

}
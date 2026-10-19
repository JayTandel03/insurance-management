package com.onerivet.util;

public final class ReceiptPdfConstants {

	private ReceiptPdfConstants() {
	}

	public static final String LOGO_RESOURCE_PATH = "/images/insuredge-logo.png";

	public static final String LOGO_RESOURCE_NAME = "company-logo";

	public static final float LOGO_WIDTH = 80f;

	public static final float LOGO_HEIGHT = 40f;

	public static final float PAGE_TOP = 780f;

	public static final float PAGE_BOTTOM = 50f;

	public static final float LEFT_X = 60f;

	public static final float RIGHT_X = 250f;

	public static final float COL_AGE_X = 300f;

	public static final float COL_GENDER_X = 390f;

	public static final float COL_VEHICLES_X = 500f;

	public static final float COL_FACTORS_X = 300f;

	public static final float COL_FACTORS_VALUE_X = 320f;

	public static final float COL_PREMIUM_X = 480f;

	public static final float LINE_START_X = 50f;

	public static final float LINE_END_X = 550f;

	public static final float TITLE_X = 180f;

	public static final float FORMULA_X = 220f;

	public static final String TITLE_PAYMENT_INVOICE = "PAYMENT INVOICE";

	public static final String HEADING_COMPANY_LOGO = "Company Logo";

	public static final String HEADING_INSURE_EDGE = "InsureEdge";

	public static final String HEADING_COVERAGE_PROVIDED_BY = "Coverage provided by:";

	public static final String HEADING_COMPANY_ADDRESS = "100 Insure Edge place, LA 16504";

	public static final String LABEL_INVOICE_NUMBER = "Invoice Number: ";

	public static final String LABEL_INVOICE_DATE = "Invoice Date: ";

	public static final String LABEL_PAYMENT_DATE = "Payment Date: ";

	public static final String LABEL_DUE_DATE = "Due Date: ";

	public static final String LABEL_PAYMENT_METHOD = "Payment Method: ";

	public static final String LABEL_TRANSACTION_ID = "Transaction ID: ";

	public static final String HEADING_NAME_INSURED = "Name Insured";

	public static final String HEADING_POLICY_NUMBER = "Policy Number";

	public static final String HEADING_POLICY_VALID = "Policy valid";

	public static final String LABEL_POLICY_VALID_TO = " to ";

	public static final String HEADING_TOTAL_PREMIUM_PAID = "Total Annual Policy Premium: $";

	public static final String NOTE_NOT_A_BILL = "(This is not a bill. Your invoice will follow in a separate mailing.)";

	public static final String HEADING_VEHICLES_COVERED = "Vehicles Covered:";

	public static final String HEADING_VIN = "VIN";

	public static final String HEADING_DRIVER_RATING = "Driver Rating Information:";

	public static final String COL_DRIVERS_INCLUDED = "Drivers Included";

	public static final String COL_AGE = "Age";

	public static final String COL_GENDER = "Gender";

	public static final String COL_VEHICLES = "Vehicles";

	public static final String DISCLAIMER_LINE_1 = "Refer to your policy and endorsements for terms, definitions, limitations,";

	public static final String DISCLAIMER_LINE_2 = "reductions, exclusions and conditions.";

	public static final String COL_COVERAGES = "Coverages";

	public static final String COL_LIMITS = "Limits";

	public static final String COL_PREMIUM = "Premium";

	public static final String LABEL_TOTAL_BASE_PRICE = "Total Base Price:";

	public static final String CURRENCY_PREFIX = "$";

	public static final String COL_ADD_ONS = "Add-Ons";

	public static final String LABEL_TOTAL_ADD_ON_PRICE = "Total Add-On Price:";

	public static final String COL_PLAN = "Plan";

	public static final String COL_FACTORS = "Factors";

	public static final String LABEL_TOTAL_PREMIUM_PAID = "Total Premium Paid:";

	public static final String FORMULA_PREFIX = "Base fare x ";

	public static final String FORMULA_MIDDLE = " x 1.3 x ";

	public static final String FORMULA_SUFFIX = " = ";

	public static final String FOOTER_LINE_1 = "Thank you for your payment. This receipt acknowledges that we have received your";

	public static final String FOOTER_LINE_2 = "payment in full. This receipt is not a guarantee of coverage. Coverage is";

	public static final String FOOTER_LINE_3 = "provided only by the terms of your policy.";

	public static final String FOOTER_CONTACT = "Questions? visit www.insuredge.com";

	public static final String WARN_LOGO_NOT_FOUND = "[ReceiptPdfGenerator] WARNING: Logo not found at classpath:";

	public static final String WARN_LOGO_SKIP = ". PDF will be generated without it.";

	public static final String PDF_ATTACHMENT = "attachment; filename=receipt.pdf";

	public static final String YEARS = " Years";

	public static final String VIN = "vin";

	public static final String VEHICLE_AGE_0_TO_3 = "0 to less than 3 years";

	public static final String VEHICLE_AGE_3_TO_5 = "3 to less than 5 years";

	public static final String VEHICLE_AGE_5_TO_10 = "5 to less than 10 years";

	public static final String VEHICLE_AGE_10_PLUS = "10+ years";

	public static final String RECEIPT_NOT_AVAILABLE_PAYMENT_PENDING = "Receipt cannot be generated because payment has not been completed";

	public static final String LABEL_CAR_AGE = "Car Age";

	public static final String LABEL_MOTORCYCLE_AGE = "Motorcycle Age";

	public static final String LABEL_CAR_VARIANT = "Car Variant";

	public static final String LABEL_MOTORCYCLE_VARIANT = "Motorcycle Variant";

	public static final float SPACE_REQUIRED_TABLE = 220f;

	public static final float Y_MOVE_HEADER_DOWN = 30f;

	public static final float Y_MOVE_SUB_LABEL_DOWN = 22f;

	public static final float X_OFFSET_SUB_TEXT = 20f;

	public static final String PREFIX_CAR = "Car : ";

	public static final String PREFIX_MOTORCYCLE = "Motorcycle : ";

	public static final float SPACE_REQUIRED_VEHICLE = 80f;

	public static final float Y_MOVE_HEADING_DOWN = 30f;

	public static final float Y_MOVE_ROW_DOWN = 20f;

	public static final float Y_MOVE_SECTION_END_DOWN = 35f;

	public static final int VEHICLE_TYPE_CAR = 1;

	public static final int VEHICLE_TYPE_MOTORCYCLE = 2;

	public static final String QUOTE_NOT_FOUND_IN_PAYMENT = "Quote Not Found in Payment.";

}
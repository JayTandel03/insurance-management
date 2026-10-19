package com.onerivet.util;

import java.util.List;

public final class Constants {

	private Constants() {
		throw new IllegalStateException(Constants.UTILITY_CLASS);
	}

	public static final String HAS_ADMIN = "hasRole('ADMIN')";

	public static final String HAS_USER = "hasRole('USER')";

	public static final String HAS_ANY_ROLE = "hasAnyRole('ADMIN', 'USER')";

	public static final long CLOCK_SKEW_SECONDS = 60L;

	public static final List<String> PUBLIC_URLS = List.of("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
			"/actuator/**", "/error/**");

	public static final String ROLE_PREFIX = "ROLE_";

	public static final String ROLES_CLAIM = "roles";

	public static final String PERMISSIONS_CLAIM = "permissions";

	public static final String OAUTH_ISSUER_URI = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}";

	public static final String OAUTH_AUDIENCE_URI = "${spring.security.oauth2.resourceserver.jwt.audience}";

	public static final String ADMIN_RESPONSE = "Only Admin can access.";

	public static final String USER_RESPONSE = "User endpoint accessed successfully.";

	public static final String ALL = "*";

	public static final List<String> ALLOWED_METHODS = List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");

	public static final List<String> ALLOWED_HEADERS = List.of("Authorization", "Content-Type", "Accept",
			"X-Requested-With");

	public static final long MAX_AGE_SECS = 3600L;

	public static final String ALL_PATHS = "/**";

	public static final int STATUS_OK = 200;

	public static final String USER_SCHEMA = "[User]";

	public static final String REF_SCHEMA = "[Ref]";

	public static final String TEMPLATE_SCHEMA = "[Template]";

	public static final String COVERAGE_SCHEMA = "[Coverage]";

	public static final String UNKNOWN = "Unknown";

	public static final String TEMPLATE_DELETED = "Template deleted successfully.";

	public static final String DRAFT_STATUS = "Draft";

	public static final String PUBLISHED_STATUS = "Published";

	public static final String TEMPLATE_ID_PREFIX = "TEMP";

	public static final String TEMPLATE_PLAN_ID_PREFIX = "TPLAN";

	public static final String TEMPLATE_COVERAGE_ID_PREFIX = "TCOV";

	public static final String EMAIL_CLAIM = "email";

	public static final String USER_ID_CLAIM = "sub";

	public static final String TEMPLATE_DRAFT_CREATED = "Template Draft Created.";

	public static final String TEMPLATE_PUBLISHED = "Template published successfully.";

	public static final Integer LIABILITY_COVERAGE_ID = 1;

	public static final Integer COLLISION_COVERAGE_ID = 2;

	public static final Integer COMPREHENSIVE_COVERAGE_ID = 3;

	public static final String COMPREHENSIVE = "Comprehensive";

	public static final String COMPREHENSIVE_COVERAGE = "Comprehensive Coverage";

	public static final String TEMPLATE_PUBLISHED_SUCCESSFULLY = "Template published successfully.";

	public static final String NAME = "NAME_";

	public static final String UTILITY_CLASS = "Utility class";

	public static final String VEHICLE_TYPE_COVERAGE_PRICE_ID_PREFIX = "VTCP";

	public static final String TEMPLATE_COVERAGE_FIELD_PRICE_ID_PREFIX = "TCFP";

	public static final String TEMPLATE_ADD_ON_PRICE_ID_PREFIX = "TAOP";

	public static final String BLANK = "";

	public static final String TEMPLATE_DRAFT_UPDATED = "Template draft updated successfully.";

	public static final String HYPHEN = "-";

	public static final String BI_PER_PERSON = "BI (Per Person)";

	public static final String BI_PER_ACCIDENT = "BI (Per Accident)";

	public static final String COMBINED_SINGLE_LIMIT = "Combined Single Limit";

	public static final String QUESTION_SCHEMA = "[Question]";

	public static final String QUESTION_CREATED = "Question created successfully.";

	public static final String QUESTION_UPDATED = "Question updated successfully.";

	public static final String QUESTION_NO_CHANGES = "No changes detected for question.";

	public static final String QUESTION_MOVED_TO_DRAFT = "Question moved to draft successfully.";

	public static final String QUESTION_PUBLISHED = "Question published successfully.";

	public static final String STATUS_CREATED = "Created";

	public static final String STATUS_DRAFT = "Draft";

	public static final String STATUS_PUBLISH = "Published";

	public static final String CREATED = "Created";

	public static final String PUBLISHED = "PUBLISHED";

	public static final String DRAFT = "DRAFT";

	public static final Integer RADIO_INPUT_TYPE = 1;

	public static final Integer DROPDOWN_INPUT_TYPE = 2;

	public static final Integer CHECKBOX_INPUT_TYPE = 6;

	public static final Integer VEHICLE_QUESTION_TYPE = 1;

	public static final Integer PERSONAL_QUESTION_TYPE = 2;

	public static final Integer CAR = 1;

	public static final Integer MOTORCYCLE = 2;

	public static final Integer BOTH = 3;

	public static final Integer TEXT_FIELD_INPUT_TYPE = 3;

	public static final String PAYMENT_SUCCESS_MESSAGE = "Payment successful.";

	public static final String CARD_NUMBER_REGEX = "^[0-9]{16}$";

	public static final String EXPIRY_DATE_REGEX = "^(0[1-9]|1[0-2])\\/\\d{2}$";

	public static final String CVV_REGEX = "^[0-9]{3}$";

	public static final Integer QUOTE_STATUS_INPROGRESS = 3;

	public static final Integer QUOTE_STATUS_COMPLETE = 4;

	public static final String QUESTION_DELETED = "Question deleted successfully.";

	public static final String TRANSACTION_ID_PREFIX_STRING = "TXN-";

	public static final String UNDERSCORE = "_";

	public static final String SPACE = " ";

	public static final String QUESTION_REQUIRED = "Question is required.";

	public static final String INVALID_VEHICLE_TYPE = "Invalid vehicle type id.";

	public static final String OPTION_LABEL_REQUIRED = "Option label is required.";

	public static final String INVALID_QUESTION_TYPE = "Invalid question type id.";

	public static final String QUESTION_TYPE_NOT_FOUND = "Question type not found.";

	public static final String INVALID_OPTION_ID = "Invalid option id.";

	public static final String QUESTION_TYPE_REQUIRED = "Question type is required.";

	public static final String MANDATORY_FIELD_REQUIRED = "Mandatory field is required.";

	public static final String VEHICLE_TYPE_REQUIRED = "Vehicle type is required for vehicle questions.";

	public static final String VEHICLE_TYPE_NOT_REQUIRED = "Vehicle type is not required for personal questions.";

	public static final String INVALID_VEHICLE_TYPE_ID = "Only vehicle type id 1 and 2 are allowed.";

	public static final String OPTIONS_REQUIRED = "Options required for selected input type.";

	public static final String OPTIONS_NOT_ALLOWED = "Options are not allowed for selected input type.";

	public static final String MAX_OPTIONS_ALLOWED = "Maximum 10 options allowed.";

	public static final String OPTION_REQUIRED = "Option required.";

	public static final String ONLY_CREATED_CAN_EDIT = "Only created questions can be edited.";

	public static final String ONLY_CREATED_CAN_CHANGE_STATUS = "Only created questions can be published or moved to draft.";

	public static final String PUBLISHED_QUESTION_DELETE = "Published question cannot be deleted.";

	public static final String DUPLICATE_QUESTION_FOUND = "Duplicate question found.";

	public static final String INVALID_STATUS = "Use PUBLISH or DRAFT.";

	public static final String INVALID_STATUS_ID = "Only status id 3 and 4 are allowed.";

	public static final String QUOTE_NOT_FOUND = "Quote not found";

	public static final String LIABILITY_COVERAGE = "Liability Coverage";

	public static final String ID_ = "ID_";

	public static final String QUOTE_SCHEMA = "[Quote]";

	public static final String TEMPLATE_NOT_FOUND = "Template not found.";

	public static final String COVERAGE_NOT_FOUND = "Coverage not found";

	public static final String QUESTION_ALREADY_PUBLISHED = "Question is already published.";

	public static final String INVOICE_ID_PREFIX_STRING = "INV-";

	public static final String QUOTE_SAVED_SUCCESSFULLY = "Quote saved successfully";

	public static final String QUOTE_UPDATED_SUCCESSFULLY = "Quote updated successfully";

	public static final String QUOTE_SUBMITTED_SUCCESSFULLY = "Quote submitted successfully";

	public static final String QUOTE_DRAFT_SAVED_SUCCESSFULLY = "Quote draft saved successfully. You can continue the application later.";

	public static final String QUOTE_DRAFT_UPDATED_SUCCESSFULLY = "Quote draft updated successfully. You can continue the application later.";

	public static final Integer STATUSID_DEFAULT = 3;

	public static final Integer STATUSID_CREATED = 4;

	public static final Integer MIN_YEAR = 2000;

	public static final Integer VEHICLE_TYPE_CAR = 1;

	public static final Integer VEHICLE_TYPE_MOTORBIKE = 2;

	public static final Integer ROADSIDE_ASSISTANCE_ADD_ON_ID = 1;

	public static final String QUOTE_PREFIX = "QTE-";

	public static final Integer STATUSID_DRAFT = 1;

	public static final Integer STATUSID_PUBLISHED = 2;

	public static final String OTHER = "other";

	public static final String VIN = "vin";

	public static final String FIRST_NAME = "first name";

	public static final String LAST_NAME = "last name";

	public static final String EMAIL = "email";

	public static final String PHONE = "phone";

	public static final String VIN_REGEX = "^[A-Za-z0-9]{17}$";

	public static final String NAME_REGEX = "^[A-Za-z ]{3,50}$";

	public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	public static final String PHONE_REGEX = "^[0-9]{10}$";

	public static final Integer BASIC_PLAN_ID = 1;

	public static final Integer STANDARD_PLAN_ID = 2;

	public static final Integer ADVANCED_PLAN_ID = 3;

	public static final String EMPTY_STRING = "";

	public static final String DOB = "dob";

	public static final String DATE_OF_BIRTH = "date of birth";

	public static final String DOB_REGEX = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

	public static final String REGISTRATION_YEAR = "registration year";

	public static final String VEHICLE_VARIANT = "vehicle variant";

	public static final String VEHICLE_TYPE = "vehicle type";

	public static final String COLON_SPACE = ": ";

	public static final String COVERAGE_ID_LABEL = "coverageId: ";

	public static final String OPTION_ID_LABEL = "optionId: ";

	public static final String QUESTION_ID_LABEL = " for questionId: ";

	public static final String BIG_DECIMAL = "BigDecimal";

	public static final String INTEGER = "Integer";

	public static final String LONG = "Long";

	public static final String DOUBLE = "Double";

	public static final int ANSWER_TEXT_MAX_LENGTH = 100;

	public static final Integer VEHICLE_TYPE_ALL = 3;

	public static final int SINGLE_VEHICLE_REQUIRED_COUNT = 1;

	public static final int ALL_VEHICLE_REQUIRED_COUNT = 2;

	public static final Integer CAR_MANUFACTURER_QUESTION_ID = 3;

	public static final Integer CAR_MODEL_QUESTION_ID = 4;

	public static final Integer BIKE_MANUFACTURER_QUESTION_ID = 10;

	public static final Integer BIKE_MODEL_QUESTION_ID = 11;

	public static final int CAR_DETAIL_INDEX = 0;

	public static final int BIKE_DETAIL_INDEX = 1;

	public static final String CARD_HOLDER_NAME_REGEX = "^[A-Za-z]+(?:\\s[A-Za-z]+)*$";

	public static final String DATE_PATTERN = "MM/yy";

	public static final Integer INPUT_TYPE_RADIO_BUTTON = 1;

	public static final Integer INPUT_TYPE_DROPDOWN = 2;

	public static final Integer INPUT_TYPE_TEXT_FIELD = 3;

	public static final Integer INPUT_TYPE_NUMERIC_FIELD = 4;

	public static final Integer INPUT_TYPE_TEXT_AREA = 5;

	public static final Integer INPUT_TYPE_CHECKBOX = 6;

	public static final Integer TEXT_FIELD_MIN_LENGTH = 5;

	public static final Integer TEXT_FIELD_MAX_LENGTH = 100;

	public static final Integer NUMERIC_FIELD_MIN_DIGITS = 1;

	public static final Integer NUMERIC_FIELD_MAX_DIGITS = 20;

	public static final Integer TEXT_AREA_MIN_LENGTH = 10;

	public static final Integer TEXT_AREA_MAX_LENGTH = 250;

	public static final String NUMERIC_FIELD_REGEX = "^[0-9]{1,20}$";

	public static final int OTHER_TEXT_MIN_LENGTH = 3;

	public static final int OTHER_TEXT_MAX_LENGTH = 30;
	
	public static final String OTHER_TEXT_REGEX = "^[a-zA-Z0-9\\- ]{3,30}$";
	
	public static final String OTHER_OPTION = "other";
	 
	public static final String  BEARER_AUTHENTICATION   = "Bearer Authentication"; 
	
	public static final String BEARER ="Bearer";

	public static final String JWT = "JWT";
	
	public static final Integer DATE_PICKER_INPUT_TYPE_ID = 7;
}
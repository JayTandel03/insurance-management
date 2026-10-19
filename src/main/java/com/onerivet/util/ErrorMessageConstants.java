package com.onerivet.util;

public class ErrorMessageConstants {

	private ErrorMessageConstants() {
		throw new IllegalStateException(Constants.UTILITY_CLASS);
	}

	public static final String UNAUTHORIZED_MESSAGE = "Unauthorized - Invalid or missing token.";

	public static final String FORBIDDEN_MESSAGE = "Forbidden - Access denied.";

	public static final String BAD_REQUEST = "Bad Request.";

	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error.";

	public static final String NOT_FOUND = "Not Found.";

	public static final String INVALID_VEHICLE_TYPE_MESSAGE = "Invalid vehicle type ID.";

	public static final String ADDON_ONLY_FOR_COMPREHENSIVE = "Add-ons are only available for Comprehensive coverage.";

	public static final String COVERAGE_NOT_FOUND = "Coverage not found.";

	public static final String PLANS_NOT_FOUND = "Plan not found.";

	public static final String VEHICLETYPE_NOT_FOUND = "No vehicle types found.";

	public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An unexpected error occurred. Please contact the system administrator.";

	public static final String API_NOT_FOUND = "API Not Found - Invalid URL.";

	public static final String VALIDATION_FAILED = "Validation failed.";

	public static final String METHOD_NOT_ALLOWED = "Method not allowed. Supported: ";

	public static final String INVALID_COVERAGE_ID = "Invalid Coverage ID.";

	public static final String ADDONS_NOT_FOUND = "No add-ons found for coverageId %d and vehicleTypeId %d";

	public static final String PARAMETER = "Parameter '";

	public static final String INVALID_TYPE = "' should be of type ";

	public static final String VEHICLE_TYPE_ID_REQUIRED = "vehicle type ID is required.";

	public static final String IS_REQUIRED = "' is required.";

	public static final String INVALID_PARAMETER = "Invalid Parameter.";

	public static final String ADDON_FETCH_FAILED = "failed to fetch add-ons.";

	public static final String TEMPLATE_NOT_FOUND = "Template not found.";

	public static final String ADDONS_NOT_SUPPORTED = "Add-ons not supported for this coverage.";

	public static final String DRAFT_DELETE_ONLY = "Only draft templates can be deleted.";

	public static final String INVALID_TEMPLATE_ID = "Invalid Template ID.";

	public static final String VEHICLE_TYPE_ID_IS_REQUIRED = "Vehicle type id is required.";

	public static final String PLAN_ID_IS_REQUIRED = "Plan id is required.";

	public static final String PLAN_NOT_FOUND = "Plan not found.";

	public static final String TEMPLATE_ALREADY_EXISTS = "Template with this name already exists.";

	public static final String STANDARD_COVERAGE_ID_REQUIRED = "Coverage id is required for standard coverage.";

	public static final String COVERAGE_NAME_REQUIRED = "Coverage Name is Required.";

	public static final String PREDEFINED_COVERAGE_MUST_BE_STANDARD = "Predefined coverage must always be isStandard = true.";

	public static final String COVERAGE_TYPE_NOT_FOUND = "Coverage type not found.";

	public static final String COVERAGE_TYPE_NAME_REQUIRED = "Coverage Type Name is Required.";

	public static final String COMPONENT_NOT_FOUND = "Component not found.";

	public static final String COMPONENT_NAME_REQUIRED = "Component Name is Required.";

	public static final String COVERAGE_FIELD_NOT_FOUND = "Coverage field not found.";

	public static final String FIELD_NAME_REQUIRED = "Field Name is Required.";

	public static final String ADDON_NOT_FOUND = "Add-on not found.";

	public static final String ADDON_NAME_REQUIRED = "Add-on Name is Required.";

	public static final String ADDON_ID_OR_NAME_REQUIRED = "Add-on id or name is required.";

	public static final String COVERAGE_TYPE_CATALOG_NOT_FOUND = "Coverage type catalog not found.";

	public static final String PRICE_MUST_BE_GREATER_THAN_ZERO = "Price must be greater than 0.";

	public static final String USER_NOT_AUTHENTICATED = "User not authenticated.";

	public static final String EMAIL_NOT_FOUND_IN_TOKEN = "Email not found in token.";

	public static final String USER_NOT_FOUND = "User not found.";

	public static final String USER_DETAILS_NOT_FOUND_IN_TOKEN = "User details not found in token.";

	public static final String PUBLISHED_STATUS_NOT_FOUND = "Published status not found.";

	public static final String TEMPLATE_ALREADY_PUBLISHED = "Template is already published.";

	public static final String FAILED_TO_PUBLISH_TEMPLATE = "Failed to publish the Template.";

	public static final String TEMPLATE_PLAN_REQUIRED = "Template must have at least one plan before publishing.";

	public static final String TEMPLATE_ID_REQUIRED = "Template id is required.";

	public static final String ONLY_DRAFT_TEMPLATE_CAN_BE_PUBLISHED = "Only draft template can be published.";

	public static final String TEMPLATE_MUST_HAVE_THREE_PLANS = "Template must have all 3 plans before publish.";

	public static final String PLAN_MUST_HAVE_THREE_STANDARD_COVERAGES = "Each plan must have Liability, Collision and Comprehensive coverage before publish.";

	public static final String COVERAGE_PRICE_REQUIRED = "Coverage price is required before publish.";

	public static final String COVERAGE_TYPE_REQUIRED = "Coverage type is required before publish.";

	public static final String FIELD_PRICE_REQUIRED = "Coverage field price is required before publish.";

	public static final String ADDON_PRICE_REQUIRED = "Add-on price is required before publish.";

	public static final String DUPLICATE_CUSTOM_COVERAGE = "Duplicate custom coverage name found.";

	public static final String PUBLISHED_TEMPLATE_CANNOT_BE_EDITED = "Published template cannot be edited.";

	public static final String DELETED_TEMPLATE_CANNOT_BE_PUBLISHED = "Deleted template cannot be published.";

	public static final String NO_PLANS_CONFIGURED = "No plans configured.";

	public static final String ALL_PLANS_MUST_BE_CONFIGURED = "All plans must be configured before publish.";

	public static final String ALL_MANDATORY_COVERAGES_REQUIRED = "All mandatory coverages must be configured before publish.";

	public static final String INVALID_REQUEST_BODY = "Invalid request body. Please provide valid JSON values.";

	public static final String ALL_FIELD_PRICING_MUST_CONFIGURE = "All field pricing must be configured before publish.";

	public static final String COVERAGE_TYPE_ID_IS_REQUIRED = "Coverage type id is required for standard coverage.";

	public static final String COMPONENT_ID_REQUIRED = "Component id is required for standard coverage.";

	public static final String COVERAGE_FIELD_REQUIRED = "Coverage field id is required for standard coverage.";

	public static final String FIELD_BASE_PRICE_REQUIRED = "Field base price is required.";

	public static final String PREDEFINED_COVERAGE_ARE_MISSING = "Predefined coverages are missing.";

	public static final String ALL_PREDEFINED_COVERAGE_ARE_REQUIRED = "All predefined coverages are required.";

	public static final String PREDEFINED_COVERAGE_STRUCTURE_IS_MISSING = "Predefined coverage structure is missing.";

	public static final String ALL_PREDEFINED_COVERAGE_TYPE_ARE_REQUIRED = "All predefined coverage types are required.";

	public static final String PREDEFINED_COMPONENT_STRUCTURE_IS_MISSING = "Predefined component structure is missing.";

	public static final String ALL_PREDEFINED_COMPONENTS_ARE_REQUIRED = "All predefined components are required.";

	public static final String ALL_PREDEFINED_FIELD_ARE_REQUIRED = "All predefined fields are required.";

	public static final String ADDON_ID_IS_REQUIRED_FOR_STANDARD_COVERAGE = "Add-on id is required for standard coverage.";

	public static final String ADDON_PRICING_IS_REQUIRED = "Add-on pricing is required.";

	public static final String ONLY_PREDEFINED_STANDARD_COVERAGE_IS_ALLOWED = "Only predefined standard coverage is allowed.";

	public static final String DRAFT_ADDON_CANNOT_BE_REUSED_ACROSS_TEMPLATES = "Draft add-on cannot be reused across templates.";

	public static final String UNABLE_TO_VALIDATE_ADDON_REUSE = "Unable to validate add-on reuse.";

	public static final String COMPREHENSIVE_REQUIRED_FOR_ADDONS = "Add-ons can only be created when Comprehensive Coverage is selected.";

	public static final String COVERAGE_TYPES_REQUIRED_FOR_CUSTOM_COVERAGE = "Coverage types are required for custom coverage.";

	public static final String COVERAGE_TYPE_ID_OR_NAME_REQUIRED = "Coverage type id or name is required.";

	public static final String DUPLICATE_COVERAGE_TYPE_NOT_ALLOWED = "Duplicate coverage type is not allowed.";

	public static final String COMPONENTS_REQUIRED_FOR_COVERAGE_TYPE = "Components are required for coverage type.";

	public static final String COMPONENT_ID_OR_NAME_REQUIRED = "Component id or name is required.";

	public static final String DUPLICATE_COMPONENT_NOT_ALLOWED = "Duplicate component is not allowed.";

	public static final String FIELDS_REQUIRED_FOR_COMPONENT = "Fields are required for component.";

	public static final String COVERAGE_FIELD_ID_OR_NAME_REQUIRED = "Coverage field id or name is required.";

	public static final String DUPLICATE_FIELD_NOT_ALLOWED = "Duplicate field is not allowed.";

	public static final String COVERAGE_TYPE_NOT_BELONG_TO_SELECTED_COVERAGE = "Coverage type does not belong to selected coverage.";

	public static final String COMPONENT_NOT_BELONG_TO_SELECTED_COVERAGE_TYPE = "Component does not belong to selected coverage type.";

	public static final String COVERAGE_FIELD_NOT_BELONG_TO_SELECTED_COMPONENT = "Coverage field does not belong to selected component.";

	public static final String ADD_ON_NOT_BELONG_TO_SELECTED_COVERAGE = "Add-on does not belong to selected coverage.";

	public static final String IS_STANDARD_REQUIRED = "isStandard is required.";

	public static final String COVERAGE_BASE_PRICE_REQUIRED = "Coverage base price is required.";

	public static final String ONLY_PREDEFINED_STANDARD_COVERAGE_ALLOWED = "Only predefined standard coverage is allowed.";

	public static final String COVERAGE_ID_AND_NAME_DO_NOT_MATCH = "Coverage id and coverage name do not match.";

	public static final String STANDARD_COVERAGE_CANNOT_BE_USED_AS_CUSTOM_COVERAGE = "Standard coverage cannot be used as custom coverage.";

	public static final String UNABLE_TO_VALIDATE_CUSTOM_COVERAGE_REUSE = "Unable to validate custom coverage reuse.";

	public static final String DRAFT_CUSTOM_COVERAGE_CANNOT_BE_REUSED_ACROSS_TEMPLATES = "Draft custom coverage cannot be reused across templates.";

	public static final String ADD_ON_ID_AND_NAME_DO_NOT_MATCH = "Add-on id and add-on name do not match.";

	public static final String UNABLE_TO_VALIDATE_ADD_ON_REUSE = "Unable to validate add-on reuse.";

	public static final String DRAFT_ADD_ON_CANNOT_BE_REUSED_ACROSS_TEMPLATES = "Draft add-on cannot be reused across templates.";

	public static final String COVERAGE_CONFIGURATION_MISSING = "Coverage Configuration is Missing.";

	public static final String PRICE_MUST_BE_MULTIPLE_OF_FIFTY = "Values must be in multiples of 50.";

	public static final String ADD_ON_PRICE_MUST_BE_MULTIPLE_OF_FIFTY = "Add-on fare should be in multiples of 50 only.";

	public static final String BI_PER_PERSON_CANNOT_EXCEED_BI_PER_ACCIDENT = "BI Per Person cannot exceed BI Per Accident.";

	public static final String CSL_AMOUNT_REQUIRED = "Combined Single Limit is required.";

	public static final String QUESTION_ALREADY_DRAFT = "Question is already in draft status.";

	public static final String QUESTION_ALREADY_EXISTS = "Question already exists.";

	public static final String QUESTION_NOT_FOUND = "Question not found.";

	public static final String INVALID_QUESTION_ID = "Invalid question id.";

	public static final String INVALID_INPUT_TYPE = "Invalid input type.";

	public static final String INPUT_TYPE_NOT_FOUND = "InputType not found.";

	public static final String VEHICLE_TYPE_NOT_FOUND = "VehicleType not found.";

	public static final String OPTION_NOT_FOUND = "Option not found.";

	public static final String CREATED_STATUS_NOT_FOUND = "Created status not found.";

	public static final String DRAFT_STATUS_NOT_FOUND = "Draft status not found.";

	public static final String PUBLISH_STATUS_NOT_FOUND = "Publish status not found.";

	public static final String INVALID_STATUS_FOUND = "Invalid Status.";

	public static final String VEHICLE_TYPE_MISSING_MSG = "Vehicle type missing for question ID: %s";

	public static final String STATUS_MISSING_MSG = "Status missing for question ID: %s.";

	public static final String QUOTE_NOT_FOUND = "Quote Not Found.";

	public static final String STATUS_NOT_FOUND = "Status Not Found.";

	public static final String PAYMENT_ALREADY_COMPLETED = "Payment Already Compeleted for this Quote.";

	public static final String TOTAL_PREMIUM_NOT_CALCULATED = "Total Premium not calculated for this quote.";

	public static final String INVALID_PAYMENT_METHOD = "Invalid payment method.";

	public static final String SELECT_PAYMENT_METHOD = "Please select payment method.";

	public static final String CARD_NUMBER_REQUIRED = "Card number is required.";

	public static final String CARD_HOLDER_NAME_REQUIRED = "Card holder name is required.";

	public static final String PAYMENT_NOT_FOUND = "Payment not found.";

	public static final String VEHICLE_AGE_FACTOR_NOT_FOUND = "Vehicle age factor not found.";

	public static final String INVALID_CARD_NUMBER = "Card number must be 16 digits.";

	public static final String EXPIRY_DATE_REQUIRED = "Expiry date is required.";

	public static final String INVALID_EXPIRY_DATE = "Expiry date must be in MM/YY format.";

	public static final String CVV_REQUIRED = "CVV is required.";

	public static final String INVALID_CVV = "CVV must be 3 digits.";

	public static final String PREMIUM_NOT_CALCULATED = "Total premium not calculated for this quote.";

	public static final String INVALID_STATUS_ID = "Only status id 3 and 4 are allowed.";

	public static final String INVALID_STATUS = "Use PUBLISH or DRAFT.";

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

	public static final String INVALID_VEHICLE_TYPE_ID = "Only vehicle type id 1, 2 and 3 are allowed.";

	public static final String OPTIONS_REQUIRED = "Options required for selected input type.";

	public static final String OPTIONS_NOT_ALLOWED = "Options are not allowed for selected input type.";

	public static final String MAX_OPTIONS_ALLOWED = "Maximum 10 options allowed.";

	public static final String OPTION_REQUIRED = "Option required.";

	public static final String ONLY_CREATED_CAN_CHANGE_STATUS = "Only created questions can be published or moved to draft.";

	public static final String PUBLISHED_QUESTION_DELETE = "Published question cannot be deleted.";

	public static final String DUPLICATE_QUESTION_FOUND = "Duplicate question found.";

	public static final String QUESTION_ALREADY_PUBLISHED = "Question is already published.";

	public static final String INVALID_VEHICLE_AGE = "Invalid vehicle age.";

	public static final String QUOTE_STATUS_NOT_FOUND = "Quote status not found.";

	public static final String ONLY_CREATED_CAN_EDIT = "Only created questions can be edited.";

	public static final String INVALID_QUOTE_STATUS = "Invalid quote status.";

	public static final String PREDEFINED_COMPONENT_CANNOT_USED = "Predefined component cannot be used in custom coverage.";

	public static final String PREDEFINED_COVERAGE_TYPE_CANNOT_USED = "Predefined Coverage Type cannot be used in custom coverage.";

	public static final String MAXIMUM_7_COMPONENTS_ALLOWED = "Maximum 7 components are allowed.";

	public static final String AT_LEAST_ONE_COMPONENT_REQUIRED = "Admin should create at least one component for custom coverage.";

	public static final String COMPONENT_ALREADY_EXISTS = "Component with this name already exists.";

	public static final String FIELD_ALREADY_EXISTS = "Field with this name already exists.";

	public static final String MAXIMUM_5_CUSTOM_COVERAGES_ALLOWED = "Maximum 5 custom coverages allowed in one template.";

	public static final String INVALID_NUMERIC_VALUE = "Please enter a valid numeric value.";

	public static final String PREDEFINED_FIELD_CANNOT_USED_IN_CUSTOM_COVERAGE = "Predefined field cannot be used in custom coverage.";

	public static final String PREDEFINED_ADD_ON_CANNOT_USED_IN_CUSTOM_COVERAGE = "Predefined add-on cannot be used in custom coverage.";

	public static final String DRAFT_CUSTOM_COMPONENT_CANNOT_REUSED = "Draft custom component cannot be reused across templates.";

	public static final String DRAFT_CUSTOM_FIELD_CANNOT_REUSED = "Draft custom field cannot be reused across templates.";

	public static final String DRAFT_CUSTOM_ADD_ON_CANNOT_REUSED = "Draft custom add-on cannot be reused across templates.";

	public static final String MAXIMUM_5_COVERAGE_TYPES_ALLOWED = "Maximum 5 coverage subtypes allowed.";

	public static final String MINIMUM_1_COMPONENT_REQUIRED = "At least one component is required for custom coverage subtype.";

	public static final String MINIMUM_1_COVERAGE_TYPE_REQUIRED = "At least one coverage subtype is required for custom coverage.";

	public static final String INVALID_COVERAGE_FOR_ADD_ON = "Invalid coverage for add-on validation.";

	public static final String VEHICLE_VARIANT_NOT_FOUND = "Vehicle variant not found";

	public static final String COVERAGE_SUBTYPE_NOT_FOUND = "Coverage sub-type not found";

	public static final String ADD_ON_NOT_FOUND = "Add-on not found";

	public static final String VEHICLE_YEAR_REQUIRED = "Vehicle registration year is required";

	public static final String VEHICLE_VARIANT_REQUIRED = "Vehicle variant is required";

	public static final String PLAN_REQUIRED = "Plan is required";

	public static final String ANSWER_REQUIRED = "This field is required";

	public static final String OPTION_NOT_VALID_FOR_QUESTION = "Selected option is not valid for this question";

	public static final String MODEL_NOT_VALID_FOR_MANUFACTURER = "Selected model is not valid for selected manufacturer";

	public static final String OTHER_VALUE_REQUIRED = "Please enter value for Other option";

	public static final String OTHER_VALUE_INVALID = "Other value must be 3 to 30 characters and only hyphen is allowed";

	public static final String INVALID_YEAR = "Vehicle registration year must be between configured minimum year and current year";

	public static final String INVALID_NAME = "Name must contain only alphabets and must be 3 to 50 characters";

	public static final String COVERAGE_REQUIRED = "Please select coverage";

	public static final String COVERAGE_TYPE_NOT_MAPPED = "Coverage sub-type is not mapped with selected coverage";

	public static final String ADD_ON_NOT_AVAILABLE = "Add-on is not available for selected coverage";

	public static final String ADD_ON_NOT_ALLOWED_FOR_MOTORBIKE = "Only Roadside Assistance add-on is allowed for motorbike";

	public static final String TEMPLATE_REQUIRED = "Template is required";

	public static final String TEMPLATE_NOT_PUBLISHED = "Only published template can be selected";

	public static final String USER_REQUIRED = "User is required";

	public static final String INVALID_COVERAGE_TYPE_ID = "Invalid coverage type id";

	public static final String INVALID_ADD_ON_ID = "Invalid add-on id";

	public static final String INVALID_BASIC_PLAN_COVERAGE = "Basic plan requires exactly 1 standard coverage. Custom coverage is optional.";

	public static final String INVALID_STANDARD_PLAN_COVERAGE = "Standard plan requires exactly 2 standard coverages. Custom coverage is optional.";

	public static final String INVALID_ADVANCED_PLAN_COVERAGE = "Advanced plan requires exactly 3 standard coverages. Custom coverage is optional.";

	public static final String ALL_ACTIVE_QUESTIONS_REQUIRED = "Please answer all active questions before final submit";

	public static final String TEMPLATE_NOT_VALID_FOR_VEHICLE_TYPE = "Template is not valid for selected vehicle type";

	public static final String COVERAGE_PRICE_NOT_FOUND = "Coverage price not found";

	public static final String ADD_ON_PRICE_NOT_FOUND = "Add On price Not Found";

	public static final String QUOTE_ALREADY_EXISTS = "Quote already exists";

	public static final String VEHICLE_REGISTRATION_YEAR_REQUIRED = "Vehicle registration year is required";

	public static final String VEHICLE_VARIANT_NOT_VALID_FOR_TYPE = "Vehicle variant is not valid for selected vehicle type";

	public static final String VEHICLE_TYPE_UPDATE_NOT_ALLOWED = "Vehicle type cannot be updated after quote creation";

	public static final String VEHICLE_REGISTRATION_YEAR_UPDATE_NOT_ALLOWED = "Vehicle registration year cannot be updated after quote creation";

	public static final String VEHICLE_VARIANT_UPDATE_NOT_ALLOWED = "Vehicle variant cannot be updated after quote creation";

	public static final String INVALID_DOB = "Invalid date of birth. Date format must be yyyy-MM-dd";

	public static final String VEHICLE_VARIANT_TEXT_REQUIRED = "Vehicle variant text is required when Other option is selected";

	public static final String VEHICLE_VARIANT_TEXT_INVALID = "Vehicle variant text is invalid";

	public static final String DUPLICATE_QUESTION = "Duplicate questionId found";

	public static final String DUPLICATE_COVERAGE = "Duplicate coverageId found.";

	public static final String DUPLICATE_COVERAGE_TYPE = "Duplicate coverageTypeId found.";

	public static final String DUPLICATE_ADD_ON = "Duplicate addOnId found.";

	public static final String QUESTION_NOT_VALID_FOR_VEHICLE_TYPE = "Question is not valid for selected vehicle type.";

	public static final String PLAN_NOT_VALID_FOR_TEMPLATE = "Plan is not valid for selected template.";

	public static final String ALL_MANDATORY_QUESTIONS_REQUIRED = "Please answer all mandatory questions before submitting the quote.";

	public static final String LANDING_PAGE_QUESTION = "This question is already captured on landing page and should not be sent in answers. questionId:";

	public static final String TEMPLATE_PLAN_REQUIRED_FOR_COVERAGE = "Template and Plan are required before selecting coverage";

	public static final String QUOTE_ALREADY_SUBMITTED = "Quote is already submitted and cannot be modified";

	public static final String INVALID_VIN = "Please enter a valid VIN number";

	public static final String INVALID_EMAIL = "Please enter valid email address";

	public static final String INVALID_PHONE = "Phone number must be 10 digits";

	public static final String PUBLISHED_QUESTION_MODIFIED = "Published question cannot be Modified";

	public static final String DUPLICATE_OPTION_FOUND = "Duplicate option found";

	public static final String DUPLICATE_COMPONENT = "Duplicate componentId found.";

	public static final String DUPLICATE_FIELD = "Duplicate fieldId found.";

	public static final String DUPLICATE_ADD_ON_NOT_ALLOWED = "Duplicate Add-On is not allowed.";

	public static final String INVALID_PARENT_QUESTION_ID = "Parent Question id must be positive.";

	public static final String INVALID_MANUFACTURER_ID = "Manufacturer id must be positive.";

	public static final String NO_MANUFACTURER_FOUND = "No manufacturers found for question id: ";

	public static final String NO_MODEL_FOUND = "No models found for manufacturer id: ";

	public static final String QUESTION_ID = " and question id:";

	public static final String INVALID_REQUEST = "Invalid request.";

	public static final String INVALID_VEHICLE_TYPEID = "Vehicle type id must be positive";

	public static final String VEHICLE_VARIANTS_NOT_FOUND = "No vehicle variants found for vehicle type";

	public static final String INPUT_TYPES_NOT_FOUND = "No input types found.";

	public static final String VEHICLE_DETAILS_REQUIRED = "Vehicle details are required";

	public static final String INVALID_CAR_VEHICLE_DETAIL = "Car registration year and car variant must be valid";

	public static final String INVALID_BIKE_VEHICLE_DETAIL = "Motorcycle registration year and motorcycle variant must be valid";

	public static final String VEHICLE_DETAILS_UPDATE_NOT_ALLOWED = "Vehicle details cannot be changed after draft creation";

	public static final String CAR_REGISTRATION_YEAR_REQUIRED = "Car registration year is required";

	public static final String CAR_VARIANT_REQUIRED = "Car variant is required";

	public static final String CAR_VARIANT_NOT_VALID = "Selected car variant is not valid for car";

	public static final String MOTORCYCLE_REGISTRATION_YEAR_REQUIRED = "Motorcycle registration year is required";

	public static final String MOTORCYCLE_VARIANT_REQUIRED = "Motorcycle variant is required";

	public static final String MOTORCYCLE_VARIANT_NOT_VALID = "Selected motorcycle variant is not valid for motorcycle";

	public static final String MOTORCYCLE_DETAILS_NOT_ALLOWED_FOR_CAR = "Motorcycle details are not allowed when vehicleTypeId is car";

	public static final String CAR_DETAILS_NOT_ALLOWED_FOR_MOTORCYCLE = "Car details are not allowed when vehicleTypeId is motorcycle";

	public static final String DOB_FUTURE_DATE = "DOB cannot be a future date";

	public static final String DOB_AGE_LESS_THAN_18 = "Age must be 18 years or above";

	public static final String INVALID_LEAP_YEAR_DOB = "Invalid DOB or invalid leap year date";

	public static final String INACTIVE_QUESTION_NOT_ALLOWED = "Inactive question answer is not allowed";

	public static final String ANSWER_TEXT_LENGTH_INVALID = "Answer text length is invalid";

	public static final String PAYMENT_FAILED_QUOTE_NOT_COMPLETED = "Quote must be completed before payment can be processed, Payment failed.";

	public static final String INVALID_CARD_HOLDER_NAME = "Card holder name must not contain special characters or numbers.";

	public static final String CARD_HAS_EXPIRED = "Card has expired";

	public static final String ANSWER_TEXT_REQUIRED = "Answer text is required. QuestionId: ";

	public static final String INVALID_TEXT_FIELD_LENGTH = "Text field answer should be between 5 and 100 characters. QuestionId: ";

	public static final String INVALID_NUMERIC_FIELD = "Numeric field answer should contain only digits and maximum 20 digits. QuestionId: ";
			
	public static final String INVALID_TEXT_AREA_LENGTH = "Text area answer should be between 10 and 250 characters. QuestionId: ";
	
	public static final String DELETED_QUESTION_CANNOT_BE_EDITED = "Deleted question cannot be edited.";
	
	public static final String OTHER_TEXT_REQUIRED = "Please enter value for Other option. ";
	
	public static final String OTHER_TEXT_INVALID = "Other option value should be 3-30 alphanumeric characters. ";
	
	public static final String OTHER_TEXT_SPECIAL_CHARACTER = "Special characters are not allowed except hyphen (-). ";
	
	public static final String DELETED_QUESTION_CANNOT_CHANGE_STATUS ="Deleted question cannot be draft or published.";
	
	public static final String DUPLICATE_OPTION ="Duplicate option is not allowed.";
	
	public static final String DATE_REQUIRED = "Date value is required.";

	public static final String DATE_INVALID_FORMAT = "Invalid date format. Date should be in yyyy-MM-dd format.";

	public static final String DATE_INVALID_LEAP_YEAR = "Invalid leap year date.";
}
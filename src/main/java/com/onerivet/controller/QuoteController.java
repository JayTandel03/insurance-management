package com.onerivet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.model.dto.PaymentRequestDto;
import com.onerivet.model.dto.PaymentResponseDto;
import com.onerivet.model.dto.PremiumBreakdownResponseDto;
import com.onerivet.model.dto.QuoteCreateResponseDto;
import com.onerivet.model.dto.QuoteDetailsResponseDto;
import com.onerivet.model.dto.QuoteRequestDto;
import com.onerivet.model.dto.QuoteResponseDto;
import com.onerivet.model.dto.response.GenericResponse;
import com.onerivet.model.enums.TemplateStatus;
import com.onerivet.service.PaymentService;
import com.onerivet.service.PremiumBreakdownService;
import com.onerivet.service.QuoteCreateService;
import com.onerivet.service.QuoteService;
import com.onerivet.util.Constants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuoteController.class);

	private final QuoteService quoteService;
	private final QuoteCreateService quoteCreateService;
	private final PremiumBreakdownService premiumBreakdownService;
	private final PaymentService paymentService;

	public QuoteController(QuoteService quoteService, QuoteCreateService quoteCreateService,
	                       PaymentService paymentService, PremiumBreakdownService premiumBreakdownService) {
		this.quoteService = quoteService;
		this.quoteCreateService = quoteCreateService;
		this.paymentService = paymentService;
		this.premiumBreakdownService = premiumBreakdownService;
	}

	@PreAuthorize(Constants.HAS_USER)
	@GetMapping("/{quoteId}")
	public GenericResponse<QuoteDetailsResponseDto> getQuoteById(@PathVariable String quoteId) {

		LOGGER.info("Get quote by quoteId request received. quoteId={}", quoteId);

		QuoteDetailsResponseDto response = quoteService.getQuoteById(quoteId);

		LOGGER.info("Quote fetched successfully. quoteId={}", quoteId);

		return new GenericResponse<>(response);
	}

	@PreAuthorize(Constants.HAS_USER)
	@GetMapping("/{quoteId}/premium-breakdown")
	public GenericResponse<PremiumBreakdownResponseDto> getPremiumBreakdown(@PathVariable String quoteId) {
		PremiumBreakdownResponseDto response = premiumBreakdownService.getPremiumBreakdown(quoteId);
		return new GenericResponse<>(response);
	}

	@PreAuthorize(Constants.HAS_USER)
	@PostMapping("/{quoteId}/payment")
	@ResponseStatus(HttpStatus.CREATED)
	public GenericResponse<PaymentResponseDto> processPayment(@PathVariable String quoteId,
	                                                          @Valid @RequestBody PaymentRequestDto requestDto) {

		PaymentResponseDto response = paymentService.processPayment(quoteId, requestDto);

		return new GenericResponse<>(response);
	}

	@GetMapping
	@PreAuthorize(Constants.HAS_USER)
	public GenericResponse<List<QuoteResponseDto>> getQuotesByStatus(@RequestParam TemplateStatus status) {

		LOGGER.info("Fetching quotes for status: {}", status);
		return new GenericResponse<>(quoteService.getQuotesByStatus(status));
	}

	@PreAuthorize(Constants.HAS_USER)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GenericResponse<QuoteCreateResponseDto> saveOrUpdateQuote(@Valid @RequestBody QuoteRequestDto requestDto) {

		QuoteCreateResponseDto response = quoteCreateService.saveOrUpdateQuote(requestDto);

		GenericResponse<QuoteCreateResponseDto> genericResponse = new GenericResponse<>();

		genericResponse.setData(response);
		LOGGER.info("Save or update quote API completed successfully");


		return genericResponse;
	}

	@PutMapping("/{quoteId}/submit")
	@ResponseStatus(HttpStatus.OK)
	public GenericResponse<QuoteCreateResponseDto> submitQuote(
	        @PathVariable String quoteId) {

	    QuoteCreateResponseDto response =
	            quoteCreateService.submitQuote(quoteId);

	    GenericResponse<QuoteCreateResponseDto> genericResponse =
	            new GenericResponse<>();

	    genericResponse.setData(response);

	    return genericResponse;
	}
}
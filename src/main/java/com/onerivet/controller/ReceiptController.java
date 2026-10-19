package com.onerivet.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onerivet.service.ReceiptService;
import com.onerivet.util.Constants;
import com.onerivet.util.ReceiptPdfConstants;


@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
	private final ReceiptService receiptService;

	public ReceiptController(ReceiptService receiptService) {
		this.receiptService = receiptService;
	}

	@PreAuthorize(Constants.HAS_USER)
	@GetMapping("/{quoteId}")
	public ResponseEntity<byte[]> downloadReceipt(@PathVariable String quoteId) throws Exception {
		byte[] pdfBytes = receiptService.generateReceipt(quoteId);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, ReceiptPdfConstants.PDF_ATTACHMENT)
				.contentType(MediaType.APPLICATION_PDF).body(pdfBytes);
	}
}
package com.onerivet.service.helper;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.onerivet.util.Constants;

public class PdfHelper {

	private static final PDType1Font BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
	private static final PDType1Font NORMAL = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
	private static final PDType1Font ITALIC = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

	public static void title(PDPageContentStream contentStream, String text, float x, float y) throws IOException {

		contentStream.beginText();
		contentStream.setFont(BOLD, 18);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text == null ? Constants.BLANK : text);
		contentStream.endText();
	}

	public static float text(PDPageContentStream contentStream, String text, float x, float y) throws IOException {

		contentStream.beginText();
		contentStream.setFont(NORMAL, 10);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text == null ? Constants.BLANK : text);
		contentStream.endText();
		return y - 15;
	}

	public static float boldText(PDPageContentStream contentStream, String text, float x, float y) throws IOException {

		contentStream.beginText();
		contentStream.setFont(BOLD, 11);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text == null ? Constants.BLANK : text);
		contentStream.endText();
		return y - 15;
	}

	public static float italic(PDPageContentStream contentStream, String text, float x, float y) throws IOException {

		contentStream.beginText();
		contentStream.setFont(ITALIC, 9);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(text == null ? Constants.BLANK : text);
		contentStream.endText();
		return y - 15;
	} 

	public static void line(PDPageContentStream contentStream, float startX, float startY, float endX, float endY)
			throws IOException {

		contentStream.moveTo(startX, startY);
		contentStream.lineTo(endX, endY);
		contentStream.stroke();
	}
	
}
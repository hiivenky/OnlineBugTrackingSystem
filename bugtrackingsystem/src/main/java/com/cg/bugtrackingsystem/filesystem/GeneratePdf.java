package com.cg.bugtrackingsystem.filesystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePdf {
	
	public static ByteArrayInputStream getCodeSnippet(String code) {
		Document document = new Document();
		System.out.println("inside generate pdf");
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(80);
            table.setWidths(new int[]{1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            PdfPCell cell;

            cell = new PdfPCell(new Phrase(code));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            document.add(new Paragraph("finalc0de"));
            document.close();

        } catch (DocumentException ex) {

            
        }
System.out.println("done here");
        return new ByteArrayInputStream(out.toByteArray());
    }
}



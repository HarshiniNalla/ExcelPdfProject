package com.example.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;
import com.example.entity.Product;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

@Service
public  class PdfService {
     private PdfService()
     {

     }
    public  static ByteArrayInputStream  productsPDFReport(List<Product> products) {
             Document document = new Document();
              ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                PdfWriter.getInstance(document, out);
                document.open();
                Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 22);
                Font fontContent = FontFactory.getFont(FontFactory.TIMES, 12);


                Paragraph title = new Paragraph("Product Details", fontHeader);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);
                PdfPTable table = new PdfPTable(6);
                Stream.of("ID", "Product Name", "Price", "Quantity", "City", "Pincode").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(Color.orange);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, fontContent));
                    table.addCell(header);
                });
                for (Product p : products) {
                    table.addCell(new Phrase(String.valueOf(p.getId()), fontContent));
                    table.addCell(new Phrase(p.getProductName(), fontContent));
                    table.addCell(new Phrase(String.valueOf(p.getPrice()), fontContent));
                    table.addCell(new Phrase(String.valueOf(p.getQuantity()), fontContent));
                    table.addCell(new Phrase(p.getCity(), fontContent));
                    table.addCell(new Phrase(String.valueOf(p.getPincode()), fontContent));



                }
                document.add(table);
                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();

            }

            return new ByteArrayInputStream(out.toByteArray());
        }
    }

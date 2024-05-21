package com.example.model;

import com.example.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
@Slf4j
public class ExcelFormat {
    private ExcelFormat() {

    }

    protected static final String[] HEADERS = {
            "id", "productName", "price", "quantity", "city", "Pincode"
    };
    public static final String SHEET_NAME = "ProductsData";

    public static ByteArrayInputStream dataToExcel(List<Product> list) throws IOException {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;
            for (Product p : list) {
                Row dataRow = sheet.createRow(rowIndex);
                dataRow.createCell(0).setCellValue(p.getId());
                dataRow.createCell(1).setCellValue(p.getProductName());
                dataRow.createCell(2).setCellValue(p.getPrice());
                dataRow.createCell(3).setCellValue(p.getQuantity());
                dataRow.createCell(4).setCellValue(p.getCity());
                dataRow.createCell(5).setCellValue(p.getPincode());
                rowIndex++;
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            log.info("failed to import data into excel");
            return null;
        }
    }
}

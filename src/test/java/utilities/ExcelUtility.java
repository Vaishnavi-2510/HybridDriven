package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

public class ExcelUtility {
	
	private FileInputStream fi;
    private FileOutputStream fo;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;
    String path;

    // Constructor
    public ExcelUtility(String path) {
        this.path = path;
    }

    // Get Row Count
    public int getRowCount(String sheetName) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fi.close();
            return 0;
        }

        int rowcount = sheet.getLastRowNum() + 1;

        workbook.close();
        fi.close();

        return rowcount;
    }

    // Get Cell Count
    public int getCellCount(String sheetName, int rownum) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fi.close();
            return 0;
        }

        row = sheet.getRow(rownum);

        int cellcount = 0;
        if (row != null) {
            cellcount = row.getLastCellNum();
        }

        workbook.close();
        fi.close();

        return cellcount;
    }

    // Get Cell Data
    public String getCellData(String sheetName, int rownum, int column) throws IOException {

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);
        sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            fi.close();
            return "";
        }

        row = sheet.getRow(rownum);
        if (row == null) {
            workbook.close();
            fi.close();
            return "";
        }

        cell = row.getCell(column);
        if (cell == null) {
            workbook.close();
            fi.close();
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        String data = formatter.formatCellValue(cell);

        workbook.close();
        fi.close();

        return data;
    }

    // Set Cell Data
    public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {

        File xlfile = new File(path);

        // Create file if not exists
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
            workbook.close();
            fo.close();
        }

        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.getSheet(sheetName);
        }

        row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }

        cell = row.getCell(column);
        if (cell == null) {
            cell = row.createCell(column);
        }

        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        workbook.write(fo);

        workbook.close();
        fi.close();
        fo.close();
    }

}
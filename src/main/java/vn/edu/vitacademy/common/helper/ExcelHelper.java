package vn.edu.vitacademy.common.helper;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelHelper {
    // Định nghĩa các hằng số cho file và sheet
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFCell cell;
    private XSSFRow row;

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            LOGGER.info("Đang set file excel với đường dẫn '{}' , và sheet name '{}'", excelPath, sheetName);
            File file = new File(excelPath);
            try (FileInputStream fileIn = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fileIn);
            }
            sheet = workbook.getSheet(sheetName);
            LOGGER.info("Đã set file excel thành công");
        } catch (Exception e) {
            LOGGER.error("Failed to set excel file with path '{}' , and sheet name '{}'. Root Cause: {}", excelPath,
                    sheetName,
                    e.getMessage());
        }
    }

    public String getCellValue(int rowNum, int colNum) {
        try {
            cell = sheet.getRow(rowNum).getCell(colNum);
            if (cell == null) {
                return "";
            }
            return cell.getStringCellValue();
        } catch (Exception e) {
            LOGGER.error("Failed to get cell value with row number '{}' , and column number '{}'. Root Cause: {}",
                    rowNum,
                    colNum,
                    e.getMessage());
            return "";
        }
    }

    public String getCellValue(String sheetName, int rowNum, int colNum) {
        try {
            XSSFSheet targetSheet = workbook.getSheet(sheetName);
            if (targetSheet == null) {
                LOGGER.error("Sheet '{}' not found in the workbook.", sheetName);
                return "";
            }
            return getCellValue(targetSheet, rowNum, colNum);
        } catch (Exception e) {
            LOGGER.error("Failed to get cell value from sheet '{}' at row {}, col {}. Root Cause: {}", sheetName,
                    rowNum,
                    colNum, e.getMessage());
            return "";
        }
    }

    public String getCellValue(XSSFSheet targetSheet, int rowNum, int colNum) {
        try {
            if (targetSheet == null) {
                return "";
            }
            row = targetSheet.getRow(rowNum);
            if (row == null) {
                return "";
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                return "";
            }
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        } catch (Exception e) {
            LOGGER.error("Failed to get cell value at row {}, col {}. Root Cause: {}", rowNum, colNum, e.getMessage());
            return "";
        }
    }

    public String getCellData(int rowNum, int colNum) {
        return getCellValue(rowNum, colNum);
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        return getCellValue(sheetName, rowNum, colNum);
    }

    public String getCellData(XSSFSheet targetSheet, int rowNum, int colNum) {
        return getCellValue(targetSheet, rowNum, colNum);
    }

    public void setCellData(String text, int rowNum, int colNum, String excelPath) {
        try {
            row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(text);

            FileOutputStream fileOut = new FileOutputStream(excelPath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            LOGGER.info("Set cell data at row {}, col {} successfully.", rowNum, colNum);
        } catch (Exception e) {
            LOGGER.error("Failed to write cell data at row {}, col {}. Root Cause: {}", rowNum, colNum, e.getMessage());
        }
    }

    public void setCellData(String text, String sheetName, int rowNum, int colNum) {
        try {
            XSSFSheet targetSheet = workbook.getSheet(sheetName);
            if (targetSheet == null) {
                LOGGER.error("Sheet '{}' not found in the workbook.", sheetName);
                return;
            }
            setCellData(text, rowNum, colNum, targetSheet);
        } catch (Exception e) {
            LOGGER.error("Failed to write cell data to sheet '{}' at row {}, col {}. Root Cause: {}", sheetName, rowNum,
                    colNum, e.getMessage());
        }
    }

    public void setCellData(String text, int rowNum, int colNum, XSSFSheet targetSheet) {
        try {
            if (targetSheet == null) {
                return;
            }
            row = targetSheet.getRow(rowNum);
            if (row == null) {
                row = targetSheet.createRow(rowNum);
            }
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(text);
            LOGGER.info("Set cell data at row {}, col {} successfully.", rowNum, colNum);
        } catch (Exception e) {
            LOGGER.error("Failed to write cell data at row {}, col {}. Root Cause: {}", rowNum, colNum, e.getMessage());
        }
    }

    public int getRowCount() {
        try {
            int rowCount = sheet.getLastRowNum();
            LOGGER.info("Row count of current sheet: {}", rowCount);
            return rowCount;
        } catch (Exception e) {
            LOGGER.error("Failed to get row count. Root Cause: {}", e.getMessage());
            return 0;
        }
    }

    public Object[][] getSheetData() {
        try {
            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();
            
            Object[][] data = new Object[rowCount][colCount];
            for (int i = 1; i <= rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = getCellValue(i, j);
                }
            }
            return data;
        } catch (Exception e) {
            LOGGER.error("Failed to get sheet data. Root Cause: {}", e.getMessage());
            return new Object[0][0];
        }
    }

    public void close() {
        try {
            workbook.close();
            LOGGER.info("Closed the workbook.");
        } catch (Exception e) {
            LOGGER.error("Failed to close the workbook. Root Cause: {}", e.getMessage());
        }
    }

}

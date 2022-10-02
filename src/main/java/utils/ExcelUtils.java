package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Purpose : To perform the excel related operations
 */

public class ExcelUtils {
    public static Sheet workSheet;
    public static XSSFSheet dataSheet = null;



    /**
     * @param file_location : input test data file path
     * @param sheetName : sheet name from the input excel which we want to read
     * @return
     */
    public Object[][] dataReaderInMap(String file_location, String sheetName) throws IOException, InvalidFormatException {
        DataFormatter formatter = new DataFormatter();
        FileInputStream fis = new FileInputStream(file_location);
        Workbook workBook = WorkbookFactory.create(fis);
        ExcelUtils.workSheet = workBook.getSheet(sheetName);
        Row row = ExcelUtils.workSheet.getRow(0);
        int lastColNum = row.getPhysicalNumberOfCells() - 1;
        int rowsCount = getRowCount();
        Object[][] testData = new Map[getRowCount()][1];
        int nRow = 0;
        for (int caseRowNum = 1; caseRowNum < ExcelUtils.workSheet.getPhysicalNumberOfRows(); caseRowNum++) {
            Map<String, String> testDataMap = new HashMap<>();
            Row eachRow = ExcelUtils.workSheet.getRow(caseRowNum);
            String resVal = eachRow.getCell(lastColNum).getStringCellValue();
            if (resVal.equalsIgnoreCase("y")) {
                int nCell = 0;
                for (int cell = 0; cell < lastColNum/*eachRow.getPhysicalNumberOfCells()-1*/; cell++) {
                    if (!formatter.formatCellValue(eachRow.getCell(cell)).equalsIgnoreCase("NA") &&
                            formatter.formatCellValue(eachRow.getCell(cell)) != "") {

                        testDataMap.put(ExcelUtils.workSheet.getRow(0).getCell(nCell) + "",
                                formatter.formatCellValue(eachRow.getCell(cell)));
                    }
                    nCell++;
                }
                testData[nRow][0] = testDataMap;
                nRow++;
            }
        }
        return testData;
    }

    public int getRowCount() {
        int count = 0;
        int totalRows = ExcelUtils.workSheet.getPhysicalNumberOfRows();
        int lastCol = ExcelUtils.workSheet.getRow(0).getPhysicalNumberOfCells() - 1;
        for (int row = 1; row < totalRows; row++) {
            Row eachRow = ExcelUtils.workSheet.getRow(row);
            String resVal = eachRow.getCell(lastCol).getStringCellValue();
            if (resVal.equalsIgnoreCase("y")) {
                count++;
            }
        }
        return count;
    }

}

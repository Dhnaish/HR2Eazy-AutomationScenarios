package Utilities;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {
	
 //	private static Sheet sheet;
	
	private static org.apache.poi.ss.usermodel.Sheet sheet;

    public static void loadExcel(String filePath, String sheetName) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        try (Workbook workbook = new XSSFWorkbook(fis)) {
			sheet = workbook.getSheet(sheetName);
		}
    }

    // Total data rows excluding header
    public static int getRowCount() {
        return sheet.getLastRowNum(); // row 0 = header
    }

    // Read a specific row and return as named map
    private static String getCellValue(Cell cell) {

        if (cell == null) return "";

        switch (cell.getCellType()) {

            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            default:
                return "";
        }
    }

    // Get row as Map (Header → Value)
    public static Map<String, String> getRowData(int rowIndex) {

        Map<String, String> rowData = new HashMap<>();

        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowIndex);

        if (dataRow == null) return rowData;

        int colCount = headerRow.getLastCellNum();

        for (int col = 0; col < colCount; col++) {

            String header = getCellValue(headerRow.getCell(col));
            String value  = getCellValue(dataRow.getCell(col));

            rowData.put(header, value);
        }

        return rowData;
    }

}

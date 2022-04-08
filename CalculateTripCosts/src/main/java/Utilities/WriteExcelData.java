package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelData {
	
	static Cell cell;

	public static void write(int row,int col,String value) throws Exception
	{
		File src = new File("./test_data/Test_output.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);	//get workbook
		XSSFSheet sheet = wb.getSheet("Output");	//get sheet
		
		Row row1 = sheet.getRow(row);	//get row
	    
		try {
			cell = row1.createCell(col);	//create Cell
		}
		catch(Exception e)
		{
			cell = row1.getCell(col);	//get cell
		}
		
		 cell.setCellValue(value);
		 
		FileOutputStream fos = new FileOutputStream(src);
		wb.write(fos);
		wb.close();
	}
	
}
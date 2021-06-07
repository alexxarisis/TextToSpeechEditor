package output;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter implements IDocumentWriter{

	@Override
	public void write(String path, String contents) {
		
		// blank sheet
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        
      
        // each cell cannot have infinite chars so we split it
		String[] lines = contents.split("\\n");
        
		 // create row and cell
		int len = lines.length;
		for (int i = 0; i < len; i++) {
	        Row row = sheet.createRow(i);
	        Cell cell = row.createCell(0);

	        // check if chars of line over the limit of the cell
	        if (lines[i].length() >= 32.767) {
	        	
	        	//split the whole line into words
	        	String[] words = lines[i].split(" ");
	        	
	        	// make appropriate cells to house them
	        	int length = words.length;
	        	for (int k = 0; k < length; k++)
	        		cell.setCellValue(words[i]);
	        } else
	        	cell.setCellValue(lines[i]);
		}


        try {
        	workbook.write(new FileOutputStream(new File(path)));;
        	workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

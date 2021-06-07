package input;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader implements IDocumentReader {

	@Override
	public String read(String path) {
		
		String contents = "";
		
		try {
			FileInputStream inputStream = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			StringBuilder builder = new StringBuilder();
			
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next();
					
					builder.append(cell.toString());
					builder.append("\t");
				}
				
				builder.append("\n");
			}
			workbook.close();
			
			contents = builder.toString();
			return contents;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return contents;
	}

}

package com.book.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.BooksExcelFile;

public class Helper {

	public static boolean checkExcelFile(MultipartFile file) {
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public static List<BooksExcelFile> excelDataStoreIntoDataBase(InputStream input) throws IOException {

		List<BooksExcelFile> booksList = new ArrayList<>(); //for storing list/multiple data

		Set<String> uniqueNames = new HashSet<>();	//for removing duplicate names

		//Loads the Excel file into a Java object so you can read/write it
		XSSFWorkbook workBook = new XSSFWorkbook(input);	//creating an in-memory Excel workbook object

		XSSFSheet sheetAt = workBook.getSheetAt(0);		//no of sheets the first sheet no index is 0

		Iterator<Row> iteratorRows= sheetAt.iterator();   // iterating rows one by one


		//SKIP
		if(iteratorRows.hasNext()) {
			iteratorRows.next();
		}
		 // Check if there’s another row
		while(iteratorRows.hasNext()) {				//its return's boolean if is there any row then give true other wise give false
			// Move to the next row
			Row iteratorRow = iteratorRows.next();			//moves the iterator to the next row and returns that row object,

			BooksExcelFile booksExcelFile = new BooksExcelFile();

			for (Cell iteratorCell : iteratorRow) {		//it’s going through all cells in a row.

				switch (iteratorCell.getColumnIndex()) {

				case 0:
					booksExcelFile.setProductName(iteratorCell.getStringCellValue());
					break;
				case 1:
					booksExcelFile.setDescription(iteratorCell.getStringCellValue());
					break;
				case 2:
					booksExcelFile.setPrice(iteratorCell.getNumericCellValue());
				default:
					break;
				}

				if(uniqueNames.add(booksExcelFile.getProductName())) {

					booksList.add(booksExcelFile);
				}

			}
				workBook.close();
		}
		return booksList;
	}

}

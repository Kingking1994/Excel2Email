package com.king.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIReadExcel {

	/**poi 解析excel文件
	 * @param args
	 */
	public static List<String> getNum() {
		List<String> list = new ArrayList<String>();
		//文件路径
		String path = "resourse\\excel\\qqEmail.xls";
		//需要解析的文件
		File file = new File(path);
		try {
			//创建Excel，读取内容
			HSSFWorkbook workbook = 
					new HSSFWorkbook(FileUtils.openInputStream(file));
			//读取默认的第一个工作表sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 0;
			//获取工作表中最后一行行号
			int lastRowNum = sheet.getLastRowNum();
			for (int i = firstRowNum; i < lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				//获取一行中单元格个数
//				int lastCellNum = row.getLastCellNum();
				//直接读取每行第一个单元格的内容
				HSSFCell cell = row.getCell(0);
				String value = cell.getStringCellValue();
				list.add(value);
			}
			return list;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	}
	

}

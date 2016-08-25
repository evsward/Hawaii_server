package com.evsward.server.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
	private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
	
	/**
     * 公共方法,创建Excel标题
     * @param sheet
     *        Excel表
     * @param arrTitle
     *        标题数组
     */
    public static HSSFSheet createExcelTitleRow(HSSFSheet sheet, String[] arrTitle, int lineCount){
    	//在索引为lineCount的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(lineCount);
		
    	for(short i=0;i<arrTitle.length;i++){
    		String title = (String)arrTitle[i];
    		// 在索引0的位置创建单元格（左上端）
    		HSSFCell cell = row.createCell(i);
    		// 定义单元格为字符串类型
    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    		// 在单元格中输入一些内容
    		cell.setCellValue(title);
    	}
    	
    	return sheet;
    }
    
    /**
     * 公共方法,创建Excel数据行
     * @param sheet
     *        Excel表
     * @param dataList
     *        每行数据List
     * @param lineCount
     *        数据行号
     * @return
     */
    public static HSSFSheet createExcelDataRow(HSSFSheet sheet, List dataList, int lineCount){
    	//在索引为lineCount的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(lineCount);
		
		for(short i=0;i<dataList.size();i++){
			Object objData = dataList.get(i);
			if(objData instanceof Long){
				long dataValue = (Long)objData;
				// 在索引为i的位置创建单元格
	    		HSSFCell cell = row.createCell(i);
	    		// 定义单元格为字符串类型
	    		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    		// 在单元格中输入一些内容
	    		cell.setCellValue(dataValue);
			}
			if(objData instanceof String){
				String dataValue = (String)objData;
				// 在索引为i的位置创建单元格
	    		HSSFCell cell = row.createCell(i);
	    		// 定义单元格为字符串类型
	    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    		// 在单元格中输入一些内容
	    		cell.setCellValue(dataValue);
			}
			if(objData instanceof Double){
				double dataValue = (Double)objData;
				//在索引为i的位置创建单元格
	    		HSSFCell cell = row.createCell(i);
	    		// 定义单元格为字符串类型
	    		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    		// 在单元格中输入一些内容
	    		cell.setCellValue(dataValue);
			}
			if(objData instanceof Date){
				Timestamp dataValue = (Timestamp)objData;
				//在索引为i的位置创建单元格
	    		HSSFCell cell = row.createCell(i);
	    		// 定义单元格为字符串类型
	    		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    		// 在单元格中输入一些内容
	    		cell.setCellValue(dataValue);
			}
		}
		
		return sheet;
    }
}

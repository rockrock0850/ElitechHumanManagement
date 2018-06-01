package com.elitech.human.resource.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel解析工具
 * 
 * @author Adam Yeh
 */
public class ExcelUtil {
	
	/**
	 * 檢查匯入的Excel格式是否正確
	 * 
	 * @create by Adam
	 * @create date: Jan 15, 2018
	 *
	 */
	public static boolean validate (List<Map<String, Object>> dataList, String[] keys) {
		boolean result = true;
		
		for (Map<String, Object> map : dataList) {
			for (String key : keys) {
				result = map.containsKey(key);
				if (!result) {break;}
			}
		}
		
		return result;
	}

	/**
	 * Excel轉成List of Map物件</br>
	 * P.S. 預設從第1行開始解析
	 * 
	 * @create by Adam
	 * @create date: Jan 15, 2018
	 *
	 * @param excel
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> parse (File excel) throws Exception {
	    return parse(excel, 1);
	}
	
	/**
	 * Excel轉成List of Map物件
	 * 
	 * @param excel Excel檔案
	 * @param rowBegin 行號( 起始值請設定成標題所在之行號 )
	 * @return List<Map<String, Object>>
	 * @throws Exception 
	 */
	public static List<Map<String, Object>> parse (File excel, int rowBegin) throws Exception {
	    if (excel == null) {return null;}
	    
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        DataFormatter fmt = new DataFormatter();
        Workbook workbook = WorkbookFactory.create(excel);
        int sheetsSize = workbook.getNumberOfSheets();
        rowBegin -= 1;
        
        // Sheet
        for (int sheetNum = 0; sheetNum < sheetsSize; sheetNum++) {
            List<String> columns = new ArrayList<String>();
            Sheet sheet = workbook.getSheetAt(sheetNum);
            int lastRowNum = sheet.getLastRowNum();
            
            // Row
            for (int rowNum = rowBegin; rowNum <= lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                
                if (row == null) {continue;}

                Map<String, Object> rowMap = new HashMap<String, Object>();
                int lastCellNum = row.getLastCellNum();
                
                // Cell 
                for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
                    Cell cell = row.getCell(cellNum);

                    // 將標題依序儲存起來作為RowMap的key值使用, 其中List的Index剛好對應cellNum
                    if (rowNum == rowBegin) {
                        columns.add(String.valueOf(cell));
                        continue;
                    }
                    
                    String cellValue = fmt.formatCellValue(cell);// 去除數字xxx.0
                    rowMap.put(columns.get(cellNum), cellValue);
                }
                
                if (rowNum == rowBegin) {continue;}// 若為標題列則不加入資料陣列
                
                dataList.add(rowMap);
            }
        } 
        
        LogUtil.log(dataList);
        
        return dataList;
    }
	
}
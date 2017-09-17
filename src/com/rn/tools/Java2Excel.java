package com.rn.tools;

import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Java2Excel
{
  public static void outEXCEL(ArrayList<Object> list, String filePath)
    throws Exception
  {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("new sheet");

    HSSFCellStyle style = wb.createCellStyle();
    setEXCELStyle(style, wb);

    HSSFRow row0 = sheet.createRow(0);
    row0.setHeight((short)500);
    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
    ArrayList title = new ArrayList();
    title.add("随机抽取结果");

    for (int i = 0; i < title.size(); i++) {
      HSSFCell cell = row0.createCell(i);
      cell.setCellValue((String)title.get(i));
      cell.setCellStyle(style);
    }

    HSSFRow row1 = sheet.createRow(1);
    row1.setHeight((short)400);
    ArrayList excelData = Excel2Java.getEXCELData();
    ArrayList listTitle = (ArrayList)excelData.get(0);
    listTitle.add("项目名称");
    listTitle.add("抽取人");
    listTitle.add("时间");

    for (int i = 0; i < listTitle.size(); i++) {
      HSSFCell cell = row1.createCell(i);
      sheet.setColumnWidth(i, 4000);
      sheet.setColumnWidth(5, 11000);
      cell.setCellValue((String)listTitle.get(i));
      cell.setCellStyle(style);
    }

    for (int i = 0; i < list.size(); i++) {
      HSSFRow row2 = sheet.createRow(i + 2);
      row2.setHeight((short)400);
      ArrayList al = (ArrayList)list.get(i);
      for (int j = 0; j < al.size(); j++) {
        HSSFCell cell1 = row2.createCell(j);
        sheet.setColumnWidth(j, 4000);
        sheet.setColumnWidth(5, 11000);
        cell1.setCellValue((String)al.get(j));
        cell1.setCellStyle(style);
      }

    }

    FileOutputStream fileOut = new FileOutputStream(filePath + ".xls");
    wb.write(fileOut);
    fileOut.close();
  }

  private static final Object createFontStyle(Workbook wb)
  {
    HSSFFont font = (HSSFFont)wb.createFont();
    font.setFontHeightInPoints((short) 12);
    font.setFontName("宋体");
    return font;
  }

  private static final void setEXCELStyle(HSSFCellStyle style, HSSFWorkbook wb)
  {
    style.setFont((HSSFFont)createFontStyle(wb));
    style.setBorderLeft((short) 1);
    style.setBorderBottom((short)1);
    style.setBorderRight((short)1);
    style.setBorderTop((short)1);
    style.setAlignment((short)2);
    style.setAlignment((short)6);
    style.setWrapText(true);
    style.setVerticalAlignment((short)1);
  }
}
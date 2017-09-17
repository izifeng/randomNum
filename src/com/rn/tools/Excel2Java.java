package com.rn.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel2Java
{
  private static final int COMPANY_TYPE_INDEX = 2;
  private static final String EXCEL_PATH = "config/dataCfg.xls";

  public static final ArrayList<Object> getEXCELData()
    throws Exception
  {
    ArrayList rows = new ArrayList();

    Workbook wb = createWb("config/dataCfg.xls");

    Sheet sheet = getSheet(wb, 0);

    List list = listFromSheet(sheet);

    ArrayList row = null;
    for (int i = 0; i < list.size(); i++) {
      Object[] obj = (Object[])list.get(i);
      row = new ArrayList();
      for (int j = 0; j < obj.length; j++) {
        row.add(obj[j]);
      }
      rows.add(row);
    }
    return rows;
  }

  public static final Workbook createWb(String filePath)
    throws IOException
  {
    if (filePath.trim().toLowerCase().endsWith("xls"))
      return new HSSFWorkbook(new FileInputStream(filePath));
    if (filePath.trim().toLowerCase().endsWith("xlsx")) {
      return new XSSFWorkbook(new FileInputStream(filePath));
    }
    throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!");
  }

  public static final Sheet getSheet(Workbook wb, int index)
  {
    return wb.getSheetAt(index);
  }

  public static final List<Object[]> listFromSheet(Sheet sheet)
  {
    List list = new ArrayList();
    for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
      Row row = sheet.getRow(r);
      if (row == null)
      {
        continue;
      }
      Object[] cells = new Object[row.getLastCellNum()];
      for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {
        Cell cell = row.getCell(c);
        if (cell == null)
          continue;
        cells[c] = getValueFromCell(cell);
      }
      list.add(cells);
    }
    return list;
  }

  public static final String getValueFromCell(Cell cell)
  {
    if (cell == null) {
      System.out.println("Cell is null !!!");
      return null;
    }
    String value = null;
    switch (cell.getCellType()) {
    case 0:
      if (HSSFDateUtil.isCellDateFormatted(cell))
        value = new SimpleDateFormat().format(cell.getDateCellValue());
      else
        value = String.valueOf((int)cell.getNumericCellValue());
      break;
    case 1:
      value = cell.getStringCellValue();
      break;
    case 2:
      double numericValue = cell.getNumericCellValue();
      if (HSSFDateUtil.isValidExcelDate(numericValue))
        value = new SimpleDateFormat().format(cell.getDateCellValue());
      else
        value = String.valueOf(numericValue);
      break;
    case 3:
      value = "";
      break;
    case 4:
      value = String.valueOf(cell.getBooleanCellValue());
      break;
    case 5:
      value = String.valueOf(cell.getErrorCellValue());
      break;
    }

    return value;
  }

  public static final ArrayList<Object> getFieldListFromEXCEL(String col, String comTyle)
    throws Exception
  {
    ArrayList excelData = getEXCELData();

    ArrayList tableHead = (ArrayList)excelData.get(0);
    ArrayList remove = new ArrayList();
    for (int i = 1; i < excelData.size(); i++) {
      remove.add(excelData.get(i));
    }

    int comTypeIndex = 2;
    for (int i = 0; i < tableHead.size(); i++) {
      if (col.equals(tableHead.get(i))) {
        comTypeIndex = i;
      }

    }

    ArrayList typeData = new ArrayList();
    for (int row = 0; row < remove.size(); row++)
    {
      ArrayList rows = (ArrayList)remove.get(row);
      if (comTyle.equals(rows.get(comTypeIndex))) {
        typeData.add(remove.get(row));
      }
    }
    return typeData;
  }
}
package com.rn.model;

import com.rn.tools.Excel2Java;
import com.rn.tools.readXMLTools;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class resultView extends AbstractTableModel
{
  Vector<String> colums;
  Vector<Vector> rows;

  public void queryEXCELAll()
  {
    this.colums = new Vector();
    try {
      ArrayList excelData = Excel2Java.getEXCELData();

      ArrayList tableHead = (ArrayList)excelData.get(0);

      for (int i = 0; i < tableHead.size(); i++) {
        this.colums.add((String)tableHead.get(i));
      }

      this.rows = new Vector(excelData.size());
      for (int i = 1; i < excelData.size(); i++)
      {
        ArrayList arr = (ArrayList)excelData.get(i);
        Vector temp = new Vector();
        for (int j = 0; j < arr.size(); j++) {
          temp.add((String)arr.get(j));
        }
        this.rows.add(temp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void queryEXCELForNum(int count)
  {
    this.colums = new Vector();
    try {
      ArrayList excelData = Excel2Java.getEXCELData();

      ArrayList tableHead = (ArrayList)excelData.get(0);

      for (int i = 0; i < tableHead.size(); i++) {
        this.colums.add((String)tableHead.get(i));
      }
      ArrayList remove = new ArrayList();
      for (int i = 1; i < excelData.size(); i++) {
        remove.add(excelData.get(i));
      }
      ArrayList random = new ArrayList();
      random.add(excelData.get(0));
      for (int i = 0; i < count; i++) {
        random.add(remove.remove(new Random().nextInt(remove.size())));
      }

      this.rows = new Vector(random.size());
      for (int i = 1; i < random.size(); i++)
      {
        ArrayList arr = (ArrayList)random.get(i);
        Vector temp = new Vector();
        for (int j = 0; j < arr.size(); j++) {
          temp.add((String)arr.get(j));
        }
        this.rows.add(temp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void queryEXCELForNumAndType(int count, String type)
  {
    this.colums = new Vector();
    try
    {
      ArrayList excelData = Excel2Java.getEXCELData();

      ArrayList tableHead = (ArrayList)excelData.get(0);

      for (int i = 0; i < tableHead.size(); i++) {
        this.colums.add((String)tableHead.get(i));
      }

      ArrayList typeLable = readXMLTools.readXMLCompanyType("JLableType", "name");
      ArrayList dataList = Excel2Java.getFieldListFromEXCEL((String)typeLable.get(0), type);

      ArrayList random = new ArrayList();
      random.add(dataList.get(0));
      for (int i = 0; i < count; i++) {
        random.add(dataList.remove(new Random().nextInt(dataList.size())));
      }

      this.rows = new Vector(random.size());
      for (int i = 1; i < random.size(); i++)
      {
        ArrayList arr = (ArrayList)random.get(i);
        Vector temp = new Vector();
        for (int j = 0; j < arr.size(); j++) {
          temp.add((String)arr.get(j));
        }
        this.rows.add(temp);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getColumnCount()
  {
    return this.colums.size();
  }

  public int getRowCount()
  {
    return this.rows.size();
  }

  public Object getValueAt(int row, int column)
  {
    return ((Vector)this.rows.get(row)).get(column);
  }

  public String getColumnName(int column)
  {
    return ((String)this.colums.get(column)).toString();
  }
}
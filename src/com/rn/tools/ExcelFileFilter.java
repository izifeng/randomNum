package com.rn.tools;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExcelFileFilter extends FileFilter
{
  String ext;

  public ExcelFileFilter(String ext)
  {
    this.ext = ext;
  }

  public boolean accept(File f)
  {
    if (f.isDirectory()) {
      return true;
    }

    String fileName = f.getName();
    int index = fileName.lastIndexOf('.');

    if ((index > 0) && (index < fileName.length() - 1))
    {
      String extension = fileName.substring(index + 1).toLowerCase();
      if (extension.equals(this.ext)) {
        return true;
      }
    }
    return false;
  }

  public String getDescription()
  {
    if (this.ext.equals("xls")) {
      return "Microsoft Excel文件(*.xls)";
    }
    return null;
  }
}
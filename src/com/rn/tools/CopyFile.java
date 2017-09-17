package com.rn.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class CopyFile
{
  public static void copyFile(String oldPath, String newPath)
  {
    try
    {
      int bytesum = 0;
      int byteread = 0;
      File oldfile = new File(oldPath);
      if (oldfile.exists()) {
        InputStream inStream = new FileInputStream(oldPath);
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];

        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread;
          fs.write(buffer, 0, byteread);
        }
        inStream.close();
      }
    }
    catch (Exception e) {
      System.out.println("复制单个文件操作出错");
      e.printStackTrace();
    }
  }

  public static void copyFolder(String oldPath, String newPath)
  {
    try
    {
      new File(newPath).mkdirs();
      File a = new File(oldPath);
      String[] file = a.list();
      File temp = null;
      for (int i = 0; i < file.length; i++) {
        if (oldPath.endsWith(File.separator)) {
          temp = new File(oldPath + file[i]);
        }
        else {
          temp = new File(oldPath + File.separator + file[i]);
        }

        if (temp.isFile()) {
          FileInputStream input = new FileInputStream(temp);
          FileOutputStream output = new FileOutputStream(newPath + "/" + 
            temp.getName().toString());
          byte[] b = new byte[5120];
          int len;
          while ((len = input.read(b)) != -1)
          {
            output.write(b, 0, len);
          }
          output.flush();
          output.close();
          input.close();
        }
        if (temp.isDirectory())
          copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
      }
    }
    catch (Exception e)
    {
      System.out.println("复制整个文件夹内容操作出错");
      e.printStackTrace();
    }
  }
}
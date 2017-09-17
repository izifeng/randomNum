package com.rn.tools;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MySelectChooser extends JFileChooser
{
  public MySelectChooser(String path)
  {
    super(path);
  }

  public void approveSelection()
  {
    File file = new File(getSelectedFile().getPath());

    if (file.exists())
      super.approveSelection();
    else
      JOptionPane.showMessageDialog(null, "你选择的文件不存在，请重新选择！");
  }
}
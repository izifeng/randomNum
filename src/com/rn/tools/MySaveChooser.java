package com.rn.tools;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MySaveChooser extends JFileChooser
{
  public MySaveChooser(String path)
  {
    super(path);
  }

  public void approveSelection()
  {
    File file = getSelectedFile();
    if (file.exists())
    {
      int copy = JOptionPane.showConfirmDialog(null, 
        "是否要覆盖当前文件？", "保存", 0, 
        3);
      if (copy == 0)
        super.approveSelection();
    }
    else {
      super.approveSelection();
    }
  }
}
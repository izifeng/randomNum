package com.rn.view;

import com.rn.model.resultView;
import javax.swing.JTable;

class threadGetResult
  implements Runnable
{
  private String comTyle;
  private String numStr;
  private resultView resultModel;
  private JTable jtb;

  public threadGetResult(String comTyle, String numStr, resultView resultModel, JTable jtb)
  {
    this.comTyle = comTyle;
    this.numStr = numStr;
    this.resultModel = resultModel;
    this.jtb = jtb;
  }

  public void run() {
    while (true)
      try {
        this.resultModel = new resultView();
        if ((this.comTyle == "") || (this.comTyle == "全部")) {
          this.resultModel.queryEXCELForNum(Integer.parseInt(this.numStr));
          this.jtb.setModel(this.resultModel);
        } else {
          this.resultModel.queryEXCELForNumAndType(Integer.parseInt(this.numStr), this.comTyle);
          this.jtb.setModel(this.resultModel);
        }
        Thread.sleep(50L);
        continue; } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
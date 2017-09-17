package com.rn.view;

import com.rn.model.resultView;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;

class threadEXERun
  implements Runnable
{
  private String comTyle;
  private String numStr;
  private resultView resultModel;
  private JTable jtb;
  private JButton jbtn_exe;
  private JButton jbtn_outResult;
  private JButton jbtn_importExcel;
  private JButton jbtn_start;
  private JTextField jtf_num;
  private JTextField jtf_proName;
  private JTextField jtf_yh;
  private int current;
  private int amount;

  public threadEXERun(String comTyle, String numStr, resultView resultModel, JTable jtb, int amount, JButton jbtn_exe, JButton jbtn_start, JTextField jtf_num, JButton jbtn_outResult, JTextField jtf_proName, JTextField jtf_yh, JButton jbtn_importExcel)
  {
    this.comTyle = comTyle;
    this.numStr = numStr;
    this.resultModel = resultModel;
    this.jtb = jtb;
    this.jbtn_exe = jbtn_exe;
    this.jbtn_start = jbtn_start;
    this.amount = amount;
    this.jtf_num = jtf_num;
    this.jbtn_outResult = jbtn_outResult;
    this.jbtn_importExcel = jbtn_importExcel;
    this.jtf_proName = jtf_proName;
    this.jtf_yh = jtf_yh;
    this.current = 0;
  }

  public void run() {
    while (this.current <= this.amount) {
      try {
        this.resultModel = new resultView();
        this.jbtn_exe.setEnabled(false);
        this.jbtn_start.setEnabled(false);
        this.jtf_num.setEditable(false);
        this.jbtn_outResult.setEnabled(false);
        this.jtf_proName.setEnabled(false);
        this.jtf_yh.setEnabled(false);
        this.jbtn_importExcel.setEnabled(false);

        if ((this.comTyle == "") || (this.comTyle == "全部")) {
          this.resultModel.queryEXCELForNum(Integer.parseInt(this.numStr));
          this.jtb.setModel(this.resultModel);
        } else {
          this.resultModel.queryEXCELForNumAndType(Integer.parseInt(this.numStr), this.comTyle);
          this.jtb.setModel(this.resultModel);
        }
        Thread.sleep(100L);
        this.current += 1;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.jbtn_exe.setEnabled(true);
    this.jbtn_start.setEnabled(true);
    this.jtf_num.setEditable(true);
    this.jbtn_outResult.setEnabled(true);
    this.jtf_proName.setEnabled(true);
    this.jtf_yh.setEnabled(true);
    this.jbtn_importExcel.setEnabled(true);
  }
}
package com.rn.view;

import com.rn.model.resultView;
import com.rn.tools.CopyFile;
import com.rn.tools.Excel2Java;
import com.rn.tools.ExcelFileFilter;
import com.rn.tools.Java2Excel;
import com.rn.tools.MySaveChooser;
import com.rn.tools.MySelectChooser;
import com.rn.tools.myFonts;
import com.rn.tools.myImages;
import com.rn.tools.readXMLTools;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class index extends JFrame implements ActionListener {
	private Container con;
	private Image img_title;
	private JMenuBar jmb;
	private JPanel jpl_resultDisplay;
	private resultView resultModel;
	private String comTyle = "";
	private JButton jbtn_start;
	private JButton jbtn_stop;
	private JButton jbtn_exe;
	private JButton jbtn_importExcel;
	private JButton jbtn_outResult;
	private JTextField jtf_num;
	private JTextField jtf_proName;
	private JTextField jtf_yh;
	private JTable jtb;
	private Thread t;
	private Thread t1;
	private JMenuItem jmi_openAllData;
	private JMenuItem jmi_about;
	private JMenuItem jmi_out;

	public index() {
		this.con = getContentPane();
		setLayout(null);

		setTitle("随机抽取系统");

		setWindowImg();

		drawBasicLayout();

		JTabel_init();

		setWindowSize();
	}

	private void setWindowImg() {
		try {
			this.img_title = ImageIO.read(myImages.img_title);
		} catch (IOException e) {
			System.out.println("找不到窗口图标图片!");
		}

		setIconImage(this.img_title);
	}

	private void setWindowSize() {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(width / 2 - 220, height / 2 - 250, 450, 480);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(3);
	}

	private void drawBasicLayout() {
		JPanel jpl_yh = new JPanel();
		jpl_yh.setLayout(null);
		jpl_yh.setBounds(10, 10, 420, 60);
		jpl_yh.setBorder(BorderFactory.createTitledBorder("项目信息区"));
		this.con.add(jpl_yh);

		JLabel jll_proName = new JLabel("项目名称：");
		jll_proName.setFont(myFonts.f1);
		this.jtf_proName = new JTextField("");
		this.jtf_proName.setBorder(BorderFactory.createLoweredBevelBorder());
		jll_proName.setBounds(15, 30, 100, 14);
		this.jtf_proName.setBounds(85, 26, 120, 22);
		jpl_yh.add(jll_proName);
		jpl_yh.add(this.jtf_proName);

		JLabel jll_yh = new JLabel("抽取人：");
		jll_yh.setFont(myFonts.f1);
		this.jtf_yh = new JTextField("");
		this.jtf_yh.setBorder(BorderFactory.createLoweredBevelBorder());
		jll_yh.setBounds(230, 30, 80, 14);
		this.jtf_yh.setBounds(285, 26, 120, 22);
		jpl_yh.add(jll_yh);
		jpl_yh.add(this.jtf_yh);

		JPanel jpl_BasicOperation = new JPanel();
		jpl_BasicOperation.setLayout(null);
		jpl_BasicOperation.setBounds(10, 80, 420, 110);
		jpl_BasicOperation.setBorder(BorderFactory.createTitledBorder("基础操作区"));
		this.con.add(jpl_BasicOperation);

		ArrayList typeLable = readXMLTools.readXMLCompanyType("JLableType",
				"name");
		JLabel jll_type = new JLabel((String) typeLable.get(0) + "：");
		jll_type.setFont(myFonts.f1);
		jll_type.setBounds(30, 30, 70, 14);
		jll_type.setHorizontalAlignment(4);
		jpl_BasicOperation.add(jll_type);

		ArrayList arrayType = readXMLTools.readXMLCompanyType("type", "name");
		String[] array = new String[arrayType.size() + 1];
		array[0] = "全部";
		for (int i = 1; i <= arrayType.size(); i++) {
			array[i] = ((String) arrayType.get(i - 1));
		}

		JComboBox box = new JComboBox(array);
		box.setPreferredSize(new Dimension(160, 22));
		box.setBounds(100, 28, 80, 20);
		jpl_BasicOperation.add(box);

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox com = (JComboBox) e.getSource();
				String str = (String) com.getSelectedItem();
				index.this.comTyle = str;
			}
		};
		box.addActionListener(listener);

		JLabel jll_num = new JLabel("抽取数量：");
		jll_num.setFont(myFonts.f1);
		jll_num.setBounds(230, 30, 70, 14);
		jpl_BasicOperation.add(jll_num);

		this.jtf_num = new JTextField("");
		this.jtf_num.setFont(myFonts.f1);
		this.jtf_num.setBorder(BorderFactory.createLoweredBevelBorder());
		this.jtf_num.setBounds(300, 26, 80, 22);
		jpl_BasicOperation.add(this.jtf_num);

		this.jbtn_start = new JButton("开始");
		this.jbtn_start.setToolTipText("开始");
		this.jbtn_start.addActionListener(this);
		this.jbtn_start.setBounds(50, 70, 60, 26);
		jpl_BasicOperation.add(this.jbtn_start);

		this.jbtn_stop = new JButton("停止");
		this.jbtn_stop.setToolTipText("停止");
		this.jbtn_stop.setEnabled(false);
		this.jbtn_stop.addActionListener(this);
		this.jbtn_stop.setBounds(150, 70, 60, 26);
		jpl_BasicOperation.add(this.jbtn_stop);

		this.jbtn_exe = new JButton("开始并自动停止");
		this.jbtn_exe.setToolTipText("开始并自动停止");
		this.jbtn_exe.addActionListener(this);
		this.jbtn_exe.setBounds(260, 70, 130, 26);
		jpl_BasicOperation.add(this.jbtn_exe);

		this.jpl_resultDisplay = new JPanel(new BorderLayout());
		this.jpl_resultDisplay.setBounds(10, 200, 420, 210);
		this.jpl_resultDisplay.setBorder(BorderFactory
				.createTitledBorder("结果显示区"));
		this.con.add(this.jpl_resultDisplay);

		this.jbtn_importExcel = new JButton("导入EXCEL数据源");
		this.jbtn_importExcel.setToolTipText("导出EXCEL");
		this.jbtn_importExcel.addActionListener(this);
		this.jbtn_importExcel.setBounds(160, 415, 140, 26);
		this.con.add(this.jbtn_importExcel);

		this.jbtn_outResult = new JButton("导出EXCEL");
		this.jbtn_outResult.setToolTipText("导出EXCEL");
		this.jbtn_outResult.setEnabled(false);
		this.jbtn_outResult.addActionListener(this);
		this.jbtn_outResult.setBounds(320, 415, 100, 26);
		this.con.add(this.jbtn_outResult);
	}

	private void JTabel_init() {
		this.resultModel = new resultView();
		JPanel jp1 = new JPanel(new BorderLayout());
		this.resultModel.queryEXCELAll();
		this.jtb = new JTable(this.resultModel);
		this.jtb.setRowHeight(22);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(0);
		this.jtb.setDefaultRenderer(Object.class, tcr);
		JScrollPane jsp = new JScrollPane(this.jtb);
		jp1.add(jsp);
		this.jpl_resultDisplay.add(jp1, "Center");
	}

	public int ckechDataNum() {
		int dataNum = 0;
		ArrayList<?> excelData = null;
		try {
			if ((this.comTyle == "") || (this.comTyle == "全部")) {
				excelData = Excel2Java.getEXCELData();
				dataNum = excelData.size() - 1;
			} else {
				ArrayList<?> typeLable = readXMLTools.readXMLCompanyType(
						"JLableType", "name");
				ArrayList<?> list = Excel2Java.getFieldListFromEXCEL(
						(String) typeLable.get(0), this.comTyle);
				dataNum = list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataNum;
	}

	private boolean isNumeric(String s) {
		if ((s != null) && (s != "")) {
			return s.matches("^[0-9]*$");
		}
		return false;
	}

	public static void main(String[] args) {
		new index();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.jbtn_start) {
			String numStr = this.jtf_num.getText().trim();

			if (numStr.length() == 0) {
				JOptionPane.showMessageDialog(this, "抽取数量不能为空，请输入!");
				return;
			}

			boolean b = isNumeric(this.jtf_num.getText().trim());
			if (!b) {
				JOptionPane.showMessageDialog(this, "抽取条数必须为正整数，请重新输入");
				this.jtf_num.setText(null);
				return;
			}

			if (Integer.parseInt(numStr) == 0) {
				JOptionPane.showMessageDialog(this, "抽取条数必须大于0!");
				return;
			}

			int numdata = ckechDataNum();
			if (Integer.parseInt(numStr) > numdata) {
				JOptionPane.showMessageDialog(this, "数据库条数太少了，抽取条数大于数据库中数据条数!");
				return;
			}

			threadGetResult th = new threadGetResult(this.comTyle, numStr,
					this.resultModel, this.jtb);
			this.t = new Thread(th);
			this.t.start();
			this.jbtn_start.setEnabled(false);
			this.jbtn_stop.setEnabled(true);
			this.jbtn_exe.setEnabled(false);
			this.jtf_num.setEditable(false);
			this.jbtn_outResult.setEnabled(false);
			this.jtf_proName.setEnabled(false);
			this.jtf_yh.setEnabled(false);
			this.jbtn_importExcel.setEnabled(false);
		}

		if ((e.getSource() == this.jbtn_stop) && (this.t.isAlive())) {
			this.t.stop();
			this.jbtn_start.setEnabled(true);
			this.jbtn_stop.setEnabled(false);
			this.jbtn_exe.setEnabled(true);
			this.jtf_num.setEditable(true);
			this.jbtn_outResult.setEnabled(true);
			this.jtf_proName.setEnabled(true);
			this.jtf_yh.setEnabled(true);
			this.jbtn_importExcel.setEnabled(true);
		}

		if (e.getSource() == this.jbtn_exe) {
			String numStr = this.jtf_num.getText().trim();
			if (numStr.length() == 0) {
				JOptionPane.showMessageDialog(this, "请输入随机数!");
				return;
			}

			boolean b = isNumeric(this.jtf_num.getText().trim());
			if (!b) {
				JOptionPane.showMessageDialog(this, "抽取条数必须为正整数，请重新输入");
				this.jtf_num.setText(null);
				return;
			}

			if (Integer.parseInt(numStr) == 0) {
				JOptionPane.showMessageDialog(this, "抽取条数必须大于0!");
				return;
			}

			int numdata = ckechDataNum();
			if (Integer.parseInt(numStr) > numdata) {
				JOptionPane.showMessageDialog(this, "数据库条数太少了，抽取条数大于数据库中数据条数!");
				return;
			}

			threadEXERun th = new threadEXERun(this.comTyle, numStr,
					this.resultModel, this.jtb, 20, this.jbtn_exe,
					this.jbtn_start, this.jtf_num, this.jbtn_outResult,
					this.jtf_proName, this.jtf_yh, this.jbtn_importExcel);
			this.t1 = new Thread(th);
			this.t1.start();
		}

		e.getSource();

		e.getSource();

		if (e.getSource() == this.jmi_out) {
			dispose();
		}

		if (e.getSource() == this.jbtn_importExcel) {
			JFileChooser jfc = new MySelectChooser(".");
			jfc.setDialogTitle("请选择要导入的文件");
			jfc.addChoosableFileFilter(new ExcelFileFilter("xls"));
			int returnVal = jfc.showOpenDialog(this);
			if (returnVal == 0) {
				String selectFle = jfc.getSelectedFile().getAbsolutePath();
				CopyFile.copyFile(selectFle, "config/dataCfg.xls");
				JOptionPane.showMessageDialog(this, "导入成功！");
				this.resultModel = new resultView();
				this.resultModel.queryEXCELAll();
				this.jtb.setModel(this.resultModel);
			}

		}

		if (e.getSource() == this.jbtn_outResult) {
			if (this.jtf_proName.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "项目名称不能为空，请输入！");
				return;
			}

			if (this.jtf_yh.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "抽取人不能为空，请输入！");
				return;
			}

			ArrayList list = new ArrayList();
			SimpleDateFormat dateformat1 = new SimpleDateFormat(
					"yyyy年MM月dd日 HH时mm分ss秒 E");
			String nowStr = dateformat1.format(new Date());
			for (int colums = 0; colums < Integer.parseInt(this.jtf_num
					.getText().trim()); colums++) {
				ArrayList array = new ArrayList();
				String id = (String) this.jtb.getModel().getValueAt(colums, 0);
				String name = (String) this.jtb.getModel()
						.getValueAt(colums, 1);
				String type = (String) this.jtb.getModel()
						.getValueAt(colums, 2);
				String proName = this.jtf_proName.getText().trim();
				String yonghu = this.jtf_yh.getText().trim();
				array.add(id);
				array.add(name);
				array.add(type);
				array.add(proName);
				array.add(yonghu);
				array.add(nowStr);
				list.add(array);
			}

			JFileChooser jfc = new MySaveChooser(".");
			jfc.setDialogTitle("请选择文件保存路径");
			jfc.addChoosableFileFilter(new ExcelFileFilter("xls"));
			int returnVal = jfc.showSaveDialog(this);
			jfc.setFileSelectionMode(1);
			if (returnVal == 0) {
				String filePath = jfc.getSelectedFile().getAbsolutePath();
				try {
					Java2Excel.outEXCEL(list, filePath);
					JOptionPane.showMessageDialog(this, "导出成功！！");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "导出失败！！，请联系开发人员！！");
					e1.printStackTrace();
				}
			}
		}
	}
}
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.lang.Integer;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class SeatChartWindow implements ActionListener{

	private JFrame frame;
	private JTable table;
	private JTextField top_name_textfield;
	private JTextField bot_name_txtfld;
	private JTextField row_fld;
	private JTextField column_fld;
	private JButton add_stu_button;
	private JButton remove_student;
	private JButton add_to_covid;
	private JTable covidTable;
	private JTable caseCountTable;
	private int numStudents = 0;
	private int numCases = 0;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SeatChartWindow window = new SeatChartWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			}
			}});
	}

	/**
	 * Create the application.
	 */
	public SeatChartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 703, 532);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//title
		JLabel title = new JLabel("Seating Chart");
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		//statistics panel
		JPanel statistics_bar = new JPanel();
		statistics_bar.setBackground(new Color(135, 206, 235));
		
		//top-green panel
		JPanel top_green = new JPanel();
		top_green.setBackground(new Color(165, 42, 42));
		
		JPanel bottom_green = new JPanel();
		bottom_green.setBackground(new Color(143, 188, 143));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(title, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
								.addComponent(bottom_green, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
								.addComponent(top_green, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statistics_bar, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(17)
							.addComponent(title)
							.addGap(5)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(top_green, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(bottom_green, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
						.addComponent(statistics_bar, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"
			}
		));
		scrollPane.setViewportView(table);
		
		bot_name_txtfld = new JTextField();
		bot_name_txtfld.setColumns(10);
		
		row_fld = new JTextField();
		row_fld.setColumns(10);
		
		column_fld = new JTextField();
		column_fld.setColumns(10);
		
		
		add_stu_button = new JButton("Add Student");
		add_stu_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String row = row_fld.getText();
				String column = column_fld.getText();
				if(!(bot_name_txtfld.getText().equals("")) || !(row.equals("")) || !(column.equals(""))) {
					if(Integer.parseInt(row)-1 <= table.getRowCount()-1 && Integer.parseInt(column)-1 <= table.getColumnCount()-1) {
						if(table.getModel().getValueAt(Integer.parseInt(row)-1, Integer.parseInt(column)-1) == null) {
							numStudents++; 
						}
						table.setValueAt(bot_name_txtfld.getText(),Integer.parseInt(row)-1,Integer.parseInt(column)-1);
						caseCountTable.setValueAt(numStudents, 1, 1);
						caseCountTable.setValueAt(updatePercentage(numCases,numStudents),2,1);
					}
				}
			}
			
		});
		
		JLabel full_name2 = new JLabel("Full Name");
		
		JLabel row_field_label = new JLabel("Row");
		
		JLabel column_field_label = new JLabel("Column");
		
		remove_student = new JButton("Remove");
		remove_student.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String row = row_fld.getText();
				String column = column_fld.getText();
				if(!(row.equals("")) || !(column.equals(""))) {
					if(table.getModel().getValueAt(Integer.parseInt(row)-1, Integer.parseInt(column)-1) != null) {
						numStudents--; 
					}
					table.setValueAt(null,Integer.parseInt(row)-1,Integer.parseInt(column)-1);
					caseCountTable.setValueAt(numStudents, 1, 1);
					caseCountTable.setValueAt(updatePercentage(numCases,numStudents),2,1);
				}
			}
			
			
		});
		
		GroupLayout gl_bottom_green = new GroupLayout(bottom_green);
		gl_bottom_green.setHorizontalGroup(
			gl_bottom_green.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottom_green.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_bottom_green.createParallelGroup(Alignment.LEADING, false)
						.addComponent(bot_name_txtfld)
						.addComponent(column_fld)
						.addComponent(row_fld))
					.addGap(4)
					.addGroup(gl_bottom_green.createParallelGroup(Alignment.LEADING)
						.addComponent(full_name2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_bottom_green.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(column_field_label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(row_field_label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_bottom_green.createParallelGroup(Alignment.LEADING)
						.addComponent(remove_student, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
						.addComponent(add_stu_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(31))
		);
		gl_bottom_green.setVerticalGroup(
			gl_bottom_green.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottom_green.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_bottom_green.createParallelGroup(Alignment.BASELINE)
						.addComponent(bot_name_txtfld, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(full_name2)
						.addComponent(add_stu_button))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_bottom_green.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bottom_green.createSequentialGroup()
							.addGroup(gl_bottom_green.createParallelGroup(Alignment.BASELINE)
								.addComponent(row_fld, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(row_field_label))
							.addGap(8)
							.addGroup(gl_bottom_green.createParallelGroup(Alignment.BASELINE)
								.addComponent(column_fld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(column_field_label))
							.addGap(18))
						.addGroup(gl_bottom_green.createSequentialGroup()
							.addComponent(remove_student, GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
							.addContainerGap())))
		);
		bottom_green.setLayout(gl_bottom_green);
		
		top_name_textfield = new JTextField();
		top_name_textfield.setColumns(10);
		
		JLabel statistics_heading = new JLabel("Statistics");
		statistics_heading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JButton removeCovid = new JButton("Remove");
		removeCovid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(covidTable.isColumnSelected(0)) {
					DefaultTableModel model = (DefaultTableModel)covidTable.getModel();
					model.removeRow(covidTable.getSelectedRow());
					caseCountTable.setValueAt(covidTable.getRowCount(), 0, 1);
					numCases--;
					caseCountTable.setValueAt(updatePercentage(numCases,numStudents),2,1);
				}
			}
			
			
		});
		GroupLayout gl_statistics_bar = new GroupLayout(statistics_bar);
		gl_statistics_bar.setHorizontalGroup(
			gl_statistics_bar.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_statistics_bar.createSequentialGroup()
					.addContainerGap(21, Short.MAX_VALUE)
					.addGroup(gl_statistics_bar.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_statistics_bar.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_statistics_bar.createSequentialGroup()
								.addComponent(removeCovid, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(gl_statistics_bar.createSequentialGroup()
								.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
								.addGap(10))
							.addGroup(gl_statistics_bar.createSequentialGroup()
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))
						.addGroup(Alignment.TRAILING, gl_statistics_bar.createSequentialGroup()
							.addComponent(statistics_heading, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addGap(44))))
		);
		gl_statistics_bar.setVerticalGroup(
			gl_statistics_bar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_statistics_bar.createSequentialGroup()
					.addContainerGap()
					.addComponent(statistics_heading)
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(removeCovid, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(34))
		);
		
		caseCountTable = new JTable();
		caseCountTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Case Count", null},
				{"Total Students", null},
				{"% of Class", null},
			},
			new String[] {
				" ", " "
			}
		));
		scrollPane_2.setViewportView(caseCountTable);
		
		covidTable = new JTable();
		covidTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"COVID"
			}
		));
		scrollPane_1.setViewportView(covidTable);
		statistics_bar.setLayout(gl_statistics_bar);
		frame.getContentPane().setLayout(groupLayout);
		add_to_covid = new JButton("COVID-19");
		add_to_covid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(top_name_textfield.getText().equals("")) && numStudents > 0) {
					DefaultTableModel model = (DefaultTableModel)covidTable.getModel();
					model.insertRow(0, new Object[] { top_name_textfield.getText() });
					caseCountTable.setValueAt(covidTable.getRowCount(), 0, 1);
					numCases++;
					caseCountTable.setValueAt(updatePercentage(numCases,numStudents),2,1);
				}
			}
			
			
		});
		
		JLabel full_name = new JLabel("Full Name");
		full_name.setForeground(new Color(255, 255, 255));
		
		GroupLayout gl_top_green = new GroupLayout(top_green);
		gl_top_green.setHorizontalGroup(
			gl_top_green.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_top_green.createSequentialGroup()
					.addGap(28)
					.addComponent(top_name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(full_name, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(add_to_covid, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_top_green.setVerticalGroup(
			gl_top_green.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_top_green.createSequentialGroup()
					.addGroup(gl_top_green.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_top_green.createSequentialGroup()
							.addGap(45)
							.addGroup(gl_top_green.createParallelGroup(Alignment.BASELINE)
								.addComponent(top_name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(full_name)))
						.addGroup(gl_top_green.createSequentialGroup()
							.addGap(22)
							.addComponent(add_to_covid, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		top_green.setLayout(gl_top_green);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public int getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(int numStudents) {
		this.numStudents = numStudents;
	}

	public int getNumCases() {
		return numCases;
	}

	public void setNumCases(int numCases) {
		this.numCases = numCases;
	}
	
	public double updatePercentage(int cases, int stu) {
		return ((double)cases/(double)stu)*100.0;
	}
}

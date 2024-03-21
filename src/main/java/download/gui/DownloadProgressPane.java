package download.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Singleton;
import download.gui.model.DownloadTableModel;
import download.gui.renderer.DownloadControlCellRenderer;

public class DownloadProgressPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JScrollPane tablePanel;
	private JPanel headPanel;
	private JLabel textLabel;
	private JTextField textField;
	private JButton fileButton;
	private JFileChooser fileChooser;
	
	public DownloadProgressPane() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout(0,20));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		init();
		addListener();
	}
	
	private void init() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setSelectedFile(new File(FileUtil.getAbsolutePath(".")));
		Singleton.put(fileChooser);
		
		headPanel = new JPanel(new BorderLayout(10,10));
		
		textLabel = new JLabel("缓存路径:");
		headPanel.add(textLabel, BorderLayout.WEST);
		
		textField = new JTextField(fileChooser.getSelectedFile().getAbsolutePath());
		textField.setEditable(false);
		headPanel.add(textField,BorderLayout.CENTER);
		
		fileButton = new JButton("选择");
		headPanel.add(fileButton, BorderLayout.EAST);
		
		add(headPanel, BorderLayout.NORTH);
		
		DownloadTableModel tableModel = new DownloadTableModel("","名称","进度");
		table = new JTable(tableModel);
		DownloadControlCellRenderer cellRenderer = new DownloadControlCellRenderer(table);
		table.getColumnModel().getColumn(0).setCellEditor(cellRenderer);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(40);
		Singleton.put(table);
		
		tablePanel = new JScrollPane(table);
		add(tablePanel, BorderLayout.CENTER);
	}
	
	private void addListener() {
		fileButton.addActionListener(e -> {
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			} 
		});
	}
	
	
}

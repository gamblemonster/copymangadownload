package download.gui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Singleton;
import download.gui.action.impl.DownloadTableMouseListener;
import download.gui.action.impl.RetryActionListener;
import download.gui.model.DownloadTableModel;

public class DownloadProgressPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private JScrollPane tablePanel;
	private JPanel headPanel;
	private JLabel textLabel;
	private JTextField textField;
	private JButton fileButton;
	private JFileChooser fileChooser;
	
	private JPopupMenu popupMenu;
	private JMenuItem retry;
	
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
		
		popupMenu = new JPopupMenu();
		retry = new JMenuItem("重试");
		
		popupMenu.add(retry);
		retry.addActionListener(new RetryActionListener());
		
		DownloadTableModel tableModel = new DownloadTableModel("名称","进度");
		table = new JTable(tableModel);
		table.addMouseListener(new DownloadTableMouseListener(popupMenu));
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

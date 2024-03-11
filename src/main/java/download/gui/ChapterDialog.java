package download.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import download.entity.Chapter;
import download.entity.Comic;
import download.gui.action.impl.ChapterTableMouseListener;
import download.gui.action.impl.DownloadActionListener;
import download.gui.model.ChapterTableModel;
import download.service.impl.ViewChapterImpl;

public class ChapterDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Comic comic;
	
	private JPanel mainPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private ChapterTableModel chapterTableModel;
	private JPanel buttonPanel;
	private JButton downloadAllButton;
	private JButton downloadSelectedButton;
	
	public ChapterDialog(JFrame parent,Comic comic) {
		// TODO Auto-generated constructor stub
		this.comic = comic;
		setModal(true);
		setSize(parent.getContentPane().getSize());
		init();
		addListner();
		getChapter();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(comic.getName());
		setLocationRelativeTo(parent);
		setVisible(true);
	}
	
	private void init() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(mainPanel);
		
		chapterTableModel = new ChapterTableModel("章节");
		table = new JTable(chapterTableModel);
		table.setToolTipText("双击查看");
		table.setName(comic.getName());
		scrollPane= new JScrollPane();
		scrollPane.setViewportView(table);
		mainPanel.add(scrollPane,BorderLayout.CENTER);
		
		buttonPanel =new JPanel(new BorderLayout());
		downloadAllButton = new JButton("下载全部");
		downloadSelectedButton = new JButton("下载选中");
		buttonPanel.add(downloadSelectedButton,BorderLayout.EAST);
		buttonPanel.add(downloadAllButton, BorderLayout.WEST);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50));
		
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
	}
	
	private void addListner() {
		table.addMouseListener(new ChapterTableMouseListener());
		DownloadActionListener downloadActionListener = new DownloadActionListener(table);
		downloadAllButton.addActionListener(downloadActionListener);
		downloadSelectedButton.addActionListener(downloadActionListener);
	}
	
	private void getChapter() {
		List<Chapter> chapters;
		try {
			chapters = new ViewChapterImpl(comic.getPath_word()).getChapterList();
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "服务器连接超时", "错误", JOptionPane.ERROR_MESSAGE);
			chapters = new ArrayList<>();
		}
		chapterTableModel.setChapters(chapters);
		table.updateUI();
	}
	
}

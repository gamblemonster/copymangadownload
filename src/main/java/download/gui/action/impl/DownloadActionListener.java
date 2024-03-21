package download.gui.action.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import download.entity.Chapter;
import download.gui.model.ChapterTableModel;
import download.service.impl.DownloadImpl;

public class DownloadActionListener implements ActionListener {
	private JTable table;
	
	public DownloadActionListener(JTable table) {
		// TODO Auto-generated constructor stub
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton) e.getSource();
		List<Chapter> chapters = new ArrayList<>();
		ChapterTableModel tableModel = (ChapterTableModel) table.getModel();
		int res = JOptionPane.showConfirmDialog(table.getRootPane(),"是否下载这些漫画？","提示", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (res != JOptionPane.YES_OPTION) {
			return;
		}
		if ("下载选中".equals(button.getText())) {
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				boolean b = (Boolean) tableModel.getValueAt(i, 0);
				if (b) chapters.add(tableModel.getChapters().get(i));
			}
		} else {
			chapters = tableModel.getChapters();
		}
		if (chapters.size() == 0) {
			JOptionPane.showMessageDialog(table.getRootPane().getParent(), "没有任何下载项","错误",JOptionPane.ERROR_MESSAGE);
			return ;
		}
		chapters.forEach(item -> {
			new DownloadImpl(item, table.getName());
		});
		JOptionPane.showMessageDialog(table.getRootPane().getParent(), "添加到了下载队列");
		
	}

}

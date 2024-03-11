package download.gui.action.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import cn.hutool.core.lang.Singleton;
import download.gui.model.DownloadTableModel;
import download.thread.ThreadPool;

public class RetryActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JTable table = Singleton.get(JTable.class);
		DownloadTableModel tableModel = (DownloadTableModel) table.getModel();
		int selected[] = table.getSelectedRows();
		for (int i = 0; i < selected.length; i++) {
			ThreadPool.execute(tableModel.getDownloadImpls().get(selected[i]));
		}
	}

}

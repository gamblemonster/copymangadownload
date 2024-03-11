package download.gui.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import cn.hutool.core.util.StrUtil;
import download.service.impl.DownloadImpl;

public class DownloadTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private Vector<DownloadImpl> downloadImpls;
	private String titles[];
	
	public DownloadTableModel(String ...titles) {
		// TODO Auto-generated constructor stub
		this.titles = titles;
		this.downloadImpls = new Vector<>();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return downloadImpls.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return downloadImpls.get(rowIndex).getName();
		} else {
			String progress = downloadImpls.get(rowIndex).getProgress() + "/" + downloadImpls.get(rowIndex).getTotal();
			if (!downloadImpls.get(rowIndex).isRun() && downloadImpls.get(rowIndex).getTotal() == 0) {
				return "等待中";
			} else if (downloadImpls.get(rowIndex).isRun()) {
				return progress;
			} else {
				return StrUtil.format("{}(失败{}页)", progress, downloadImpls.get(rowIndex).getTotal() - downloadImpls.get(rowIndex).getProgress());
			}
		}
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}
	
	public Vector<DownloadImpl> getDownloadImpls() {
		return downloadImpls;
	}

}

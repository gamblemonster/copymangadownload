package download.gui.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import cn.hutool.core.io.StreamProgress;
import download.enums.DownloadStatus;
import download.service.impl.DownloadImpl;

public class DownloadTableModel extends AbstractTableModel implements StreamProgress {
	private static final long serialVersionUID = 1L;
	
	private Vector<DownloadImpl> downloadImpls;
	private String titles[];
	
	// 最大数量
	private int maxNum = 4;

	public DownloadTableModel(String ...titles) {
		// TODO Auto-generated constructor stub
		this.titles = titles;
		this.downloadImpls = new Vector<>();
	}
	
	public void add(DownloadImpl download) {
		downloadImpls.add(download);
		download.setProgress(this);
		updateQueue();
	}
	
	private void updateQueue() {
		long currentNum = downloadImpls.stream().filter(downloadImpls -> downloadImpls.getDownloadStatus() == DownloadStatus.START).count();
		if (currentNum < maxNum) {
			downloadImpls.stream().filter(downloadImpls -> downloadImpls.getDownloadStatus() == DownloadStatus.WAIT)
					.limit(maxNum - currentNum).forEach(downloadImpls -> downloadImpls.start());
		}
		fireTableDataChanged();
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
			return downloadImpls.get(rowIndex).getDownloadStatus();
		} else if (columnIndex == 1) {
			return downloadImpls.get(rowIndex).getName();
		} else {
			String progress = downloadImpls.get(rowIndex).getProgress() + "/" + downloadImpls.get(rowIndex).getTotal();
			return progress;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			DownloadImpl downloadImpl = downloadImpls.get(rowIndex);
			DownloadStatus status = (DownloadStatus) aValue;
			switch (status) {
			case START:
				if (DownloadStatus.WAIT.equals(getValueAt(rowIndex, columnIndex))) {
					downloadImpl.start();
				} else {
					downloadImpl.resume();
				}
				break;
			case PAUSE:
				downloadImpl.pause();
				break;
			default:
				break;
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return columnIndex == 0;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void progress(long total, long progressSize) {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		updateQueue();
	}

}

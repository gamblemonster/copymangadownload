package download.gui.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import download.enums.DownloadStatus;
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
	
	public Vector<DownloadImpl> getDownloadImpls() {
		return downloadImpls;
	}

}

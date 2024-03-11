package download.service;

import java.io.Serializable;

import javax.swing.JTable;

import cn.hutool.core.lang.Singleton;
import download.gui.model.DownloadTableModel;

public abstract class Download implements Runnable,Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean isRun = false;

	protected abstract boolean download();
	
	public abstract long getTotal();
	
	public abstract long getProgress();
	
	public abstract String getName();
	
	public abstract String getId();
	
	
	public final boolean isRun() {
		// TODO Auto-generated method stub
		return isRun;
	}

	protected final void setRun(boolean isRun) {
		// TODO Auto-generated method stub
		this.isRun = isRun;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (isRun()) return;
		setRun(true);
		if (download()) {
			DownloadTableModel tableModel = (DownloadTableModel) Singleton.get(JTable.class).getModel();
			tableModel.getDownloadImpls().remove(this);
		}
		setRun(false);
	}
}

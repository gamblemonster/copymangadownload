package download.service;

import java.io.Serializable;

import javax.swing.JTable;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Singleton;
import download.enums.DownloadStatus;
import download.gui.model.DownloadTableModel;
import download.thread.DownloadThreadPool;

@SuppressWarnings("unused")
public abstract class Download implements Runnable,Serializable {
	private static final long serialVersionUID = 1L;
	
	private DownloadStatus status = DownloadStatus.WAIT;
	
	protected StreamProgress progress;

	protected abstract boolean download();
	
	public abstract long getTotal();
	
	public abstract long getProgress();
	
	public abstract String getName();
	
	public abstract String getId();
	
	
	public final DownloadStatus getDownloadStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	
	public final void start() {
		this.status = DownloadStatus.START;
		DownloadThreadPool.execute(this);
	}
	
	public final void pause() {
		this.status = DownloadStatus.PAUSE;
	}
	
	public final void resume() {
		start();
	}
	
	public final void setProgress(StreamProgress progress) {
		this.progress = progress;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (null != progress) progress.start();
		if (download()) {
			this.status = DownloadStatus.FINISH;
		} else {
			if (this.status != DownloadStatus.PAUSE) {
				this.status = DownloadStatus.FAILED;
			}
		}
		if (null != progress) progress.finish();
	}
}

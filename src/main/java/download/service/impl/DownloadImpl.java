package download.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import download.entity.Chapter;
import download.entity.DownloadEntity;
import download.gui.model.DownloadTableModel;
import download.service.Download;
import download.thread.ThreadPool;

public class DownloadImpl extends Download {
	private static final long serialVersionUID = 1L;
	
	private Chapter chapter;
	private String comicName;
	private String pathFormatter;
	private List<DownloadEntity> downloadEntities;
	
	public DownloadImpl(Chapter chapter,String comicName) {
		// TODO Auto-generated constructor stub
		this.chapter = chapter;
		this.comicName = comicName;
		downloadEntities = new ArrayList<>();
		pathFormatter = Singleton.get(JFileChooser.class).getSelectedFile().getAbsolutePath() + "/{}/{}/{}";
		start();
	}
	
	private void start() {
		DownloadTableModel tableModel = (DownloadTableModel) Singleton.get(JTable.class).getModel();
		if (tableModel.getDownloadImpls().stream().filter(item -> item.getId()==chapter.getId()).count() == 0) {
			tableModel.getDownloadImpls().add(this);
			Singleton.get(JTable.class).updateUI();
			ThreadPool.execute(this);
		}
	}

	@Override
	protected boolean download() {
		// TODO Auto-generated method stub
		if (null == chapter) {
			return true;
		}
		
		boolean success = true;
		
		if (downloadEntities.size() == 0) {
			new ImgSrcImpl(chapter.getId(), chapter.getPath_word()).getUrlsList().forEach(item -> {
				downloadEntities.add(new DownloadEntity(item));
			});
		}
		
		for (int i = 0; i < downloadEntities.size(); i++) {
			if (downloadEntities.get(i).isSuccess()) {
				continue;
			}
			try {
				HttpUtil.downloadFile(downloadEntities.get(i).getUrl(), 
						new File(StrUtil.format(pathFormatter, comicName, chapter.getName(), i + ".jpg")),
						60000);
				downloadEntities.get(i).setSuccess(true);
				Singleton.get(JTable.class).updateUI();
			} catch (Exception e) {
				// TODO: handle exception
				downloadEntities.get(i).setSuccess(false);
				success = false;
			}
		}
		
		return success;
	}

	@Override
	public long getTotal() {
		// TODO Auto-generated method stub
		return downloadEntities.size();
	}

	@Override
	public long getProgress() {
		// TODO Auto-generated method stub
		return downloadEntities.stream().filter(item -> item.isSuccess()).count();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return StrUtil.format("{}({})", comicName, chapter.getName());
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return chapter.getId();
	}

}

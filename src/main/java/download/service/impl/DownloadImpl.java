package download.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JTable;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import download.entity.Chapter;
import download.entity.DownloadEntity;
import download.enums.DownloadStatus;
import download.gui.model.DownloadTableModel;
import download.service.Download;

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
		updateTable();
	}
	
	private void updateTable() {
		DownloadTableModel tableModel = (DownloadTableModel) Singleton.get(JTable.class).getModel();
		tableModel.add(this);
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
			if (getDownloadStatus() != DownloadStatus.START) { // 其实在下面写这个是一样的，这里写可以少建文件和省网络请求
				success = false;
				break;
			}
			InputStream inputStream = null;
			BufferedInputStream bufferedInputStream = null;
			FileOutputStream outputStream = null;
			try {
				inputStream = HttpUtil.createGet(downloadEntities.get(i).getUrl()).timeout(30000).executeAsync().bodyStream();
				bufferedInputStream = new BufferedInputStream(inputStream);
				outputStream = new FileOutputStream(FileUtil.touch(new File(StrUtil.format(pathFormatter, comicName, chapter.getName(), i + ".jpg"))));
				byte[] buffer = new byte[2 * 1024];
				int length = 0;
				while ((length=bufferedInputStream.read(buffer)) > 0 && getDownloadStatus() == DownloadStatus.START) {
					outputStream.write(buffer, 0, length);
				}
				if (getDownloadStatus() == DownloadStatus.START) {
					downloadEntities.get(i).setSuccess(true);
				}
			} catch (Exception e) {
				// TODO: handle exception
				downloadEntities.get(i).setSuccess(false);
				success = false;
			} finally {
				IoUtil.close(outputStream);
				IoUtil.close(bufferedInputStream);
				IoUtil.close(inputStream);
			}
			if (null != progress) progress.progress(getTotal(), getProgress());
		}
		
		return getDownloadStatus() == DownloadStatus.START?success:false;
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

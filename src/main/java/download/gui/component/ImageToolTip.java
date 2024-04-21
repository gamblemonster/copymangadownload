package download.gui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.JToolTip;

import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;

public class ImageToolTip extends JToolTip {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	private final int maxWidth,maxHeight;
	private int fontSize = 12;
	
	private FIFOCache<String, ImageReadTask> imageCache;
	private short maxCacheSize = 100;
	
	public ImageToolTip() {
		// TODO Auto-generated constructor stub
		maxHeight = new BigDecimal(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.3).intValue();
		maxWidth = maxHeight/4*3; // 封面默认4:3
		setPreferredSize(new Dimension(maxWidth, maxHeight));
		imageCache = new FIFOCache<>(maxCacheSize);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		this.fontSize = g.getFont().getSize();
		String errString = "封面加载中";
		if (null == getTipText()) {
			errString = "未找到封面";
			g.drawString(errString, getWidth()/2 + fontSize*errString.length()/2, getHeight()/2 + fontSize/2);
			return;
		}
		
		String url = getTipText();
		ImageReadTask readTask = imageCache.get(url);
		if (null == readTask) {
			readTask =new ImageReadTask(url, (imageToolTip) -> {
				if (imageToolTip.getUrl().equals(ImageToolTip.this.getTipText())) {
					ImageToolTip.this.image = imageToolTip.getImage();
					ImageToolTip.this.repaint();
				}
			});
			imageCache.put(url, readTask);
			ThreadUtil.execute(readTask);
			this.image = null;
		} else if (readTask.isFinish()) {
			this.image = readTask.getImage();
		} else if (readTask.isError()) {
			errString = "加载失败，重新尝试中";
			ThreadUtil.execute(readTask);
			this.image = null;
		} else {
			this.image = null;
		}
		
		if (null == image) {
			Color defaultColor = g.getColor();
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(defaultColor);
			g.drawString(errString, getWidth()/2 - fontSize*errString.length()/2, getHeight()/2 + fontSize/2);
		} else {
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
}

class ImageReadTask implements Runnable {
	private boolean error;
	private boolean finish;
	private String url;
	private BufferedImage image;
	private Consumer<ImageReadTask> callback;
	
	public ImageReadTask(String url,Consumer<ImageReadTask> callback) {
		// TODO Auto-generated constructor stub
		this.url = url;
		this.callback = callback;
	}
	
	public boolean isFinish() {
		return finish;
	}
	
	public boolean isError() {
		return error;
	}
	
	public String getUrl() {
		return url;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.error = false;
		this.finish = false;
		try {
			this.image = ImageIO.read(HttpUtil.createGet(url).timeout(5000).execute().bodyStream());
			this.finish = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.error = true;
		}
		callback.accept(this);
	}
	
}


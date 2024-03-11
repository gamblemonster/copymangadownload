package download.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import javax.swing.JPanel;

public class ImgView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private double scale;
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if (null != image) {
			computedScale();
			
			int imageWidth = new BigDecimal(image.getWidth() * scale).intValue();
			int imageHeight = new BigDecimal(image.getHeight() * scale).intValue();
			
			int startX = (getWidth() - imageWidth) / 2;
			int startY = (getHeight() - imageHeight) / 2;
			
			g.drawImage(image, startX, startY, imageWidth, imageHeight, null);
		} else {
			g.drawString("此页加载失败，翻页或重新打开可尝试再次加载", 10, getHeight() / 2);
		}
	}
	
	private void computedScale() {
		int imageWidth =image.getWidth();
		int imageHeight = image.getHeight();
		
		if (imageHeight == 0 || getHeight() == 0) {
			return;
		}
		
		double imgRatio =imageWidth*1.0/imageHeight;
		double cmptRatio = getWidth()*1.0/getHeight();
		
		if (imgRatio > cmptRatio) { // 说明是宽图片，需要以宽作为比例放缩
			scale = getWidth()*1.0/imageWidth;
		} else { // 不是就是长图片，同理
			scale = getHeight()*1.0/imageHeight;
		}
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		updateUI();
	}

}

package download.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;

import javax.swing.JPanel;

public class ImgView extends JPanel implements MouseMotionListener,MouseWheelListener,MouseListener {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private double scale;
	
	private int startX,startY,imageWidth,imageHeight;
	
	private int mouseStartX,mouseStartY,mouseMoveX,mouseMoveY;
	
	private boolean setting;
	
	public ImgView() {
		// TODO Auto-generated constructor stub
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if (null != image) {
			if (!setting) {
				initImageInfo();
			}
			g.drawImage(image, startX + mouseMoveX, startY + mouseMoveY, imageWidth, imageHeight, null);
		} else {
			g.drawString("此页加载失败，翻页或重新打开可尝试再次加载", 10, getHeight() / 2);
		}
	}
	
	private void initImageInfo() {
		computedScale();
		
		imageWidth = new BigDecimal(image.getWidth() * scale).intValue();
		imageHeight = new BigDecimal(image.getHeight() * scale).intValue();
		
		startX = (getWidth() - imageWidth) / 2;
		startY = (getHeight() - imageHeight) / 2;
		
		mouseMoveX = mouseMoveY = 0;
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
	
	private boolean pointInImage(int x,int y) {
		return null!=image&&x>startX&&y>startY&&x<startX+imageWidth&&y<startY+imageHeight;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
		this.setting = false;
		repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if (!pointInImage(e.getX(), e.getY())) {
			return;
		}
		setting = true;
		
		double startXScale = (e.getX() - startX)/(imageWidth*1.0);
		double startYScale = (e.getY() - startY)/(imageHeight*1.0);
		if (e.getWheelRotation() < 0) {
			scale += scale < 3?0.1:0;
		} else {
			if (image.getWidth() * (scale-0.1) > getWidth()/2 || image.getHeight() * (scale-0.1) > getHeight()/2) {
				scale -= 0.1;
			}
		}
		imageWidth = new BigDecimal(image.getWidth() * scale).intValue();
		imageHeight = new BigDecimal(image.getHeight() * scale).intValue();
		startX -= (imageWidth * startXScale - (e.getX() - startX));
		startY -= (imageHeight * startYScale - (e.getY() - startY));
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseMoveX = e.getX() - mouseStartX;
		mouseMoveY = e.getY() - mouseStartY;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			setting = false;
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		setting = true;
		mouseStartX = e.getX();
		mouseStartY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		startX +=mouseMoveX;
		startY +=mouseMoveY;
		mouseMoveX = mouseMoveY = 0;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

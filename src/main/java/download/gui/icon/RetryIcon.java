package download.gui.icon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class RetryIcon implements Icon {
	private int iconWidth;
	private int iconHeight;
	private boolean autoSize;
	
	public RetryIcon() {
		// TODO Auto-generated constructor stub
		iconWidth = 10;
		iconHeight = 10;
		autoSize = true;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		if (autoSize) {
			iconWidth = c.getWidth() - 2*x;
			iconHeight = c.getHeight() - 2*y;
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		Polygon triangle = new Polygon();
		triangle.addPoint(x+iconWidth/2+1, y+iconHeight/2);
		triangle.addPoint(x+iconWidth, y+iconHeight/2);
		triangle.addPoint(x+iconWidth, y-1);
		g.fillPolygon(triangle);
		((Graphics2D)g).setStroke(new BasicStroke(1.2f));
		g.drawArc(x, y, iconWidth, iconHeight, 30, 300);
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return iconWidth;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return iconHeight;
	}
	
	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}
	
	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}
	
	public void setAutoSize(boolean autoSize) {
		this.autoSize = autoSize;
	}

}

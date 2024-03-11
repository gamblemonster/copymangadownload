package download.gui.icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class PlayIcon implements Icon {
	private int iconWidth;
	private int iconHeight;
	private Color color;
	private boolean autoSize;
	
	public PlayIcon() {
		// TODO Auto-generated constructor stub
		iconWidth = 10;
		iconHeight = 10;
		color = Color.BLACK;
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
		g.setColor(color);
		Polygon triangle = new Polygon();
		triangle.addPoint(x, y+iconHeight);
		triangle.addPoint(x+iconWidth, y+iconHeight/2);
		triangle.addPoint(x, y);
		g.fillPolygon(triangle);
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
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setAutoSize(boolean autoSize) {
		this.autoSize = autoSize;
	}

}

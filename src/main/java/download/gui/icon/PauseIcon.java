package download.gui.icon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class PauseIcon implements Icon {
	private int iconWidth;
	private int iconHeight;
	private Color color;
	private boolean autoSize;
	private int weight;
	
	public PauseIcon() {
		// TODO Auto-generated constructor stub
		iconWidth = 10;
		iconHeight = 10;
		color = Color.BLACK;
		autoSize = true;
		weight = 2;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		if (autoSize) {
			iconWidth = c.getWidth() - 2*x;
			iconHeight = c.getHeight() - 2*y;
		}
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)g).setStroke(new BasicStroke(weight));
		g.setColor(color);
		g.drawLine(x+weight, y+weight, x+weight, y + iconHeight-weight);
		g.drawLine(x+iconWidth-weight, y+weight, x+iconWidth-weight, y+iconHeight-weight);
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

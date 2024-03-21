package download.gui.icon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class SuccessIcon implements Icon {
	private int iconWidth;
	private int iconHeight;
	private boolean autoSize;
	private int weight;
	
	public SuccessIcon() {
		// TODO Auto-generated constructor stub
		iconWidth = 10;
		iconHeight = 10;
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
		g.setColor(Color.BLACK);
		((Graphics2D)g).setStroke(new BasicStroke(weight));
		g.drawLine(x, y + iconHeight/2, x + iconWidth/3, y + iconHeight);
		g.drawLine(x + iconWidth/3, y + iconHeight, x + iconWidth, y);
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

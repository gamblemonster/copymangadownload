package download.gui.action.impl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class DownloadTableMouseListener extends MouseAdapter {
	private JPopupMenu popupMenu;
	
	public DownloadTableMouseListener(JPopupMenu popupMenu) {
		// TODO Auto-generated constructor stub
		this.popupMenu = popupMenu;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON3) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}

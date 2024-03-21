package download.gui.action.impl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JTable;

import download.gui.ImgDialog;
import download.gui.model.ChapterTableModel;

public class ChapterTableMouseListener extends MouseAdapter {
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			JTable table = (JTable) e.getComponent();
			if (!"checkbox".equals(table.getColumnName(table.columnAtPoint(e.getPoint())))) {
				JDialog dialog = (JDialog) table.getRootPane().getParent();
				ChapterTableModel tableModel = (ChapterTableModel) table.getModel();
				new ImgDialog(dialog ,tableModel.getChapters().get(table.getSelectedRow()));
			}
		}
	}
}

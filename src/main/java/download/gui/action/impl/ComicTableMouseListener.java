package download.gui.action.impl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import cn.hutool.core.lang.Singleton;
import download.gui.ChapterDialog;
import download.gui.GUI;
import download.gui.model.ComicTableModel;

public class ComicTableMouseListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
			JTable table = (JTable) e.getComponent();
			ComicTableModel comicTableModel = (ComicTableModel) table.getModel();
			GUI gui = Singleton.get(GUI.class);
			gui.setTitle("加载中...");
			new ChapterDialog(gui,comicTableModel.getComics().get(table.getSelectedRow()));
			gui.setTitle("漫画");
		}
	}

}

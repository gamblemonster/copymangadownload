package download.gui.action.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.hutool.core.lang.Singleton;
import download.entity.Comic;
import download.gui.GUI;
import download.gui.action.Callback;
import download.gui.action.PaginationAction;
import download.gui.model.ComicTableModel;
import download.service.Searchs;
import download.service.impl.SearchsImpl;

public class SearchActionListener implements ActionListener,PaginationAction,KeyListener {
	private JTextField text;
	private ComicTableModel tableModel;
	private Callback callback;
	private int currentPage = 1;
	private int rows = 25;
	private int total = 0;
	private Searchs search;
	
	public SearchActionListener(JTextField textField,ComicTableModel comicTableModel,Callback callback) {
		// TODO Auto-generated constructor stub
		this.text = textField;
		this.tableModel = comicTableModel;
		this.callback = callback;
	}
	
	private void search() {
		Singleton.get(GUI.class).setTitle("搜索中");
		List<Comic> comics;
		try {
			comics = search.getComicList(currentPage, rows);
		} catch (Exception e) {
			// TODO: handle exception
			comics = new ArrayList<>();
			JOptionPane.showMessageDialog(Singleton.get(GUI.class), "服务器连接超时", "错误", JOptionPane.ERROR_MESSAGE);
		}
		tableModel.setComics(comics);
		total = search.getTotal();
		Singleton.get(GUI.class).setTitle("漫画");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("".equals(text.getText())) {
			JOptionPane.showMessageDialog(text.getRootPane(), "请输入查询值");
			return;
		}
		search = new SearchsImpl(text.getText());
		currentPage = 1;
		search();
		callback.call();
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return total;
	}

	@Override
	public void nextPage() {
		// TODO Auto-generated method stub
		currentPage++;
		search();
		callback.call();
	}

	@Override
	public void previousPage() {
		// TODO Auto-generated method stub
		currentPage--;
		search();
		callback.call();
	}

	@Override
	public int getCurrentpage() {
		// TODO Auto-generated method stub
		return currentPage;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return rows;
	}

	@Override
	public void onCurrentChange(int page) {
		// TODO Auto-generated method stub
		currentPage = page;
		search();
		callback.call();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if ("".equals(text.getText())) {
				JOptionPane.showMessageDialog(text.getRootPane(), "请输入查询值");
				return;
			}
			search = new SearchsImpl(text.getText());
			currentPage = 1;
			search();
			callback.call();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package download.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import download.entity.Comic;

public class ComicTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private List<Comic> comics;
	
	private String[] titles;
	
	public ComicTableModel(String... titles) {
		// TODO Auto-generated constructor stub
		comics = new ArrayList<>();
		this.titles = titles;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return comics.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Comic comic = comics.get(rowIndex);
		return 0 == columnIndex? comic.getName():comic.getAuthor();
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}
	
	public List<Comic> getComics() {
		return comics;
	}
	
	public void setComics(List<Comic> comics) {
		this.comics = comics;
	}

}

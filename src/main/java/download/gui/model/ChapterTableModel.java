package download.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import download.entity.Chapter;

public class ChapterTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private String[] titles;
	private List<Chapter> chapters;
	
	public ChapterTableModel(String... titles) {
		// TODO Auto-generated constructor stub
		this.titles = titles;
		this.chapters= new ArrayList<>();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return chapters.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return chapters.get(rowIndex).getName();
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}
	
	public List<Chapter> getChapters() {
		return chapters;
	}
	
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

}

package download.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import download.entity.Chapter;

public class ChapterTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private String[] titles;
	private List<Chapter> chapters;
	private boolean[] selected; // 状态不写到Chapter类里，防止实体类定义混乱
	
	public ChapterTableModel(String... titles) {
		// TODO Auto-generated constructor stub
		this.titles = titles;
		this.chapters= new ArrayList<>();
		this.selected = new boolean[chapters.size()];
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
		return columnIndex == 0?selected[rowIndex]:chapters.get(rowIndex).getName();
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return columnIndex == 0;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			selected[rowIndex] = (boolean) aValue;
		}
	}
	
	public List<Chapter> getChapters() {
		return chapters;
	}
	
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
		this.selected = new boolean[chapters.size()];
	}

}

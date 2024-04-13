package download.gui.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import download.gui.model.ComicTableModel;

public class ComicCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		ComicTableModel tableModel = (ComicTableModel) table.getModel();
		this.setToolTipText(tableModel.getComics().get(row).getCover());
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}

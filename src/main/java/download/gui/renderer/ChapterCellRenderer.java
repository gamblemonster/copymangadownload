package download.gui.renderer;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ChapterCellRenderer extends DefaultCellEditor implements TableCellRenderer {
	private static final long serialVersionUID = 1L;
	private JCheckBox checkBox;
	private JTable table;
	
	public ChapterCellRenderer(JTable table) {
		// TODO Auto-generated constructor stub
		super(new JCheckBox());
		this.checkBox = new JCheckBox();
		this.table = table;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		if (value instanceof Boolean) {
			checkBox.setSelected(((Boolean) value).booleanValue());
		}
		return checkBox;
	}
	
	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		super.stopCellEditing();
		TableHeaderRenderer tableHeaderRenderer = (TableHeaderRenderer) table.getTableHeader().getDefaultRenderer();
		int colnum = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			if ("checkbox".equals(table.getColumnName(i))) {
				colnum = i;
				break;
			}
		}
		int n = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			boolean b = (Boolean) table.getValueAt(i, colnum);
			if (b) n++;
		}
		tableHeaderRenderer.setSelected(n == table.getRowCount());
		table.getTableHeader().repaint();
		return true;
	}

}

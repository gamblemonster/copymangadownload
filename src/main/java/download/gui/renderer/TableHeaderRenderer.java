package download.gui.renderer;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class TableHeaderRenderer extends MouseAdapter implements TableCellRenderer {
	private JTable table;
	private JCheckBox checkBox;
	private JLabel label;
	
	public TableHeaderRenderer(JTable table) {
		// TODO Auto-generated constructor stub
		this.table = table;
		table.getTableHeader().addMouseListener(this);
		checkBox = new JCheckBox();
		label = new JLabel();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int selectColumn = table.getTableHeader().columnAtPoint(e.getPoint());
		if ("checkbox".equals(table.getColumnName(selectColumn))) {
			TableModel tableModel = table.getModel();
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				tableModel.setValueAt(!checkBox.isSelected(), i, 0);
			}
			checkBox.setSelected(!checkBox.isSelected());
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		if ("checkbox".equals(value)) {
			return checkBox;
		} else {
			label.setText(String.valueOf(value));
			return label;
		}
	}
	
	public void setSelected(boolean b) {
		checkBox.setSelected(b);
	}

}

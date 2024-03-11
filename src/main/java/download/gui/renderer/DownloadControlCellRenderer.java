package download.gui.renderer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import download.gui.icon.PauseIcon;
import download.gui.icon.PlayIcon;

public class DownloadControlCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private int column;
	private JButton renderButton;  
	private JButton editButton;
	private int code;
	private PlayIcon playIcon;
	private PauseIcon pauseIcon;
	
	public DownloadControlCellRenderer() {
		// TODO Auto-generated constructor stub
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);  
		editButton.addActionListener(this);  
		
		playIcon = new PlayIcon();
		pauseIcon = new PauseIcon();
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		code = (int) value;
		editButton.setIcon(Integer.valueOf(1)==value?pauseIcon:playIcon);
		return editButton;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		this.table = table;
		this.column = column;
		renderButton.setIcon(Integer.valueOf(code)==value?pauseIcon:playIcon);
		return renderButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		fireEditingStopped();
		table.getModel().setValueAt(1, table.getSelectedColumn(), column);
	}

}

package download.gui.renderer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import download.enums.DownloadStatus;
import download.gui.icon.PauseIcon;
import download.gui.icon.PlayIcon;
import download.gui.icon.RetryIcon;
import download.gui.icon.SuccessIcon;

public class DownloadControlCellRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JButton renderButton;  
	private JButton editButton;
	private JLabel finishLabel;
	private DownloadStatus code;
	private PlayIcon playIcon;
	private PauseIcon pauseIcon;
	private RetryIcon retryIcon;
	private SuccessIcon successIcon;
	
	public DownloadControlCellRenderer(JTable table) {
		// TODO Auto-generated constructor stub
		this.table = table;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);  
		editButton.addActionListener(this);
		
		playIcon = new PlayIcon();
		pauseIcon = new PauseIcon();
		retryIcon = new RetryIcon();
		successIcon = new SuccessIcon();
		
		finishLabel = new JLabel(successIcon,JLabel.CENTER);
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		code = (DownloadStatus) value;
		Component component = editButton;
		switch (code) {
		case START:
			editButton.setIcon(pauseIcon);
			break;
		case PAUSE:
		case WAIT:
			editButton.setIcon(playIcon);
			break;
		case FAILED:
			editButton.setIcon(retryIcon);
			break;
		default:
			component = finishLabel;
			break;
		}
		return component;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		Component component = renderButton;
		DownloadStatus status = (DownloadStatus) value;
		switch (status) {
		case START :
			renderButton.setIcon(pauseIcon);
			break;
		case PAUSE:
		case WAIT:
			renderButton.setIcon(playIcon);
			break;
		case FAILED:
			renderButton.setIcon(retryIcon);
		default:
			component = finishLabel;
			break;
		}
		return component;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DownloadStatus status = (DownloadStatus) table.getValueAt(table.getSelectedRow(), 0);
		switch (status) {
		case WAIT:
		case PAUSE:
		case FAILED:
			code = DownloadStatus.START;
			break;
		case START:
			code = DownloadStatus.PAUSE;
			break;
		default:
			break;
		}
		fireEditingStopped();
	}

}

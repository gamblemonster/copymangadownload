package download.gui.component;

import javax.swing.JComboBox;

import cn.hutool.core.util.StrUtil;
import download.gui.action.Callback;
import download.gui.action.PaginationAction;

class PageComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;
	
	private static final String formatString = "第{}/{}页";
	
	private PaginationAction action;
	
	private Callback callback;
	
	private boolean state = true;
	
	private int total = 0;
	
	public PageComboBox(PaginationAction action,Callback callback) {
		// TODO Auto-generated constructor stub
		this.action = action;
		this.callback = callback;
		addListener();
	}
	
	private void addListener() {
		addActionListener(l -> {
			if (state && getSelectedIndex() != -1) { // 防止重复触发事件
				action.onCurrentChange(getSelectedIndex() + 1);
				callback.call();
			}
		});
	}
	
	public void setTotal(int total) {
		if (this.total != total) {
			this.state = false;
			removeAllItems();
			for (int i = 0; i < total; i++) {
				addItem(StrUtil.format(formatString, i+1,total));
			}
			this.total =total;
			this.state = true;
		}
	}
	
	public void setSelectedIndex(int anIndex,boolean state) {
		// TODO Auto-generated method stub
		this.state = state;
		setSelectedIndex(anIndex);
		this.state = true;
	}
	
	public void setActionListener(PaginationAction paginationAction) {
		this.action = paginationAction;
	}
}

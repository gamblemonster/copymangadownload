package download.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hutool.core.util.StrUtil;
import download.gui.action.PaginationAction;
import download.gui.action.impl.DefaultPaginationAction;

public class Pagination extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton prevButton;
	private JButton nextButton;
	private JLabel totalLabel;
	private JPanel pagePanel;
	private PageComboBox comboBox;
	
	private PaginationAction action;
	
	public Pagination() {
		this(new DefaultPaginationAction());
	}
	
	public Pagination(PaginationAction paginationAction) {
		// TODO Auto-generated constructor stub
		this.action = paginationAction;
		setLayout(new BorderLayout());
		init();
		addListener();
		initItems();
		updateComponent();
	}
	
	private void init() {
		prevButton = new JButton("上一页");
		nextButton = new JButton("下一页");
		totalLabel = new JLabel();
		pagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		comboBox = new PageComboBox(action,() -> updateButton());
		
		pagePanel.add(comboBox);
		pagePanel.add(totalLabel);
		
		add(prevButton,BorderLayout.WEST);
		add(pagePanel,BorderLayout.CENTER);
		add(nextButton,BorderLayout.EAST);
	}
	
	private void addListener() {
		prevButton.addActionListener(e -> previous());
		nextButton.addActionListener(e -> next());
	}
	
	private void next() {
		action.nextPage();
		updateButton();
		setSelectedPage();
	}
	
	private void previous() {
		action.previousPage();
		updateButton();
		setSelectedPage();
	}
	
	private void setSelectedPage() {
		comboBox.setSelectedIndex(action.getCurrentpage() - 1,false);
	}
	
	private void initItems() {
		int pageNum = action.getTotal()==0?1:action.getTotal()/action.getPageSize() + (action.getTotal()%action.getPageSize()==0?0:1);
		comboBox.setTotal(pageNum);
	}
	
	private void updateButton() {
		if (action.getCurrentpage() * action.getPageSize() >= action.getTotal()) {
			nextButton.setEnabled(false);
		} else {
			nextButton.setEnabled(true);
		}
		if (action.getCurrentpage() <= 1) {
			prevButton.setEnabled(false);
		} else {
			prevButton.setEnabled(true);
		}
		if (action.getTotal() == 0) {
			comboBox.setEnabled(false);
		} else {
			comboBox.setEnabled(true);
		}
	}
	
	public void updateComponent() {
		updateButton();
		initItems();
		setSelectedPage();
		totalLabel.setText(StrUtil.format("共{}条",action.getTotal()));
	}
	
	public void setActionListener(PaginationAction paginationAction) {
		this.action = paginationAction;
		comboBox.setActionListener(paginationAction);
	}
}

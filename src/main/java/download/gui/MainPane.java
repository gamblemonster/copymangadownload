package download.gui;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import download.gui.action.impl.ComicTableMouseListener;
import download.gui.action.impl.SearchActionListener;
import download.gui.model.ComicTableModel;

public class MainPane extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel searchPanel;
	private JLabel searchLabel;
	private JTextField searchField;
	private JButton serachButton;
	
	private Pagination pagination;
	
	private JScrollPane tablePanel;
	private JTable comicTable;
	private ComicTableModel comicTableModel;
	
	public MainPane() {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout(0,20));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		init();
		groupCmpt();
		addListener();
	}
	
	
	private void init() {
		searchPanel = new JPanel(new BorderLayout(10,10));
		
		searchLabel = new JLabel("关键字：");
		searchField = new JTextField();
		serachButton = new JButton("搜索");
		
		pagination = new Pagination();

		tablePanel = new JScrollPane();
		comicTableModel = new ComicTableModel("名称","作者");
		comicTable = new JTable(comicTableModel);
		comicTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		comicTable.setToolTipText("双击查看详情");
	}
	
	private void groupCmpt() {
		searchPanel.add(searchLabel,BorderLayout.WEST);
		searchPanel.add(searchField,BorderLayout.CENTER);
		searchPanel.add(serachButton,BorderLayout.EAST);
		
		add(searchPanel,BorderLayout.NORTH);
		
		add(pagination,BorderLayout.SOUTH);
		
		tablePanel.setViewportView(comicTable);
		add(tablePanel);
	}
	
	private void addListener() {
		SearchActionListener searchActionListener = new SearchActionListener(searchField, comicTableModel,() -> {
			comicTable.updateUI();
			pagination.updateComponent();
		});
		serachButton.addActionListener(searchActionListener);
		pagination.setActionListener(searchActionListener);
		searchField.addKeyListener(searchActionListener);
		comicTable.addMouseListener(new ComicTableMouseListener());
	}
}

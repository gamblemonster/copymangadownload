package download.gui;

import java.awt.Toolkit;
import java.math.BigDecimal;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
	
	public GUI() {
		init();
		int width = new BigDecimal(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5).intValue();
		int height = new BigDecimal(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.5).intValue();
		setTitle("漫画");
		setSize(width,height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(tabbedPane);
		setVisible(true);
	}
	
	private void init() {
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("搜索漫画",new MainPane());
		tabbedPane.addTab("下载进度", new DownloadProgressPane());
	}

}
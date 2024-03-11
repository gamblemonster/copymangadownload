package download;

import java.awt.EventQueue;
import javax.swing.UIManager;

import cn.hutool.core.lang.Singleton;
import download.gui.GUI;

public class Start {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(() -> {
			Singleton.put(new GUI());
		});
	}

}

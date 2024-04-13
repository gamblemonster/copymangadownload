package download.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpUtil;
import download.entity.Chapter;
import download.gui.action.PaginationAction;
import download.gui.component.ImgView;
import download.gui.component.Pagination;
import download.service.impl.ImgSrcImpl;

public class ImgDialog extends JDialog implements PaginationAction{
	private static final long serialVersionUID = 1L;
	
	private JPanel mainJPanel;
	private ImgView imageView;
	private Pagination pagination;
	private JDialog parent;
	
	private String parentTitle;
	private boolean isInit;
	
	private Chapter chapter;
	
	private List<String> urlList;
	private BufferedImage[] imgArray;
	
	private int currentPage = 1;
	private int total = 0;
	private double progerss;

	public ImgDialog(JDialog parent, Chapter chapter) {
		// TODO Auto-generated constructor stub
		super(parent);
		this.chapter = chapter;
		this.parent = parent;
		this.isInit = false;
		parentTitle = parent.getTitle();
		
		parent.setTitle("加载中...");
		
		getImage();
		int width = new BigDecimal(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.95/1.5).intValue();
		int height = new BigDecimal(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.95).intValue();
		setSize(width, height);
		
		init();
		this.isInit = true;
		parent.setTitle(parentTitle);
		
		pagination.updateComponent();
		
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle(chapter.getName());
		setVisible(true);
	}
	
	private void init() {
		mainJPanel = new JPanel(new BorderLayout());
		mainJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		pagination = new Pagination(this);
		mainJPanel.add(pagination, BorderLayout.SOUTH);
		
		imageView = new ImgView();
		mainJPanel.add(imageView, BorderLayout.CENTER);
		imageView.setImage(getImage(currentPage-1));
		
		add(mainJPanel);
	}
	
	private void getImage() {
		urlList = new ImgSrcImpl(chapter.getId(),chapter.getPath_word()).getUrlsList();
		imgArray = new BufferedImage[urlList.size()];
		total = urlList.size();
	}
	
	private BufferedImage getImage(int index) {
		BufferedImage result = null;
		if (index > -1 && index < imgArray.length) {
			if (imgArray[index] == null) {
				InputStream stream = null;
				// 图片服务太慢，直接加载太容易失败，只能绕远路
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					HttpUtil.createGet(urlList.get(index), true)
					.setConnectionTimeout(5000)
					.setReadTimeout(15000).executeAsync()
					.writeBody(outputStream, true, new StreamProgress() {
						
						@Override
						public void start() {
							// TODO Auto-generated method stub
							setTitle("加载中 Loading... 0%");
							if (!isInit) {
								parent.setTitle("加载中 Loading... 0%");
							}
						}
						
						@Override
						public void progress(long total, long progressSize) {
							// TODO Auto-generated method stub
							progerss = progressSize*1.0/total;
							setTitle("加载中 Loading... " + (Math.round(progerss*100)) + "%");
							if (!isInit) {
								parent.setTitle("加载中 Loading... " + (Math.round(progerss*100)) + "%");
							}
						}
						
						@Override
						public void finish() {
							// TODO Auto-generated method stub
							
						}
					});
					stream = new ByteArrayInputStream(outputStream.toByteArray());
					imgArray[index] = ImageIO.read(stream);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "服务器连接超时", "错误", JOptionPane.ERROR_MESSAGE);
				} finally {
					// byte流关不关无所谓的，但关流习惯还是得有
					IoUtil.close(stream);
					IoUtil.close(outputStream);
				}
			}
			result = imgArray[index];
		}
		
		return result;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return total;
	}

	@Override
	public void nextPage() {
		// TODO Auto-generated method stub
		currentPage++;
		setTitle("加载中 Loading...");
		imageView.setImage(getImage(currentPage-1));
		setTitle(chapter.getName());
	}

	@Override
	public void previousPage() {
		// TODO Auto-generated method stub
		currentPage--;
		setTitle("加载中 Loading...");
		imageView.setImage(getImage(currentPage-1));
		setTitle(chapter.getName());
	}

	@Override
	public int getCurrentpage() {
		// TODO Auto-generated method stub
		return currentPage;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void onCurrentChange(int page) {
		// TODO Auto-generated method stub
		currentPage = page;
		setTitle("加载中 Loading...");
		imageView.setImage(getImage(currentPage-1));
		setTitle(chapter.getName());
	}
}

package download.entity;

import java.io.Serializable;

public class DownloadEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private boolean use;
	private String url;
	public DownloadEntity(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isUse() {
		return use;
	}
	public void setUse(boolean use) {
		this.use = use;
	}
	public String getUrl() {
		return url;
	}
}

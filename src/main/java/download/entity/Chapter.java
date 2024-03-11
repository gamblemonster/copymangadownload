package download.entity;

import java.io.Serializable;

public class Chapter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String id;
	private String path_word;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath_word() {
		return path_word;
	}
	public void setPath_word(String path_word) {
		this.path_word = path_word;
	}

}

package download.service;

import java.util.List;

import download.entity.Comic;

public interface Searchs {
	List<Comic> getComicList(Integer page, Integer rows);
	int getTotal();
}

package download.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import download.api.API;
import download.entity.Comic;
import download.service.Searchs;

public class SearchsImpl implements Searchs {
	
	private String searchKey;
	private int total;
	
	public SearchsImpl(String searchKey) {
		// TODO Auto-generated constructor stub
		this.searchKey = searchKey;
	}

	public List<Comic> getComicList(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		String string = HttpUtil.get(StrUtil.format(API.SEARCH_URL, searchKey, (page-1)*rows, rows));
		JSONObject results = JSONObject.parseObject(string).getJSONObject("results");
		total = results.getIntValue("total");
		JSONArray list = results.getJSONArray("list");
		List<Comic> comics = new ArrayList<Comic>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject comicJsonObject = list.getJSONObject(i);
			Comic comic = new Comic();
			comic.setName(comicJsonObject.getString("name"));
			comic.setCover(comicJsonObject.getString("cover"));
			comic.setPath_word(comicJsonObject.getString("path_word"));
			JSONArray authorArray = comicJsonObject.getJSONArray("author");
			if (authorArray.size()>0) {
				comic.setAuthor(authorArray.getJSONObject(0).getString("name"));
			} else {
				comic.setAuthor("-");
			}
			
			comics.add(comic);
		}
		return comics;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return total;
	}

}

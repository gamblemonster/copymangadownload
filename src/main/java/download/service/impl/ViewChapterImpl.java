package download.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import download.api.API;
import download.entity.Chapter;
import download.service.ViewChapter;

public class ViewChapterImpl implements ViewChapter {
	private String path_word;
	
	public ViewChapterImpl(String path_word) {
		// TODO Auto-generated constructor stub
		this.path_word = path_word;
	}

	@Override
	public List<Chapter> getChapterList() {
		// TODO Auto-generated method stub
		String jsonString = HttpUtil.get(StrUtil.format(API.CHAPTER_URL, path_word));
		String string = JSONObject.parseObject(jsonString).getString("results");
		AES aes = new AES(Mode.CBC,Padding.PKCS5Padding,API.PASSWORD.getBytes(CharsetUtil.CHARSET_UTF_8),string.substring(0, 16).getBytes(CharsetUtil.CHARSET_UTF_8));
		JSONArray list = JSONObject.parseObject(aes.decryptStr(string.substring(16), CharsetUtil.CHARSET_UTF_8))
				.getJSONObject("groups")
				.getJSONObject("default")
				.getJSONArray("chapters");
		List<Chapter> chapters = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			JSONObject chapterJsonObject = list.getJSONObject(i);
			Chapter chapter = new Chapter();
			chapter.setId(chapterJsonObject.getString("id"));
			chapter.setName(chapterJsonObject.getString("name"));
			chapter.setPath_word(path_word);
			chapters.add(chapter);
		}
		return chapters;
	}

}

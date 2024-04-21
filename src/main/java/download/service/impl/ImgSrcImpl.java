package download.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import download.api.API;
import download.service.ImgSrc;

public class ImgSrcImpl implements ImgSrc {
	
	private String id;
	private String path_word;
	
	public ImgSrcImpl(String id,String path_word) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.path_word = path_word;
	}

	@Override
	public List<String> getUrlsList() {
		// TODO Auto-generated method stub
		String string = HttpUtil.get(StrUtil.format(API.IMG_LIST_URL, path_word,id));
		String cipher = ReUtil.get("<div class=\"imageData\" contentKey=\"(.*?)\">", string, 1);
		AES aes = new AES(Mode.CBC,Padding.PKCS5Padding,API.PASSWORD.getBytes(CharsetUtil.CHARSET_UTF_8),cipher.substring(0, 16).getBytes(CharsetUtil.CHARSET_UTF_8));
		JSONArray array = JSONArray.parseArray(aes.decryptStr(cipher.substring(16), CharsetUtil.CHARSET_UTF_8));
		List<String> urList = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			urList.add(array.getJSONObject(i).getString("url"));
		}
		return urList;
	}
	
}

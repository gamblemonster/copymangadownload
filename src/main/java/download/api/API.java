package download.api;

import cn.hutool.core.util.StrUtil;

public interface API {
	String MAIN_URL = "www.mangacopy.com";
	String SEARCH_URL = StrUtil.format("https://{}/api/kb/web/searcha/comics?q={}&offset={}&platform=2&limit={}", MAIN_URL);
	String CHAPTER_URL = StrUtil.format("https://{}/comicdetail/{}/chapters", MAIN_URL);
	String IMG_LIST_URL = StrUtil.format("https://{}/comic/{}/chapter/{}", MAIN_URL);
	String PASSWORD = "xxxmanga.woo.key";
}

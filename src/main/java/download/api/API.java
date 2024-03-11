package download.api;

public interface API {
	String MAIN_URL = "www.mangacopy.com";
	String SEARCH_URL = "https://{}/api/kb/web/searcha/comics?q={}&offset={}&platform=2&limit={}";
	String CHAPTER_URL = "https://{}/comicdetail/{}/chapters";
	String IMG_LIST_URL = "https://{}/comic/{}/chapter/{}";
	String PASSWORD = "xxxmanga.woo.key";
}

package download.gui.action;

public interface PaginationAction {
	int getTotal();
	void nextPage();
	void previousPage();
	int getCurrentpage();
	int getPageSize();
	void onCurrentChange(int page);
}

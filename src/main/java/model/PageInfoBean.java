package model;

import java.util.List;
import java.util.ArrayList;

public class PageInfoBean<T> {
	public final static Integer DEFAULT_PAGE_SIZE=8;//首頁分頁預設pageSize值
	public final static Integer DEFAULT_CHAPTER_SIZE=1;//章節分類預設pageSize值
	public final static Integer DEFAULT_NAVIGATE_PAGES=3;//預設首頁導航頁碼數
	public final static Integer DEFAULT_CHAPTER_NAVIGATE_PAGES=1;//預設章節導航頁碼數
	// 當前頁: 預設值為1
	private int pageNum;
	// 每頁的資料數
	private int pageSize;
	// 當前頁的資料數(最後一頁數量可能不同)
	private int size;

	// 當前頁的第一個元素在資料庫中的行號
	private int startRow;
	// 當前頁的最後一個元素在資料庫中的行號
	private int endRow;
	// 總資料數
	private int total;
	// 總頁數
	private int pages;
	// 結果集
	private List<T> list=new ArrayList<T>();

	// 前一頁
	private int prePage;
	// 下一頁
	private int nextPage;

	// 是否為第一頁
	private boolean isFirstPage = false;
	// 是否為最後一頁
	private boolean isLastPage = false;
	// 是否有前一頁
	private boolean hasPreviousPage = false;
	// 是否有後一頁
	private boolean hasNextPage = false;
	
	// 導航頁碼數
	private int navigatePages;
	// 所有導航頁號碼
	private int[] navigatepageNums;
	// 導航條上的第一頁
	private int navigateFirstPage;
	// 導航條上的最後一頁
	private int navigateLastPage;

	public PageInfoBean() {
	}
	
	public PageInfoBean(int pageNum, int pageSize,int total,List<T> list) {
		this(pageNum, pageSize, total, list, DEFAULT_NAVIGATE_PAGES);
	}
	
	/**
	 * 設定初始化資料
	 * @param pageNum 當前頁
	 * @param pageSize 每頁資料數
	 * @param total 總資料數
	 * @param list 資料集
	 * @param navigatePages 導航顯示數
	 */
	public PageInfoBean(int pageNum, int pageSize,int total,List<T> list, int navigatePages) {
		//TODO:如果pageNum與pageSize給定不合理，設定預設值(不進行分頁)
		
			this.pageNum=pageNum;
			this.pageSize=pageSize;
			this.list=list;
			this.navigatePages=navigatePages;
			this.total=total;
			
			//計算開始與結束的資料列
			calculateStartAndEndRow();
			//計算size
			this.size=this.endRow-this.startRow+1;
			
			//計算總頁數
			this.pages=(int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
			//計算導航數
			calcNavigatepageNums();
			//計算前/後頁
			calcPage();
			//設定判斷邊界頁面
			judgePageBoudary();
	}
	/**
	 * 計算開始和結束的資料列
	 */
    private void calculateStartAndEndRow() {
    	this.startRow = (this.pageNum - 1) * this.pageSize + 1;
    	this.endRow = ((this.startRow-1 + this.pageSize)>this.total) ? (this.total) : (this.startRow-1 + this.pageSize);
    }
	/**
	 * 計算導航頁數
	 */
	private void calcNavigatepageNums() {
		// 當總頁數小於或等於導航頁碼數時
		if (pages <= navigatePages) {
			navigatepageNums = new int[pages];
			for (int i = 0; i < pages; i++) {
				navigatepageNums[i] = i + 1;
			}
		} else { // 當總頁數大於導航頁碼數時
			navigatepageNums = new int[navigatePages];
			int startNum = pageNum - navigatePages / 2;
			int endNum = pageNum + navigatePages / 2;

			if (startNum < 1) {
				startNum = 1;
				// 最前navigatePages頁
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			} else if (endNum > pages) {
				endNum = pages;
				// 最後navigatePages頁
				for (int i = navigatePages - 1; i >= 0; i--) {
					navigatepageNums[i] = endNum--;
				}
			} else {
				// 所有中間頁
				for (int i = 0; i < navigatePages; i++) {
					navigatepageNums[i] = startNum++;
				}
			}
		}
	}

	/**
	 * 計算前後頁、第一頁、最後一頁
	 */
	private void calcPage() {
		if (navigatepageNums != null && navigatepageNums.length > 0) {
			navigateFirstPage = navigatepageNums[0];
			navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
			if (pageNum > 1) {
				prePage = pageNum - 1;
			}
			if (pageNum < pages) {
				nextPage = pageNum + 1;
			}
		}
	}
	
	/**
	 * 判定頁面邊界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNum == 1;
		isLastPage = pageNum == pages || pages == 0;
		hasPreviousPage = pageNum > 1;
		hasNextPage = pageNum < pages;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public int getNavigateFirstPage() {
		return navigateFirstPage;
	}

	public void setNavigateFirstPage(int navigateFirstPage) {
		this.navigateFirstPage = navigateFirstPage;
	}

	public int getNavigateLastPage() {
		return navigateLastPage;
	}

	public void setNavigateLastPage(int navigateLastPage) {
		this.navigateLastPage = navigateLastPage;
	}

}

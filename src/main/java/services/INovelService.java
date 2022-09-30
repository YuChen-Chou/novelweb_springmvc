package services;

import java.util.List;

import model.Chapter;
import model.Classification;
import model.Novels;
import model.PageInfoBean;
import model.Slideshow;

public interface INovelService {
	
	/**
	 * 查詢id返回Novels物件
	 * @param id
	 * @return Novels物件
	 */
	Novels findById(Integer id);
	
	/**
	 * 新增Novels物件，注意:必須判斷書名是否存在，若存在則拋出異常
	 * @param novels
	 */
	void addNovels(Novels novels,String imgPath);
	
	/**
	 * 更新Novels物件，注意:必須判斷書名是否存在，若存在則拋出異常
	 * @param novels
	 */
	void updateNovels(Novels novels,String imgPath);
	
	/**
	 * 刪除Novels物件，注意:必須判斷書名是否存在，若存在則拋出異常
	 * @param novels
	 */
	void deleteNovels(Novels novels);
	
	
	/**
	 * 查詢全部Novels物件
	 * @return List<Novels>
	 */
	List<Novels> findAllNovels();
	
	
	/**
	 * 
	 * @param authorId 作者id
	 * @return List<Novels>
	 */
	List<Novels> findNovelsByAuthorId(int authorId);
	
	/**
	 * 查詢全部分類
	 * @return Classification
	 */
	List<Classification> findAllNovelsClassification();
	
	/**
	 * 查詢分類
	 * @return Classification
	 */
	Classification findNovelsClassification(int id);
	
	/**
	 * 新增Novels物件，注意:必須判斷書名是否存在，若存在則拋出異常
	 * @param novels
	 */
	void addChapter(Chapter chapter,String path);
	
	/**
	 * 更新Novels物件，注意:必須判斷書名是否存在，若存在則拋出異常
	 * @param novels
	 */
	void updateChapter(Chapter chapter);
	
	/**
	 * 查詢全部Chapter物件
	 * @return List<Chapter>
	 */
	List<Chapter> findAllChapterByNovel(Novels novels);
	
	/**
	 * 查詢分類的小說list
	 * @param id
	 * @return 小說list
	 */
	List<Novels> findAllNovelsByClassId(String id);
	
	/**
	 * 最新的小說排序
	 * @return
	 */
	List<Novels> findNewAllNovels();
	
	/**
	 * 最新的小說排序-分頁
	 * @return
	 */
	PageInfoBean<Novels> findNewAllByPage(int pageNum);
	
	/**
	 * 查詢分類的小說list-分頁
	 * @return
	 */
	PageInfoBean<Novels> findNovelsByClassIdByPage(String classid, int pageNum);
	
	/**
	 * 前4個最新更新的小說List
	 * @return List<Novels>
	 */
	List<Novels> findNewNovelsTop4();
	
	/**
	 * 找該章節id的章節物件
	 * @param chapterid
	 * @return Chapter 物件
	 */
	Chapter findChapterByChapterId(int chapterid);
	
	/**
	 * 查詢小說chapter-分頁
	 * @return PageInfoBean<Chapter>
	 */
	PageInfoBean<Chapter> findChaptersByNovelIdByPage(String novelid, int pageNum);
	
	/**
	 * 查找該searchString關鍵字相關的小說
	 * @param searchString
	 * @param pageNum
	 * @return PageInfoBean<Novels>
	 */
	PageInfoBean<Novels> findNovelsBySearchStringByPage(String searchString, Integer pageNum);

	/**
	 * 新增小說分類
	 * @param classification
	 */
	void addClassification(Classification classification);
	
	/**
	 * 更新小說分類名稱
	 * @param classification
	 */
	void updateClassification(Classification classification);
	
	/**
	 * 查詢啟用的幻燈片清單
	 * @return List<Slideshow>
	 */
	List<Slideshow> findAllEnableSlideshowList();
}

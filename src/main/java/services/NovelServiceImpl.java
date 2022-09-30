package services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AuthorDao;
import dao.ChapterDao;
import dao.ClassificationDao;
import dao.NovelsDao;
import dao.SlideshowDao;
import exception.DaoException;
import model.Author;
import model.Chapter;
import model.Classification;
import model.Novels;
import model.PageInfoBean;
import model.Slideshow;

@Service("novelService")
public class NovelServiceImpl implements INovelService {
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private NovelsDao novelsDao;
	
	@Autowired
	private ChapterDao chapterDao;
	
	@Autowired
	private ClassificationDao classificationDao;
	
	@Autowired
	private SlideshowDao slideshowDao;

	@Override
	public Novels findById(Integer id) {
		
		if(novelsDao.ckeckNovelIdIsExist(id)) {
			return novelsDao.queryId(id);
		}
		throw new DaoException("小說ID不存在");
	}
	
	@Override
	public void addNovels(Novels novels,String imgPath) {
		
		System.out.println("===========addNovels=========");
		System.out.println(novels.getName());
		System.out.println(novels.getAuthor().getId());
		System.out.println(novels.getIntroduction());
		System.out.println(novels.getClassification().getId());
		System.out.println(novels.getStatus().getName());
		System.out.println(novels.getPicture());
		//1.需要作者及分類物件
		Author author = authorDao.queryId(novels.getAuthor().getId());
		novels.setAuthor(author);
		Classification classification = findNovelsClassification(novels.getClassification().getId());
		novels.setClassification(classification);
		if(!imgPath.isEmpty()) {
			novels.setPicture(imgPath);
		}
		novelsDao.add(novels);
	}

	@Override
	public void updateNovels(Novels novels, String imgPath) {
		//查找小說
		Novels q_novels = novelsDao.queryId(novels.getId());
		//1.需要作者及分類物件
		Author author = authorDao.queryId(novels.getAuthor().getId());
		novels.setAuthor(author);
		Classification classification = findNovelsClassification(novels.getClassification().getId());
		novels.setClassification(classification);
		if(!imgPath.isEmpty()) {
			novels.setPicture(imgPath);//新圖片
		}else {
			novels.setPicture(q_novels.getPicture());//保持原來圖片
		}
		novelsDao.update(novels);
	}

	@Override
	public void deleteNovels(Novels novels) {
		if(novelsDao.ckeckNovelIdIsExist(novels.getId())) {
			novelsDao.delete(novels);
		}else {
			throw new DaoException("刪除失敗，小說ID不存在");
		}

	}

	@Override
	public List<Novels> findAllNovels() {
		return novelsDao.queryAll();
	}

	@Override
	public List<Novels> findNovelsByAuthorId(int authorId) {
		
		List<Novels> novels =null;
		if(authorDao.ckeckAuthorIdIsExist(authorId)) {
			novels = novelsDao.queryNovelsByAuthor(authorId);
		} else {
			throw new DaoException("找不到該作者");
		}

		return novels;
	}

	@Override
	public List<Classification> findAllNovelsClassification() {
		
		return classificationDao.queryAll();
	}

	@Override
	public Classification findNovelsClassification(int id) {
		//先判斷是否存在該id
		if(classificationDao.ckeckIsIDExist(id))
			return classificationDao.queryId(id);
		throw new DaoException("分類ID不存在");
	}

	@Override
	public void addChapter(Chapter chapter,String path) {
		
		//1.先找小說
		Novels novels=findById(chapter.getNovels().getId());
		chapter.setNovels(novels);
		
		//更新存檔路徑
		if(!path.isEmpty()) {
			chapter.setContentpath(path);
			chapter.setContent("");
		}

		chapterDao.add(chapter);
	}

	@Override
	public void updateChapter(Chapter chapter) {
		
		//1.先找小說
		Novels novels=findById(chapter.getNovels().getId());
		chapter.setNovels(novels);
		
		//已有存檔路徑-不需存內容
		chapter.setContent("");
		
		chapterDao.update(chapter);
	}

	@Override
	public List<Chapter> findAllChapterByNovel(Novels novels) {
		
		return chapterDao.queryAllChapterByNovelId(novels.getId());
	}

	@Override
	public List<Novels> findAllNovelsByClassId(String id) {
		List<Novels> novels = novelsDao.queryNovelsByClassId(id);
		if(!novels.isEmpty()) {
			return novels;
		}
		
		throw new DaoException("找不到該分類小說");
	}

	@Override
	public List<Novels> findNewAllNovels() {
		
		return novelsDao.queryNewAll();
	}

	@Override
	public List<Novels> findNewNovelsTop4() {
		// TODO Auto-generated method stub
		return novelsDao.queryNewNovelsTop4();
	}

	@Override
	public PageInfoBean<Novels> findNewAllByPage(int pageNum) {
		PageInfoBean<Novels> p_novels=novelsDao.queryNewAllByPage(pageNum, PageInfoBean.DEFAULT_PAGE_SIZE);
		List<Novels> novels = p_novels.getList();
		if(!novels.isEmpty()) {
			return p_novels;
		}
		return p_novels;
	}

	@Override
	public PageInfoBean<Novels> findNovelsByClassIdByPage(String classid, int pageNum) {
		PageInfoBean<Novels> p_novels=novelsDao.queryNovelsByClassId(classid,pageNum,PageInfoBean.DEFAULT_PAGE_SIZE);
		List<Novels> novels = p_novels.getList();
		if(!novels.isEmpty()) {
			return p_novels;
		}
		
		throw new DaoException("找不到該分類小說");
	}

	@Override
	public Chapter findChapterByChapterId(int chapterid) {
		Chapter chapter=chapterDao.queryId(chapterid);
		
		return chapter;
	}

	@Override
	public PageInfoBean<Chapter> findChaptersByNovelIdByPage(String novelid, int pageNum) {
		PageInfoBean<Chapter> p_chapter=chapterDao.queryChapterByNovelId(novelid,pageNum,PageInfoBean.DEFAULT_CHAPTER_SIZE);
		
		return p_chapter;
	}

	@Override
	public PageInfoBean<Novels> findNovelsBySearchStringByPage(String searchString, Integer pageNum) {
		PageInfoBean<Novels> p_novels=novelsDao.queryNovelsBySearchString(searchString,pageNum,PageInfoBean.DEFAULT_PAGE_SIZE);
		List<Novels> novels = p_novels.getList();
		if(!novels.isEmpty()) {
			return p_novels;
		}
		
		throw new DaoException("找不到相關小說");
	}
	
	@Override
	public void addClassification(Classification classification) {
		String name=classification.getName().trim();
		if(name!="")
			if(!classificationDao.ckeckIsExist(classification.getId(), name))
				classificationDao.add(classification);
			else {
				throw new DaoException("不可以重複新增類別");
			}
		else {
			throw new DaoException("不可以是空白的類別");
		}
	}

	@Override
	public void updateClassification(Classification classification) {
		System.out.println(classification.getName());
		//判斷名稱是否沒被使用過
		if(!classificationDao.ckeckClassificationNameIsExist(classification.getName()))
			classificationDao.update(classification);
		else {
			throw new DaoException("該分類名稱已經被使用");
		}
		
	}

	@Override
	public List<Slideshow> findAllEnableSlideshowList() {
		
		return slideshowDao.queryAllEnable();
	}
	
	

}

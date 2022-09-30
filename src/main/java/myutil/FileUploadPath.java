package myutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


import model.Chapter;
import model.Novels;
import model.Slideshow;

public class FileUploadPath {
	
	/**
	 * 活動網頁存檔位置
	 * @param slideshow 幻燈片物件
	 * @param imgfile 上傳的圖片
	 * @param request 
	 * @return imgPath 存到資料庫的路徑: resources/img/slideshow/ + filename
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String getImgLoadPath(Slideshow slideshow ,MultipartFile imgfile,HttpServletRequest request) throws IllegalStateException, IOException {
		String imgPath="";//存到資料庫的路徑
		if(!imgfile.isEmpty()) {
			String path = request.getServletContext().getRealPath("/resources/img/slideshow/");
			String filename = imgfile.getOriginalFilename();
			File filepath= new File(path,filename);
			if(!filepath.isDirectory()) {//檔案目錄不存在，新建一個檔案目錄
				filepath.getParentFile().mkdir();
			}
			//圖片都上傳到該路徑
			imgfile.transferTo(new File(path+filename));
			//存到資料庫的路徑
			imgPath="resources/img/slideshow/"+filename;
			System.out.println(path+filename);
		}
		return imgPath;
	}
	
	
	/**
	 * 小說封面檔案存檔位置
	 * @param novels 小說物件
	 * @param imgfile 上傳的圖片
	 * @param request
	 * @return imgPath 存到資料庫的路徑: resources/img/cover/ + novels.getAuthor().getId() + / + filename;
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String getImgLoadPath(Novels novels ,MultipartFile imgfile,HttpServletRequest request) throws IllegalStateException, IOException {
		String imgPath="";//存到資料庫的路徑
		if(!imgfile.isEmpty()) {
			String path = request.getServletContext().getRealPath("/resources/img/cover/"+novels.getAuthor().getId()+"/");
			String filename = imgfile.getOriginalFilename();
			File filepath= new File(path,filename);
			if(!filepath.isDirectory()) {//檔案目錄不存在，新建一個檔案目錄
				filepath.getParentFile().mkdir();
			}
			//圖片都上傳到該路徑
			imgfile.transferTo(new File(path+filename));
			//存到資料庫的路徑
			imgPath="resources/img/cover/"+novels.getAuthor().getId()+"/"+filename;
			System.out.println(path+filename);
		}
		return imgPath;
	}
	
	
	
	
	/**
	 * 創建章節txt並寫入資料
	 * @param chapter
	 * @param request
	 * @return chapterPath 章節存放路徑:/resources/novels/ + chapter.getNovels().getId() +"/" + filename
	 * @throws UnsupportedEncodingException 
	 */
	public String saveChapterPath(Chapter chapter, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String chapterPath="";//存到資料庫的路徑
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String filename=df.format(new Date())+".txt";
		if(!chapter.getContent().isEmpty()) {
			String path=request.getServletContext().getRealPath("/resources/novels/"+chapter.getNovels().getId()+"/");
			File filepath= new File(path,filename);
			if(!filepath.isDirectory()) {//檔案目錄不存在，新建一個檔案目錄
				filepath.getParentFile().mkdir();
			}
			//寫入txt
			try {
				if(filepath.createNewFile()) {//創建檔案 若false表示檔案已經存在
					BufferedWriter out= new BufferedWriter(new FileWriter(filepath));
					out.write(chapter.getContent()+"");
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			chapterPath="/resources/novels/"+chapter.getNovels().getId()+"/"+filename;

		}

		return chapterPath;
	}
	
	/**
	 * 更新章節txt資料
	 * @param chapter
	 * @param request
	 * @throws UnsupportedEncodingException 
	 */
	public void updateChapter(Chapter chapter, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String chapterPath=request.getServletContext().getRealPath(chapter.getContentpath());//存到資料庫的路徑
		//System.out.println(chapterPath);
		if(!chapter.getContent().isEmpty()) {
			File filepath= new File(chapterPath);
			if(filepath.exists()) {
				System.out.println(filepath);
				//寫入txt
				try {
					BufferedWriter out= new BufferedWriter(new FileWriter(filepath));
					out.write(chapter.getContent());
					out.flush();
					out.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 讀取章節txt資料-用於textarea顯示(換行要用\r\n)
	 * @param chapter
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String readChapterPath(Chapter chapter, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String s="";
		StringBuffer chapterContent = new StringBuffer();
		String chapterPath=request.getServletContext().getRealPath(chapter.getContentpath());
		File filepath= new File(chapterPath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filepath));
			BufferedReader br = new BufferedReader(reader);
			
			//readLine()換行會被跳過，所以要補上\r\n
			while((s=br.readLine())!=null) {
				chapterContent.append(s).append("\r\n");
			}
			
			System.out.println("====read====");
			System.out.println(chapterPath);
			System.out.println(chapterContent);
			br.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return chapterContent.toString();
	}
	/**
	 * 讀取章節txt資料-用於<p>內容</p>顯示(換行要用<br>)
	 * @param chapter
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String readChapterPathByBr(Chapter chapter, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String s="";
		StringBuffer chapterContent = new StringBuffer();
		String chapterPath=request.getServletContext().getRealPath(chapter.getContentpath());
		File filepath= new File(chapterPath);
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filepath));
			BufferedReader br = new BufferedReader(reader);
			//換行會被跳過
			while((s=br.readLine())!=null) {
				chapterContent.append(s).append("<br>");
			}
			
			System.out.println("====read====");
			System.out.println(chapterPath);
			System.out.println(chapterContent);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return chapterContent.toString();
	}
	
}

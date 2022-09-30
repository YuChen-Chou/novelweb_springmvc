package services;

import java.util.List;

import model.Author;
import model.Favorites;
import model.Member;

public interface IMemberService {
	
	/**
	 * 查詢id返回Member物件
	 * @param id
	 * @return Member物件
	 */
	Member findById(int id);
	
	/**
	 * 新增Member物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param member
	 */
	void addMember(Member member);
	
	/**
	 * 更新Member物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param member
	 */
	void updateMember(Member member);
	
	/**
	 * 刪除Member物件，注意:必須判斷用戶名是否存在，若存在則拋出異常
	 * @param member
	 */
	void deleteMember(Member member);
	
	/**
	 * 查詢全部Member物件
	 * @return List<Member>
	 */
	List<Member> findAllMembers();
	
	/**
	 * 查詢帳號返回Member物件
	 * @param username
	 * @return Member
	 */
	Member queryMember(String username);
	
	/**
	 * 查詢email存在
	 * @param email
	 * @return true:存在,false:不存在
	 */
	boolean ckeckEmailIsExist(String email);
	
	/**
	 * 查詢username存在
	 * @param username
	 * @return true:存在,false:不存在
	 */
	boolean ckeckUsernameIsExist(String username);
	
	/**
	 * 登入驗證邏輯，密碼MD5加密驗證
	 * @param username 會員帳號
	 * @param password 會員密碼
	 * @return 返回會員資料，若不存在，則拋出異常
	 */
	Member login(String username, String password);
	
	
	/**
	 * 作者註冊
	 * @param member 會員物件
	 * @param authorName 作者名
	 * @return Member會員物件
	 */
	Member authorRegist(Member member,String authorName);
	
	/**
	 * 查詢該會員id的作者物件
	 * @param memberId 會員id
	 * @return 作者物件
	 */
	Author findAuthorByMemberId(int memberId);
	
	/**
	 * 查詢是否為作者會員
	 * @param memberId
	 * @return true:是作者, false:是一般會員
	 */
	boolean isAuthor(int memberId);
	
	/**
	 * 加入最愛表
	 * @param member
	 * @param novelId
	 */
	void addFavorites(Member member,int novelId);
	
	/**
	 * 刪除最愛表單中資料
	 * @param favoritesId
	 */
	void deleteFavorites(int favoritesId);
	
	/**
	 * 查詢該會員的最愛表單
	 * @param member
	 * @return List<Favorites>
	 */
	List<Favorites> findFavorites(Member member);

}

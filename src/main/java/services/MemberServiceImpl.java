package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.AuthorDao;
import dao.FavoritesDao;
import dao.MemberClassStateEnum;
import dao.MemberDao;
import dao.NovelsDao;
import exception.DaoException;
import model.Author;
import model.Favorites;
import model.Member;
import model.Memberclass;
import model.Novels;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private NovelsDao novelsDao;
	
	@Autowired
	private FavoritesDao favoritesDao;
	

	@Override
	public Member findById(int id) {
		if(memberDao.ckeckIdIsExist(id)) 
			return memberDao.queryId(id);
		throw new DaoException("ID不存在");
	}

	@Override
	public void addMember(Member member) {
		if(!ckeckUsernameIsExist(member.getUsername())) {
			if(!ckeckEmailIsExist(member.getEmail())) {
				memberDao.add(member);
			} else {
				throw new DaoException("信箱已經被使用過了");
			}
		} else {
			throw new DaoException("帳號已經被使用過了");
		}
	}

	@Override
	public void updateMember(Member member) {
		if(ckeckUsernameIsExist(member.getUsername())) {
			Member q_member = memberDao.queryMember(member.getUsername());
			String email = q_member.getEmail();
			
			if(!ckeckEmailIsExist(member.getEmail()) || email.equals(member.getEmail())) {
				System.out.println("=======更新資料=======");
				memberDao.update(member);
			} else {
				throw new DaoException("信箱已經被使用過了");
			}
		} else {
			throw new DaoException("該帳號不存在");
		}
	}

	@Override
	public void deleteMember(Member member) {
		if(ckeckUsernameIsExist(member.getUsername())) {
			memberDao.delete(member);
		}else {
			throw new DaoException("該帳號不存在");
		}
	}

	@Override
	public List<Member> findAllMembers() {
		return memberDao.queryAll();
	}

	@Override
	public Member queryMember(String username) {
		if(ckeckUsernameIsExist(username)) {
			Member member = memberDao.queryMember(username);
			return member;
		} 
		throw new DaoException("該帳號不存在");
	}

	@Override
	public boolean ckeckEmailIsExist(String email) {
		if(memberDao.ckeckEmailIsExist(email))
			return true;
		return false;
	}

	@Override
	public boolean ckeckUsernameIsExist(String username) {
		if(memberDao.ckeckUsernameIsExist(username))
			return true;
		return false;
	}


	@Override
	public Member login(String username, String password) {
		if(memberDao.ckeckloginIsExist(username, password)) {
			Member member = memberDao.queryMember(username, password);
			return member;
		}
		throw new DaoException("(帳號密碼錯誤，請重新輸入)");
	}
	


	@Override
	public Member authorRegist(Member member, String authorName) {
		//判斷該作者名稱是否被使用過
		//沒使用過 新增作者 修改會員class_id為1 返回Author
		Author author =null;
		if(!authorDao.ckeckNameIdIsExist(authorName)) {
			author = new Author(authorName,member);
			authorDao.add(author);
			Memberclass memberclass=new Memberclass(MemberClassStateEnum.AUTHORMEMBER.ordinal(),MemberClassStateEnum.AUTHORMEMBER.getName());
			member.setMemberclass(memberclass);
			memberDao.update(member);
		}else {
			throw new DaoException("此名稱已經被使用，請換過!");
		}
		
		return member;
	}

	@Override
	public Author findAuthorByMemberId(int memberId) {
		Author author = null;
		if(authorDao.isAuthor(memberId)) {
			author = authorDao.queryAuthor(memberId);
		} else {
			throw new DaoException("此會員尚未申請作者");
		}
		
		return author;
	}

	@Override
	public boolean isAuthor(int memberId) {
		return authorDao.isAuthor(memberId);
	}

	@Override
	public void addFavorites(Member member, int novelId) {
		Novels novels=novelsDao.queryId(novelId);
		Favorites favorites = new Favorites(novels,member);
		favoritesDao.add(favorites);
	}

	@Override
	public void deleteFavorites(int favoritesId) {
		Favorites favorites = favoritesDao.queryId(favoritesId);
		favoritesDao.delete(favorites);
	}

	@Override
	public List<Favorites> findFavorites(Member member) {
		return favoritesDao.queryAllByMemberId(member.getId());
	}

	@Override
	public boolean checkFavoritesByMember(Member member, int novelId) {
		return favoritesDao.ckeckNovelIdIsExist(member.getId(), novelId);
	}

}

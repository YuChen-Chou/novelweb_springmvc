package model;

import java.util.Calendar;
import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import dao.MemberClassStateEnum;
import validation.ValidAdd;
import validation.ValidLogin;
import validation.ValidUpdate;

@Entity
@Table(name="member")
public class Member {
	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "名字不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;
	
	@NotBlank(message = "帳號不能為空\n", groups = { ValidLogin.class, ValidAdd.class , ValidUpdate.class })
	private String username;
	
	@NotBlank(message = "密碼不能為空\n", groups = { ValidLogin.class, ValidAdd.class, ValidUpdate.class })
	@Size(min = 6, max = 20, message = "密碼長度必須為6~20個字\n", groups = { ValidLogin.class, ValidAdd.class,
			ValidUpdate.class })
	private String password;
	
	@NotBlank(message = "信箱不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	@Email(regexp = "^(.+)@(.+)$", message = "EMAIL格式不正確\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String email;
	
	@NotBlank(message = "電話不能為空\n", groups = { ValidAdd.class, ValidUpdate.class })
	private String phone;
	
	@NotBlank(message = "性別不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String gender;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable=false)//加入此設定，避免被update影響
	private Calendar createtime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;
	
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "class_id")
	private Memberclass memberclass=new Memberclass(MemberClassStateEnum.ORDINARYMEMBER.ordinal(), MemberClassStateEnum.ORDINARYMEMBER.getName());//給預設值，若建構式沒有寫此欄位，會自動使用預設值
	
	//給作者表用的資料
	@OneToMany(mappedBy = "member")
	@Cascade({CascadeType.ALL})
	private List<Author> authors;
	
	//給喜愛表用
	@OneToMany(mappedBy = "member")
	@Cascade({CascadeType.ALL})
	private List<Favorites> favorites;
	
	public Member() {
		super();
	}
	
	public Member(Integer id) {
		this.id = id;
	}
	

	public Member(String name, String username, String password, String email, String phone, String gender) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}
	public Member(Integer id, String name, String username, String password, String email, String phone, String gender) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}

	public Member(Integer id, String name, String username, String password, String email, String phone, String gender,
			Memberclass memberclass) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.memberclass = memberclass;
	}

	public Member(String name, String username, String password, String email, String phone, String gender, Memberclass memberclass) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.memberclass = memberclass;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Calendar getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Calendar createtime) {
		this.createtime = createtime;
	}
	public Calendar getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Calendar updatetime) {
		this.updatetime = updatetime;
	}
	public Memberclass getMemberclass() {
		return memberclass;
	}
	public void setMemberclass(Memberclass memberclass) {
		this.memberclass = memberclass;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Favorites> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Favorites> favorites) {
		this.favorites = favorites;
	}
	
}

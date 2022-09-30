package model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import validation.ValidAdd;
import validation.ValidLogin;
import validation.ValidUpdate;

@Entity
@Table(name = "admin")
public class Admin {

	@Id
	@Column(name = "admin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "帳號不能為空", groups = { ValidLogin.class, ValidAdd.class })
	private String username;

	@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String name;

	@NotBlank(message = "密碼不能為空", groups = { ValidLogin.class, ValidAdd.class, ValidUpdate.class })
	@Size(min = 6, max = 20, message = "密碼長度必須為6~20個字", groups = { ValidLogin.class, ValidAdd.class,
			ValidUpdate.class })
	private String password;

	@NotBlank(message = "信箱不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	@Email(regexp = "^(.+)@(.+)$", message = "EMAIL格式不正確", groups = { ValidAdd.class, ValidUpdate.class })
	private String email;

	@NotBlank(message = "電話不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String phone;

	@NotBlank(message = "級別不能為空", groups = { ValidAdd.class, ValidUpdate.class })
	private String grade = "admin";
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "createtime", nullable = false, updatable=false)//加入此設定，避免被update影響
	private Calendar createtime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Calendar updatetime;

	public Admin() {
		super();
	}

	public Admin(Integer id) {
		this.id = id;
	}

	// 透過username查詢物件
	public Admin(@NotBlank(message = "帳號不能為空", groups = { ValidLogin.class, ValidAdd.class }) String username) {
		this.username = username;
	}

	public Admin(@NotBlank(message = "帳號不能為空", groups = { ValidLogin.class, ValidAdd.class }) String username,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name,
			@NotBlank(message = "密碼不能為空", groups = { ValidLogin.class, ValidAdd.class,
					ValidUpdate.class }) @Size(min = 6, max = 10, message = "密碼長度必須為6~10個字", groups = {
							ValidLogin.class, ValidAdd.class, ValidUpdate.class }) String password,
			@NotBlank(message = "信箱不能為空", groups = { ValidAdd.class,
					ValidUpdate.class }) @Email(regexp = "^(.+)@(.+)$", message = "EMAIL格式不正確", groups = { ValidAdd.class, ValidUpdate.class }) String email,
			@NotBlank(message = "電話不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String phone) {
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	public Admin(Integer id,
			@NotBlank(message = "帳號不能為空", groups = { ValidLogin.class, ValidAdd.class }) String username,
			@NotBlank(message = "名字不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String name,
			@NotBlank(message = "密碼不能為空", groups = { ValidLogin.class, ValidAdd.class,
					ValidUpdate.class }) @Size(min = 6, max = 10, message = "密碼長度必須為6~10個字", groups = {
							ValidLogin.class, ValidAdd.class, ValidUpdate.class }) String password,
			@NotBlank(message = "信箱不能為空", groups = { ValidAdd.class,
					ValidUpdate.class }) @Email(regexp = "^(.+)@(.+)$", message = "EMAIL格式不正確", groups = { ValidAdd.class, ValidUpdate.class }) String email,
			@NotBlank(message = "電話不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String phone,
			@NotBlank(message = "級別不能為空", groups = { ValidAdd.class, ValidUpdate.class }) String grade) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.grade = grade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}

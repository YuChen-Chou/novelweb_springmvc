package model;

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
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import validation.ValidAdd;

@Entity
@Table(name="author")
public class Author {
	@Id
	@Column(name = "author_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "作者名字不能為空", groups = { ValidAdd.class})
	private String name;
	
	@ManyToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "member_id")
	private Member member;
	
	//給小說表用的資料
	@OneToMany(mappedBy = "author")
	@Cascade({CascadeType.ALL})
	private List<Novels> novels;

	public Author() {
	}

	public Author(Integer id) {
		this.id = id;
	}
	
	public Author(String name) {
		this.name = name;
	}

	public Author(String name, Member member) {
		this.name = name;
		this.member = member;
	}


	public Author(Integer id, String name, Member member) {
		this.id = id;
		this.name = name;
		this.member = member;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Novels> getNovels() {
		return novels;
	}

	public void setNovels(List<Novels> novels) {
		this.novels = novels;
	}

}

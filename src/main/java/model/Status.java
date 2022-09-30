package model;

import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "status")
public class Status {
	@Id
	@Column(name = "status_id")
	private Integer id;
	private String name;
	
	//給小說表用的資料
	@OneToMany(mappedBy = "status")
	@Cascade({CascadeType.ALL})
	private List<Novels> novels;

	public Status() {
	}

	public Status(Integer id) {
		this.id = id;
	}

	public Status(Integer id, String name) {
		this.id = id;
		this.name = name;
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

	public List<Novels> getNovels() {
		return novels;
	}

	public void setNovels(List<Novels> novels) {
		this.novels = novels;
	}

}

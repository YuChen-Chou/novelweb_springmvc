package model;

import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="memberclass")
public class Memberclass {
	@Id
	@Column(name = "class_id")
	private int id;
	private String name;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "memberclass")
	@Cascade({CascadeType.ALL})
	private List<Member> members;
	
	public Memberclass() {
		super();
	}
	public Memberclass(int id) {
		this.id = id;
	}
	public Memberclass(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Memberclass(int id, String name, List<Member> members) {
		this.id = id;
		this.name = name;
		this.members = members;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Member> getMember() {
		return members;
	}
	public void setMember(List<Member> members) {
		this.members = members;
	}
	//關聯異動
	public Member addMember(Member member) {
		getMember().add(member);
		member.setMemberclass(this);

		return member;
	}
	//關聯異動
	public Member removeMember(Member member) {
		getMember().remove(member);
		member.setMemberclass(null);

		return member;
	}
}

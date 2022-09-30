package dao;

public enum MemberClassStateEnum {
	ORDINARYMEMBER("普通會員"), AUTHORMEMBER("作者會員");

	private String name;

	private MemberClassStateEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

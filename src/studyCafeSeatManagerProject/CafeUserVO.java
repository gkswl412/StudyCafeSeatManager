package studyCafeSeatManagerProject;

public class CafeUserVO {
	private String name;
	private String phone_number;
	private String id;
	private int remain_time;
	private String email;
	private String ox;
	

	//default 持失切
	public CafeUserVO() {}
	//持失切
	public CafeUserVO(String name,String phone_number, String id, int remain_time, String email, String ox) {
		super();
		this.phone_number = phone_number;
		this.email = email;
		this.name = name;
		this.id = id;
		this.remain_time = remain_time;
		this.ox = ox;
	}
	public String getOx() {
		return ox;
	}
	public void setOx(String ox) {
		this.ox = ox;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getRemain_time() {
		return remain_time;
	}
	public void setRemain_time(int remain_time) {
		this.remain_time = remain_time;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CafeUserVO [name=").append(name).append(", phone_number=").append(phone_number).append(", id=")
				.append(id).append(", remain_time=").append(remain_time).append(", email=").append(email)
				.append(", ox=").append(ox).append("]");
		return builder.toString();
	}


	
	
	
}

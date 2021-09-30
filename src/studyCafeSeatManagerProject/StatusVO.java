package studyCafeSeatManagerProject;

import java.util.Date;
import java.sql.Timestamp;


public class StatusVO {
	private int seatno;
	private String phone_number;
	private String name;
	private String id;
	private Timestamp start_time;
	private int remain_time;
	private String temp_remain_time;
	public StatusVO() {}
	public StatusVO(int seatno, String phone_number, String name, String id, Timestamp start_time, int remain_time) {
		super();
		this.seatno = seatno;
		this.phone_number = phone_number;
		this.name = name;
		this.id = id;
		this.start_time = start_time;
		this.remain_time = remain_time;
	}
	public int getSeatno() {
		return seatno;
	}
	public void setSeatno(int seatno) {
		this.seatno = seatno;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
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
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public int getRemain_time() {
		return remain_time;
	}
	public void setRemain_time(int remain_time) {
		this.remain_time = remain_time;
	}
	public String getTemp_remain_time() {
		return temp_remain_time;
	}
	public void setTemp_remain_time(String temp_remain_time) {
		this.temp_remain_time = temp_remain_time;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StatusVO [seatno=").append(seatno).append(", phone_number=").append(phone_number)
				.append(", name=").append(name).append(", id=").append(id).append(", start_time=").append(start_time)
				.append(", remain_time=").append(remain_time).append("]");
		return builder.toString();
	}
	
	
}

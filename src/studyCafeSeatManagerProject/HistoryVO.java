package studyCafeSeatManagerProject;

import java.sql.Timestamp;
import java.util.Date;

public class HistoryVO {
	private int seatno;
	private String phone_number;
	private String name;
	private String id;
	private Timestamp start_time;
	private Timestamp end_time;
	
	public HistoryVO() {}
	
	public HistoryVO(int seatno, String name, String phone_number, String id, Timestamp start_time, Timestamp end_time) {
		super();
		this.seatno = seatno;
		this.phone_number = phone_number;
		this.name = name;
		this.id = id;
		this.start_time = start_time;
		this.end_time = end_time;
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

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HistoryVO [seatno=").append(seatno).append(", phone_number=").append(phone_number)
				.append(", name=").append(name).append(", id=").append(id).append(", start_time=").append(start_time)
				.append(", end_time=").append(end_time).append("]");
		return builder.toString();
	}
	
}


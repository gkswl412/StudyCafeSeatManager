package studyCafeSeatManagerProject;

public class SeatVO {
	private int seatno;
	private String available;
	private String bookable;
	private String status;
	
	public SeatVO() {}
	
	public SeatVO(int seatno, String available, String bookable, String status) {
		super();
		this.seatno = seatno;
		this.available = available;
		this.bookable = bookable;
		this.status = status;
	}

	public int getSeatno() {
		return seatno;
	}

	public void setSeatno(int seatno) {
		this.seatno = seatno;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getBookable() {
		return bookable;
	}

	public void setBookable(String bookable) {
		this.bookable = bookable;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeatVO [seatno=").append(seatno).append(", available=").append(available).append(", bookable=")
				.append(bookable).append(", status=").append(status).append("]");
		return builder.toString();
	}

	
	
}

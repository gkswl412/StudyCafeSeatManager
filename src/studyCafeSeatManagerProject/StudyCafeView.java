package studyCafeSeatManagerProject;

import java.util.List;

public class StudyCafeView {
	StudyCafeDAO dao = new StudyCafeDAO();
	public void memberListDisplay(List<CafeUserVO> list) {
		if(list.size()==0) {
			System.out.println("가입한 회원이 없습니다.");
		}else {
			System.out.printf("%s\t%-14s%-15s%s","이름","핸드폰번호","아이디","e-mail");
			System.out.println();
			System.out.println("===============================================================");
			for(CafeUserVO user:list) {
				System.out.printf("%s\t%-15s\t%-15s\t%s",user.getName(),
						user.getPhone_number(),user.getId(),user.getEmail());
				System.out.println();
			}
		}
	}
	
	public void availableSeatListDisplay(List<SeatVO> seatList) {
		System.out.println("이용가능한 좌석");
		for(SeatVO seat:seatList) {
			System.out.println(seat.getSeatno() + " 번 좌석");
		}
	}

	public void displayOnePersonOneSeatWarning(int seatNo) {
		System.out.println("1인 1좌석만 이용가능 합니다.");
		System.out.println("현재 이용 중이신 " + seatNo + " 번 좌석 이용 부탁드립니다.");
	}
	
	public void displayYouHaveNoSeat() {
		System.out.println("이용 중인 좌석이 없습니다.");
	}

	public void displayRealTimeStatus(List<StatusVO> realTimeStatus) {
		if(realTimeStatus.size()==0) {
			System.out.println("현재 이용중인 회원이 없습니다.");
		}else {
			System.out.printf("%s\t%s\t%-12s\t%-10s\t%-30s\t%s","좌석번호","이름","핸드폰번호","아이디","시작 시간","남은 시간");
			System.out.println();
			System.out.println("==========================================================================================");
			for(StatusVO st:realTimeStatus) {
				System.out.printf("%d\t%s\t%-12s\t%-10s\t%-30s\t%s",st.getSeatno(),st.getName(),st.getPhone_number(),
						st.getId(),st.getStart_time(),st.getTemp_remain_time());
				System.out.println();
			}
		}
	}

	public void displaySeat(List<SeatVO> seatTable) {
		System.out.printf("%s\t%-10s\t%-10s\t%s","좌석번호","활성화 여부","예약가능 여부","현재상태");
		System.out.println();
		System.out.println("================================================");
		for(SeatVO seat:seatTable) {
			System.out.printf("%d\t%-10s\t%-10s\t%s",seat.getSeatno(),seat.getAvailable(),
					seat.getBookable(),seat.getStatus());
			System.out.println();
		}
	}

	public void displayBye() {
		System.out.println("퇴실완료! 감사합니다 Bye~");
	}
	
	public void displayHistory(List<HistoryVO> historyTable) {
		if(historyTable.size()==0) {
			System.out.println("남아있는 기록이 없습니다. 기록은 최대 7일간 보관됩니다.");
		}else {
			System.out.printf("%s\t%s\t%-12s\t%-12s\t%-30s\t%s","좌석번호","이름","핸드폰번호","아이디","입실시간","퇴실시간");
			System.out.println();
			System.out.println("====================================================================================================");
			for(HistoryVO history:historyTable) {
				System.out.printf("%d\t%s\t%s\t%-12s\t%-30s\t%s",history.getSeatno(),history.getName(),history.getPhone_number(),
						history.getId(),history.getStart_time(), history.getEnd_time());
				System.out.println();
			}
		}
	}
	
	public void displayMemberChargePageFeeList() {
		System.out.println("**********************");
		System.out.println("* Member Charge page *");
		System.out.println("**********************");
		System.out.println("<시간제>");
		System.out.println("1.....<1시간:3,000원>");
		System.out.println("2.....<2시간:5,000원>");
		System.out.println("3.....<3시간:6,000원>");
		System.out.println("4.....<5시간:9,000원>");
		System.out.println("5.....<30시간:50,000원>");
		System.out.println("6.....<60시간:90,000원>");
		System.out.println("7.....<100시간:140,000원>");
		System.out.println("8.....<200시간:260,000원>");
//											System.out.println("<기간제>");
//											System.out.println("9.....<14일:150,000원>");
//											System.out.println("10....<28일:250,000원>");
//											System.out.println("11....<90일:700,000원>");
//											System.out.println("12....<180일:1,200,000원>");
		System.out.println("9....back");
		System.out.print(">");
	}
}

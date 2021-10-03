package studyCafeSeatManagerProject;

import java.util.List;

public class StudyCafeView {
	StudyCafeDAO dao = new StudyCafeDAO();
	public void memberListDisplay(List<CafeUserVO> list) {
		if(list.size()==0) {
			System.out.println("������ ȸ���� �����ϴ�.");
		}else {
			System.out.printf("%s\t%-14s%-15s%s","�̸�","�ڵ�����ȣ","���̵�","e-mail");
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
		System.out.println("�̿밡���� �¼�");
		for(SeatVO seat:seatList) {
			System.out.println(seat.getSeatno() + " �� �¼�");
		}
	}

	public void displayOnePersonOneSeatWarning(int seatNo) {
		System.out.println("1�� 1�¼��� �̿밡�� �մϴ�.");
		System.out.println("���� �̿� ���̽� " + seatNo + " �� �¼� �̿� ��Ź�帳�ϴ�.");
	}
	
	public void displayYouHaveNoSeat() {
		System.out.println("�̿� ���� �¼��� �����ϴ�.");
	}

	public void displayRealTimeStatus(List<StatusVO> realTimeStatus) {
		if(realTimeStatus.size()==0) {
			System.out.println("���� �̿����� ȸ���� �����ϴ�.");
		}else {
			System.out.printf("%s\t%s\t%-12s\t%-10s\t%-30s\t%s","�¼���ȣ","�̸�","�ڵ�����ȣ","���̵�","���� �ð�","���� �ð�");
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
		System.out.printf("%s\t%-10s\t%-10s\t%s","�¼���ȣ","Ȱ��ȭ ����","���డ�� ����","�������");
		System.out.println();
		System.out.println("================================================");
		for(SeatVO seat:seatTable) {
			System.out.printf("%d\t%-10s\t%-10s\t%s",seat.getSeatno(),seat.getAvailable(),
					seat.getBookable(),seat.getStatus());
			System.out.println();
		}
	}

	public void displayBye() {
		System.out.println("��ǿϷ�! �����մϴ� Bye~");
	}
	
	public void displayHistory(List<HistoryVO> historyTable) {
		if(historyTable.size()==0) {
			System.out.println("�����ִ� ����� �����ϴ�. ����� �ִ� 7�ϰ� �����˴ϴ�.");
		}else {
			System.out.printf("%s\t%s\t%-12s\t%-12s\t%-30s\t%s","�¼���ȣ","�̸�","�ڵ�����ȣ","���̵�","�Խǽð�","��ǽð�");
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
		System.out.println("<�ð���>");
		System.out.println("1.....<1�ð�:3,000��>");
		System.out.println("2.....<2�ð�:5,000��>");
		System.out.println("3.....<3�ð�:6,000��>");
		System.out.println("4.....<5�ð�:9,000��>");
		System.out.println("5.....<30�ð�:50,000��>");
		System.out.println("6.....<60�ð�:90,000��>");
		System.out.println("7.....<100�ð�:140,000��>");
		System.out.println("8.....<200�ð�:260,000��>");
//											System.out.println("<�Ⱓ��>");
//											System.out.println("9.....<14��:150,000��>");
//											System.out.println("10....<28��:250,000��>");
//											System.out.println("11....<90��:700,000��>");
//											System.out.println("12....<180��:1,200,000��>");
		System.out.println("9....back");
		System.out.print(">");
	}
}

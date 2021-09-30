package studyCafeSeatManagerProject;

import java.util.List;
import java.util.Scanner;

public class StudyCafeController {
	static Scanner sc = new Scanner(System.in);
	static StudyCafeService service = new StudyCafeService();
	static StudyCafeView view = new StudyCafeView();
	static String inputStr;
	public static void main(String[] args) {
		while(true) {
			System.out.println("******************************");
			System.out.println("*   StudyCafe Seat Manager   *");
			System.out.println("******************************");
			System.out.println("1.������ 2.����� 3.Out");
			System.out.print(">");
			inputStr = sc.next();
			if(inputStr.equals("3")) {
				System.out.println("Bye~");
				break;
			}
			switch(inputStr) {
			case "1": 
				while(true) {
					System.out.println("*********************");
					System.out.println("*   Manager page    *");
					System.out.println("*********************");
					System.out.println("1.�¼� Ȱ��ȭ 2.�¼� ��Ȱ��ȭ 3.�ǽð� ��Ȳ�� 4.ȸ����� 5.�̿��� 6.Back");
					System.out.print(">");
					inputStr = sc.next();
					if(inputStr.equals("6")) break;
					switch(inputStr) {
					case "1": view.displaySeat(getSeatTable()); enableSeat(); break;
					case "2": view.displaySeat(getSeatTable()); disableSeat(); break;
					case "3": view.displayRealTimeStatus(realTimeStatus()); break;
					case "4": view.memberListDisplay(getMemberList()); break;
					case "5": view.displayHistory(getHistoryList()); break;
					}
				}
				break;
			case "2":
				while(true) {
					System.out.println("*********************");
					System.out.println("*     User page     *");
					System.out.println("*********************");
					System.out.println("1.ȸ��/��ȸ�� �α��� 2.ȸ������ 3.ȸ��Ż�� 4.Back");
					System.out.print(">");
					inputStr = sc.next();
					if(inputStr.equals("4")) break;
					switch(inputStr) {
					case "1": 
						while(true) {
							System.out.println("**********************");
							System.out.println("*     Login page     *");
							System.out.println("**********************");
							System.out.println("1.ȸ�� 2.��ȸ�� 3.back");
							System.out.print(">");
							inputStr = sc.next();
							if(inputStr.equals("3")) break;
							switch(inputStr) {
							case "1": 
								CafeUserVO user = memberLogin();
								String time = "";
								if(user != null) {
									while(true) {
										//���� �ð��� �پ��, ����������� ���� �ð��� ���� ���� �ʴ� ������ �־���.
										//�ذ� ���ؼ� refresh �޼��� ����. �ش�޼���� user��ü�� cafe_user���̺��� �ֽ������� ������Ʈ �ϴ� ����� ��.
										user = refresh(user);
										System.out.println("*********************");
										System.out.println("*    Choose work    *");
										System.out.println("*********************");
										System.out.println("�ȳ��ϼ���. " + user.getName() + " ��!");
										if(getRemainTime(user)=="user") {
											time = service.calTime(user.getRemain_time());
										}else {
											time = getRemainTime(user);
										}
										System.out.println("���� ���� �ð��� " + time + " �Դϴ�.");
										System.out.println("1.�ð����� 2.�¼����� 3.��� 4.back");
										System.out.print(">");
										inputStr = sc.next();
										if(inputStr.equals("4")) break;
										switch(inputStr) {
										case "1": 
											memberChargePage(user,inputStr);
											break;
										case "2":
											//�����ϰ� ���� ��: �̹� �¼� �̿����� �������, "1�� 1�¼��� �̿� ���� �մϴ�" ��� ������ �����ְ� ����
											//OnePersonOneSeat ��� �޼ҵ带 �����, ���� dao�� ��ƺ���.
											int seatNo = checkStatusVO(user);
											if(seatNo != 0) {
												view.displayOnePersonOneSeatWarning(seatNo);
												break;
											}
											//�����ϰ� ���� ��: �����ð��� 0���� �۰ų� ���� ����� ������ �̿��ش޶�� ������ break;
											if(user.getRemain_time() <= 0 ) {
												System.out.println("���� �ð��� �����ϴ�. ������ �̿� ��Ź�帮�ڽ��ϴ�.");
												break;
											}
											//�����ϰ� ���� ��: �����ð��� 1�� �̸��� ����� ������ �̿��� �޶�� ���� ��������. �¼� ���� �Ұ�
											if(user.getRemain_time() < 60) {
												System.out.println("���� �ð��� 1�� �̸��Դϴ�. ������ �̿� ��Ź�帮�ڽ��ϴ�.");
												break;
											}
											while(true) {
												System.out.println("*****************");
												System.out.println("*  Choose Seat  *");
												System.out.println("*****************");
												List<SeatVO> seatList = getAvailableSeat();
												if(seatList.isEmpty()) {
													System.out.println("���� �Դϴ�.");
													break;
												}
												view.availableSeatListDisplay(seatList); 
												chooseSeat(user,seatList);
												break;
											}
											break;
										case "3":
											int seatNo2 = checkStatusVO(user);
											if(seatNo2 == 0) {
												view.displayYouHaveNoSeat();
												break;
											}
											checkOut(user); 
											view.displayBye();
											break;
										}
									}
								}
								break;
							case "2": 
								CafeUserVO user2 = nonMemberLogin();
								String time2 = "";
								if(user2 != null) {
									while(true) {
										user2 = refresh(user2);
										System.out.println("*********************");
										System.out.println("*    Choose work    *");
										System.out.println("*********************");
										System.out.println("�ȳ��ϼ���. " + user2.getName() + " ��!");
										if(getRemainTime(user2)=="user") {
											time2 = service.calTime(user2.getRemain_time());
										}else {
											time2 = getRemainTime(user2);
										}
										System.out.println("���� ���� �ð��� " + time2 + " �Դϴ�.");
										System.out.println("1.�ð����� 2.�¼����� 3.��� 4.back");
										System.out.print(">");
										inputStr = sc.next();
										if(inputStr.equals("4")) break;
										switch(inputStr) {
										case "1": 
											while(true) {
												System.out.println("**************************");
												System.out.println("* Non-member Charge page *");
												System.out.println("**************************");
//												System.out.println("��ȸ���� �ð��� �̿븸 �����մϴ�.");
												System.out.println("1.....<1�ð�:3,000��>");
												System.out.println("2.....<2�ð�:5,000��>");
												System.out.println("3.....<3�ð�:6,000��>");
												System.out.println("4.....<5�ð�:9,000��>");
												System.out.println("5.....back");
												System.out.print(">");
												inputStr = sc.next();
												if(inputStr.equals("5")) break;
												switch(inputStr) {
												case "1": service.charge(user2, 1); break;
												case "2": service.charge(user2, 2); break;
												case "3": service.charge(user2, 3); break;
												case "4": service.charge(user2, 5); break;
												}
											}
											break;
										case "2":
											//�����ϰ� ���� ��: �̹� �¼� �̿����� �������, "1�� 1�¼��� �̿� ���� �մϴ�" ��� ������ �����ְ� ����
											//OnePersonOneSeat ��� �޼ҵ带 �����, ���� dao�� ��ƺ���.
											int seatNo3 = checkStatusVO(user2);
											if(seatNo3 != 0) {
												view.displayOnePersonOneSeatWarning(seatNo3);
												break;
											}
											//�����ϰ� ���� ��: �����ð��� 0���� �۰ų� ���� ����� ������ �̿��ش޶�� ������ break;
											if(user2.getRemain_time() <= 0 ) {
												System.out.println("���� �ð��� �����ϴ�. ������ �̿� ��Ź�帮�ڽ��ϴ�.");
												break;
											}
											//�����ϰ� ���� ��: �����ð��� 1�� �̸��� ����� ������ �̿��� �޶�� ���� ��������. �¼� ���� �Ұ�
											if(user2.getRemain_time() < 60) {
												System.out.println("���� �ð��� 1�� �̸��Դϴ�. ������ �̿� ��Ź�帮�ڽ��ϴ�.");
												break;
											}
											while(true) {
												System.out.println("*****************");
												System.out.println("*  Choose Seat  *");
												System.out.println("*****************");
												List<SeatVO> seatList2 = getAvailableSeat();
												if(seatList2.isEmpty()) {
													System.out.println("���� �����ִ� �¼��� �����ϴ�.");
													break;
												}
												view.availableSeatListDisplay(seatList2); 
												chooseSeat(user2,seatList2);
												break;
											}
											break;
										case "3":
											int seatNo4 = checkStatusVO(user2);
											if(seatNo4 == 0) {
												view.displayYouHaveNoSeat();
												break;
											}
											checkOut(user2); view.displayBye(); break;
										}
									}
								}
							}
						}
						break;
					case "2": signUp(); break;
					case "3": deleteMyAccount(); break;
					}
				}
				break;
			}
		}

	}

	public static void memberChargePage(CafeUserVO user,String inputStr) {
		service.memberChargePage(user, inputStr);
	}
	
	public static String getRemainTime(CafeUserVO user){
		return service.getRemainTime(user);
	}
	
	public static List<SeatVO> getSeatTable(){
		return service.getSeatTable();
	}

	public static CafeUserVO refresh(CafeUserVO user) {
		return service.refresh(user);
	}

	private static void checkOut(CafeUserVO user) {
		service.checkOut(user);
	}

	private static int checkStatusVO(CafeUserVO user) {
		return service.checkStatusVO(user);
	}


	private static void chooseSeat(CafeUserVO user, List<SeatVO> seatList) {
		service.chooseSeat(user,seatList);
	}

	private static List<SeatVO> getAvailableSeat() {
		return service.getAvailableSeat();
	}

	private static void deleteMyAccount() {
		service.deleteMyAccount();
	}

	private static void signUp() {
		service.signUp();		
	}

	private static CafeUserVO memberLogin() {
		return service.memberLogin();		
	}
	
	private static CafeUserVO nonMemberLogin() {
		return service.nonMemberLogin();
	}
	
	private static List<HistoryVO> getHistoryList() {
		return service.getHistoryList();
	}

	private static List<CafeUserVO> getMemberList() {
		return service.getMemberList();	
	}

	private static List<StatusVO> realTimeStatus() {
		return service.realTimeStatus();
	}

	private static void disableSeat() {
		service.disableSeatController(inputStr);
	}
		
	private static void enableSeat() {
		service.enableSeatController(inputStr);
	}
}

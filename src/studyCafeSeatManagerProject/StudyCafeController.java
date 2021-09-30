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
			System.out.println("1.관리자 2.사용자 3.Out");
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
					System.out.println("1.좌석 활성화 2.좌석 비활성화 3.실시간 현황판 4.회원목록 5.이용기록 6.Back");
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
					System.out.println("1.회원/비회원 로그인 2.회원가입 3.회원탈퇴 4.Back");
					System.out.print(">");
					inputStr = sc.next();
					if(inputStr.equals("4")) break;
					switch(inputStr) {
					case "1": 
						while(true) {
							System.out.println("**********************");
							System.out.println("*     Login page     *");
							System.out.println("**********************");
							System.out.println("1.회원 2.비회원 3.back");
							System.out.print(">");
							inputStr = sc.next();
							if(inputStr.equals("3")) break;
							switch(inputStr) {
							case "1": 
								CafeUserVO user = memberLogin();
								String time = "";
								if(user != null) {
									while(true) {
										//남은 시간이 줄어들어도, 퇴실전까지는 남은 시간이 리셋 되지 않는 문제가 있었음.
										//해결 위해서 refresh 메서드 만듬. 해당메서드는 user객체를 cafe_user테이블의 최신정보로 업데이트 하는 기능을 함.
										user = refresh(user);
										System.out.println("*********************");
										System.out.println("*    Choose work    *");
										System.out.println("*********************");
										System.out.println("안녕하세요. " + user.getName() + " 님!");
										if(getRemainTime(user)=="user") {
											time = service.calTime(user.getRemain_time());
										}else {
											time = getRemainTime(user);
										}
										System.out.println("현재 남은 시간은 " + time + " 입니다.");
										System.out.println("1.시간충전 2.좌석선택 3.퇴실 4.back");
										System.out.print(">");
										inputStr = sc.next();
										if(inputStr.equals("4")) break;
										switch(inputStr) {
										case "1": 
											memberChargePage(user,inputStr);
											break;
										case "2":
											//구현하고 싶은 것: 이미 좌석 이용중인 사람에게, "1인 1좌석만 이용 가능 합니다" 라는 문구를 보여주고 싶음
											//OnePersonOneSeat 라는 메소드를 만들고, 여러 dao를 담아보자.
											int seatNo = checkStatusVO(user);
											if(seatNo != 0) {
												view.displayOnePersonOneSeatWarning(seatNo);
												break;
											}
											//구현하고 싶은 것: 충전시간이 0보다 작거나 같은 사람은 충전후 이용해달라는 문구후 break;
											if(user.getRemain_time() <= 0 ) {
												System.out.println("남은 시간이 없습니다. 충전후 이용 부탁드리겠습니다.");
												break;
											}
											//구현하고 싶은 것: 충전시간이 1분 미만인 사람은 충전후 이용해 달라는 문구 보여주자. 좌석 선택 불가
											if(user.getRemain_time() < 60) {
												System.out.println("남은 시간이 1분 미만입니다. 충전후 이용 부탁드리겠습니다.");
												break;
											}
											while(true) {
												System.out.println("*****************");
												System.out.println("*  Choose Seat  *");
												System.out.println("*****************");
												List<SeatVO> seatList = getAvailableSeat();
												if(seatList.isEmpty()) {
													System.out.println("만석 입니다.");
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
										System.out.println("안녕하세요. " + user2.getName() + " 님!");
										if(getRemainTime(user2)=="user") {
											time2 = service.calTime(user2.getRemain_time());
										}else {
											time2 = getRemainTime(user2);
										}
										System.out.println("현재 남은 시간은 " + time2 + " 입니다.");
										System.out.println("1.시간충전 2.좌석선택 3.퇴실 4.back");
										System.out.print(">");
										inputStr = sc.next();
										if(inputStr.equals("4")) break;
										switch(inputStr) {
										case "1": 
											while(true) {
												System.out.println("**************************");
												System.out.println("* Non-member Charge page *");
												System.out.println("**************************");
//												System.out.println("비회원은 시간제 이용만 가능합니다.");
												System.out.println("1.....<1시간:3,000원>");
												System.out.println("2.....<2시간:5,000원>");
												System.out.println("3.....<3시간:6,000원>");
												System.out.println("4.....<5시간:9,000원>");
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
											//구현하고 싶은 것: 이미 좌석 이용중인 사람에게, "1인 1좌석만 이용 가능 합니다" 라는 문구를 보여주고 싶음
											//OnePersonOneSeat 라는 메소드를 만들고, 여러 dao를 담아보자.
											int seatNo3 = checkStatusVO(user2);
											if(seatNo3 != 0) {
												view.displayOnePersonOneSeatWarning(seatNo3);
												break;
											}
											//구현하고 싶은 것: 충전시간이 0보다 작거나 같은 사람은 충전후 이용해달라는 문구후 break;
											if(user2.getRemain_time() <= 0 ) {
												System.out.println("남은 시간이 없습니다. 충전후 이용 부탁드리겠습니다.");
												break;
											}
											//구현하고 싶은 것: 충전시간이 1분 미만인 사람은 충전후 이용해 달라는 문구 보여주자. 좌석 선택 불가
											if(user2.getRemain_time() < 60) {
												System.out.println("남은 시간이 1분 미만입니다. 충전후 이용 부탁드리겠습니다.");
												break;
											}
											while(true) {
												System.out.println("*****************");
												System.out.println("*  Choose Seat  *");
												System.out.println("*****************");
												List<SeatVO> seatList2 = getAvailableSeat();
												if(seatList2.isEmpty()) {
													System.out.println("현재 남아있는 좌석이 없습니다.");
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

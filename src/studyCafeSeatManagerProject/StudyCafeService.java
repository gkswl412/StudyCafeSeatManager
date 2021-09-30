package studyCafeSeatManagerProject;

import java.util.List;

public class StudyCafeService {
	static StudyCafeDAO dao = new StudyCafeDAO();
	
	public void enableSeatController(String inputStr) {
		dao.enableSeatController(inputStr);
	}
	public void disableSeatController(String inputStr) {
		dao.disableSeatController(inputStr);
	}

	public List<CafeUserVO> getMemberList(){
		return dao.getMemberList();
	}

	public CafeUserVO memberLogin() {
		return dao.memberLogin();
	}
	public CafeUserVO nonMemberLogin() {
		return dao.nonMemberLogin();
	}
	public void signUp() {
		dao.signUp();
	}
	public void deleteMyAccount() {
		dao.deleteMyAccount();
	}
	public static void charge(CafeUserVO user, int hour) {
		dao.charge(user, hour);
	}
//	public void charge2(CafeUserVO user, int hour) {
//		dao.charge2(user, hour);
//	}
	public String calTime(int time) {
		return dao.calTime(time);
	}
	public List<SeatVO> getAvailableSeat() {
		return dao.getAvailableSeat();
	}
	public void chooseSeat(CafeUserVO user, List<SeatVO> seatList) {
		dao.chooseSeat(user,seatList);
	}
	
	//������ ���� �¼� �̿������� Ȯ���ϴ� ����
	public int checkStatusVO(CafeUserVO user) {
		return dao.checkStatusVO(user,dao.getStatusTable());
	}
	
	
	public void checkOut(CafeUserVO user) {
		dao.calUsingTime(user); //�̿�ð� ��� �޼���
		dao.submitSeat(user); //status ���̺��� �ش� row �����ϴ� �޼���
	}
	
	public CafeUserVO refresh(CafeUserVO user) {
		return dao.refresh(user);
	}
	
	public List<StatusVO> realTimeStatus() {
		return dao.realTimeStatus();
	}
	
	public List<SeatVO> getSeatTable(){
		return dao.getSeatTable();
	}
	public List<HistoryVO> getHistoryList() {
		return dao.getHistoryList();
	}
	public String getRemainTime(CafeUserVO user) {
		return dao.getRemainTime(user);
	}
	public static void memberChargePage(CafeUserVO user,String inputStr) {
		dao.memberChargePage(user,inputStr);
	}
}

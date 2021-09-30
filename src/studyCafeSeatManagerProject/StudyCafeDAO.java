package studyCafeSeatManagerProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudyCafeDAO {
	static StudyCafeView view = new StudyCafeView();
	Scanner sc = new Scanner(System.in);
	public void enableSeat(String input) {
		if(input=="all") {
			Connection conn = DBUtil.dbConnect();
			PreparedStatement st = null;
			String sql = "update seat set available='O' where available='X'";
			try {
				st = conn.prepareStatement(sql);
				int result = st.executeUpdate();
				if(result > 0) {
					System.out.println();
					System.out.println("모든 좌석이 활성화 되었습니다.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("이미 모든 좌석이 활성화 되어있습니다.");
					System.out.println();
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.dbClose(conn, st, null);
			}
		}
	}
	public void enableSeat(String[] input) {
		//List<String> list = new ArrayList<>();
		//list = Arrays.asList(input);
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = "update seat set \"AVAILABLE\"='O'\r\n"
				+ "where seatno = ?";
		int cnt = 0;
		try {
			st = conn.prepareStatement(sql);
			for(String s:input) {
				st.setString(1,s);
				cnt += st.executeUpdate();
			}
			if(cnt > 0) {
				System.out.println();
				System.out.println(cnt + "건 Success!");
				System.out.println();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
	}
	public void disableSeat(String input) {
		if(input=="all") {
			Connection conn = DBUtil.dbConnect();
			PreparedStatement st = null;
			String sql = "update seat set \"AVAILABLE\" = 'X' where available='O' and status='empty'";
			try {
				st = conn.prepareStatement(sql);
				int result = st.executeUpdate();
				if(result > 0) {
					System.out.println();
					System.out.println("이용 중인 좌석을 제외한 모든 좌석이 비활성화 되었습니다.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("이미 모든 좌석이 비활성화 되어있습니다.");
					System.out.println();
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.dbClose(conn, st, null);
			}
		}
	}
	public void disableSeat(String[] input) {
		//List<String> list = new ArrayList<>();
		//list = Arrays.asList(input);
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = "update seat set \"AVAILABLE\"='X'\r\n"
				+ "where seatno = ? and status='empty'";
		int cnt = 0;
		try {
			st = conn.prepareStatement(sql);
			for(String s:input) {
				st.setString(1,s);
				cnt += st.executeUpdate();
			}
			if(cnt > 0) {
				System.out.println();
				System.out.println(cnt + "건 Success!");
				System.out.println();
			}else {
				System.out.println("이용 중인 좌석은 비활성화할 수 없습니다. 혹은 잘못된 좌석번호.");
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
	}
	public List<CafeUserVO> getMemberList() {
		Connection conn = DBUtil.dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,id,remain_time,email,ox from cafe_user";
		List<CafeUserVO> list = new ArrayList<>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				CafeUserVO user = new CafeUserVO(rs.getString(1),rs.getString(2),
						rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6));
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return list;
	}
	
	public CafeUserVO memberLogin() {
		System.out.print("ID>");
		String id = sc.next();
		System.out.print("Password>");
		String password = sc.next();
		CafeUserVO user = null;
		Connection conn = DBUtil.dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,id,remain_time,email,ox,password from cafe_user";
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				if(id.equals(rs.getString(3)) && password.equals(rs.getString(7))) {
					user = new CafeUserVO(rs.getString(1),rs.getString(2),
					rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6));
					System.out.println("로그인 성공");
					return user;
				}
			}
			System.out.println("로그인 실패");
			System.out.println("ID 혹은 Password가 틀렸습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return user;	
	}
	
	public CafeUserVO nonMemberLogin() {
		System.out.print("이름>");
		String name = sc.next();
		System.out.print("핸드폰 번호>");
		String phone_number = sc.next();
		CafeUserVO user = null;
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,ox,remain_time from cafe_user";
		String sql2 = "insert into cafe_user(name,phone_number,ox) values(?,?,?)";
		String ox = "비회원";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && ox.equals(rs.getString(3))) {
					System.out.println("비회원 로그인 성공");
					user = new CafeUserVO();
					user.setName(rs.getString(1));
					user.setPhone_number(rs.getString(2));
					user.setOx(rs.getString(3));
					user.setRemain_time(rs.getInt(4));
					return user;
				} else if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && !ox.equals(rs.getString(3))){
					System.out.println("저희 Study Cafe 회원입니다. 회원페이지를 이용해주세요.");
					return user;
				} else if(!name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2))) {
					System.out.println("등록된 핸드폰 번호와 이름이 일치하지 않습니다. 이름을 확인해주세요.");
					return user;
				}
			}
			st.close();
			st = conn.prepareStatement(sql2);
			st.setString(1, name);
			st.setString(2, phone_number);
			st.setString(3, "비회원");
			int cnt = st.executeUpdate();
			if(cnt > 0) {
				System.out.println("비회원으로 등록되었습니다. 시간충전후 이용 가능!");
			}
			conn.commit();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return user;	
	}
	
	public void signUp() {
		String phone_number="";
		String id="";
		String email="";
		List<String> ckPhone = new ArrayList<>();
		List<String> ckID = new ArrayList<>();
		List<String> ckEmail = new ArrayList<>();
		Connection con = DBUtil.dbConnect();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql2 ="select id,email from cafe_user where ox='회원' order by 1";
		String sql3 ="select phone_number from cafe_user order by 1";
		try {
			pst = con.prepareStatement(sql3);
			rs = pst.executeQuery();
			while(rs.next()) {
				ckPhone.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtil.dbClose(con, pst, rs);
		}
		Connection concon = DBUtil.dbConnect();
		PreparedStatement pstpst = null;
		ResultSet rsrs = null;
		try {
			pstpst = concon.prepareStatement(sql2);
			rsrs = pstpst.executeQuery();
			while(rsrs.next()) {
				ckID.add(rsrs.getString(1));
				ckEmail.add(rsrs.getString(2));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			DBUtil.dbClose(concon, pstpst, rsrs);
		}	
		
		System.out.print("이름>");
		String name = sc.next();
		boolean b = true;
		while(b == true) {
			System.out.print("핸드폰 번호>");
			phone_number = sc.next();
			int count = 0;
			for(String ph:ckPhone) {
				if(ph.equals(phone_number)) {
					System.out.println("이미 등록된 핸드폰 번호입니다. 확인후 이용 부탁드립니다.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("핸드폰 번호 등록 완료");
				b = false;
			}
		}	
		while(b == false) {
			System.out.print("ID>");
			id = sc.next();
			int count = 0;
			for(String d:ckID) {
				if(d.equals(id)) {
					System.out.println("이미 등록된 아이디입니다. 다른 아이디로 이용 부탁드립니다.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("아이디 등록 완료");
				b = true;
			}
		}
		System.out.print("Password>");
		String password = sc.next();
		while(b == true) {
			System.out.print("email>");
			email = sc.next();
			int count = 0;
			for(String e:ckEmail) {
				if(e.equals(email)) {
					System.out.println("이미 등록된 email 주소입니다. 확인후 이용 부탁드립니다.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("email 등록 완료");
				b = false;
			}
		}
		int remain_time = 0;
		String ox = "회원";
		
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = "insert into cafe_user values(?,?,?,?,?,?,?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, phone_number);		
			st.setString(2, email);
			st.setString(3, name);
			st.setString(4, id);
			st.setString(5, password);
			st.setString(6, ox);
			st.setInt(7, remain_time);
			int cnt = st.executeUpdate();
			if(cnt>0) {
				System.out.println("회원가입 성공! 충전후 이용해주세요.");
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
	}
	
	public void deleteMyAccount() {
		System.out.print("ID>");
		String id = sc.next();
		System.out.print("Password>");
		String password = sc.next();
		System.out.print("탈퇴시 남아있는 시간은 전부 소진됩니다. 정말 탈퇴 하시겠습니까?");
		System.out.println("1.yse  2.no");
		System.out.print(">");
		String input = sc.next();
		if(input.equals("2")){
			System.out.println("탈퇴가 취소되었습니다.");
		}else {
			boolean b = false;
			Connection conn = DBUtil.dbConnect();
			PreparedStatement st = null;
			ResultSet rs = null;
			String sql = "select id,password from cafe_user";
			Connection conn2 = DBUtil.dbConnect();
			PreparedStatement st2 = null;
			String sql2 = "delete cafe_user where id=?";
			try {
				st = conn.prepareStatement(sql);
				rs = st.executeQuery();
				while(rs.next()) {
					if(id.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
						b = true;
					}
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.dbClose(conn, st, rs);
			}
			if(b) {
				try {
					st2 = conn2.prepareStatement(sql2);
					st2.setString(1, id);
					int cnt = st2.executeUpdate();
					if(cnt > 0) {
						System.out.println("탈퇴 완료. 이용해 주셔서 감사합니다.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtil.dbClose(conn2, st2, null);
				}
			} else {
				System.out.println("ID 혹은 Password를 확인해주세요.");
			}
		}
	}
	
	public void charge(CafeUserVO user, int hour) {
		user.setRemain_time(user.getRemain_time()+(hour*60*60));
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = "update cafe_user\r\n"
				+ "set remain_time = ? where phone_number= ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, user.getRemain_time());		
			st.setString(2, user.getPhone_number());
			int cnt = st.executeUpdate();
			if(cnt>0) {
				System.out.println("충전 완료. 좌석 선택 후 이용해주세요.");
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
		synchronizeRemainTime(user);
	}
	
	public String calTime(int time) {
		if(time < 60) {
			return time + "초";
		} else if(time < 3600) {
			return time/60 + " 분 " + time%60 + " 초 ";
		} else if(time < 86400) {
			return time/3600 + " 시간 " + (time%3600)/60 + 
					" 분 " + (time%3600)%60 + " 초 ";
		} else {
			return time/86400 + " 일 " + (time%86400)/3600 + 
					" 시간 " + ((time%86400)%3600)/60 + " 분 " + 
					(((time%86400)%3600)/60)%60 + " 초 ";
		}
	}
	
	public List<SeatVO> getAvailableSeat() {
		Connection conn = DBUtil.dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from seat where available='O' and status='empty'";
		List<SeatVO> seatList = new ArrayList<>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				SeatVO seat = new SeatVO(rs.getInt(1),rs.getString(2),
						rs.getString(3),rs.getString(4));
				seatList.add(seat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return seatList;
	}
	
	public void chooseSeat(CafeUserVO user,List<SeatVO> seatList) {
		System.out.print("좌석번호 입력>");
		int input = sc.nextInt();
		if(input > 40 | input <= 0) {
			System.out.println("잘못된 입력입니다.");
			return;
		} 
		for(SeatVO seat:seatList) {
			if(seat.getSeatno() == input) {
				Connection conn = DBUtil.dbConnect();
				PreparedStatement st = null;
				String sql = "update seat set status='occupied' where seatno=?";
				try {
					st = conn.prepareStatement(sql);
					st.setInt(1, seat.getSeatno());
					st.executeUpdate();
					conn.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtil.dbClose(conn, st, null);
				}
				Connection conn2 = DBUtil.dbConnect();
				PreparedStatement st2 = null;
				String sql2 = "insert into status values(?,?,?,?,sysdate,?)";
				try {
					st2 = conn2.prepareStatement(sql2);
					st2.setInt(1, seat.getSeatno());
					st2.setString(2, user.getPhone_number());
					st2.setString(3, user.getName());
					st2.setString(4, user.getId());
					st2.setInt(5, user.getRemain_time());
					st2.executeUpdate();
					conn2.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtil.dbClose(conn2, st2, null);
				}
				System.out.println("좌석 선택 완료. 감사합니다.");
				System.out.println(input + " 번 좌석에서 오늘도 열공!");
				return;
			}
		}
		System.out.println("선택하신 " + input + " 번 좌석은 " + "이미 사용중이거나, 현재 점검중인 자리입니다.");
	}
	
	public List<StatusVO> getStatusTable() {
		List<StatusVO> list = new ArrayList<>();
		Connection conn = DBUtil.dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from status";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				StatusVO status = new StatusVO(rs.getInt(1),rs.getString(2),
						rs.getString(3),rs.getString(4),rs.getTimestamp(5),rs.getInt(6));
				list.add(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return list;
	}
	
	public int checkStatusVO(CafeUserVO user, List<StatusVO> statusTable) {
		for(StatusVO status:statusTable) {
			if(status.getPhone_number().equals(user.getPhone_number())) {
				return status.getSeatno();
			}
		}
		return 0;
	}
	
	//1분간격으로 calUsingTime 메서드 작동하게 하자.
	//퇴실시에도 작동하게 한다.
	public void calUsingTime(CafeUserVO user) {
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = 
		"update cafe_user\r\n"
		+ "set remain_time = "
		+ "remain_time - (select (sysdate-(select start_time from status where phone_number=?))*24*60*60 "
		+ "as ss from dual)\r\n"
		+ "where phone_number=?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user.getPhone_number());
			st.setString(2, user.getPhone_number());
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
	}
	
	public void submitSeat(CafeUserVO user) {
		
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		String sql = "delete status where phone_number=?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1,user.getPhone_number());
			st.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, null);
		}
		
//		Connection conn2 = DBUtil.dbConnect();
//		PreparedStatement st2 = null;
//		String sql2 = "update seat set status='empty' where seatno=?";
//		try {
//			st2 = conn2.prepareStatement(sql2);
//			st2.setInt(1, seatNo);
//			st2.executeUpdate();
//			conn2.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.dbClose(conn2, st2, null);
//		}
	}
	
//	public void RecordOnHistoryTable(CafeUserVO user) {
//		StatusVO status = new StatusVO();
//		Connection conn = DBUtil.dbConnect();
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		String sql1 = "select * from status where phone_number=?";
//		try {
//			st = conn.prepareStatement(sql1);
//			st.setString(1, user.getPhone_number());
//			rs = st.executeQuery();
//			while(rs.next()) {
//				status.setSeatno(rs.getInt(1));
//				status.setPhone_number(rs.getString(2));
//				status.setName(rs.getString(3));
//				status.setId(rs.getString(4));
//				status.setStart_time(rs.getTimestamp(5));
//				status.setRemain_time(rs.getInt(6));
//			}
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.dbClose(conn, st, rs);
//		}
//		
//		Connection conn2 = DBUtil.dbConnect();
//		PreparedStatement st2 = null;
//		String sql2 = "insert into history values(?,?,?,?,?,sysdate)";
//		try {
//			st2 = conn2.prepareStatement(sql2);
//			st2.setInt(1, status.getSeatno());
//			st2.setString(2, status.getPhone_number());
//			st2.setString(3, status.getName());
//			st2.setString(4, status.getId());
//			st2.setTimestamp(5, (Timestamp) status.getStart_time());
//			st2.executeUpdate();
//			conn2.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.dbClose(conn2, st2, null);
//		}
//		
//	}
	
	public CafeUserVO refresh(CafeUserVO user) {
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,id,remain_time,email,ox "
				+ "from cafe_user where phone_number=?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, user.getPhone_number());
			rs = st.executeQuery();
			while(rs.next()) {
				user = new CafeUserVO(rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getInt(4), rs.getString(5), rs.getString(6));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return user;
	}
	
	public List<StatusVO> realTimeStatus() {
		List<StatusVO> list = new ArrayList<>();
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select seatno,phone_number,name,id,start_time,"
				+ "to_number(to_char(remain_time-(sysdate-start_time)*24*60*60)) "
				+ "from status order by seatno";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				StatusVO status = new StatusVO();
				status.setSeatno(rs.getInt(1));	
				status.setPhone_number(rs.getString(2));
				status.setName(rs.getString(3));
				status.setId(rs.getString(4));
				status.setStart_time(rs.getTimestamp(5));
				status.setTemp_remain_time(calTime(rs.getInt(6)));
				list.add(status);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return list;
	}
	
	public List<SeatVO> getSeatTable() {
		List<SeatVO> list = new ArrayList<>();
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from seat order by seatno";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				SeatVO seat = new SeatVO();
				seat.setSeatno(rs.getInt(1));	
				seat.setAvailable(rs.getString(2));
				seat.setBookable(rs.getString(3));
				seat.setStatus(rs.getString(4));
				list.add(seat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return list;
	}
	
	public void enableSeatController(String inputStr) {
			System.out.println("Please input seat number (Command : \"all\" , \"back\")");
			System.out.print(">");
			inputStr = sc.next();
			if(inputStr.equals("back")) {
				return;
			}else if(inputStr.equals("all")) {
				enableSeat("all");
			}else {
				enableSeat(inputStr.split(","));
			}
	}
	
	public void disableSeatController(String inputStr) {
			System.out.println("Please input seat number (Command : \"all\" , \"back\")");
			System.out.print(">");
			inputStr = sc.next();
			if(inputStr.equals("back")) {
				return;
			}else if(inputStr.equals("all")) {
				disableSeat("all");
			}else {
				disableSeat(inputStr.split(","));
			}
	}
	
	public List<HistoryVO> getHistoryList() {
		List<HistoryVO> list = new ArrayList<>();
		Connection conn = DBUtil.dbConnect();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select seatno,name,phone_number,id,start_time,end_time from history order by end_time";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				HistoryVO history = new HistoryVO(rs.getInt(1),rs.getString(2),rs.getString(3),
						rs.getString(4),rs.getTimestamp(5),rs.getTimestamp(6));
				list.add(history);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return list;
	}
	
	// 시간충전or퇴실or1분간격으로 남은시간 줄어들때, Cafe_user테이블의 남은시간값으로 Status테이블의 남은시간을 동기화 시키는 메서드
	public void synchronizeRemainTime(CafeUserVO user) {
		List<StatusVO> statusTable = getStatusTable();
		if(checkStatusVO(user, statusTable) != 0) {
			Connection conn2 = DBUtil.dbConnect();
			PreparedStatement st2 = null;
			String sql2 = "update status\r\n"
					+ "set remain_time = ? where phone_number= ?";
			try {
				st2 = conn2.prepareStatement(sql2);
				st2.setInt(1, user.getRemain_time());	
				st2.setString(2, user.getPhone_number());
				st2.executeUpdate();
				conn2.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtil.dbClose(conn2, st2, null);
			}
		}
	}
	
	public String getRemainTime(CafeUserVO user) {
		List<StatusVO> list = realTimeStatus();
		for(StatusVO st:list) {
			if(st.getPhone_number().equals(user.getPhone_number())) {
				return st.getTemp_remain_time();
			}
		}
		return "user";
	}
	public void memberChargePage(CafeUserVO user, String inputStr) {
		while(true) {
			view.displayMemberChargePageFeeList();
			inputStr = sc.next();
			if(inputStr.equals("9")) break;
			switch(inputStr) {
			case "1": charge(user, 1); break;
			case "2": charge(user, 2); break;
			case "3": charge(user, 3); break;
			case "4": charge(user, 5); break;
			case "5": charge(user, 30); break;
			case "6": charge(user, 60); break;
			case "7": charge(user, 100); break;
			case "8": charge(user, 200); break;
			//추후 구현 예정
//												case "9": service.charge2(user, 336); break;
//												case "10": service.charge2(user, 672); break;
//												case "11": service.charge2(user, 2160); break;
//												case "12": service.charge2(user, 4320); break;
			}
		}
	}
	
//	public void charge2(CafeUserVO user, int hour) {
//		if(user.getRemain_time() != 0) {
//			System.out.println("시간제 이용 중에는 기간제 이용이 불가합니다. 남은 시간을 전부 사용 후 이용해주세요.");
//			return;
//		}
//		user.setRemain_time(user.getRemain_time()+(hour*60*60));
//		Connection conn = DBUtil.dbConnect();
//		PreparedStatement st = null;
//		String sql = "update cafe_user\r\n"
//				+ "set remain_time = ?,ox = '기간제' where phone_number= ?";
//		try {
//			st = conn.prepareStatement(sql);
//			st.setInt(1, user.getRemain_time());		
//			st.setString(2, user.getPhone_number());
//			int cnt = st.executeUpdate();
//			if(cnt>0) {
//				System.out.println("충전 완료. 좌석 선택 후 이용해주세요.");
//			}
//			conn.commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.dbClose(conn, st, null);
//		}
//		synchronizeRemainTime(user);
//	}


}
	


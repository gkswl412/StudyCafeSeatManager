package studyCafeSeatManagerProject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
					System.out.println("��� �¼��� Ȱ��ȭ �Ǿ����ϴ�.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("�̹� ��� �¼��� Ȱ��ȭ �Ǿ��ֽ��ϴ�.");
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
				System.out.println(cnt + "�� Success!");
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
					System.out.println("�̿� ���� �¼��� ������ ��� �¼��� ��Ȱ��ȭ �Ǿ����ϴ�.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("�̹� ��� �¼��� ��Ȱ��ȭ �Ǿ��ֽ��ϴ�.");
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
				System.out.println(cnt + "�� Success!");
				System.out.println();
			}else {
				System.out.println("�̿� ���� �¼��� ��Ȱ��ȭ�� �� �����ϴ�. Ȥ�� �߸��� �¼���ȣ.");
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
					System.out.println("�α��� ����");
					return user;
				}
			}
			System.out.println("�α��� ����");
			System.out.println("ID Ȥ�� Password�� Ʋ�Ƚ��ϴ�.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbClose(conn, st, rs);
		}
		return user;	
	}
	
	public CafeUserVO nonMemberLogin() {
		boolean b = false;
		String name="";
		String regExp="";
		String phone_number="";
		while(b==false){
			System.out.print("�̸�>");
			name = sc.next();
			regExp = "[��-�R]{1,5}";
			b = Pattern.matches(regExp, name);
			if(!b) {
				System.out.println("�ִ� �ѱ� 5����. �ٽ� �Է� ���.");
			}
		}
		while(b == true) {
			System.out.print("�ڵ��� ��ȣ>");
			phone_number = sc.next();
			regExp = "010[0-9]{3,4}[0-9]{4}";
			b = !Pattern.matches(regExp, phone_number);
			if(b == true) {
				System.out.println("�ǹٸ� �Է��� �ƴմϴ�!");
				System.out.println("(-���� 10~11�ڸ� ex)01000000000)");
			}
		}	
		CafeUserVO user = null;
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,ox,remain_time from cafe_user";
		String sql2 = "insert into cafe_user(name,phone_number,ox) values(?,?,?)";
		String ox = "��ȸ��";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && ox.equals(rs.getString(3))) {
					System.out.println("��ȸ�� �α��� ����");
					user = new CafeUserVO();
					user.setName(rs.getString(1));
					user.setPhone_number(rs.getString(2));
					user.setOx(rs.getString(3));
					user.setRemain_time(rs.getInt(4));
					return user;
				} else if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && !ox.equals(rs.getString(3))){
					System.out.println("���� Study Cafe ȸ���Դϴ�. ȸ���������� �̿����ּ���.");
					return user;
				} else if(!name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2))) {
					System.out.println("��ϵ� �ڵ��� ��ȣ�� �̸��� ��ġ���� �ʽ��ϴ�. �̸��� Ȯ�����ּ���.");
					return user;
				}
			}
			st.close();
			st = conn.prepareStatement(sql2);
			st.setString(1, name);
			st.setString(2, phone_number);
			st.setString(3, "��ȸ��");
			int cnt = st.executeUpdate();
			if(cnt > 0) {
				System.out.println("��ȸ������ ��ϵǾ����ϴ�. �ð������� �̿� ����!");
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
		String regExp="";
		String phone_number="";
		String id="";
		String email="";
		String name="";
		String password="";
		boolean b = false;
		boolean c = false;
		List<String> ckPhone = new ArrayList<>();
		List<String> ckID = new ArrayList<>();
		List<String> ckEmail = new ArrayList<>();
		Connection con = DBUtil.dbConnect();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql2 ="select id,email from cafe_user where ox='ȸ��' order by 1";
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
		while(b==false){
			System.out.print("�̸�>");
			name = sc.next();
			regExp = "[��-�R]{1,5}";
			b = Pattern.matches(regExp, name);
			if(!b) {
				System.out.println("�ִ� �ѱ� 5����. �ٽ� �Է� ���.");
			}
		}
		System.out.println("�̸� ��� �Ϸ�");
		while(b == true) {
			c = false;
			while(c==false) {
				System.out.print("�ڵ��� ��ȣ>");
				phone_number = sc.next();
				regExp = "010[0-9]{3,4}[0-9]{4}";
				c = Pattern.matches(regExp, phone_number);
				if(c==false) {
					System.out.println("�ǹٸ� �Է��� �ƴմϴ�!");
					System.out.println("(-���� 10~11�ڸ� ex)01000000000)");
				}
			}
			int count = 0;
			for(String ph:ckPhone) {
				if(ph.equals(phone_number)) {
					System.out.println("�̹� ��ϵ� �ڵ��� ��ȣ�Դϴ�. Ȯ���� �̿� ��Ź�帳�ϴ�.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("�ڵ��� ��ȣ ��� �Ϸ�");
				b = false;
			}
		}	
		while(b == false) {
			c = true;
			while(c == true) {
				System.out.print("ID>");
				id = sc.next();
				regExp = "[a-z0-9]{1,}";
				c = !(Pattern.matches(regExp, id));
				if(c == true) {
					System.out.println("�ǹٸ� �Է��� �ƴմϴ�!");
					System.out.println("(���� �ҹ��� + ����)");
				}
			}
			int count = 0;
			for(String d:ckID) {
				if(d.equals(id)) {
					System.out.println("�̹� ��ϵ� ���̵��Դϴ�. �ٸ� ���̵�� �̿� ��Ź�帳�ϴ�.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("���̵� ��� �Ϸ�");
				b = true;
			}
		}
		while(b==true) {
			System.out.print("Password>");
			password = sc.next();
			regExp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-z\\d!@#$%^&*]{10,20}$";
			b = !(Pattern.matches(regExp, password));
			if(b==true) {
				System.out.println("�ǹٸ� �Է��� �ƴմϴ�!");
				System.out.println("���� �ҹ��� + ���� + Ư�� ����(!@#$%^&*) �ڸ���:10~20");
			}
		}
		System.out.println("��й�ȣ ��� �Ϸ�");
		while(b == false) {
			c = false;
			while(c==false) {
				System.out.print("email>");
				email = sc.next();
				regExp = "[a-z0-9]+@[a-z0-9]+\\.[a-z0-9]+(\\.[a-z0-9]+)?";
				c = Pattern.matches(regExp, email);
				if(c==false) {
					System.out.println("�ǹٸ� �Է��� �ƴմϴ�!");
					System.out.println("ex)aaaa@aaaa.com");
				}
			}
			int count = 0;
			for(String e:ckEmail) {
				if(e.equals(email)) {
					System.out.println("�̹� ��ϵ� email �ּ��Դϴ�. Ȯ���� �̿� ��Ź�帳�ϴ�.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("email ��� �Ϸ�");
				b = true;
			}
		}
		int remain_time = 0;
		String ox = "ȸ��";
		
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
				System.out.println("ȸ������ ����! ������ �̿����ּ���.");
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
		System.out.print("Ż��� �����ִ� �ð��� ���� �����˴ϴ�. ���� Ż�� �Ͻðڽ��ϱ�?");
		System.out.println("1.yse  2.no");
		System.out.print(">");
		String input = sc.next();
		if(input.equals("2")){
			System.out.println("Ż�� ��ҵǾ����ϴ�.");
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
						System.out.println("Ż�� �Ϸ�. �̿��� �ּż� �����մϴ�.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtil.dbClose(conn2, st2, null);
				}
			} else {
				System.out.println("ID Ȥ�� Password�� Ȯ�����ּ���.");
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
				System.out.println("���� �Ϸ�. �¼� ���� �� �̿����ּ���.");
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
			return time + "��";
		} else if(time < 3600) {
			return time/60 + " �� " + time%60 + " �� ";
		} else if(time < 86400) {
			return time/3600 + " �ð� " + (time%3600)/60 + 
					" �� " + (time%3600)%60 + " �� ";
		} else {
			return time/86400 + " �� " + (time%86400)/3600 + 
					" �ð� " + ((time%86400)%3600)/60 + " �� " + 
					((time%86400)%3600)%60 + " �� ";
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
		System.out.print("�¼���ȣ �Է�>");
		int input = sc.nextInt();
		if(input > 40 | input <= 0) {
			System.out.println("�߸��� �Է��Դϴ�.");
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
				System.out.println("�¼� ���� �Ϸ�. �����մϴ�.");
				System.out.println(input + " �� �¼����� ���õ� ����!");
				return;
			}
		}
		System.out.println("�����Ͻ� " + input + " �� �¼��� " + "�̹� ������̰ų�, ���� �������� �ڸ��Դϴ�.");
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
	
	//1�а������� calUsingTime �޼��� �۵��ϰ� ����.
	//��ǽÿ��� �۵��ϰ� �Ѵ�.
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
	
	// �ð�����or���or1�а������� �����ð� �پ�鶧, Cafe_user���̺��� �����ð������� Status���̺��� �����ð��� ����ȭ ��Ű�� �޼���
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
			//���� ���� ����
//												case "9": service.charge2(user, 336); break;
//												case "10": service.charge2(user, 672); break;
//												case "11": service.charge2(user, 2160); break;
//												case "12": service.charge2(user, 4320); break;
			}
		}
	}
	
//	public void charge2(CafeUserVO user, int hour) {
//		if(user.getRemain_time() != 0) {
//			System.out.println("�ð��� �̿� �߿��� �Ⱓ�� �̿��� �Ұ��մϴ�. ���� �ð��� ���� ��� �� �̿����ּ���.");
//			return;
//		}
//		user.setRemain_time(user.getRemain_time()+(hour*60*60));
//		Connection conn = DBUtil.dbConnect();
//		PreparedStatement st = null;
//		String sql = "update cafe_user\r\n"
//				+ "set remain_time = ?,ox = '�Ⱓ��' where phone_number= ?";
//		try {
//			st = conn.prepareStatement(sql);
//			st.setInt(1, user.getRemain_time());		
//			st.setString(2, user.getPhone_number());
//			int cnt = st.executeUpdate();
//			if(cnt>0) {
//				System.out.println("���� �Ϸ�. �¼� ���� �� �̿����ּ���.");
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
	


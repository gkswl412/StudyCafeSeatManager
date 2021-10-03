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
					System.out.println("¸ðµç ÁÂ¼®ÀÌ È°¼ºÈ­ µÇ¾ú½À´Ï´Ù.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("ÀÌ¹Ì ¸ðµç ÁÂ¼®ÀÌ È°¼ºÈ­ µÇ¾îÀÖ½À´Ï´Ù.");
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
				System.out.println(cnt + "°Ç Success!");
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
					System.out.println("ÀÌ¿ë ÁßÀÎ ÁÂ¼®À» Á¦¿ÜÇÑ ¸ðµç ÁÂ¼®ÀÌ ºñÈ°¼ºÈ­ µÇ¾ú½À´Ï´Ù.");
					System.out.println();
				}else {
					System.out.println();
					System.out.println("ÀÌ¹Ì ¸ðµç ÁÂ¼®ÀÌ ºñÈ°¼ºÈ­ µÇ¾îÀÖ½À´Ï´Ù.");
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
				System.out.println(cnt + "°Ç Success!");
				System.out.println();
			}else {
				System.out.println("ÀÌ¿ë ÁßÀÎ ÁÂ¼®Àº ºñÈ°¼ºÈ­ÇÒ ¼ö ¾ø½À´Ï´Ù. È¤Àº Àß¸øµÈ ÁÂ¼®¹øÈ£.");
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
					System.out.println("·Î±×ÀÎ ¼º°ø");
					return user;
				}
			}
			System.out.println("·Î±×ÀÎ ½ÇÆÐ");
			System.out.println("ID È¤Àº Password°¡ Æ²·È½À´Ï´Ù.");
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
			System.out.print("ÀÌ¸§>");
			name = sc.next();
			regExp = "[°¡-ÆR]{1,5}";
			b = Pattern.matches(regExp, name);
			if(!b) {
				System.out.println("ÃÖ´ë ÇÑ±Û 5±ÛÀÚ. ´Ù½Ã ÀÔ·Â ¿ä¸Á.");
			}
		}
		while(b == true) {
			System.out.print("ÇÚµåÆù ¹øÈ£>");
			phone_number = sc.next();
			regExp = "010[0-9]{3,4}[0-9]{4}";
			b = !Pattern.matches(regExp, phone_number);
			if(b == true) {
				System.out.println("¿Ç¹Ù¸¥ ÀÔ·ÂÀÌ ¾Æ´Õ´Ï´Ù!");
				System.out.println("(-¾øÀÌ 10~11ÀÚ¸® ex)01000000000)");
			}
		}	
		CafeUserVO user = null;
		Connection conn = DBUtil.dbConnect();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select name,phone_number,ox,remain_time from cafe_user";
		String sql2 = "insert into cafe_user(name,phone_number,ox) values(?,?,?)";
		String ox = "ºñÈ¸¿ø";
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && ox.equals(rs.getString(3))) {
					System.out.println("ºñÈ¸¿ø ·Î±×ÀÎ ¼º°ø");
					user = new CafeUserVO();
					user.setName(rs.getString(1));
					user.setPhone_number(rs.getString(2));
					user.setOx(rs.getString(3));
					user.setRemain_time(rs.getInt(4));
					return user;
				} else if(name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2)) && !ox.equals(rs.getString(3))){
					System.out.println("ÀúÈñ Study Cafe È¸¿øÀÔ´Ï´Ù. È¸¿øÆäÀÌÁö¸¦ ÀÌ¿ëÇØÁÖ¼¼¿ä.");
					return user;
				} else if(!name.equals(rs.getString(1)) && phone_number.equals(rs.getString(2))) {
					System.out.println("µî·ÏµÈ ÇÚµåÆù ¹øÈ£¿Í ÀÌ¸§ÀÌ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù. ÀÌ¸§À» È®ÀÎÇØÁÖ¼¼¿ä.");
					return user;
				}
			}
			st.close();
			st = conn.prepareStatement(sql2);
			st.setString(1, name);
			st.setString(2, phone_number);
			st.setString(3, "ºñÈ¸¿ø");
			int cnt = st.executeUpdate();
			if(cnt > 0) {
				System.out.println("ºñÈ¸¿øÀ¸·Î µî·ÏµÇ¾ú½À´Ï´Ù. ½Ã°£ÃæÀüÈÄ ÀÌ¿ë °¡´É!");
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
		String sql2 ="select id,email from cafe_user where ox='È¸¿ø' order by 1";
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
			System.out.print("ÀÌ¸§>");
			name = sc.next();
			regExp = "[°¡-ÆR]{1,5}";
			b = Pattern.matches(regExp, name);
			if(!b) {
				System.out.println("ÃÖ´ë ÇÑ±Û 5±ÛÀÚ. ´Ù½Ã ÀÔ·Â ¿ä¸Á.");
			}
		}
		System.out.println("ÀÌ¸§ µî·Ï ¿Ï·á");
		while(b == true) {
			c = false;
			while(c==false) {
				System.out.print("ÇÚµåÆù ¹øÈ£>");
				phone_number = sc.next();
				regExp = "010[0-9]{3,4}[0-9]{4}";
				c = Pattern.matches(regExp, phone_number);
				if(c==false) {
					System.out.println("¿Ç¹Ù¸¥ ÀÔ·ÂÀÌ ¾Æ´Õ´Ï´Ù!");
					System.out.println("(-¾øÀÌ 10~11ÀÚ¸® ex)01000000000)");
				}
			}
			int count = 0;
			for(String ph:ckPhone) {
				if(ph.equals(phone_number)) {
					System.out.println("ÀÌ¹Ì µî·ÏµÈ ÇÚµåÆù ¹øÈ£ÀÔ´Ï´Ù. È®ÀÎÈÄ ÀÌ¿ë ºÎÅ¹µå¸³´Ï´Ù.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("ÇÚµåÆù ¹øÈ£ µî·Ï ¿Ï·á");
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
					System.out.println("¿Ç¹Ù¸¥ ÀÔ·ÂÀÌ ¾Æ´Õ´Ï´Ù!");
					System.out.println("(¿µ¾î ¼Ò¹®ÀÚ + ¼ýÀÚ)");
				}
			}
			int count = 0;
			for(String d:ckID) {
				if(d.equals(id)) {
					System.out.println("ÀÌ¹Ì µî·ÏµÈ ¾ÆÀÌµðÀÔ´Ï´Ù. ´Ù¸¥ ¾ÆÀÌµð·Î ÀÌ¿ë ºÎÅ¹µå¸³´Ï´Ù.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("¾ÆÀÌµð µî·Ï ¿Ï·á");
				b = true;
			}
		}
		while(b==true) {
			System.out.print("Password>");
			password = sc.next();
			regExp = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[a-z\\d!@#$%^&*]{10,20}$";
			b = !(Pattern.matches(regExp, password));
			if(b==true) {
				System.out.println("¿Ç¹Ù¸¥ ÀÔ·ÂÀÌ ¾Æ´Õ´Ï´Ù!");
				System.out.println("¿µ¾î ¼Ò¹®ÀÚ + ¼ýÀÚ + Æ¯¼ö ¹®ÀÚ(!@#$%^&*) ÀÚ¸´¼ö:10~20");
			}
		}
		System.out.println("ºñ¹Ð¹øÈ£ µî·Ï ¿Ï·á");
		while(b == false) {
			c = false;
			while(c==false) {
				System.out.print("email>");
				email = sc.next();
				regExp = "[a-z0-9]+@[a-z0-9]+\\.[a-z0-9]+(\\.[a-z0-9]+)?";
				c = Pattern.matches(regExp, email);
				if(c==false) {
					System.out.println("¿Ç¹Ù¸¥ ÀÔ·ÂÀÌ ¾Æ´Õ´Ï´Ù!");
					System.out.println("ex)aaaa@aaaa.com");
				}
			}
			int count = 0;
			for(String e:ckEmail) {
				if(e.equals(email)) {
					System.out.println("ÀÌ¹Ì µî·ÏµÈ email ÁÖ¼ÒÀÔ´Ï´Ù. È®ÀÎÈÄ ÀÌ¿ë ºÎÅ¹µå¸³´Ï´Ù.");
					count ++;
				}
			}
			if(count == 0) {
				System.out.println("email µî·Ï ¿Ï·á");
				b = true;
			}
		}
		int remain_time = 0;
		String ox = "È¸¿ø";
		
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
				System.out.println("È¸¿ø°¡ÀÔ ¼º°ø! ÃæÀüÈÄ ÀÌ¿ëÇØÁÖ¼¼¿ä.");
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
		System.out.print("Å»Åð½Ã ³²¾ÆÀÖ´Â ½Ã°£Àº ÀüºÎ ¼ÒÁøµË´Ï´Ù. Á¤¸» Å»Åð ÇÏ½Ã°Ú½À´Ï±î?");
		System.out.println("1.yse  2.no");
		System.out.print(">");
		String input = sc.next();
		if(input.equals("2")){
			System.out.println("Å»Åð°¡ Ãë¼ÒµÇ¾ú½À´Ï´Ù.");
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
						System.out.println("Å»Åð ¿Ï·á. ÀÌ¿ëÇØ ÁÖ¼Å¼­ °¨»çÇÕ´Ï´Ù.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtil.dbClose(conn2, st2, null);
				}
			} else {
				System.out.println("ID È¤Àº Password¸¦ È®ÀÎÇØÁÖ¼¼¿ä.");
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
				System.out.println("ÃæÀü ¿Ï·á. ÁÂ¼® ¼±ÅÃ ÈÄ ÀÌ¿ëÇØÁÖ¼¼¿ä.");
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
			return time + "ÃÊ";
		} else if(time < 3600) {
			return time/60 + " ºÐ " + time%60 + " ÃÊ ";
		} else if(time < 86400) {
			return time/3600 + " ½Ã°£ " + (time%3600)/60 + 
					" ºÐ " + (time%3600)%60 + " ÃÊ ";
		} else {
			return time/86400 + " ÀÏ " + (time%86400)/3600 + 
					" ½Ã°£ " + ((time%86400)%3600)/60 + " ºÐ " + 
					((time%86400)%3600)%60 + " ÃÊ ";
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
		System.out.print("ÁÂ¼®¹øÈ£ ÀÔ·Â>");
		int input = sc.nextInt();
		if(input > 40 | input <= 0) {
			System.out.println("Àß¸øµÈ ÀÔ·ÂÀÔ´Ï´Ù.");
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
				System.out.println("ÁÂ¼® ¼±ÅÃ ¿Ï·á. °¨»çÇÕ´Ï´Ù.");
				System.out.println(input + " ¹ø ÁÂ¼®¿¡¼­ ¿À´Ãµµ ¿­°ø!");
				return;
			}
		}
		System.out.println("¼±ÅÃÇÏ½Å " + input + " ¹ø ÁÂ¼®Àº " + "ÀÌ¹Ì »ç¿ëÁßÀÌ°Å³ª, ÇöÀç Á¡°ËÁßÀÎ ÀÚ¸®ÀÔ´Ï´Ù.");
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
	
	//1ºÐ°£°ÝÀ¸·Î calUsingTime ¸Þ¼­µå ÀÛµ¿ÇÏ°Ô ÇÏÀÚ.
	//Åð½Ç½Ã¿¡µµ ÀÛµ¿ÇÏ°Ô ÇÑ´Ù.
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
	
	// ½Ã°£ÃæÀüorÅð½Çor1ºÐ°£°ÝÀ¸·Î ³²Àº½Ã°£ ÁÙ¾îµé¶§, Cafe_userÅ×ÀÌºíÀÇ ³²Àº½Ã°£°ªÀ¸·Î StatusÅ×ÀÌºíÀÇ ³²Àº½Ã°£À» µ¿±âÈ­ ½ÃÅ°´Â ¸Þ¼­µå
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
			//ÃßÈÄ ±¸Çö ¿¹Á¤
//												case "9": service.charge2(user, 336); break;
//												case "10": service.charge2(user, 672); break;
//												case "11": service.charge2(user, 2160); break;
//												case "12": service.charge2(user, 4320); break;
			}
		}
	}
	
//	public void charge2(CafeUserVO user, int hour) {
//		if(user.getRemain_time() != 0) {
//			System.out.println("½Ã°£Á¦ ÀÌ¿ë Áß¿¡´Â ±â°£Á¦ ÀÌ¿ëÀÌ ºÒ°¡ÇÕ´Ï´Ù. ³²Àº ½Ã°£À» ÀüºÎ »ç¿ë ÈÄ ÀÌ¿ëÇØÁÖ¼¼¿ä.");
//			return;
//		}
//		user.setRemain_time(user.getRemain_time()+(hour*60*60));
//		Connection conn = DBUtil.dbConnect();
//		PreparedStatement st = null;
//		String sql = "update cafe_user\r\n"
//				+ "set remain_time = ?,ox = '±â°£Á¦' where phone_number= ?";
//		try {
//			st = conn.prepareStatement(sql);
//			st.setInt(1, user.getRemain_time());		
//			st.setString(2, user.getPhone_number());
//			int cnt = st.executeUpdate();
//			if(cnt>0) {
//				System.out.println("ÃæÀü ¿Ï·á. ÁÂ¼® ¼±ÅÃ ÈÄ ÀÌ¿ëÇØÁÖ¼¼¿ä.");
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
	


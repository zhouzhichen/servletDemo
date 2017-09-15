package pageDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtil.DBUtil;

public class PageDao {
	public static int rows() {
		Connection conn = DBUtil.getConn();
		String sql = "select count(*) from case1";
		PreparedStatement pst;
		ResultSet rs = null;
		int rows = 0;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			rs.last();
			rows = rs.getRow();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}

	public static int totalPage() {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1";
		PreparedStatement pst;
		ResultSet rs = null;
		int rows = 0;
		int b = 0;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				++b;
			}
			rows = (b - 1) / 4 + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
}

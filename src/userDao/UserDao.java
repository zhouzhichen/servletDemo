package userDao;

import java.io.File;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import DBUtil.DBUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import userBean.UserBean;

public class UserDao {

	public static void insert(int id, String name) {
		Connection conn = DBUtil.getConn();
		String sql = "insert into case1(id,name) values(?,?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void delete(int id) {
		Connection conn = DBUtil.getConn();
		String sql = "delete from case1 where id=" + id;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static UserBean select(int id) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 where id =" + id;
		UserBean user = null;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static List<UserBean> selectAll() {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1";
		List list = new ArrayList();

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void update(int id, String name) {
		Connection conn = DBUtil.getConn();
		String sql = "update  case1 set name=" + name + " where id=" + id;
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static UserBean login(int id, String name) {
		UserBean user = null;
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 where id=? and name=?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static List<UserBean> last(int a) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 limit " + (a - 1) * 4 + ",4";
		List list = new ArrayList();

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<UserBean> previous(int a) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 limit " + (a - 2) * 4 + ",4";
		List list = new ArrayList();

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<UserBean> next(int a) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 limit " + (a) * 4 + ",4";
		List list = new ArrayList();

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<UserBean> search(String str) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from case1 where name like '%" + str + "%'";
		List list = new ArrayList();

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.executeQuery();
			ResultSet rs = pst.getResultSet();
			while (rs.next()) {
				UserBean user = new UserBean();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				list.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<UserBean> choose(String a[]) {
		Connection conn = DBUtil.getConn();
		List list = new ArrayList();
		for (int i = 0; i < a.length; i++) {
			int j = Integer.parseInt(a[i]);
			String sql = "select * from case1 where id=" + j;
			try {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.executeQuery();
				ResultSet rs = pst.getResultSet();
				while (rs.next()) {
					UserBean user = new UserBean();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					list.add(user);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 从MySQL中读取数据写到Excel
	public static void MysqltoExcel(List<UserBean> list, String fileName, HttpServletResponse response) {
		WritableWorkbook wwb = null;
		try {
			// 为Excel表单设置中文格式
			fileName = URLEncoder.encode(fileName, "utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			wwb = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet ws = wwb.createSheet("学生表", 0);

			try {
				ws.addCell(new Label(0, 0, "id"));
				ws.addCell(new Label(1, 0, "姓名"));
				for (int i = 0; i < list.size(); i++) {
					ws.addCell(new Label(0, i + 1, list.get(i).getId() + ""));
					ws.addCell(new Label(1, i + 1, list.get(i).getName()));
				}
				wwb.write();
				wwb.close();
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
	}

}

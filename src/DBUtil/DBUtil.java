package DBUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DBUtil {
	public static Connection getConn() {
		Connection conn = null;
		Properties pro = new Properties();
		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		//有getClassLoader()代表是在类加载器classes里面的路径，classes为根目录，所以以上写法为正确的
		//InputStream in = DBUtil.class.getResourceAsStream("/jdbc.properties");
		//没有getClassLoader()代表的是一般的，直接在src下面的目录执行，/代表的是项目的根目录，也就是src根目录
		/*
		 * 虚拟路径和绝对路径详解：
		绝对路径不讲了，直接来。
		虚拟路径：
			1.在一般情况之下，../代表退到上一级那个级层了，但是并没有指定级名。.和./代表的是当前目录
			2./一般代表的是当前项目的根目录src（在java和servlet文件上），而在jsp文件上的/则一般表示在服务器上的根路径即http://localhost:3306/
		 */		
		System.out.println(in);
		try {
			pro.load(in);
			BasicDataSource bs = (BasicDataSource) BasicDataSourceFactory.createDataSource(pro);
			conn = bs.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeing(ResultSet s, Statement s1, Connection c) {
		if (s != null) {
			try {
				s.close();
				s = null;
				// 赋值为空的原因是为了释放的准确性和资源能够得到及时的释放。
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (s1 != null) {
			try {
				s1.close();
				s1 = null;
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		if (c != null) {
			try {
				c.close();
				c = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pageDao.PageDao;
import userDao.UserDao;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 public int a = 1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("login".equals(method)) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			if (UserDao.login(Integer.parseInt(id), name) != null) {
				List li = UserDao.last(1);
				request.setAttribute("li", li);
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}
		}
		if ("insert".equals(method)) {
			String id = request.getParameter("1");
			String name = request.getParameter("2");
			UserDao.insert(Integer.parseInt(id), name);
			List li = UserDao.last(1);
			request.setAttribute("li", li);
			// request作用在jsp和servlet界面，起传递数据的作用，可以封装参数（键值对），也可以得到参数
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		if ("delete".equals(method)) {
			String id = request.getParameter("id");
			UserDao.delete(Integer.parseInt(id));
			List li = UserDao.last(1);
			request.setAttribute("li", li);
			// request作用在jsp和servlet界面，起传递数据的作用，可以封装参数（键值对），也可以得到参数
			request.getRequestDispatcher("home.jsp").forward(request, response);

		}
		if ("previous".equals(method)) {
			/*
			 * int allow=Integer.parseInt(request.getParameter("a")); if(allow==1){ allow=2;
			 * UserDao.previous(allow);
			 * 
			 * }else { UserDao.previous(allow); } request.setAttribute("a", allow-1);
			 */
			List li = new ArrayList();
			if (a == 1) {
				a = 2;
				li = UserDao.previous(a);
			} else {
				li = UserDao.previous(a);
			}
			a--;//每次查完相应数据过后,a也要相应的跟着页面变化
			request.setAttribute("a", a);
			request.setAttribute("li", li);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		if ("next".equals(method)) {
			/*
			 * int allow = Integer.parseInt(request.getParameter("a"));
			 * 
			 * UserDao.next(allow);
			 * 
			 * request.setAttribute("a", allow + 1);
			 */
			List li = new ArrayList();
			if (a == PageDao.totalPage()) {
				a = a - 1;
				li = UserDao.next(a);
			} else {
				li = UserDao.next(a);
			}
			a++;
			request.setAttribute("a", a);
			request.setAttribute("li", li);
			request.getRequestDispatcher("home.jsp").forward(request, response);

		}
		if("search".equals(method)) {
			String str=request.getParameter("find");
			List li = new ArrayList();
			li=UserDao.search(str);
			request.setAttribute("li", li);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		if("choose".equals(method)) {
			String str=request.getParameter("abc");
			String str1=request.getParameter("files");
			String s[]=str.split(",");
			List li = new ArrayList();
			li=UserDao.choose(s);
			UserDao.MysqltoExcel(li, str1, response);
			request.setAttribute("li", li);
//			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

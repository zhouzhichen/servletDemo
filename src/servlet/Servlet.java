package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
			String free = request.getParameter("free");
			if (UserDao.login(Integer.parseInt(id), name) != null) {
				List li = UserDao.last(1);
				request.setAttribute("li", li);
				if ("1".equals(free)) {
					HttpSession session = request.getSession(); // 得到session，并将用户信息存到session中
					session.setAttribute("id", id);
					session.setAttribute("name", name);
					Cookie cookie = new Cookie("session", session.getId());//将session的id以键值对的形式存到cookie中
					cookie.setMaxAge(60*5);
					response.addCookie(cookie);
					//此时需要在应用域中添加一个属性，用于储存用户的sessionid和对应的session关系  
					//以保证后面可以根据sessionid获取到session ，
					//因为可以通过session得到sessionid，但是一般不能通过sessionid 得到session，所以通过以下方式设置得到session
					getServletContext().setAttribute(session.getId(), session); 
					
				}
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
			 * int allow=Integer.parseInt(request.getParameter("a"));
			 * if(allow==1){ allow=2; UserDao.previous(allow);
			 * 
			 * }else { UserDao.previous(allow); } request.setAttribute("a",
			 * allow-1);
			 */
			List li = new ArrayList();
			if (a == 1) {
				a = 2;
				li = UserDao.previous(a);
			} else {
				li = UserDao.previous(a);
			}
			a--;// 每次查完相应数据过后,a也要相应的跟着页面变化
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
		if ("search".equals(method)) {
			String str = request.getParameter("find");
			List li = new ArrayList();
			li = UserDao.search(str);
			request.setAttribute("li", li);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
		if ("choose".equals(method)) {
			String str = request.getParameter("abc");
			String str1 = request.getParameter("files");
			String s[] = str.split(",");
			List li = new ArrayList();
			li = UserDao.choose(s);
			UserDao.MysqltoExcel(li, str1, response);
			request.setAttribute("li", li);
			// request.getRequestDispatcher("home.jsp").forward(request,
			// response);
		}
		if ("upload".equals(method)) {
			fileControl(request, response);
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

	public void fileControl(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// 得到上传文件的保存目录，将上传文件放在WEB-INF的目录下，不允许外界直接访问，保证上传文件的安全性
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
		File file = new File(savePath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		String message = "";
		try {
			// 使用Apache文件上传组处理文件的上传步骤
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("utf-8");
			// 判断提交上来的数据是否是上传表单的数据
			// 在解析请求之前先判断enctype属性是否为multipart/form-data
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			// 使用ServletFileUpload解析器上传数据，解析结果为一个List<FileItem>集合
			// 每一个FileItem对应的是一个Form表单的输入项

			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 如果输入fileitem里封装的是普通输入项的数据
					String name = item.getFieldName();
					// 解决普通输入项的中文乱码问题
					String value = item.getString("utf-8");
					// 或者用 String value=new
					// String(value.getBytes("iso-8859-1"),"utf-8");
					System.out.println(name + "=" + value);
				} else {
					// 如果fileitem中封装的是上传文件
					// 得到上传文件的名称
					String filename = item.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 因为不同浏览器提交的文件名是不一样的，所以我们一般只保留文件名而不要路径
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					// 获取item中上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
					byte buffer[] = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item.delete();// 处理文件上传时生成的临时文件
				}
			}

		} catch (Exception e) {
			message = "文件上传失败";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		try {
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

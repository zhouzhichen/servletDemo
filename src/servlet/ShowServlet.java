package servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowServlet
 */
@WebServlet("/ShowServlet")
public class ShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadFilePath = this.getServletContext().getRealPath("/WEB-INf/upload");
		Map<String, String> fileNameMap = new HashMap<String, String>();
		listfile(new File(uploadFilePath), fileNameMap);
		request.setAttribute("fileNameMap", fileNameMap);
		try {
			request.getRequestDispatcher("ListFile.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	// 循环递归遍历得到map集合
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void listfile(File file, Map<String, String> map) {
		if (!file.isFile()) {
			File files[] = file.listFiles();
			for (File f : files) {
				listfile(f, map);
			}
		} else {
			String realname = file.getName().substring(file.getName().indexOf("_") + 1);
			map.put(file.getName(), realname);
		}
	}

}

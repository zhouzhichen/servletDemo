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
		//得到的是服务器的根目录
		String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
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
		 if(file == null) return;
		if (!file.isFile()) {
			File f[] = file.listFiles();
			System.out.println(11111);
			if(f == null) return;
			for(int i=0;i<f.length;i++){
				listfile(f[i], map);
			}
		} else {
			String realName = file.getName().substring(file.getName().indexOf("_") + 1);
			map.put(file.getName(), realName);
		}
	}

}

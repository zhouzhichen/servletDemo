package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("filename");// 上传和下载文件名都如23239283-92489-阿凡达.avi
		fileName = new String(fileName.getBytes("iso8859-1"), "utf-8");
		String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		// 此处为什么？
		// String path = findFileSavePathByFileName(fileName, fileSaveRootPath);

		File file = new File(fileSaveRootPath + "\\" + fileName);
		System.out.println(222);
		if (!file.exists()) {
			return;
		}
		String realname = fileName.substring(fileName.indexOf("_") + 1);
		// 设置响应头。控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "utf-8"));
		FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileName);
		OutputStream out = response.getOutputStream();
		System.out.println(333);
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
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

	// 为什么
	/*public String findFileSavePathByFileName(String filename, String saveRootPath) {
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode & 0xf) >> 4;
		String dir = saveRootPath + "\\" + dir1 + "\\" + dir2;
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}
*/
}

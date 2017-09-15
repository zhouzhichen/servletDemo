package com.rf.zzc.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Demo1 extends HttpServlet {

	String str;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		System.out.println("用get传递");
		PrintWriter out = resp.getWriter();
		//this表示这个servlet类
		out.append("1.通过this获取的init-param方式: " + this.getInitParameter("name1") + "<br>");
		out.append("2.通过getServletConfig()获取的方式: " + this.getServletConfig().getInitParameter("name1") + "<br>");
		out.append("3.通过init方法的ServletConfig参数获取的方式: " + str + "<br>");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("用post传递");
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		str = config.getInitParameter("name1");
	}

}

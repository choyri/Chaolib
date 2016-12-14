package com.choyri.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跳转
 */
public class WebUtil {

	/**
	 * 跳转通用方法
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param uri
	 *            Object 类型为 RequestDispatcher 则进行 forward，类型为 String 则进行 sendRedirect 
	 */
	public static void goTo(HttpServletRequest request, HttpServletResponse response, Object uri) {
		if (uri instanceof RequestDispatcher) {
			try {
				((RequestDispatcher) uri).forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		} else if (uri instanceof String) {
			try {
				response.sendRedirect(request.getContextPath() + uri);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
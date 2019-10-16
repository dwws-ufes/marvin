package br.ufes.informatica.marvin.core.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO: document this type.
 *
 * @author Vítor E. Silva Souza (vitorsouza@gmail.com)
 * @version 1.0
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = { "/public/servlet/changePassword/*" })
public class ChangePasswordServlet extends HttpServlet {
	/** Serialization id. */
	private static final long serialVersionUID = 1L;

	/** Path to the folder where the view files (web pages) for this action are placed. */
	private static final String CHANGE_PASSWORD_VIEW_URL = "/public/changePassword/index.xhtml";

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Extracts the password code from the URL.
		String passwordCode = request.getPathInfo();
		if (passwordCode != null && passwordCode.length() > 0) passwordCode = passwordCode.substring(1);

		// FIXME: send the password code to the JSF controller.
		String url = CHANGE_PASSWORD_VIEW_URL + "?code=" + passwordCode;
		response.sendRedirect(request.getContextPath() + url);
	}
}

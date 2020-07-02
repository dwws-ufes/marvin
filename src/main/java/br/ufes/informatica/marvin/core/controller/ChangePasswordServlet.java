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
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/servlet/changePassword/*"})
public class ChangePasswordServlet extends HttpServlet {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Path to the folder where the view files (web pages) for this feature are placed. */
  private static final String CHANGE_PASSWORD_VIEW_URL = "/core/changePassword/index.xhtml";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Extracts the password code from the URL.
    String passwordCode = request.getPathInfo();
    if (passwordCode != null && passwordCode.length() > 0)
      passwordCode = passwordCode.substring(1);

    // FIXME: send the password code to the JSF controller.
    String url = CHANGE_PASSWORD_VIEW_URL + "?code=" + passwordCode;
    response.sendRedirect(request.getContextPath() + url);
  }
}

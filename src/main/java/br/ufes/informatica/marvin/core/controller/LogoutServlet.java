package br.ufes.informatica.marvin.core.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A servlet that serves to invalidate the user's session and, therefore, log her out of the system.
 * 
 * @author Vítor E. Silva Souza (https://github.com/vitorsouza/)
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
  /** The unique identifier for a serializable class. */
  private static final long serialVersionUID = 1L;

  /** Logger for this class. */
  private static final Logger logger = Logger.getLogger(LogoutServlet.class.getCanonicalName());

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    logger.log(Level.FINER, "Invalidating a user session...");

    // Destroys the session for this user.
    HttpSession session = request.getSession(false);
    if (session != null)
      session.invalidate();

    // Redirects back to the initial page.
    response.sendRedirect(request.getContextPath() + "/login.xhtml");
  }
}

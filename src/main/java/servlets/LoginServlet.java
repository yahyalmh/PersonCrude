package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by asus x555L on 2017/02/26.
 */
@WebServlet(name = "LoginServlet",urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uname=request.getParameter("username");
        String pass=request.getParameter("password");
        HttpSession session=request.getSession();
        session.setAttribute("signin",false);

        if (uname!=null&& pass!=null&&uname.equalsIgnoreCase("admin")&&pass.equalsIgnoreCase("admin")){


            session.setAttribute("signin",true);
            response.getWriter().print("<h1>Hi, "+uname+"<br> you login successfuly :)</h1>");

        }else {
            RequestDispatcher rd= request.getRequestDispatcher("index.html");
            rd.include(request,response);
        }
    }


}

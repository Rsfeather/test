package murach.admin;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import murach.business.User;
import murach.data.UserDB;

public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        String url = "/index.jsp";
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "display_users";  // default action
        }
        
        // perform action and set URL to appropriate page
        if (action.equals("display_users")) {            
            // get list of users
            ArrayList<User> users = UserDB.selectUsers();            

            // set as a request attribute
            request.setAttribute("users", users);
            // forward to index.jsp
            url = "/index.jsp";
        } 
        else if (action.equals("display_user")) {
            // get user for specified email
            String email = request.getParameter("email");
            User user = UserDB.selectUser(email);
            // set as session attribute
            session.setAttribute("user",user);
            // forward to user.jsp
            url = "/user.jsp";
        }
        else if (action.equals("update_user")) {
            // update user in database
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            User user =(User) session.getAttribute("user");
            user.setFirstName(firstName);
            user.setLastName(lastName);
            UserDB.update(user);
            ArrayList<User> users = UserDB.selectUsers();
            request.setAttribute("users",users);
            // get current user list and set as request attribute
            // forward to index.jsp
            url="/index.jsp";
        }
        else if (action.equals("delete_user")) {
            String email = request.getParameter("email");
            User user = UserDB.selectUser(email);
            UserDB.delete(user);
            ArrayList<User> users = UserDB.selectUsers();
            request.setAttribute("users", users);
            // get the user for the specified email
            
            // delete the user            
            // get current list of users
            // set as request attribute
            // forward to index.jsp
            url="/index.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }    
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }    
}
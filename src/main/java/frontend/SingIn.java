package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stalker on 28.01.16.
 */
public class SingIn extends HttpServlet {

    private AccountService accountService;

    public SingIn(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
        Map<String,Object> pageVariables = new HashMap<>();

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getPage("singIn.html",pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        Map<String,Object> pageVariables = new HashMap<>();

        UserProfile profile = accountService.getUser(login);
        if(profile != null && password.equals(profile.getPassword())){
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            boolean result = accountService.addSession(sessionId,profile);
            if(result) {
                pageVariables.put("result", "Авторизация прошла успешно, вы молодец");
            } else {
                pageVariables.put("result", "Вы уже авторизованы, вы молодец");
            }
        } else {
            pageVariables.put("result", "Что то пошло не так, но вы молодец");
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(PageGenerator.getPage("result.html",pageVariables));
    }

}

package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stalker on 28.01.16.
 */

public class SingUp extends HttpServlet {

    private AccountService accountService;

    public static final String RESPONSE_SUCCESS = "Вы молодец, регистрация прошла успешно";
    public static final String RESPONSE_ERROR = "Вы молодец, но что то пошло не так";

    public SingUp(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Map<String,Object> pageVariables = new HashMap<>();

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(PageGenerator.getPage("singUp.html", pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        UserProfile profile = new UserProfile(login,password,email);

        response.setContentType("text/html;charset=utf-8");

        Map<String,Object> pageVariables = new HashMap<>();

        boolean result = accountService.addUser(login, profile);
        if(result){
            pageVariables.put("result", RESPONSE_SUCCESS);
        }
        else {
            pageVariables.put("result", RESPONSE_ERROR);
        }

        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("result.html",pageVariables));
    }

}
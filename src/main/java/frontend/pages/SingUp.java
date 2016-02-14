package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import databaseService.messageSystem.MessageAddUser;
import frontend.FrontendThread;
import frontend.UserState;
import messageSystem.AddressService;
import messageSystem.Message;
import messageSystem.MessageSystem;
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

public class SingUp extends HttpServlet {

    private AccountService accountService;
    private FrontendThread frontend;

    public static final String RESPONSE_SUCCESS = "Вы молодец, регистрация прошла успешно";
    public static final String RESPONSE_ERROR = "Вы молодец, но что то пошло не так";
    public static final String RESPONSE_WAIT = "Пожалуйста подождите";

    public SingUp(AccountService accountService,FrontendThread frontend) {
        this.accountService = accountService;
        this.frontend = frontend;
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

        frontend.addUser(login,profile);
        frontend.setUserState(login,UserState.PENDING_REGISTRATION);

        UserState state = frontend.getUserState(login);
        System.out.println(state);

        if(state == UserState.SUCCESSFUL_REGISTERED){
            pageVariables.put("result", RESPONSE_SUCCESS);
            frontend.setUserState(login,UserState.SLEEPS);
        }
        if(state == UserState.PENDING_REGISTRATION){
            pageVariables.put("result", RESPONSE_WAIT);
        }
        if(state == UserState.UNSUCCESSFUL_REGISTERED){
            pageVariables.put("result", RESPONSE_ERROR);
        }


        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("result.html",pageVariables));
    }

}
package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
import frontend.UserState;
import messageSystem.AddressService;
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
    private FrontendThread frontend;

    public static final String RESPONSE_SUCCESS = "Авторизация прошла успешно, вы молодец";
    public static final String RESPONSE_ALREADY_SINGIN = "Вы уже авторизованы, вы молодец";
    public static final String RESPONSE_ERROR = "Что то пошло не так, но вы молодец";
    public static final String RESPONSE_WAIT = "Подождите";

    public SingIn(AccountService accountService, FrontendThread frontend) {
        this.accountService = accountService;
        this.frontend = frontend;
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
        Map<String,Object> pageVariables = new HashMap<>();

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(PageGenerator.getPage("singIn.html", pageVariables));
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
            frontend.addSession(sessionId,profile);
            frontend.setUserState(sessionId,UserState.PENDING_AUTHORIZED);

            UserState state = frontend.getUserState(sessionId);
            if(state == UserState.SUCCESSFUL_AUTHORIZED) {
                pageVariables.put("result", RESPONSE_SUCCESS);
                frontend.setUserState(sessionId,UserState.SLEEPS);
            }
            if(state == UserState.UNSUCCESSFUL_AUTHORIZED){
                pageVariables.put("result", RESPONSE_ALREADY_SINGIN);
            }
            if(state == UserState.PENDING_AUTHORIZED){
                pageVariables.put("result", RESPONSE_WAIT);
            }
        } else {
            pageVariables.put("result", RESPONSE_ERROR);
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("result.html", pageVariables));
    }

}

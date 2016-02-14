package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceInMemory;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
import frontend.UserState;
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
public class Logout extends HttpServlet {
    private AccountService accountService;
    private FrontendThread frontend;

    public static final String RESPONSE_SUCCESS =  "Вы успешно вышли";
    public static final String RESPONSE_ERROR =  "Что то не так";
    public static final String RESPONSE_WAIT =  "Ждите";

    public Logout(AccountService accountService, FrontendThread frontend) {
        this.accountService = accountService;
        this.frontend = frontend;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId  =  session.getId();
        frontend.removeSession(sessionId);
        UserProfile profile = accountService.getSession(sessionId);

        String login = profile.getLogin();
        frontend.setUserState(login,UserState.PENDING_LEAVING);

        UserState state = frontend.getUserState(login);
        if(state == UserState.SUCCESSFUL_LEAVING) {
            pageVariables.put("result", RESPONSE_SUCCESS);
            frontend.setUserState(login, UserState.SLEEPS);
        }
        if(state == UserState.PENDING_LEAVING){
            pageVariables.put("result",RESPONSE_WAIT);
        }
        if(state == UserState.UNSUCCESSFUL_LEAVING){
            pageVariables.put("result", RESPONSE_ERROR);
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("result.html", pageVariables));
    }

}

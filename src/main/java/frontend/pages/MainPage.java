package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceThread;
import databaseService.dataSets.UserProfile;
import frontend.FrontendThread;
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
//response.setStatus(HttpServletResponse.SC_OK);
// throw ServletException

public class MainPage extends HttpServlet {
    private AccountService accountService;
    private FrontendThread frontend;

    public MainPage(AccountService accountService, FrontendThread frontend) {
        this.accountService = accountService;
        this.frontend = frontend;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String, Object> pageVariables = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId  =  session.getId();
        UserProfile profile = accountService.getSession(sessionId);
        if(profile != null){
            pageVariables.put("name", profile.getLogin());
        } else {
            pageVariables.put("name", "Друг");
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("mainpage.html", pageVariables));
    }

}

package frontend;

import main.AccountService;
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
    public static final String RESPONSE_SUCCESS =  "Вы успешно вышли";
    public static final String RESPONSE_ERROR =  "Что то не так";
    public Logout(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        HttpSession session = request.getSession();
        String sessionId  =  session.getId();
        boolean result = accountService.removeSession(sessionId);
        if(result){
            pageVariables.put("result",RESPONSE_SUCCESS);
        } else {
            pageVariables.put("result", RESPONSE_ERROR);
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(PageGenerator.getPage("result.html", pageVariables));
    }

}

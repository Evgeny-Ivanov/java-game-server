package frontend;

import main.AccountService;
import org.eclipse.jetty.server.Server;
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
public class Admin extends HttpServlet {
    private AccountService accountService;
    public static final String  PASSWORD = "password";
    private Server server;
    public Admin(AccountService accountService,Server server){
        this.server = server;
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Map<String,Object> pageVariables = new HashMap<>();
        pageVariables.put("users", accountService.getCountUsers());
        pageVariables.put("sessions", accountService.getCountSessions());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(PageGenerator.getPage("admin.html", pageVariables));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int time = Integer.parseInt(request.getParameter("time"));
        String password = request.getParameter("password");

        if(password.equals(PASSWORD)) {
            System.out.print("\nСервер остановиться через: " + time + " ms");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                server.stop();
                System.out.println("\nОстановка");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", "Неверный пароль");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(PageGenerator.getPage("result.html", pageVariables));
    }
}

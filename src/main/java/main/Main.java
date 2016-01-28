package main;

import frontend.Logout;
import frontend.Mainpage;
import frontend.SingIn;
import frontend.SingUp;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author v.chibrikov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        SingIn singIn = new SingIn(accountService);
        SingUp singUp = new SingUp(accountService);
        Mainpage mainpage = new Mainpage(accountService);
        Logout logout = new Logout(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(singIn), "/singIn");
        context.addServlet(new ServletHolder(singUp), "/singUp");
        context.addServlet(new ServletHolder(mainpage), "/");
        context.addServlet(new ServletHolder(logout), "/logout");


        Server server = new Server(8888);
        server.setHandler(context);

        server.start();
        server.join();
    }
}

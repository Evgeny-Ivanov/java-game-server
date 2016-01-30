package main;

import base.GameMechanics;
import base.WebSocketService;
import frontend.game.GameServlet;
import frontend.game.WebSocketGameServlet;
import frontend.game.WebSocketServiceImpl;
import frontend.pages.*;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author v.chibrikov
 */
public class Main {
    public static final String URL_SINGUP = "/singUp";

    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        Server server = new Server(8888);

        SingIn singIn = new SingIn(accountService);
        SingUp singUp = new SingUp(accountService);
        MainPage mainpage = new MainPage(accountService);
        Logout logout = new Logout(accountService);
        Admin admin = new Admin(accountService,server);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        WebSocketGameServlet webSocketGameServlet = new WebSocketGameServlet(accountService, gameMechanics, webSocketService);
        GameServlet gameServlet = new GameServlet();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(singIn), "/singIn");
        context.addServlet(new ServletHolder(singUp), URL_SINGUP);
        context.addServlet(new ServletHolder(mainpage), "/");
        context.addServlet(new ServletHolder(logout), "/logout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(webSocketGameServlet), "/gameplay");
        context.addServlet(new ServletHolder(gameServlet), "/game");

        server.setHandler(context);
        server.start();

        gameMechanics.run();

    }
}

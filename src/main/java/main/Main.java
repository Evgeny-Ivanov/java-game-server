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
import resourceSystem.ResourceContext;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author v.chibrikov
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        try(final FileInputStream fis = new FileInputStream("cfg/server.properties")){
            final Properties properties = new Properties();
            properties.load(fis);
            port = Integer.parseInt(properties.getProperty("port"));

        }
        AccountService accountService = new AccountService();
        Server server = new Server(port);


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
        context.addServlet(new ServletHolder(singUp), "/singUp");
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

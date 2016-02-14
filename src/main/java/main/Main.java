package main;

import base.GameMechanics;
import base.WebSocketService;
import databaseService.*;
import frontend.FrontendThread;
import frontend.game.GameServlet;
import frontend.game.WebSocketGameServlet;
import frontend.game.WebSocketServiceImpl;
import frontend.pages.*;
import mechanics.GameMechanicsImpl;
import messageSystem.Message;
import messageSystem.MessageSystem;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ThreadPool;

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
        MessageSystem messageSystem = new MessageSystem();
        DBServiceThread dbService = new DBServiceThread(messageSystem);
        FrontendThread frontend = new FrontendThread(messageSystem);
        messageSystem.addService(dbService.getAddress());
        messageSystem.addService(frontend.getAddress());

        Thread dbServiceThread = new Thread(dbService);
        Thread frontendThread = new Thread(frontend);

        Server server = new Server(port);

        AccountService accountService = dbService.getAccountService();
        SingIn singIn = new SingIn(accountService,frontend);
        SingUp singUp = new SingUp(accountService,frontend);
        MainPage mainpage = new MainPage(accountService,frontend);
        Logout logout = new Logout(accountService,frontend);
        Admin admin = new Admin(accountService,frontend,server);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanicsImpl gameMechanics = new GameMechanicsImpl(webSocketService);
        WebSocketGameServlet webSocketGameServlet = new WebSocketGameServlet(dbService.getAccountService(), gameMechanics, webSocketService);
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
        dbServiceThread.start();
        frontendThread.start();
        gameMechanics.start();

    }
}

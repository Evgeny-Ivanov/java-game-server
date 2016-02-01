package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceInMemory;
import org.eclipse.jetty.server.Server;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static frontend.pages.Helpers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by stalker on 29.01.16.
 */
public class AdminTest {
    AccountService accountService = new DBServiceInMemory();

    @Test
    public void stopServer() throws IOException{
        StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = getMockedResponse(stringWriter);

        Server server = new Server(8888);

        try {
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }

        Admin admin = spy(new Admin(accountService, server));
        when(request.getParameter("time")).thenReturn("10");
        when(request.getParameter("password")).thenReturn(Admin.PASSWORD);
        admin.doPost(request, response);
        assertTrue(server.isStopped());
    }

    @Test
    public void doGetTest() throws IOException {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = getMockedResponse(stringWriter);

        Server server = mock(Server.class);
        Admin admin = spy(new Admin(accountService,server));

        admin.doGet(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("users",accountService.getCountUsers());
        pageVariables.put("sessions",accountService.getCountSessions());
        assertEquals(PageGenerator.getPage("admin.html", pageVariables), stringWriter.toString());
    }
}
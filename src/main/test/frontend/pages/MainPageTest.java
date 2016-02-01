package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceInMemory;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by stalker on 29.01.16.
 */
public class MainPageTest {
    private AccountService accountService = new DBServiceInMemory();
    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);

        accountService.addSession(Helpers.idSession, Helpers.testUser);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(Helpers.idSession);

        MainPage mainPage = spy(new MainPage(accountService));

        mainPage.doGet(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("name", Helpers.testUser.getLogin());
        assertEquals(PageGenerator.getPage("mainpage.html", pageVariables), stringWriter.toString());
    }
}
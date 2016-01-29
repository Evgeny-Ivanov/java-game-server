package frontend;

import main.AccountService;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static frontend.Helpers.getMockedResponse;
import static frontend.Helpers.idSession;
import static frontend.Helpers.testUser;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by stalker on 29.01.16.
 */
public class MainPageTest {
    private AccountService accountService = new AccountService();
    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = getMockedResponse(stringWriter);

        accountService.addSession(idSession,testUser);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(idSession);

        MainPage mainPage = spy(new MainPage(accountService));

        mainPage.doGet(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("name",testUser.getLogin());
        assertEquals(PageGenerator.getPage("mainpage.html", pageVariables), stringWriter.toString());
    }
}
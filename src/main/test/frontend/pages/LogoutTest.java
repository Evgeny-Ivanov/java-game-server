package frontend.pages;

import main.AccountService;
import org.junit.Before;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by stalker on 29.01.16.
 */
public class LogoutTest {

    private AccountService accountService  = spy(new AccountService());

    @Before
    public void addSession(){
        accountService.addSession(Helpers.idSession, Helpers.testUser);
    }

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = Helpers.getMockedRequest();
        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);

        Logout logout = spy(new Logout(accountService));
        logout.doGet(request,response);
        verify(accountService, times(1)).removeSession(Helpers.idSession);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", Logout.RESPONSE_SUCCESS);
        String testResponseString = PageGenerator.getPage("result.html", pageVariables);
        assertEquals(testResponseString, stringWriter.toString());
    }
}
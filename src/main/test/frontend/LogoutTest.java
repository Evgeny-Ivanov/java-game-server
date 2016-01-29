package frontend;

import main.AccountService;
import main.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static frontend.Helpers.*;
import static org.mockito.Mockito.*;

/**
 * Created by stalker on 29.01.16.
 */
public class LogoutTest {

    private AccountService accountService  = spy(new AccountService());

    @Before
    public void addSession(){
        accountService.addSession(idSession,testUser);
    }

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = getMockedRequest();
        HttpServletResponse response = getMockedResponse(stringWriter);

        Logout logout = spy(new Logout(accountService));
        logout.doGet(request,response);
        verify(accountService, times(1)).removeSession(idSession);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", Logout.RESPONSE_SUCCESS);
        String testResponseString = PageGenerator.getPage("result.html", pageVariables);
        assertEquals(testResponseString, stringWriter.toString());
    }
}
package frontend.pages;

import main.AccountService;
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
public class SingInTest {

    private AccountService accountService = spy(new AccountService());

    @Test
    public void testDoGet() throws Exception {
        SingIn singIn = spy(new SingIn(accountService));
        final StringWriter stringWriter = new StringWriter();
        accountService.addUser(Helpers.testUser.getLogin(), Helpers.testUser);

        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);
        HttpServletRequest request = Helpers.getMockedRequest();
        singIn.doGet(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        String testResponseString = PageGenerator.getPage("singIn.html", pageVariables);
        assertEquals(testResponseString, stringWriter.toString());
    }

    @Test
    public void testDoPost() throws Exception {
        SingIn singIn = spy(new SingIn(accountService));
        final StringWriter stringWriter = new StringWriter();
        accountService.addUser(Helpers.testUser.getLogin(), Helpers.testUser);

        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);
        HttpServletRequest request = Helpers.getMockedRequest();
        singIn.doPost(request,response);
        verify(accountService, times(1)).addSession(Helpers.idSession, Helpers.testUser);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", SingIn.RESPONSE_SUCCESS);
        String testResponseString = PageGenerator.getPage("result.html", pageVariables);
        assertEquals(testResponseString, stringWriter.toString());


    }

}
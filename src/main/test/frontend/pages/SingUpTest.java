package frontend.pages;

import databaseService.AccountService;
import databaseService.DBServiceInMemory;

import databaseService.dataSets.UserProfile;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by stalker on 28.01.16.
 */
public class SingUpTest {
    private AccountService accountService = spy(new DBServiceInMemory());

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);

        SingUp spySingUp = spy(new SingUp(accountService));

        spySingUp.doGet(request,response);

        Map<String, Object> pageVariables = new HashMap<>();
        assertEquals(PageGenerator.getPage("singUp.html", pageVariables), stringWriter.toString());
    }

    @Test
    public void testDoPost() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = Helpers.getMockedResponse(stringWriter);//имитируем response
        HttpServletRequest request = Helpers.getMockedRequest();//имитируем request

        SingUp spySingUp = spy(new SingUp(accountService));

        spySingUp.doPost(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", SingUp.RESPONSE_SUCCESS);
        String testResponseString = PageGenerator.getPage("result.html", pageVariables);

        assertEquals(testResponseString, stringWriter.toString());
        UserProfile profile = accountService.getUser(Helpers.testUser.getLogin());
        verify(accountService, times(1)).addUser(Helpers.testUser.getLogin(), profile);

        final StringWriter stringWriter2 = new StringWriter();
        response = Helpers.getMockedResponse(stringWriter2);
        pageVariables.clear();
        pageVariables.put("result", SingUp.RESPONSE_ERROR);
        spySingUp.doPost(request, response);
        testResponseString = PageGenerator.getPage("result.html", pageVariables);
        assertEquals(testResponseString, stringWriter2.toString());

    }



}
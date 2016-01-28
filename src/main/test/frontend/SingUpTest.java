package frontend;

import main.AccountService;

import main.UserProfile;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by stalker on 28.01.16.
 */
public class SingUpTest {
    private AccountService accountServer = new AccountService();
    private UserProfile testUser = new UserProfile("testLogin", "testPassword", "testEmail");

    @Test
    public void testDoGet() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = getMockedResponse(stringWriter);

        SingUp spySingUp = spy(new SingUp(accountServer));

        spySingUp.doGet(request,response);

        Map<String, Object> pageVariables = new HashMap<>();
        assertEquals(PageGenerator.getPage("singUp.html", pageVariables), stringWriter.toString());
    }

    @Test
    public void testDoPost() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);//имитируем response
        HttpServletRequest request = getMockedRequest();//имитируем request

        SingUp spySingUp = spy(new SingUp(accountServer));

        spySingUp.doPost(request, response);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("result", SingUp.RESPONSE_SUCCESS);
        String testResponseString = PageGenerator.getPage("result.html", pageVariables);

        assertEquals(testResponseString, stringWriter.toString());
        //verify(accountServer, times(1)).addUser();//проверяем сколько раз был вызван метод ??

        final StringWriter stringWriter2 = new StringWriter();
        response = getMockedResponse(stringWriter2);
        pageVariables.clear();
        pageVariables.put("result", SingUp.RESPONSE_ERROR);
        spySingUp.doPost(request, response);
        testResponseString = PageGenerator.getPage("result.html", pageVariables);
        assertEquals(testResponseString, stringWriter2.toString());
    }

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);//с помощью этой штуки будем писать в наш поток
        when(response.getWriter()).thenReturn(writer);

        return response;
    }

    private HttpServletRequest getMockedRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("login")).thenReturn(testUser.getLogin());
        when(request.getParameter("password")).thenReturn(testUser.getPassword());
        when(request.getParameter("email")).thenReturn(testUser.getEmail());

        return request;
    }


}
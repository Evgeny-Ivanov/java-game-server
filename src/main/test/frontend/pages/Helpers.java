package frontend.pages;

import main.UserProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by stalker on 29.01.16.
 */
public class Helpers {
    public static UserProfile testUser = new UserProfile("testLogin", "testPassword", "testEmail");
    public static String idSession = "testIdSession";

    public static HttpServletResponse getMockedResponse(StringWriter stringWriter) {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);//с помощью этой штуки будем писать в наш поток
        try {
            when(response.getWriter()).thenReturn(writer);
        }catch (IOException e){
            e.printStackTrace();
        }

        return response;
    }

    public static HttpServletRequest getMockedRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getParameter("login")).thenReturn(testUser.getLogin());//аналог - stub(request.getParameter("login")).toReturn(testUser.getLogin());
        //или - doReturn("testLogin").when(request).getParameter("login")
        when(request.getParameter("password")).thenReturn(testUser.getPassword());
        when(request.getParameter("email")).thenReturn(testUser.getEmail());

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(idSession);

        return request;
    }

    public static UserProfile getTestUser(){
        return testUser;
    }


}

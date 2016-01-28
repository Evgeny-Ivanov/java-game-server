package main;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by stalker on 28.01.16.
 */
public class AccountServiceTest {
    @NotNull
    private final AccountService accountService = new AccountService();
    @NotNull
    private final UserProfile testUser = new UserProfile("testLogin", "testPassword", "testEmail");

    @Test
    public void testAddUser() throws Exception {
        boolean result1 = accountService.addUser(testUser.getLogin(), testUser);

        final UserProfile user = accountService.getUser(testUser.getLogin());

        assertNotNull(user);
        assertTrue(result1);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(testUser.getEmail(), user.getEmail());

        boolean result2 = accountService.addUser(testUser.getLogin(), testUser);
        assertFalse(result2);
    }

    @Test
    public void testAddSession() throws Exception {
        final String idSession  = "testIdSession";
        boolean result1 = accountService.addSession(idSession,testUser);

        final UserProfile user = accountService.getSession(idSession);

        assertNotNull(user);
        assertTrue(result1);
        assertEquals(user.getLogin(), testUser.getLogin());
        assertEquals(user.getPassword(), testUser.getPassword());
        assertEquals(user.getEmail(), testUser.getEmail());

        boolean result2 = accountService.addSession(idSession, testUser);
        assertFalse(result2);
    }

    @Test
    public void testRemoveSession() throws Exception {
        final String idSession  = "testIdSession";
        boolean resultAdd = accountService.addSession(idSession, testUser);
        assertTrue(resultAdd);

        boolean resultRemove1 = accountService.removeSession(idSession);
        assertTrue(resultRemove1);

        UserProfile user = accountService.getSession(idSession);
        assertNull(user);

        boolean resultRemove2 = accountService.removeSession(idSession);
        assertFalse(resultRemove2);
    }

    @Test
    public void testGetCountUsers() throws Exception {
        int startCountUsers = accountService.getCountUsers();
        accountService.addUser(testUser.getLogin(), testUser);
        accountService.addUser(testUser.getLogin(), testUser);
        assertEquals(accountService.getCountUsers(), startCountUsers + 1);

        UserProfile testUser2 = new UserProfile("Незнаю что", "сюда", "написать");
        accountService.addUser(testUser2.getLogin(), testUser2);
        assertEquals(accountService.getCountUsers(), startCountUsers + 2);
    }

    @Test
    public void testGetCountSessions() throws Exception {
        int startCountSessions = accountService.getCountSessions();
        accountService.addSession("1", testUser);
        accountService.addSession("1", testUser);
        assertEquals(accountService.getCountSessions(), startCountSessions + 1);

        UserProfile testUser2 = new UserProfile("Незнаю что", "сюда", "написать");
        accountService.addSession("2", testUser2);
        assertEquals(accountService.getCountSessions(), startCountSessions + 2);

        accountService.removeSession("2");
        assertEquals(accountService.getCountSessions(), startCountSessions + 1);
    }

}
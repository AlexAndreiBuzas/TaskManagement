package me.buzas.task.manager;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.abstraction.manager.UserManager;
import me.buzas.task.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserManagerTest {

    private UserManager userManager;
    private UserDataAccess userDataAccess;

    @BeforeEach
    public void setUp() {
        userDataAccess = Mockito.mock(UserDataAccess.class);
        userManager = new UserManagerImpl(userDataAccess);
    }

    @Test
    public void testAddUser() {
        User user = new User(1, "John", "Team_Soft", "Developer");

        userManager.addUser(user);

        verify(userDataAccess).saveUser(user);
    }

    @Test
    public void testAddUser_NullUser() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.addUser(null)
        );
        assertEquals("User cannot be null.", exception.getMessage());
    }

    @Test
    public void testGetUser() {
        User expectedUser = new User(1, "John", "Team_Soft", "Developer");

        when(userDataAccess.loadUser(1)).thenReturn(expectedUser);

        User actualUser = userManager.getUser(1);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        verify(userDataAccess).loadUser(1);
    }

    @Test
    public void testGetUser_InvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.getUser(0)
        );
        assertEquals("User ID must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testGetUser_NonExistentUser() {
        when(userDataAccess.loadUser(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.getUser(1)
        );
        assertEquals("User with ID '1' does not exist.", exception.getMessage());
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "John", "Team_Soft", "Developer");
        user.setUsername("John_Doe");

        when(userDataAccess.loadUser(1)).thenReturn(user);

        userManager.updateUser(user);

        verify(userDataAccess).updateUser(user);
    }

    @Test
    public void testUpdateUser_NonExistentUser() {
        User user = new User(1, "John", "Team_Soft", "Developer");

        when(userDataAccess.loadUser(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.updateUser(user)
        );
        assertEquals("User with ID '1' does not exist.", exception.getMessage());
    }

    @Test
    public void testDeleteUser() {
        User user = new User(1, "John", "Team_Soft", "Developer");

        when(userDataAccess.loadUser(1)).thenReturn(user);

        userManager.removeUser(1);

        verify(userDataAccess).deleteUser(1);
    }

    @Test
    public void testDeleteUser_InvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.removeUser(0)
        );
        assertEquals("User ID must be greater than 0.", exception.getMessage());
    }

    @Test
    public void testDeleteUser_NonExistentUser() {
        when(userDataAccess.loadUser(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                userManager.removeUser(1)
        );
        assertEquals("User with ID '1' does not exist.", exception.getMessage());
    }
}
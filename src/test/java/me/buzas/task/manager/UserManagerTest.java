package me.buzas.task.manager;

import me.buzas.task.abstraction.data.UserDataAccess;
import me.buzas.task.abstraction.manager.UserManager;
import me.buzas.task.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

        verify(userDataAccess, times(1)).saveUser(user);
    }

    @Test
    public void testGetUser() {
        User expectedUser = new User(1, "John", "Team_Soft", "Developer");

        when(userDataAccess.loadUser(1)).thenReturn(expectedUser);

        User actualUser = userManager.getUser(1);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        verify(userDataAccess, times(1)).loadUser(1);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "John", "Team_Soft", "Developer");
        user.setUsername("John_Doe");

        userManager.updateUser(user);

        verify(userDataAccess, times(1)).updateUser(user);
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;

        userManager.removeUser(userId);

        verify(userDataAccess, times(1)).deleteUser(userId);
    }
}
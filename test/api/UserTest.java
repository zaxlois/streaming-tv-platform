package api;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @org.junit.jupiter.api.Test
    void userTest() {
        User user = new User("zax","lois","zaxlois","1");
        Assertions.assertEquals(user.getUsername(),"zaxlois");
        Assertions.assertEquals(user.getPassword(),"1");
    }
}
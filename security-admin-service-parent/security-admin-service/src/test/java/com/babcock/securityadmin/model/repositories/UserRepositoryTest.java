package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = User.class)
public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setName("Hamish Smith");
        user.setAddress("21 Boot Crescent, Hackney");
        user.setPhoneNumber("071 777 6666");
        entityManager.persist(user);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.remove(user);
    }

    @Test
    public void getUserById() {
        assertEquals("Hamish Smith", userRepository.findOne(user.getId()).getName());
    }

}
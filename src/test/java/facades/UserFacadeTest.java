package facades;

import dtos.UserDTO;
import entities.Role;
import entities.User;
import facades.UserFacade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private User user1, user2;

    @BeforeAll
    static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("puTest");
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from User").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();

            user1 = new User("user1", "password1", "address1", "phone1", "email1", "1991", 100);
            user2 = new User("user2", "password2", "address2", "phone2", "email2", "1992", 200);
            Role role = new Role("user");

            em.persist(user1);
            em.persist(user2);
            em.persist(role);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from User").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }



    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO("user3", "password3", "address3", "phone3", "email3", "1993", 300);
        UserDTO createdUser = facade.createUser(userDTO);
        assertNotNull(createdUser);
        assertEquals(userDTO.getUser_name(), createdUser.getUser_name());
        assertEquals(userDTO.getAddress(), createdUser.getAddress());
        assertEquals(userDTO.getPhone(), createdUser.getPhone());
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        assertEquals(userDTO.getBirthYear(), createdUser.getBirthYear());
        assertEquals(userDTO.getAccount(), createdUser.getAccount());
        assertTrue(createdUser.getRoles().contains("user"));
    }

    @Test
    public void testGetAllUsers() {
        List<UserDTO> allUsers = facade.getAllUsers();

        assertNotNull(allUsers);
        assertEquals(2, allUsers.size()); // expecting 2 users in total created in setUp()
    }

}

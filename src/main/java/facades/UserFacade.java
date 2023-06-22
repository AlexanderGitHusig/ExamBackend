package facades;

import dtos.UserDTO;
import entities.Assignment;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(UserDTO userDTO) {
        EntityManager em = emf.createEntityManager();
        User user = new User(userDTO.getUser_name(), userDTO.getUser_pass(), userDTO.getAddress(), userDTO.getPhone(), userDTO.getEmail(),userDTO.getBirthYear(), userDTO.getAccount());
        user.addRole(new Role("user"));
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }
    public List<UserDTO> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        List<User> users;
        try {
            users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally {
            em.close();
        }
        return UserDTO.getDtos(users);
    }

    public void addAssignmentToUser(String username, Long assignmentId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.user_name = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            Assignment assignment = em.find(Assignment.class, assignmentId);

            if (user != null && assignment != null) {
                user.getAssignmentList().add(assignment);
                assignment.getUsers().add(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new IllegalStateException("Error adding assignment to user", e);
        } finally {
            em.close();
        }
    }


}

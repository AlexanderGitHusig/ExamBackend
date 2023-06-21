package facades;

import dtos.AssignmentDTO;
import facades.AssignmentFacade;
import entities.Assignment;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AssignmentFacadeTest {

    private static EntityManagerFactory emf;
    private static AssignmentFacade facade;
    private Assignment a1, a2;

    public AssignmentFacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = AssignmentFacade.getAssignmentFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        a1 = new Assignment("Name1", "2023-06-21", "Contact1");
        a2 = new Assignment("Name2", "2023-06-22", "Contact2");
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Assignment").executeUpdate();
            em.persist(a1);
            em.persist(a2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {}

    @Test
    public void testCreateAssignment() {
        AssignmentDTO assignmentDTO = new AssignmentDTO("NameTest", "2023-06-21", "ContactTest");
        AssignmentDTO created = facade.createAssignment(assignmentDTO);
        assertNotNull(created);
        assertEquals("NameTest", created.getFamilyName());
    }
    @Test
    public void testGetAllAssignments() {
        assertEquals(2, facade.getAllAssignments().size());
    }


}

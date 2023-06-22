package facades;

import facades.DinnerEventFacade;
import utils.EMF_Creator;
import entities.Dinnerevent;
import dtos.DinnerEventDTO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DinnerEventFacadeTest {

    private static EntityManagerFactory emf;
    private static DinnerEventFacade facade;
    private Dinnerevent dinnerevent1, dinnerevent2;

    public DinnerEventFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DinnerEventFacade.getDinnerEventFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        emf.close();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        dinnerevent1 = new Dinnerevent("19:00", "Dinner Party 1", "Location 1", "Dish 1", 120);
        dinnerevent2 = new Dinnerevent("19:30", "Dinner Party 2", "Location 2", "Dish 2", 180);
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Dinnerevent").executeUpdate();

            // create some DinnerEvent objects
            em.persist(dinnerevent1);
            em.persist(dinnerevent2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        //Remove any data after each test was run
    }

    @Test
    public void testCreateEvent() {
        String time = "19:00";
        String eventName = "Dinner Party 1";
        String location = "Location 1";
        String dish = "Dish 1";
        int pricePerPerson = 120;

        DinnerEventDTO dinnerEventDTO = new DinnerEventDTO(time, eventName, location, dish, pricePerPerson);

        DinnerEventDTO result = facade.createEvent(dinnerEventDTO);

        assertNotNull(result);
        assertEquals(time, result.getTime());
        assertEquals(eventName, result.getEventName());
        assertEquals(location, result.getLocation());
        assertEquals(dish, result.getDish());
        assertEquals(pricePerPerson, result.getPricePerPerson());
    }

    @Test
    public void testGetAllDinnerEvents() {
        List<DinnerEventDTO> dinnerEvents = facade.getAllDinnerEvents();
        assertEquals(2, dinnerEvents.size()); // Made 2 events, so 2 is expected.
    }

    @Test
    public void testDeleteDinnerEvent() {
        facade.deleteDinnerEvent(dinnerevent1.getId());
        List<DinnerEventDTO> dinnerEvents = facade.getAllDinnerEvents();
        assertEquals(1, dinnerEvents.size()); // expected to be 1 as we deleted 1 in the test
    }

}
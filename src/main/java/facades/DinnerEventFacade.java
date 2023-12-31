package facades;

import dtos.DinnerEventDTO;
import entities.Assignment;
import entities.Dinnerevent;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class DinnerEventFacade {
    private static DinnerEventFacade instance;
    private static EntityManagerFactory emf;


    private DinnerEventFacade() {
    }

    public static DinnerEventFacade getDinnerEventFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DinnerEventFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Create DinnerEvent
    public DinnerEventDTO createEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = getEntityManager();
        Dinnerevent dinnerevent = new Dinnerevent(dinnerEventDTO.getTime(), dinnerEventDTO.getEventName(), dinnerEventDTO.getLocation(), dinnerEventDTO.getDish(), dinnerEventDTO.getPricePerPerson());
        try {
            em.getTransaction().begin();
            em.persist(dinnerevent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerevent);
    }


    //Get all DinnerEvents
    public List<DinnerEventDTO> getAllDinnerEvents() {
        EntityManager em = getEntityManager();
        try {
            List<Dinnerevent> dinnerEvents = em.createQuery("SELECT d FROM Dinnerevent d", Dinnerevent.class).getResultList();
            return dinnerEvents.stream()
                    .map(DinnerEventDTO::new)
                    .collect(Collectors.toList());
        } finally {
            em.close();
        }
    }



    //Delete DinnerEvent
    public DinnerEventDTO deleteDinnerEvent(Long id) {
        EntityManager em = getEntityManager();
        Dinnerevent dinnerEvent = em.find(Dinnerevent.class, id);
        try {
            em.getTransaction().begin();
            em.remove(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    public DinnerEventDTO updateDinnerEvent(DinnerEventDTO dinnerEventDTO) {
        EntityManager em = getEntityManager();
        Dinnerevent dinnerEvent = em.find(Dinnerevent.class, dinnerEventDTO.getId());
        try {
            em.getTransaction().begin();
            dinnerEvent.setTime(dinnerEventDTO.getTime());
            dinnerEvent.setEventName(dinnerEventDTO.getEventName());
            dinnerEvent.setLocation(dinnerEventDTO.getLocation());
            dinnerEvent.setDish(dinnerEventDTO.getDish());
            dinnerEvent.setPricePerPerson(dinnerEventDTO.getPricePerPerson());
            em.merge(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }



}

package facades;

import dtos.AssignmentDTO;
import entities.Assignment;
import entities.User;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class AssignmentFacade {
    private static EntityManagerFactory emf;
    private static AssignmentFacade instance;

    private AssignmentFacade() {
    }

    public static AssignmentFacade getAssignmentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AssignmentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment(assignmentDTO.getFamilyName(), assignmentDTO.getCreateDate(), assignmentDTO.getContactInfo());

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(assignment);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create assignment", e);
        } finally {
            em.close();
        }

        return new AssignmentDTO(assignment);
    }
    public List<AssignmentDTO> getAllAssignments() {
        EntityManager em = getEntityManager();
        List<Assignment> assignments;
        try {
            assignments = em.createQuery("SELECT a FROM Assignment a", Assignment.class).getResultList();

        } finally {
            em.close();
        }
        return AssignmentDTO.getDtos(assignments);
    }

}
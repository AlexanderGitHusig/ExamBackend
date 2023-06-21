package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DinnerEventDTO;
import facades.DinnerEventFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("dinnerevent")
public class DinnerEventResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DinnerEventFacade FACADE = DinnerEventFacade.getDinnerEventFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getAllDinnerEvents() {
        return GSON.toJson(FACADE.getAllDinnerEvents());
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response createDinnerEvent(String dinnerEvent) {
        DinnerEventDTO dinnerEventDTO = GSON.fromJson(dinnerEvent, DinnerEventDTO.class);
        DinnerEventDTO createdDinnerEvent = FACADE.createEvent(dinnerEventDTO);
        return Response.ok().entity(GSON.toJson(createdDinnerEvent)).build();
    }

}
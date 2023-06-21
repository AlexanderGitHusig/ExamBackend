package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    public String getAllUsers() {
        return GSON.toJson(FACADE.getAllUsers());
    }
}

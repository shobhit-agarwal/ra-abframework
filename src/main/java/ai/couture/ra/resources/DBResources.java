package ai.couture.ra.resources;

import ai.couture.ra.core.Model;
import ai.couture.ra.db.ModelDB;
import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the resource for the model database
 * which handles all the methods to be called at a specific url path
 */
@Path("/api/")
@Produces(MediaType.APPLICATION_JSON)
public class DBResources {
    private Jdbi jdbi;

    /**
     * This is a constructor with one parameter jdbi
     * @param jdbi the jdbi database factory
     */
    public DBResources(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_initialize'
     * which initializes the database of the models
     * @return string which signifies models have been initialized
     */
    @GET
    @Timed
    @Path("/model_initialize/")
    public String prnt1(){
        return ModelDB.initialize(jdbi);
//        return "Models Initialized";
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_db?name=model_name'
     * with name as a query parameter
     * which returns the model of given name with its current version
     * model_name is to be replaced with the name of the model needed
     * @param name name of the model
     * @return the model of given name with highest version
     */
    @GET
    @Timed
    @Path("/model_db/")
    public Model prnt2(@QueryParam("name") String name){
        return ModelDB.getModel(jdbi, name);
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_all'
     * which returns all the models with their current version
     * @return list of models with their highest version
     */
    @GET
    @Timed
    @Path("/model_all/")
    public List<Model> prnt3(){
        return ModelDB.getallModels(jdbi);
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_type?type=type_name'
     * with type as a query parameter
     * which returns the models of given type with their current version
     * type_name is to be replaced with the type of the models needed
     * @param type the type of the model
     * @return the list of models of given type with their current version
     */
    @GET
    @Timed
    @Path("/model_type/")
    public List<Model> prnt4(@QueryParam("type") String type){
        return ModelDB.getModelsType(jdbi, type);
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_ids_type?type=type_name'
     * with type as a query parameter
     * which returns the model ids of current version of models of given type
     * @param type the type of the model
     * @return the list of model ids of current version of models of given type
     */
    @GET
    @Timed
    @Path("/model_ids_type/")
    public List<Integer> prnt5(@QueryParam("type") String type){
        List<Model> Models = ModelDB.getModelsType(jdbi, type);
        List<Integer> Model_Ids = new ArrayList<>();
        for (Model model:Models) {
            Model_Ids.add(model.getModelId());
        }
        return Model_Ids;
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_add'
     * which adds new models to the existing table
     * @return string which signifies models have been added
     */
    @GET
    @Timed
    @Path("/model_add/")
    public String prnt6(){
        ModelDB.add(jdbi);
        return "Models added";
    }
}
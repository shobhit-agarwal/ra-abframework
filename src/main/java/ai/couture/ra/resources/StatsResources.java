package ai.couture.ra.resources;

import ai.couture.ra.db.ModelStats;
import com.codahale.metrics.annotation.Timed;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This class is the resource for the model statistics database
 * which handles all the methods to be called at a specific url path
 */
@Path("/api/model_stats")
@Produces(MediaType.APPLICATION_JSON)
public class StatsResources {
    private Jdbi jdbi;

    /**
     * This is a constructor with one parameter jdbi
     * @param jdbi the jdbi database factory
     */
    public StatsResources(Jdbi jdbi){
        this.jdbi = jdbi;
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_stats/initialize'
     * which initializes the database of the model statistics
     * @return string which signifies model statistics have been initialized
     */
    @GET
    @Timed
    @Path("/initialize/")
    public String prnt3(){
        ModelStats.initialize(jdbi);
        return "Model Stats Initialized";
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_stats/add?Itr=itr_no&Model=model_id&Percent=user_percent'
     * with three query parameters Itr, Model, Percent
     * which adds a row to the table initializing three columns (Iteration No, Model Id, User Percent)
     * @param Itr the iteration no. of the deployment
     * @param Model the model id of the model deployed
     * @param Percent percentage of users assigned to the model
     * @return string which signifies models have been added
     */
    @GET
    @Timed
    @Path("/add/")
    public String prnt8(@QueryParam("Itr") String Itr, @QueryParam("Model") String Model, @QueryParam("Percent") String Percent){
        Integer ItrNo = Integer.parseInt(Itr);
        Integer ModelId = Integer.parseInt(Model);
        Double UserPercent = Double.parseDouble(Percent);
        ModelStats.add(jdbi, ItrNo, ModelId, UserPercent);
        return "Added a stat";
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_stats/start'
     * which enters the current time as the Start Time to the rows whose models are being deployed currently
     */
    @GET
    @Timed
    @Path("/start/")
    public void prnt1(){
        ModelStats.start(jdbi);
    }

    /**
     * This method is called at url: 'localhost:9000/api/model_stats/end'
     * which enters the current time as the End Time to the rows whose models were being deployed recently
     */
    @GET
    @Timed
    @Path("/end/")
    public void prnt2(){
        ModelStats.end(jdbi);
    }
}
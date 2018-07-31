package ai.couture.ra.db;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

/**
 * This class handles all the methods and queries related to statistics of the models deployed
 */
public class ModelStats {


    private static final String dbname = "ModelStats";
    private static final String tablename = "ModelStats";

    /**
     * This method is used to initialize the database with a table of model statistics
     * having six columns (Serial No, Iteration No, Model Id, User Percent, Start Time, End Time)
     * @param jdbi the jdbi database factory
     */
    public static void initialize(Jdbi jdbi){
        Handle handle = jdbi.open();
        String sql = "CREATE DATABASE IF NOT EXISTS " + dbname;
        handle.execute(sql);

        String db = "USE " + dbname;
        handle.execute(db);

        String drop = "DROP TABLE IF EXISTS " + tablename;
        handle.execute(drop);

        String create = "CREATE TABLE IF NOT EXISTS " + tablename +
                " (SerialNo INTEGER unsigned not NULL AUTO_INCREMENT, ItrNo INTEGER, ModelId INTEGER, " +
                " UserPercent DOUBLE, StartTime VARCHAR(255), EndTime VARCHAR(255)," +
                " PRIMARY KEY (SerialNo))";
        handle.execute(create);
        System.out.println("Created table " + tablename + " in given database");
        handle.close();
//        return "Model Stats Initialized";
    }

    /**
     * This method is used to add a row to the table
     * initializing three columns (Iteration No, Model Id, User Percent)
     * @param jdbi the jdbi database factory
     * @param ItrNo the iteration no. of the deployment
     * @param ModelId the model id of the model deployed
     * @param UserPercent percentage of users assigned to the model
     */
    public static void add(Jdbi jdbi, Integer ItrNo, Integer ModelId, Double UserPercent){
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);
        handle.execute("INSERT INTO " + tablename + " (ItrNo, ModelID, UserPercent) values (?, ?, ?)",
                ItrNo, ModelId, UserPercent);
//        String time = "UPDATE " + tablename +
//                " SET StartTime " + " = CURRENT_TIMESTAMP() WHERE ItrNo = " +
//                Integer.toString(ItrNo) + " AND ModelId = " + Integer.toString(ModelId);
//        handle.execute(time);
        handle.close();
    }

    /**
     * This method is used to enter the current time as the Start Time
     * to the rows whose models are being deployed currently
     * @param jdbi the jdbi database factory
     */
    public static void start(Jdbi jdbi) {
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);
        String time = "UPDATE " + tablename +
                " SET StartTime " + " = CURRENT_TIMESTAMP() WHERE StartTime IS NULL";
        handle.execute(time);
        handle.close();
    }

    /**
     * This method is used to enter the current time as the End Time
     * to the rows whose models were being deployed recently
     * @param jdbi the jdbi database factory
     */
    public static void end(Jdbi jdbi) {
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);
        String time = "UPDATE " + tablename +
                " SET EndTime " + " = CURRENT_TIMESTAMP() WHERE EndTime IS NULL";
        handle.execute(time);
        handle.close();
    }
}
package ai.couture.ra.db;

import ai.couture.ra.core.Model;
import ai.couture.ra.core.Model_mapper2;
import ai.couture.ra.core.Model_mapper1;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import java.util.*;

/**
 * This class handles all the methods and queries related to the model database
 */
public class ModelDB {

    private static final String dbname = "Models";
    private static final String tablename = "Models";
//    private static final String path = "home/sushant_tarun/Documents/project/microservices/couture1/src/main/resources/assets/";

    /**
     * This method is used to initialize the database with a table of models
     * having five columns (ModelId, Name, Version, HDFS Path and Type)
     * intialized with the currently existing models
     * @param jdbi the jdbi database factory
     * @return the string signifying that models are initialized
     */
    public static String initialize(Jdbi jdbi){
        Handle handle = jdbi.open();

        String sql = "CREATE DATABASE IF NOT EXISTS " + dbname;
        handle.execute(sql);

        String db = "USE " + dbname;
        handle.execute(db);

        handle.execute("DROP TABLE IF EXISTS " + tablename );

        handle.execute("CREATE TABLE " + tablename + " (ModelId INTEGER AUTO_INCREMENT primary key," +
                "Name VARCHAR(100), Version VARCHAR(50), HDFS_Path VARCHAR(255), Type VARCHAR(100))");
        System.out.println("Created Table");

        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model1", "1.0.0", "model1_100.parquet", "recommender");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model1", "1.1.0", "model1_110.parquet", "recommender");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model2", "1.0.0", "model2_100.parquet", "clustering");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model2", "1.0.2", "model2_102.parquet", "clustering");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model1", "1.0.2", "model1_102.parquet", "recommender");

        handle.close();
        return "Models Initialized";
    }

    /**
     * This method is used to add new models to the existing table
     * @param jdbi the jdbi database factory
     */
    public static void add(Jdbi jdbi){
        Handle handle = jdbi.open();

        String db = "USE " + dbname;
        handle.execute(db);

        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model1", "2.0.0", "model1_200.parquet", "recommender");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model2", "2.0.1", "model2_201.parquet", "clustering");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model2", "2.1.1", "model2_211.parquet", "clustering");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model3", "1.0.1", "model3_101.parquet", "clustering");
        handle.execute("insert into " + tablename + " (Name, Version, HDFS_Path, Type) values (?, ?, ?, ?)",
                "model3", "1.1.0", "model3_110.parquet", "clustering");

        handle.close();
    }

    /**
     * This method returns the model of given name with its current version
     * @param jdbi the jdbi database factory
     * @param name the name of the model to be returned
     * @return the model with the highest version
     */
    public static Model getModel(Jdbi jdbi, String name){
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);

//        System.out.println("Function called");
        List<Model> m1 = handle.createQuery("SELECT ModelId, Name, Version, HDFS_Path, Type FROM " + tablename +
                " WHERE Name = :name AND Version = (SELECT MAX(Version) from " + tablename + " WHERE Name = :name)")
                .bind("name", name).map(new Model_mapper1()).list();

        handle.close();
        return m1.get(0);
    }

    /**
     * This method returns all the models with their current version
     * @param jdbi the jdbi database factory
     * @return list of models with their highest version
     */
    public static List<Model> getallModels(Jdbi jdbi){
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);

        List<Model> m2 = handle.createQuery("SELECT Name, MAX(Version) AS Version FROM " + tablename + " " +
                " GROUP BY Name")
                .map(new Model_mapper2()).list();
        List<Model> m4 = new ArrayList<>();
        for (Model m:m2) {
            List<Model> m3 = handle.createQuery("SELECT ModelId, Name, Version, HDFS_Path, Type FROM " + tablename + " " +
                    " WHERE Name = :name AND Version = :version").bind("name", m.getName())
                    .bind("version", m.getVersion()).map(new Model_mapper1()).list();
            m4.addAll(m3);
        }

        handle.close();
        return m4;
    }

    /**
     * This method returns the models of given type with their current version
     * @param jdbi the jdbi database factory
     * @param Type the type of the model
     * @return the list of models of given type with their current version
     */
    public static List<Model> getModelsType(Jdbi jdbi, String Type){
        Handle handle = jdbi.open();
        String db = "USE " + dbname;
        handle.execute(db);

        List<Model> m2 = handle.createQuery("SELECT Name, MAX(Version) AS Version FROM " + tablename + " " +
                " WHERE Type = :type GROUP BY Name").bind("type",Type)
                .map(new Model_mapper2()).list();
//        System.out.println(m2);
        List<Model> m4 = new ArrayList<>();
        for (Model m:m2) {
//            System.out.println(m.getName() + " " + m.getVersion());
            List<Model> m3 = handle.createQuery("SELECT ModelId, Name, Version, HDFS_Path, Type FROM " + tablename + " " +
                    " WHERE Name = :name AND Version = :version").bind("name", m.getName())
                    .bind("version", m.getVersion()).map(new Model_mapper1()).list();
            m4.addAll(m3);
        }

        handle.close();
        return m4;
    }
}
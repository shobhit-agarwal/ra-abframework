package ai.couture.ra;

import ai.couture.ra.resources.DBResources;
import ai.couture.ra.resources.StatsResources;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

/**
 * This method is the application class of the dropwizard which contains the main function
 */
class buildAPIApplication extends Application<buildAPIConfiguration> {

    /**
     * This method is the main method which is called first while running the program
     * and further calls the run method
     * @param args string arguments
     * @throws Exception in case of exception error is thrown
     */
    public static void main(final String[] args) throws Exception {
        new buildAPIApplication().run(args);
    }

    /**
     * This method is used to get the name of the application
     * @return the namre of the application
     */
    @Override
    public String getName() {
        return "buildAPI";
    }

    /**
     * This method is used to bootstrap the configuration of the application
     * @param bootstrap the bootstrap
     */
    @Override
    public void initialize(final Bootstrap<buildAPIConfiguration> bootstrap) {
        // TODO: application initialization
    }

    /**
     * This method is used to register the environments and configurations required for running the program
     * @param configuration the configuration of the program
     * @param environment the environment of the program
     */
    @Override
    public void run(final buildAPIConfiguration configuration, final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        environment.jersey().register(new DBResources(jdbi));
        environment.jersey().register(new StatsResources(jdbi));

    }
}
package ai.couture.ra.core;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implements a mapper between the Model and query result of mysql commands
 */
public class Model_mapper1 implements RowMapper<Model> {
    /**
     * This method helps to map the model object with all five parameters to the result of mysql queries
     * @param rs result of the mysql queries
     * @param ctx context of the sql query
     * @return the mapped model
     * @throws SQLException exception is thrown when sql statement has error
     */
    public Model map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Model(rs.getInt("ModelId"), rs.getString("Name"), rs.getString("Version"), rs.getString("HDFS_Path"), rs.getString("Type"));
    }

}


package ibf2022.paf.assessment.server.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;

// TODO: Task 6
@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String INSERT_NEW_TASK_SQL = "insert into task(user_id, description, priority, due_date) values(?, ?, ?, ?)";

    public Integer insertTask(Task task) {
        
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
                
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

                PreparedStatement ps = con.prepareStatement(INSERT_NEW_TASK_SQL, new String[]{"task_id"});
                ps.setString(1, task.getUserId());
                ps.setString(2, task.getDescription());
                ps.setInt(3, task.getPriority());
                ps.setDate(4, task.getDueDate());

                return ps;
            }
        };

        Integer affectedRow = jdbcTemplate.update(psc, generatedKeyHolder);
        Integer returnedId = generatedKeyHolder.getKey().intValue();

        return affectedRow > 0 ? returnedId : null;
    } 

}
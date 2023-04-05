package ibf2022.paf.assessment.server.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.exception.AccountResourceException;
import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String FIND_USER_BY_USERNAME_SQL = "select * from user where username=?";
    private final String INSERT_NEW_USER_SQL = "insert into user(user_id, username, name) values(?, ?, ?)";
    private final String INSERT_NEW_USER_WITHOUTNAME_SQL = "insert into user(user_id, username) values(?, ?)";
    
    public Optional<User> findUserByUsername(String username) {

        // check username is valid
        Pattern regex = Pattern.compile("[!@#$%&*()'+,-./:;<=>?^_`{|}]\t\n");
        if (regex.matcher(username).find()) {
            throw new AccountResourceException("Username cannot have special character");
        }

        List<User> users = jdbcTemplate.query(FIND_USER_BY_USERNAME_SQL, BeanPropertyRowMapper.newInstance(User.class), username);

        if (users.size() > 0) {

            return Optional.of(users.get(0));

        } else {

            return Optional.empty();
        }
        
    }

    public String insertUser(User user) {

        // generate user id
        String userId = UUID.randomUUID().toString().substring(0, 8);
        user.setUserId(userId);
        Integer inserted;

        // check if userName is provided
        if (null == user.getName() || user.getName().isEmpty()) {
            inserted = jdbcTemplate.update(INSERT_NEW_USER_WITHOUTNAME_SQL, user.getUserId(), user.getUsername());
        } else {
            inserted = jdbcTemplate.update(INSERT_NEW_USER_SQL, user.getUserId(), user.getUsername(), user.getName());
        }

        if (inserted > 0) {

            return userId;

        } else {

            throw new AccountResourceException("Insert for user record failed.");
        }

    }

}

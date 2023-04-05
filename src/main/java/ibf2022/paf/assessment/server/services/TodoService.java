package ibf2022.paf.assessment.server.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.exception.AccountResourceException;
import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Transactional
    public Integer upsertTask(List<Task> tasks, String username) {

        // c1: check if userId exists - create if no
        // c2: insert the task

        // check c1:
        // get userId from userName
        User user = new User();
        Optional<User> opt = userRepo.findUserByUsername(username);
        if (null == opt) {

            // set same username and name
            user.setUsername(username);
            user.setName(username);

            String userId = userRepo.insertUser(user);
            user.setUserId(userId);

        } else {

            user = opt.get();

        }

        // check c2:
        // insert the tasks
        List<Integer> taskIds = new LinkedList<Integer>();
        for (Task t : tasks) {
            t.setUserId(user.getUserId());
            Integer taskId = taskRepo.insertTask(t);
            taskIds.add(taskId);
        }

        // check if all tasks are updated
        Boolean updated = taskIds.size() == tasks.size() ? true : false;

        if (updated) {

            return taskIds.size();

        } else {

            throw new AccountResourceException("Updated tasks record failed");

        }

    }

}

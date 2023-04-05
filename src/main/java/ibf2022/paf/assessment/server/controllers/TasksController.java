package ibf2022.paf.assessment.server.controllers;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.services.TodoService;

// TODO: Task 4, Task 8
@Controller
@RequestMapping(path={""})
public class TasksController {

    @Autowired
    TodoService toDoSvc;

    @PostMapping(path={"/task"})
    public ModelAndView saveTask(@RequestBody MultiValueMap<String, String> form, Model model) throws ParseException {

        //get the username
        String username = form.getFirst("username");

        // check number of key set
        Integer taskNumber = (form.keySet().size() - 1) / 3;

        // populate into tasks
        List<Task> tasks = new LinkedList<Task>();
        for (int i =0; i < taskNumber ; i++) {
            
            String description = form.getFirst("description-%d".formatted(i));
            String priority = form.getFirst("priority-%d".formatted(i));
            String dueDate = form.getFirst("dueDate-%d".formatted(i));
            
            Task task = new Task();
            task.setDescription(description);
            task.setPriority(Integer.valueOf(priority));
            task.setDueDate(java.sql.Date.valueOf(dueDate));
            task.setUserName(username);

            tasks.add(task);

        }
            

        try {
            Integer taskCount = toDoSvc.upsertTask(tasks, username);

            final ModelAndView mav = new ModelAndView("result.html");
            mav.addObject("taskCount", taskCount);
            mav.addObject("username", username);
            mav.setStatus(HttpStatus.OK);
            
            return mav;

        } catch (Exception ex) {

            System.out.println("Exception: " + ex.getMessage());
            final ModelAndView mav = new ModelAndView("error.html");
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    
            return mav;

        }

    }
}

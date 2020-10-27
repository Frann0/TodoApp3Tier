package sample.bll;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Task;
import sample.dal.TaskRepository;

import javax.xml.bind.JAXBException;

public class TaskManager {
    private TaskRepository taskRepository = new TaskRepository();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void add(Task task) throws JAXBException {
        tasks.add(task);
        TaskRepository.saveTasks(tasks);
    }

    public ObservableList<Task> getTasks() throws JAXBException {
        tasks = TaskRepository.loadTasks();
        return tasks;
    }

    public void postpone(Task task, int numberOfDays) throws JAXBException {
        task.setDuedate(task.getDuedate().plusDays(numberOfDays));
        TaskRepository.saveTasks(tasks);
    }

    public void complete(Task task) throws JAXBException {
        task.setIsCompleted(true);
        TaskRepository.saveTasks(tasks);
    }

}

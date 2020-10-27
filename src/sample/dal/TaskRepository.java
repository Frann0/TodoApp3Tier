package sample.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Task;
import sample.Model.TaskListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class TaskRepository {

    public static void saveTasks(ObservableList<Task> tasks) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        TaskListWrapper wrapper = new TaskListWrapper();
        wrapper.setTasks(tasks);

        m.marshal(wrapper,new File("Tasks.xml"));
    }

    public static ObservableList<Task> loadTasks() throws JAXBException{
        File file = new File("Tasks.xml");
        if (!file.exists()){
            return FXCollections.observableArrayList();
        }

        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();

        TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);
        System.out.println(file);
        return FXCollections.observableArrayList(wrapper.getTasks());
    }
}

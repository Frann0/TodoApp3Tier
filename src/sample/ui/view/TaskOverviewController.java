package sample.ui.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import sample.Model.Task;
import sample.bll.TaskManager;

import javax.xml.bind.JAXBException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TaskOverviewController implements Initializable {
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private Task selectedTask;
    private final TaskManager taskManager = new TaskManager();
    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, String> descriptionColumn;
    @FXML
    private TableColumn<Task, LocalDate> dueDateColumn;
    @FXML
    private TableColumn<Task, Boolean> isCompletedColumn;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tasks = Task.loadTasks();

        loadTasks();
        taskTable.setItems(tasks);
        taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedTask = newValue);

        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDuedateProperty());
        isCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().getIsCompletedProperty());
    }

    private void loadTasks() {
        try {
            tasks = taskManager.getTasks();
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
            showError(e);
        }
    }

    @FXML
    public void addTaskClicked() {
        TextInputDialog input = new TextInputDialog();
        input.setHeaderText("Add task");
        input.setTitle("Add task");
        input.setContentText("Please provide a description for your new task");

        String description = input.showAndWait().get();
        try {
            taskManager.add(new Task(description));
        } catch (JAXBException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void postpone1DayClicked() throws JAXBException {
        if (selectedTask == null) {
            showMissingSelectionWarning();
            return;
        }
        try {
            taskManager.postpone(selectedTask,1);
        } catch (JAXBException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void postpone1MonthClicked() {
        if (selectedTask == null) {
            showMissingSelectionWarning();
            return;
        }

        try {
            taskManager.postpone(selectedTask,30);
        } catch (JAXBException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void completeTaskClicked() {
        if (selectedTask == null) {
            showMissingSelectionWarning();
            return;
        }
        try {
            taskManager.complete(selectedTask);
        } catch (JAXBException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("An error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void showMissingSelectionWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("No task selected");
        alert.setContentText("You need to select a task before you can use this button");
        alert.showAndWait();
    }

    private void showError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("An error occured");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}

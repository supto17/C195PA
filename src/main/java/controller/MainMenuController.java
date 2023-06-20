package controller;

import dao.appointmentAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import model.Appointments;

import java.sql.SQLException;

public class MainMenuController {

    public TableView allAppointmentsTable;
    public TableColumn apptID;
    public TableColumn apptTitle;
    public TableColumn apptDescription;
    public TableColumn apptLocation;
    public TableColumn apptContact;
    public TableColumn apptType;
    public TableColumn apptStartTime;
    public TableColumn apptEndTime;
    public TableColumn apptStartDate;
    public TableColumn apptEndDate;
    public TableColumn apptCustomerID;
    public TableColumn apptUserID;
    public ToggleGroup viewSort;

    public void initialize() throws SQLException {
        ObservableList<Appointments> allAppointments = appointmentAccess.getAllAppointments();
        allAppointmentsTable.setItems(allAppointments);
    }

    public void onActionViewByMonth(ActionEvent actionEvent) {
    }

    public void onActionViewByWeek(ActionEvent actionEvent) {
    }

    public void onActionViewAll(ActionEvent actionEvent) {
    }
}

package GUI;

import FurnitureApp.CustomerInfo.Address;
import FurnitureApp.CustomerInfo.Customer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateCustomerController
{
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseNumberTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField customerNameTextField;

    @FXML
    private Button createButton;

    private HelloController mainController;

    public void setMainController(HelloController mainController)
    {
        this.mainController = mainController;
    }

    @FXML
    public void createCustomer()
    {
        //make new customer object
        Customer newCustomer = new Customer();

        newCustomer.setName(customerNameTextField.getText());

        //make their address
        Address address = new Address();

        String streetName = streetTextField.getText();
        String city = cityTextField.getText();
        String zipCode = zipCodeTextField.getText();
        String houseNumber = houseNumberTextField.getText();

        if (streetName.isEmpty() || city.isEmpty() || zipCode.isEmpty() || houseNumber.isEmpty())
        {
            showAlert("All text fields must be filled!");
            return;
        }

        int zipCodeInt = 0;
        int houseNumberInt = 0;

        try
        {
            zipCodeInt = Integer.parseInt(zipCodeTextField.getText());
            houseNumberInt = Integer.parseInt(houseNumberTextField.getText());
        } catch (NumberFormatException e)
        {
            showAlert("Please enter a valid zip code and/or house number.");
            return;
        }

        address.setZipCode(zipCodeInt);
        address.setHouseNumber(houseNumberInt);
        address.setCity(city);
        address.setStreet(streetName);

        newCustomer.setAddress(address);

        mainController.setCurrentCustomer(newCustomer);

        showAlert("Customer created successfully.");

        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

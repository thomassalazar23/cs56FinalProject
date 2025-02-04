package GUI;

import FurnitureApp.FurnitureBusiness;
import FurnitureApp.FurnitureInfo.Furniture;
import FurnitureApp.FurnitureInfo.Chair;
import FurnitureApp.FurnitureInfo.Sofa;
import FurnitureApp.FurnitureInfo.Table;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import FurnitureApp.CustomerInfo.Customer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {


    @FXML private TextField searchCustomer, furnitureNameField, modelNumberField, priceField, typeField;
    @FXML private TableView<Furniture> purchaseTable;
    @FXML private TableColumn<Furniture, String> nameColumn, typeColumn;
    @FXML private TableColumn<Furniture, Integer> priceColumn, modelColumn;
    @FXML private Label totalSpentLabel, purchaseStatusLabel;
    @FXML Label customerNameLabel;

    private Furniture selectedFurniture;
    private FurnitureBusiness furnitureBusiness = new FurnitureBusiness();
    private List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer;

    //sets current customer
    public void setCurrentCustomer(Customer inCustomer)
    {
        this.currentCustomer = inCustomer;
    }

    //initializes the table on screen
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Selection listener for updating purchase status
        purchaseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedFurniture = newSelection;
                purchaseStatusLabel.setText("Has purchased: " + furnitureBusiness.hasBought(currentCustomer, selectedFurniture));
            } else {
                purchaseStatusLabel.setText("No furniture selected.");
            }
        });
    }

    //searches for customer in furniture business object
    //will update the UI to reflect selected/created customer
    @FXML
    protected void searchCustomer() throws IOException
    {
        //fetch the inputted name from customer text field
        String name = searchCustomer.getText();

        //show error if textfield was left empty
        if(name.isEmpty())
        {
            showAlert("Please enter a customer name.");
            return;
        }

        //search for all customers with name
        ArrayList<Customer> foundCustomers = furnitureBusiness.searchCustomers(name);

        //if no customers were found
        if(foundCustomers.isEmpty())
        {
            //asks user to create customer or not
           showCustomerNotFound();
        }
    }

    //adds purchase to customer's purchase history
    @FXML
    protected void addPurchase() {

        //if there is no current customer, show error message
        if (currentCustomer == null) {
            showAlert("Please add or select a customer first.");
            return;
        }

        //grabs all the inputted text
        String furnitureName = furnitureNameField.getText();
        String modelNumber = modelNumberField.getText();
        String priceText = priceField.getText();
        String type = typeField.getText();

        //incase any of them are empty
        if (furnitureName.isEmpty() || modelNumber.isEmpty() || priceText.isEmpty() || type.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        int model, price;
        try { //turns the string to integer
            model = Integer.parseInt(modelNumber);
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showAlert("Model and Price must be valid numbers.");
            return;
        }

        //will hold new furniture based on entered type
        Furniture furnitureItem;

        //makes a new object type I added a method in these classes for easier construction
        switch (type.toLowerCase()) {
            //create new chair
            case "chair": furnitureItem = new Chair(furnitureName, model, price); break;

            //create new sofa object
            case "sofa": furnitureItem = new Sofa(furnitureName, model, price); break;

            //create new table object
            case "table": furnitureItem = new Table(furnitureName, model, price); break;

            //if user inputs invalid type, show error message
            default:
                showAlert("Invalid furniture type. Use 'Chair', 'Sofa', or 'Table'.");
                return;
        }

        //call the purchase function with current customer and created furniture
        furnitureBusiness.purchase(currentCustomer, furnitureItem);
        updateUI();
    }

    //updates UI to reflect the current customer and their purchase history
    private void updateUI() {

        //if customer is null, make no changes
        if (currentCustomer == null)
        {
            return;
        }

        //set text above table to current customers name
        customerNameLabel.setText(currentCustomer.getName());

        //retrieve the customers purchase history
        List<Furniture> purchases = furnitureBusiness.getPurchases(currentCustomer);

        //update table
        purchaseTable.setItems(FXCollections.observableArrayList(purchases));

        //update total money spent by customer
        totalSpentLabel.setText("Total Spent: $" + furnitureBusiness.moneySpent(currentCustomer));
    }

    //shows an error pop up of whatever message in the method
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    //will ask the user if they want to create a new customer
    private void showCustomerNotFound() throws IOException
    {
        //create new alert with yes or no buttons
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"No Customers found, create a new one?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        //if user clicks yes
        if (result.isPresent() && result.get() == ButtonType.YES)
        {
            //create a new customer using the customer creation scene
            showCustomerCreationWindow();

            //update the UI to reflect the new customer added
            furnitureBusiness.addCustomer(currentCustomer);
            updateUI();
        }

        //otherwise, just return
        else
        {
            return;
        }
    }

    //will show the customer creation window where user will create new customer
    private void showCustomerCreationWindow() throws IOException
    {
        //load fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateCustomerScreen.fxml"));
        Stage modalStage = new Stage();

        //retrieve the controller for customer creation window
        Parent root = fxmlLoader.load();
        CreateCustomerController customerCreateController = fxmlLoader.getController();

        //pass this controller to the customer creation controller
        //this will allow the creation controller to pass the customer
        //back to main controller
        customerCreateController.setMainController(this);

        modalStage.setScene(new Scene(root));
        modalStage.setTitle("Create Customer");
        modalStage.showAndWait();

        return;
    }

}

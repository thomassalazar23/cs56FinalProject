package GUI;

import FurnitureApp.FurnitureBusiness;
import FurnitureApp.FurnitureInfo.Furniture;
import FurnitureApp.FurnitureInfo.Chair;
import FurnitureApp.FurnitureInfo.Sofa;
import FurnitureApp.FurnitureInfo.Table;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import FurnitureApp.CustomerInfo.Customer;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML private TextField customerName, furnitureNameField, modelNumberField, priceField, typeField;
    @FXML private TableView<Furniture> purchaseTable;
    @FXML private TableColumn<Furniture, String> nameColumn, typeColumn;
    @FXML private TableColumn<Furniture, Integer> priceColumn, modelColumn;
    @FXML private Label totalSpentLabel, purchaseStatusLabel;

    private Furniture selectedFurniture;
    private FurnitureBusiness furnitureBusiness = new FurnitureBusiness();
    private List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer;

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

    @FXML
    protected void addCustomer() {
        /*
        I'm not really sure if the Customer is added theres exceptions when adding one so take a look at it
        Not sure if Customers are supposed to be in an arraylist or just there

         */
        String name = customerName.getText();
        if (name.isEmpty()) {
            showAlert("Customer name cannot be empty.");
            return;
        }
        currentCustomer = new Customer();
        currentCustomer.setName(name); //sets name
        customers.add(currentCustomer); //the arraylist
        furnitureBusiness.getPurchases(currentCustomer); //grabs the purchase

        selectedFurniture = null;
        purchaseStatusLabel.setText("No purchases yet for this customer");
        updateUI();
    }

    @FXML
    protected void addPurchase() {
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

        Furniture furnitureItem;
        switch (type.toLowerCase()) { //makes a new object type I added a method in these classes for easier construction
            case "chair": furnitureItem = new Chair(furnitureName, model, price); break;
            case "sofa": furnitureItem = new Sofa(furnitureName, model, price); break;
            case "table": furnitureItem = new Table(furnitureName, model, price); break;
            default:
                showAlert("Invalid furniture type. Use 'Chair', 'Sofa', or 'Table'.");
                return;
        }

        furnitureBusiness.purchase(currentCustomer, furnitureItem);
        updateUI();
    }

    //is supposed to update the money spent for each Customer but something goes wrong I think the customer isnt added
    private void updateUI() {
        if (currentCustomer == null) return;

        List<Furniture> purchases = furnitureBusiness.getPurchases(currentCustomer);
        purchaseTable.setItems(FXCollections.observableArrayList(purchases));

        totalSpentLabel.setText("Total Spent: $" + furnitureBusiness.moneySpent(currentCustomer));
    }
    //shows an error pop up of whatever message in the method
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

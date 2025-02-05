package GUI;

import FurnitureApp.FurnitureBusiness;
import FurnitureApp.FurnitureInfo.Furniture;
import FurnitureApp.FurnitureInfo.Chair;
import FurnitureApp.FurnitureInfo.Sofa;
import FurnitureApp.FurnitureInfo.Table;
import FurnitureApp.CustomerInfo.Customer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private TextField furnitureNameField, modelNumberField, priceField, typeField;
    @FXML private TableView<Furniture> purchaseTable;
    @FXML private TableColumn<Furniture, String> nameColumn, typeColumn;
    @FXML private TableColumn<Furniture, Integer> priceColumn, modelColumn;
    @FXML private Label totalSpentLabel, purchaseStatusLabel;
    @FXML private Label customerNameLabel;

    @FXML private ListView<Customer> customerListView;
    @FXML private TextField newCustomerNameField;

    private Furniture selectedFurniture;
    private FurnitureBusiness furnitureBusiness = new FurnitureBusiness();
    private List<Customer> customers = new ArrayList<>();
    private Customer currentCustomer;

    public void setCurrentCustomer(Customer inCustomer) {
        this.currentCustomer = inCustomer;
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

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
    protected void chooseCustomer() throws IOException {
        if (customers.isEmpty()) {
            showCustomerCreationWindow();
        } else {
            showCustomerSelectionWindow(new ArrayList<>(customers));
        }
        updateUI();
    }

    @FXML
    protected void addPurchase() {
        if (currentCustomer == null) {
            showAlert("Please add or select a customer first.");
            return;
        }
        String furnitureName = furnitureNameField.getText();
        String modelNumber = modelNumberField.getText();
        String priceText = priceField.getText();
        String type = typeField.getText();
        if (furnitureName.isEmpty() || modelNumber.isEmpty() || priceText.isEmpty() || type.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }
        int model, price;
        try {
            model = Integer.parseInt(modelNumber);
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showAlert("Model and Price must be valid numbers.");
            return;
        }
        Furniture furnitureItem;
        switch (type.toLowerCase()) {
            case "chair": furnitureItem = new Chair(furnitureName, model, price); break;
            case "sofa":  furnitureItem = new Sofa(furnitureName, model, price); break;
            case "table": furnitureItem = new Table(furnitureName, model, price); break;
            default:
                showAlert("Invalid furniture type. Use 'Chair', 'Sofa', or 'Table'.");
                return;
        }
        furnitureBusiness.purchase(currentCustomer, furnitureItem);
        updateUI();
    }

    private void updateUI() {
        if (currentCustomer == null) {
            return;
        }
        customerNameLabel.setText(currentCustomer.getName());
        List<Furniture> purchases = furnitureBusiness.getPurchases(currentCustomer);
        if (purchases == null) {
            purchases = new ArrayList<>();
        }
        purchaseTable.setItems(FXCollections.observableArrayList(purchases));
        totalSpentLabel.setText("Total Spent: $" + furnitureBusiness.moneySpent(currentCustomer));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showCustomerSelectionWindow(ArrayList<Customer> selectionList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createCustomer.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        customerListView.setItems(FXCollections.observableArrayList(selectionList));
        Stage modalStage = new Stage();
        modalStage.setScene(new Scene(root));
        modalStage.setTitle("Select or Create Customer");
        modalStage.showAndWait();
    }

    private void showCustomerCreationWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createCustomer.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        // Populate the ListView with the local customers (could be empty).
        customerListView.setItems(FXCollections.observableArrayList(customers));
        Stage modalStage = new Stage();
        modalStage.setScene(new Scene(root));
        modalStage.setTitle("Create Customer");
        modalStage.showAndWait();
    }

    @FXML
    private void handleSelectCustomer() {
        Customer selected = customerListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            setCurrentCustomer(selected);
            updateUI();
            Stage stage = (Stage) customerListView.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Please select a customer from the list.");
        }
    }

    @FXML
    private void handleSaveNewCustomer() {
        String newName = newCustomerNameField.getText().trim();
        if (!newName.isEmpty()) {
            Customer newCustomer = new Customer();
            newCustomer.setName(newName);
            setCurrentCustomer(newCustomer);
            customers.add(newCustomer);
            updateUI();
            Stage stage = (Stage) newCustomerNameField.getScene().getWindow();
            stage.close();
        } else {
            showAlert("Please enter a new customer name.");
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) newCustomerNameField.getScene().getWindow();
        stage.close();
    }
}

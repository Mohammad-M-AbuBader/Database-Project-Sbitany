package Controllers;

import Utilities.CreateTable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ProductController {

    @FXML
    private AnchorPane anchorPane;
    public void d(){

    }
    @FXML
    private Label lblTotalProducts;

    @FXML
    private TextField txProductCode;

    @FXML
    private Button btSearch;

    @FXML
    private TableView<?> tvProducts;

    @FXML
    private TableColumn<?, ?> cmCodeProducts;

    @FXML
    private TableColumn<?, ?> cmNameProducts;

    @FXML
    private TableColumn<?, ?> cmManufacturerProduct;

    @FXML
    private TableColumn<?, ?> cmSellingPriceProducts;

    @FXML
    private TableColumn<?, ?> P;

    @FXML
    private TableColumn<?, ?> cmBranchStorage;

    @FXML
    private TableColumn<?, ?> cmStatusBranchStorage;

    @FXML
    private TableColumn<?, ?> cmQuantityBranchStorage;

    @FXML
    private TableColumn<?, ?> cmMainStorage;

    @FXML
    private TableColumn<?, ?> cmStatusMainStorage;

    @FXML
    private TableColumn<?, ?> cmQuantityMainStorage;
}


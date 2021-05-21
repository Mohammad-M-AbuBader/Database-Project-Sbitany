/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 17-5-2021  3:24 AM
 */
package Utilities;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Define a CreateTable class with one constructor
public final class CreateTable<T> {

    // Attribute
    private TableView<T> tableView;

    // Constructor with specific data
    public CreateTable(int width, int height) {
        this.tableView = new TableView<>();
        this.tableView.setEditable(false);
        this.tableView.setMinHeight(height);
        this.tableView.setMinWidth(width);
        this.tableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2;" +
                " -fx-font-family:" + "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000;" +
                " -fx-font-weight: BOLd; ");
    }

    // add new column to this table
    public <E> void createColumn(String attribute, String name, int width) {
        // column for name of the baby
        TableColumn<T, E> tableColumn = new TableColumn<>(name);
        tableColumn.setMinWidth(width);
        tableColumn.setSortable(false);
        tableColumn.setResizable(false);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(attribute));
        this.tableView.getColumns().add(tableColumn);
    }

    // add new recorde to this table
    public void addRecord(T data) {
        if (data != null)
            this.tableView.getItems().add(data);
    }

    // clear all data in this table
    public void clearTable() {
        this.tableView.getItems().clear();
    }

    // return this tableView
    public TableView<T> getTableView() {
        return this.tableView;
    }
}

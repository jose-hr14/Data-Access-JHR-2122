package com.jhr2122.unit5.finalactivity;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.List;

/**
 * This class manages de modal window opened by the main library controller to select a book-user matching the
 * search pattern during the lending-returning operations.
 */
public class ListviewController{
    List<BooksEntity> booksEntities;
    List<UsersEntity> usersEntities;
    LibraryController libraryController;

    @FXML
    private ImageView iconCancel;

    @FXML
    private ImageView iconConfirm;

    @FXML
    private ListView<Object> listView;

    public void setLibraryController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    public void setBooksEntities(List<BooksEntity> booksEntities) {
        this.booksEntities = booksEntities;
         listView.getItems().addAll( FXCollections.observableList(booksEntities));
    }

    public void setUsersEntities(List<UsersEntity> usersEntities) {
        this.usersEntities = usersEntities;
            listView.getItems().addAll( FXCollections.observableList(usersEntities));
    }

    /**
     * Sets the selected user or book primary key on the designated textfield of the lending-returning panel from
     * the main form.
     * @param event
     */
    @FXML
    void iconConfirmModalListener(MouseEvent event) {
        if(listView.getSelectionModel().getSelectedItem() instanceof BooksEntity)
        {
            libraryController.getTxfSearchIsbn().setText(((BooksEntity) listView.getSelectionModel().getSelectedItem()).getIsbn());
            libraryController.getTxfFoundBookName().setText(listView.getSelectionModel().getSelectedItem().toString());
            libraryController.getTxfFoundBookName().setDisable(true);
            libraryController.getIconSearchIsbn().setDisable(true);

        }
        else if(listView.getSelectionModel().getSelectedItem() instanceof UsersEntity)
        {
            libraryController.getTxfSearchUserCode().setText(((UsersEntity) listView.getSelectionModel().getSelectedItem()).getCode());
            libraryController.getTxfFoundUserName().setText(listView.getSelectionModel().getSelectedItem().toString());
            libraryController.getTxfFoundUserName().setDisable(true);
            libraryController.getIconSearchUserCode().setDisable(true);
        }
        Stage stage = (Stage) listView.getScene().getWindow();
        stage.close();
    }

    /**
     * Closes the modal window when the cancel button is clicked.
     * @param event
     */
    @FXML
    void iconCancelModalListener(MouseEvent event) {
        Stage stage = (Stage) listView.getScene().getWindow();
        stage.close();
    }
}

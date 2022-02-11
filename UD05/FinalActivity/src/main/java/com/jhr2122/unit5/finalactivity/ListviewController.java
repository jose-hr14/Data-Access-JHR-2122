package com.jhr2122.unit5.finalactivity;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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


    public LibraryController getLibraryController() {
        return libraryController;
    }

    public void setLibraryController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    public ImageView getIconCancel() {
        return iconCancel;
    }

    public void setIconCancel(ImageView iconCancel) {
        this.iconCancel = iconCancel;
    }

    public ImageView getIconConfirm() {
        return iconConfirm;
    }

    public void setIconConfirm(ImageView iconConfirm) {
        this.iconConfirm = iconConfirm;
    }

    public List<BooksEntity> getBooksEntities() {
        return booksEntities;
    }

    public void setBooksEntities(List<BooksEntity> booksEntities) {
        this.booksEntities = booksEntities;
         listView.getItems().addAll( FXCollections.observableList(booksEntities));
    }

    public List<UsersEntity> getUsersEntities() {
        return usersEntities;
    }

    public void setUsersEntities(List<UsersEntity> usersEntities) {
        this.usersEntities = usersEntities;
            listView.getItems().addAll( FXCollections.observableList(usersEntities));
    }

    @FXML
    void iconConfirmModalListener(MouseEvent event) {
        if(listView.getSelectionModel().getSelectedItem() instanceof BooksEntity)
        {
            libraryController.getTxfSearchIsbn().setText(((BooksEntity) listView.getSelectionModel().getSelectedItem()).getIsbn());
            libraryController.getTxfFoundBookName().setText(listView.getSelectionModel().getSelectedItem().toString());
            libraryController.getTxfFoundBookName().setDisable(true);
        }
        else if(listView.getSelectionModel().getSelectedItem() instanceof UsersEntity)
        {
            libraryController.getTxfSearchUserCode().setText(((UsersEntity) listView.getSelectionModel().getSelectedItem()).getCode());
            libraryController.getTxfFoundUserName().setText(listView.getSelectionModel().getSelectedItem().toString());
            libraryController.getTxfFoundUserName().setDisable(true);
        }
        Stage stage = (Stage) listView.getScene().getWindow();
        stage.close();
    }

    @FXML
    void iconCancelModalListener(MouseEvent event) {
        Stage stage = (Stage) listView.getScene().getWindow();
        stage.close();
    }
}

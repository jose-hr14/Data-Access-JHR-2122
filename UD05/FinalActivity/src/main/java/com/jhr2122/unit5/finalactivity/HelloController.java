package com.jhr2122.unit5.finalactivity;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HelloController {
    public HelloController() {
    }

    @FXML
    private GridPane upperPane;

    @FXML
    private ImageView iconUser;

    @FXML
    private GridPane bottomPanel;

    @FXML
    private ImageView iconLentBook;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView iconAdd;

    @FXML
    private ImageView iconExit;

    @FXML
    private ImageView iconRead;

    @FXML
    private ImageView iconBook;

    @FXML
    private ImageView iconReturnBook;

    @FXML
    private ImageView iconEdit;

    @FXML
    private TextField label1;


    @FXML
    void iconReadListener(ActionEvent event) {
        label1.setText("JavaFX test");
    }
}
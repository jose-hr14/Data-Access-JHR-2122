package com.jhr2122.unit5.finalactivity;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.time.LocalDate;

public class LibraryController {

    DatabaseManager databaseManager;
    boolean isRead;
    boolean isAdd;
    boolean isDelete;
    public LibraryController() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        databaseManager = new DatabaseManager(session);

        isRead = false;
        isAdd = false;
        isDelete = false;
    }

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane bottomConfirmPane;

    @FXML
    private GridPane bottomPanel;

    @FXML
    private ImageView iconAdd;

    @FXML
    private ImageView iconBook;

    @FXML
    private ImageView iconCancel;

    @FXML
    private ImageView iconConfirm;

    @FXML
    private ImageView iconEdit;

    @FXML
    private ImageView iconExit;

    @FXML
    private ImageView iconLentBook;

    @FXML
    private ImageView iconRead;

    @FXML
    private ImageView iconReturnBook;

    @FXML
    private ImageView iconSearchIsbn;

    @FXML
    private ImageView iconSearchUserCode;

    @FXML
    private ImageView iconUser;

    @FXML
    private DatePicker lblUserBirthdate;

    @FXML
    private TextField lblUserCode;

    @FXML
    private TextField lblUserName;

    @FXML
    private TextField lblUserSurname;

    @FXML
    private GridPane paneBook;

    @FXML
    private GridPane paneLent;

    @FXML
    private GridPane paneUser;

    @FXML
    private Slider sliderCopies;

    @FXML
    private TextField txfCover;

    @FXML
    private TextField txfFoundBookName;

    @FXML
    private TextField txfFoundUserName;

    @FXML
    private TextField txfISBN;

    @FXML
    private TextField txfOutline;

    @FXML
    private TextField txfPublisher;

    @FXML
    private TextField txfSearchIsbn;

    @FXML
    private TextField txfSearchUserCode;

    @FXML
    private TextField txfTitle;

    @FXML
    private GridPane upperPane;

    @FXML
    void iconReadListener(MouseEvent event) {

    }

    @FXML
    void userIconListener(MouseEvent event) {
        paneBook.setVisible(false);
        paneLent.setVisible(false);
        paneUser.setVisible(true);
    }

    @FXML
    void bookIconListener(MouseEvent event) {
        paneUser.setVisible(false);
        paneLent.setVisible(false);
        paneBook.setVisible(true);
    }

    @FXML
    void lentIconListener(MouseEvent event) {
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
    }

    @FXML
    void iconAddListener(MouseEvent event) {
        bottomPanel.setVisible(false);
        bottomConfirmPane.setVisible(true);
        upperPane.setDisable(true);
        isAdd = true;
    }

    @FXML
    void iconExitListener(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void iconSearchUserCodeListener(MouseEvent event) {
        txfFoundUserName.setText(databaseManager.retrieveUserByID(txfSearchUserCode.getText()).toString());
    }

    @FXML
    void iconSearchIsbnListener(MouseEvent event) {

    }

    @FXML
    void iconCancelListener(MouseEvent event) {
        changePanelFromStandarToConfirm();
    }

    @FXML
    void iconConfirmListener(MouseEvent event) {
        if(paneUser.isVisible() && isAdd)
        {
            UsersEntity usersEntity = new UsersEntity(lblUserCode.getText(), lblUserName.getText(),
                    lblUserSurname.getText(), java.sql.Date.valueOf(lblUserBirthdate.getValue()));
            databaseManager.saveUser(usersEntity);
            isAdd = false;
            cleanUserFields();
            changePanelFromStandarToConfirm();
        }
        if(paneBook.isVisible() && isAdd){
            BooksEntity booksEntity = new BooksEntity(txfISBN.getText(), txfTitle.getText(),
                    (int) sliderCopies.getValue(), txfCover.getText(), txfOutline.getText(),
                    txfPublisher.getText());
            databaseManager.saveBook(booksEntity);
            isAdd = false;
            changePanelFromStandarToConfirm();
        }
    }

    void changePanelFromStandarToConfirm()
    {
        bottomPanel.setVisible(true);
        bottomConfirmPane.setVisible(false);
        upperPane.setDisable(false);
    }

    void cleanUserFields()
    {
        lblUserCode.clear();
        lblUserName.clear();
        lblUserSurname.clear();
        lblUserBirthdate.setValue(null);
    }
}

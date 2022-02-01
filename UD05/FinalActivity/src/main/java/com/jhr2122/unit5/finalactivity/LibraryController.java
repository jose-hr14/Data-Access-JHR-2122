package com.jhr2122.unit5.finalactivity;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;

public class LibraryController {

    DatabaseManager databaseManager;
    boolean isRead;
    boolean isAdd;
    boolean isDelete;
    boolean isEdit;
    boolean isSearchingToEdit;
    public LibraryController() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        databaseManager = new DatabaseManager(session);

        isRead = false;
        isAdd = false;
        isDelete = false;
        isEdit = false;
        isSearchingToEdit = false;
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
        if(paneUser.isVisible())
        {
            lblUserCode.setDisable(false);
            isRead = true;
            changePanelFromStandardToConfirm();
        }
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
        changePanelFromConfirmToStandard();
        if(paneUser.isVisible())
        {
            disableUserFields();
            cleanUserFields();
            isEdit = false;
            isRead = false;
            isAdd = false;
            isDelete = false;
            isSearchingToEdit = false;
        }
    }

    @FXML
    void iconAddListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        isAdd = true;
        if(paneUser.isVisible())
        {
            enableUserFields();
        }
        else if(paneBook.isVisible())
        {
            enableBookFields();
        }
    }

    @FXML
    void iconEditListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        if(paneUser.isVisible())
        {
            isSearchingToEdit = true;
            lblUserCode.setDisable(false);
        }
    }

    @FXML
    void iconConfirmListener(MouseEvent event) {
        if(paneUser.isVisible() && isAdd)
        {
            UsersEntity usersEntity = new UsersEntity(lblUserCode.getText(), lblUserName.getText(),
                    lblUserSurname.getText(), java.sql.Date.valueOf(lblUserBirthdate.getValue()));
            databaseManager.saveUser(usersEntity);
            cleanUserFields();
            disableUserFields();
            changePanelFromConfirmToStandard();
            isAdd = false;
        }
        else if(paneUser.isVisible() && isRead)
        {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            lblUserName.setText(usersEntity.getName());
            lblUserSurname.setText(usersEntity.getSurname());
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());
            isRead = false;
        }
        else if(paneUser.isVisible() && isSearchingToEdit)
        {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            lblUserName.setText(usersEntity.getName());
            lblUserSurname.setText(usersEntity.getSurname());
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());

            lblUserCode.setDisable(true);
            lblUserName.setDisable(false);
            lblUserSurname.setDisable(false);
            lblUserBirthdate.setDisable(false);
            lblUserBirthdate.setDisable(false);

            isSearchingToEdit = false;
            isEdit = true;
        }
        else if(paneUser.isVisible() && isEdit)
        {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            usersEntity.setName(lblUserName.getText());
            usersEntity.setSurname(lblUserSurname.getText());
            usersEntity.setBirthdate(Date.valueOf(lblUserBirthdate.getValue()));
            databaseManager.updateUser(usersEntity);

            cleanUserFields();
            disableUserFields();
            changePanelFromConfirmToStandard();

            isEdit = false;
        }
        else if(paneBook.isVisible() && isAdd){
            BooksEntity booksEntity = new BooksEntity(txfISBN.getText(), txfTitle.getText(),
                    (int) sliderCopies.getValue(), txfCover.getText(), txfOutline.getText(),
                    txfPublisher.getText());
            databaseManager.saveBook(booksEntity);
            changePanelFromConfirmToStandard();

            isAdd = false;
        }
    }

    void changePanelFromConfirmToStandard()
    {
        bottomPanel.setVisible(true);
        bottomConfirmPane.setVisible(false);
        upperPane.setDisable(false);
    }

    void changePanelFromStandardToConfirm()
    {
        bottomPanel.setVisible(false);
        bottomConfirmPane.setVisible(true);
        upperPane.setDisable(true);
    }

    void enableUserFields()
    {
        lblUserCode.setDisable(false);
        lblUserName.setDisable(false);
        lblUserSurname.setDisable(false);
        lblUserBirthdate.setDisable(false);
    }

    void enableBookFields()
    {
        txfISBN.setDisable(false);
        txfTitle.setDisable(false);
        sliderCopies.setDisable(false);
        txfCover.setDisable(false);
        txfOutline.setDisable(false);
        txfPublisher.setDisable(false);
    }

    void disableBookFields()
    {
        txfISBN.setDisable(true);
        txfTitle.setDisable(true);
        sliderCopies.setDisable(true);
        txfCover.setDisable(true);
        txfOutline.setDisable(true);
        txfPublisher.setDisable(true);
    }

    void disableUserFields()
    {
        lblUserCode.setDisable(true);
        lblUserName.setDisable(true);
        lblUserSurname.setDisable(true);
        lblUserBirthdate.setDisable(true);
    }

    void cleanUserFields()
    {
        lblUserCode.clear();
        lblUserName.clear();
        lblUserSurname.clear();
        lblUserBirthdate.setValue(null);
    }
}

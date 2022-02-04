package com.jhr2122.unit5.finalactivity;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import java.time.LocalDate;

public class LibraryController {

    DatabaseManager databaseManager;
    boolean isRead;
    boolean isAdd;
    boolean isDelete;
    boolean isEdit;
    boolean isSearchingToEdit;
    boolean isBorrowing;
    boolean isReturning;

    public LibraryController() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        databaseManager = new DatabaseManager(session);

        isRead = false;
        isAdd = false;
        isDelete = false;
        isEdit = false;
        isSearchingToEdit = false;
        isBorrowing = false;
        isReturning = false;
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
    private Label lblBorrowOrReturnState;

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
    void userIconListener(MouseEvent event) {
        paneBook.setVisible(false);
        paneLent.setVisible(false);
        paneUser.setVisible(true);
        isBorrowing = false;
        isReturning = false;
    }

    @FXML
    void bookIconListener(MouseEvent event) {
        paneUser.setVisible(false);
        paneLent.setVisible(false);
        paneBook.setVisible(true);
        isBorrowing = false;
        isReturning = false;
    }

    @FXML
    void lentIconListener(MouseEvent event) {
        lblBorrowOrReturnState.setText("Borrow menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        isBorrowing = true;
        isReturning = false;
    }

    @FXML
    void returnIconListener(MouseEvent event){
        lblBorrowOrReturnState.setText("Return menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        isBorrowing = false;
        isReturning = true;
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
        txfFoundBookName.setText(databaseManager.retrieveBookByID(txfSearchIsbn.getText()).toString());
    }

    @FXML
    void iconCancelListener(MouseEvent event) {
        if (paneUser.isVisible()) {
            disableUserFields();
            cleanUserFields();
        } else if (paneBook.isVisible()) {
            disableBookFields();
            cleanBookFields();
        }
        else if(isBorrowing)
        {
            disableRentReturnFields();
            cleanRentReturnFields();
        }
        changePanelFromConfirmToStandard();
        isEdit = false;
        isRead = false;
        isAdd = false;
        isDelete = false;
        isSearchingToEdit = false;
    }

    @FXML
    void iconReadListener(MouseEvent event) {
        if (paneUser.isVisible()) {
            lblUserCode.setDisable(false);
            isRead = true;
            changePanelFromStandardToConfirm();
        }
        else if(paneBook.isVisible())
        {
            txfISBN.setDisable(false);
            isRead = true;
            changePanelFromStandardToConfirm();
        }
    }

    @FXML
    void iconAddListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        isAdd = true;
        if (paneUser.isVisible()) {
            enableUserFields();
        } else if (paneBook.isVisible()) {
            enableBookFields();
        }
        else if(isBorrowing)
        {
            enableRentReturnFields();
        }
    }

    @FXML
    void iconEditListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        if (paneUser.isVisible()) {
            isSearchingToEdit = true;
            lblUserCode.setDisable(false);
        }
        else if(paneBook.isVisible()){
            isSearchingToEdit = true;
            txfISBN.setDisable(false);
        }
    }

    @FXML
    void iconConfirmListener(MouseEvent event) {
        if (paneUser.isVisible() && isAdd) {
            UsersEntity usersEntity = new UsersEntity(lblUserCode.getText(), lblUserName.getText(),
                    lblUserSurname.getText(), java.sql.Date.valueOf(lblUserBirthdate.getValue()));
            databaseManager.saveUser(usersEntity);
            cleanUserFields();
            disableUserFields();
            changePanelFromConfirmToStandard();
            isAdd = false;
        } else if (paneUser.isVisible() && isRead) {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            lblUserName.setText(usersEntity.getName());
            lblUserSurname.setText(usersEntity.getSurname());
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());
            isRead = false;
        } else if (paneUser.isVisible() && isSearchingToEdit) {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            lblUserName.setText(usersEntity.getName());
            lblUserSurname.setText(usersEntity.getSurname());
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());

            enableUserFields();
            lblUserCode.setDisable(true);

            isSearchingToEdit = false;
            isEdit = true;
        } else if (paneUser.isVisible() && isEdit) {
            UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
            usersEntity.setName(lblUserName.getText());
            usersEntity.setSurname(lblUserSurname.getText());
            usersEntity.setBirthdate(Date.valueOf(lblUserBirthdate.getValue()));
            databaseManager.updateUser(usersEntity);

            cleanUserFields();
            disableUserFields();
            changePanelFromConfirmToStandard();

            isEdit = false;
        } else if (paneBook.isVisible() && isAdd) {
            BooksEntity booksEntity = new BooksEntity(txfISBN.getText(), txfTitle.getText(),
                    (int) sliderCopies.getValue(), txfCover.getText(), txfOutline.getText(),
                    txfPublisher.getText());
            databaseManager.saveBook(booksEntity);
            changePanelFromConfirmToStandard();
            cleanBookFields();
            disableBookFields();

            isAdd = false;
        }
        else if (paneBook.isVisible() && isRead) {
            BooksEntity booksEntity = databaseManager.retrieveBookByID(txfISBN.getText());
            txfTitle.setText(booksEntity.getTitle());
            sliderCopies.setValue(booksEntity.getCopies());
            txfCover.setText(booksEntity.getCover());
            txfOutline.setText(booksEntity.getOutline());
            txfPublisher.setText(booksEntity.getPublisher());
        }
        else if (paneBook.isVisible() && isSearchingToEdit) {
            BooksEntity booksEntity = databaseManager.retrieveBookByID(txfISBN.getText());
            txfTitle.setText(booksEntity.getTitle());
            sliderCopies.setValue(booksEntity.getCopies());
            txfCover.setText(booksEntity.getCover());
            txfOutline.setText(booksEntity.getOutline());
            txfPublisher.setText(booksEntity.getPublisher());

            enableBookFields();
            txfISBN.setDisable(true);

            isSearchingToEdit = false;
            isEdit = true;
        }
        else if (paneBook.isVisible() && isEdit) {
            BooksEntity booksEntity = databaseManager.retrieveBookByID(txfISBN.getText());

            booksEntity.setTitle(txfTitle.getText());
            booksEntity.setCopies((int) sliderCopies.getValue());
            booksEntity.setCover(txfCover.getText());
            booksEntity.setOutline(txfOutline.getText());
            booksEntity.setPublisher(txfPublisher.getText());

            databaseManager.updateBook(booksEntity);

            cleanBookFields();
            disableBookFields();
            changePanelFromConfirmToStandard();

            isEdit = false;
        }
        else if(isBorrowing && isAdd)
        {
            LendingEntity lendingEntity = new LendingEntity();
            lendingEntity.setBorrower(databaseManager.retrieveUserByID(txfSearchUserCode.getText()));
            lendingEntity.setBook(databaseManager.retrieveBookByID(txfSearchIsbn.getText()));
            lendingEntity.setLendingdate(Date.valueOf(LocalDate.now()));
            try {
                databaseManager.saveLending(lendingEntity);
            } catch (Exception e) {
                //Reserve book option
            }

            isAdd = false;
            disableRentReturnFields();
        }
    }

    void changePanelFromConfirmToStandard() {
        bottomPanel.setVisible(true);
        bottomConfirmPane.setVisible(false);
        upperPane.setDisable(false);
    }

    void changePanelFromStandardToConfirm() {
        bottomPanel.setVisible(false);
        bottomConfirmPane.setVisible(true);
        upperPane.setDisable(true);
    }

    void enableUserFields() {
        lblUserCode.setDisable(false);
        lblUserName.setDisable(false);
        lblUserSurname.setDisable(false);
        lblUserBirthdate.setDisable(false);
    }

    void enableBookFields() {
        txfISBN.setDisable(false);
        txfTitle.setDisable(false);
        sliderCopies.setDisable(false);
        txfCover.setDisable(false);
        txfOutline.setDisable(false);
        txfPublisher.setDisable(false);
    }

    void disableBookFields() {
        txfISBN.setDisable(true);
        txfTitle.setDisable(true);
        sliderCopies.setDisable(true);
        txfCover.setDisable(true);
        txfOutline.setDisable(true);
        txfPublisher.setDisable(true);
    }

    void cleanBookFields() {

        txfISBN.clear();
        txfTitle.clear();
        sliderCopies.setValue(1);
        txfCover.clear();
        txfOutline.clear();
        txfPublisher.clear();
    }

    void disableUserFields() {
        lblUserCode.setDisable(true);
        lblUserName.setDisable(true);
        lblUserSurname.setDisable(true);
        lblUserBirthdate.setDisable(true);
    }

    void cleanUserFields() {
        lblUserCode.clear();
        lblUserName.clear();
        lblUserSurname.clear();
        lblUserBirthdate.setValue(null);
    }

    void enableRentReturnFields()
    {
        iconSearchUserCode.setDisable(false);
        iconSearchUserCode.setVisible(true);
        txfSearchUserCode.setDisable(false);
        iconSearchIsbn.setDisable(false);
        iconSearchIsbn.setVisible(true);
        txfSearchIsbn.setDisable(false);
    }

    void disableRentReturnFields()
    {
        iconSearchUserCode.setDisable(true);
        iconSearchUserCode.setVisible(false);
        txfSearchUserCode.setDisable(true);
        iconSearchIsbn.setDisable(true);
        iconSearchIsbn.setVisible(false);
        txfSearchIsbn.setDisable(true);
    }

    void cleanRentReturnFields()
    {
        txfSearchUserCode.clear();
        txfFoundUserName.clear();
        txfSearchIsbn.clear();
        txfFoundBookName.clear();
    }
}

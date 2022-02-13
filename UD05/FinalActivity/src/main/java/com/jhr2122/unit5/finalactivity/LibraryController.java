package com.jhr2122.unit5.finalactivity;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryController {
    //500 pixeles de ancho
    DatabaseManager databaseManager;
    boolean isRead;
    boolean isAdd;
    boolean isDelete;
    boolean isEdit;
    boolean isSearchingToEdit;
    boolean isBorrowing;
    boolean isReturning;
    ColorAdjust effect;
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
    private ImageView iconCancelModal;
    @FXML
    private ImageView iconConfirmModal;
    @FXML
    private ListView<BooksEntity> listView;

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

        getEffect();
    }

    public TextField getTxfSearchIsbn() {
        return txfSearchIsbn;
    }

    public void setTxfSearchIsbn(TextField txfSearchIsbn) {
        this.txfSearchIsbn = txfSearchIsbn;
    }

    public TextField getTxfSearchUserCode() {
        return txfSearchUserCode;
    }

    public void setTxfSearchUserCode(TextField txfSearchUserCode) {
        this.txfSearchUserCode = txfSearchUserCode;
    }

    public TextField getTxfFoundBookName() {
        return txfFoundBookName;
    }

    public void setTxfFoundBookName(TextField txfFoundBookName) {
        this.txfFoundBookName = txfFoundBookName;
    }

    public TextField getTxfFoundUserName() {
        return txfFoundUserName;
    }

    public void setTxfFoundUserName(TextField txfFoundUserName) {
        this.txfFoundUserName = txfFoundUserName;
    }

    @FXML
    void userIconListener(MouseEvent event) {
        paneBook.setVisible(false);
        paneLent.setVisible(false);
        paneUser.setVisible(true);
        iconRead.setVisible(true);
        iconEdit.setVisible(true);
        isBorrowing = false;
        isReturning = false;

        iconUser.setEffect(effect);
        iconBook.setEffect(null);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(null);
    }

    @FXML
    void bookIconListener(MouseEvent event) {
        paneUser.setVisible(false);
        paneLent.setVisible(false);
        paneBook.setVisible(true);
        iconRead.setVisible(true);
        iconEdit.setVisible(true);
        isBorrowing = false;
        isReturning = false;

        iconUser.setEffect(null);
        iconBook.setEffect(effect);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(null);
    }

    @FXML
    void lentIconListener(MouseEvent event) {
        lblBorrowOrReturnState.setText("Borrow menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        iconRead.setVisible(false);
        iconEdit.setVisible(false);
        isBorrowing = true;
        isReturning = false;

        iconUser.setEffect(null);
        iconBook.setEffect(null);
        iconLentBook.setEffect(effect);
        iconReturnBook.setEffect(null);
    }

    @FXML
    void returnIconListener(MouseEvent event) {
        lblBorrowOrReturnState.setText("Return menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        iconRead.setVisible(false);
        iconEdit.setVisible(false);
        isBorrowing = false;
        isReturning = true;

        iconUser.setEffect(null);
        iconBook.setEffect(null);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(effect);
    }

    @FXML
    void iconExitListener(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void iconSearchUserCodeListener(MouseEvent event) {
        try {
            openModalWindowUsers(databaseManager.retrieveUsersByWildcard(txfFoundUserName.getText()));
        } catch (HibernateException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getClass().toString());
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void iconSearchIsbnListener(MouseEvent event) {
        try {
            if (isBorrowing)
                openModalWindowBooks(databaseManager.retrieveBooksByWildcard(txfFoundBookName.getText()));
            else if (isReturning) {
                List<BooksEntity> bookToBeReturned = new ArrayList<>();
                for (LendingEntity lending : databaseManager.retrieveUserByID(txfSearchUserCode.getText()).getLentBooks()) {
                    bookToBeReturned.add(lending.getBook());
                }
                if (bookToBeReturned.isEmpty())
                    throw new HibernateException("This user has no book to be returned");
                else
                    openModalWindowBooks(bookToBeReturned);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getClass().toString());
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void iconCancelListener(MouseEvent event) {
        if (paneUser.isVisible()) {
            disableUserFields();
            cleanUserFields();
        } else if (paneBook.isVisible()) {
            disableBookFields();
            cleanBookFields();
        } else if (isBorrowing || isReturning) {
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
        } else if (paneBook.isVisible()) {
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
        } else if (isBorrowing) {
            enableRentReturnFields();
        } else if (isReturning) {
            enableRentReturnFields();
        }
    }

    @FXML
    void iconEditListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        if (paneUser.isVisible()) {
            isSearchingToEdit = true;
            lblUserCode.setDisable(false);
        } else if (paneBook.isVisible()) {
            isSearchingToEdit = true;
            txfISBN.setDisable(false);
        }
    }

    @FXML
    void iconConfirmListener(MouseEvent event) {
        if (paneUser.isVisible() && isAdd) {
            if (!lblUserCode.getText().isEmpty() && !lblUserName.getText().isEmpty() && !lblUserSurname.getText().isEmpty())
                addUser();
            else
                openAlertDialog(Alert.AlertType.WARNING, "Attention", "Some fields are blank",
                        "Id, name and surname are mandatory");
        } else if (paneUser.isVisible() && isRead) {
            readUser();
        } else if (paneUser.isVisible() && isSearchingToEdit) {
            searchingUserToEdit();
        } else if (paneUser.isVisible() && isEdit) {
            editUser();
        } else if (paneBook.isVisible() && isAdd) {
            if(!txfISBN.getText().isEmpty() && !txfTitle.getText().isEmpty() && !txfPublisher.getText().isEmpty())
                addBook();
            else
                openAlertDialog(Alert.AlertType.WARNING, "Attention", "Some fields are blank",
                        "ISBN, tittle and publisher are mandatory");
        } else if (paneBook.isVisible() && isRead) {
            readBook();
        } else if (paneBook.isVisible() && isSearchingToEdit) {
            searchBookToEdit();
        } else if (paneBook.isVisible() && isEdit) {
            editBook();
        } else if (isBorrowing && isAdd) {
            addLending();
        } else if (isReturning && isAdd) {
            addReturning();
        }
    }

    private void addReturning() {
        try {
            LendingEntity lendingEntity = databaseManager.retrieveLendingByIDAAndISBN
                    (databaseManager.retrieveUserByID(txfSearchUserCode.getText()),
                            databaseManager.retrieveBookByID(txfSearchIsbn.getText()));
            databaseManager.saveReturn(lendingEntity);
            if (lendingEntity.getBorrower().getFined() != null
                    && lendingEntity.getBorrower().getFined().toLocalDate().isAfter(LocalDate.now())) {
                openAlertDialog(Alert.AlertType.ERROR, "Error", "Late returnal",
                        "User was fined until " + lendingEntity.getBorrower().getFined());
            }
            List<ReservationsEntity> reservationsEntity = databaseManager.retrieveReservationsByBook(lendingEntity.getBook());
            if (!databaseManager.retrieveReservationsByBook(lendingEntity.getBook()).isEmpty())
                openAlertDialog(Alert.AlertType.INFORMATION, "Attention", "A user has a reservation " +
                        "of this book", "Please, notify " +
                        databaseManager.retrieveReservationsByBook(lendingEntity.getBook()).get(0).getBorrower().toString());
        } catch (Exception e) {
            e.printStackTrace();
            openAlertDialog(Alert.AlertType.ERROR, "Error", e.getClass().toString(), e.getMessage());
        }
        isAdd = false;
        disableRentReturnFields();
        cleanRentReturnFields();
        changePanelFromConfirmToStandard();
    }

    private void addLending() {
        if (!txfSearchUserCode.getText().isEmpty() && !txfSearchIsbn.getText().isEmpty()) {
            LendingEntity lendingEntity = new LendingEntity();
            lendingEntity.setBorrower(databaseManager.retrieveUserByID(txfSearchUserCode.getText()));
            lendingEntity.setBook(databaseManager.retrieveBookByID(txfSearchIsbn.getText()));
            lendingEntity.setLendingdate(Date.valueOf(LocalDate.now()));
            if (lendingEntity.getBook().getCopies() < 1) {
                Optional<ButtonType> result = openConfirmationDialog("Attention", "The are no copies available",
                        "Do you want to make a reservation?");
                if (result.get() == ButtonType.OK) {
                    addReservation(lendingEntity);
                }
            } else if (databaseManager.bookIsReserved(lendingEntity.getBook())) {
                List<ReservationsEntity> reservationsOfThisBook = databaseManager.
                        retrieveReservationsByBook(lendingEntity.getBook());
                if (reservationsOfThisBook.get(0).getBorrower().equals(lendingEntity.getBorrower())) {
                    try {
                        databaseManager.saveLending(lendingEntity);
                        databaseManager.deleteReservation(reservationsOfThisBook.get(0));
                    } catch (Exception e) {
                        openAlertDialog(Alert.AlertType.ERROR, "Attention", "Error", e.getMessage());
                    }
                } else {
                    Optional<ButtonType> result = openConfirmationDialog("Attention", "The available copies are reserved",
                            "Do you want to make a reservation?");
                    if (result.get() == ButtonType.OK) {
                        addReservation(lendingEntity);
                    }
                }
            } else {
                try {
                    databaseManager.saveLending(lendingEntity);
                } catch (Exception e) {
                    openAlertDialog(Alert.AlertType.ERROR, "Attention", "Error", e.getMessage());
                }
            }
            isAdd = false;
            disableRentReturnFields();
            cleanRentReturnFields();
            changePanelFromConfirmToStandard();
        }
    }

    private void addReservation(LendingEntity lendingEntity) {
        ReservationsEntity reservationsEntity = new ReservationsEntity();
        reservationsEntity.setBorrower(lendingEntity.getBorrower());
        reservationsEntity.setBook(lendingEntity.getBook());
        reservationsEntity.setReservation(Date.valueOf(LocalDate.now()));
        databaseManager.saveReservation(reservationsEntity);
    }

    private void editBook() {
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

    private void searchBookToEdit() {
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

    private void readBook() {
        BooksEntity booksEntity = databaseManager.retrieveBookByID(txfISBN.getText());
        txfTitle.setText(booksEntity.getTitle());
        sliderCopies.setValue(booksEntity.getCopies());
        txfCover.setText(booksEntity.getCover());
        txfOutline.setText(booksEntity.getOutline());
        txfPublisher.setText(booksEntity.getPublisher());
    }

    private void addBook() {
        BooksEntity booksEntity = new BooksEntity(txfISBN.getText(), txfTitle.getText(),
                (int) sliderCopies.getValue(), txfCover.getText(), txfOutline.getText(),
                txfPublisher.getText());
        databaseManager.saveBook(booksEntity);
        changePanelFromConfirmToStandard();
        cleanBookFields();
        disableBookFields();

        isAdd = false;
    }

    private void editUser() {
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

    private void searchingUserToEdit() {
        UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
        lblUserName.setText(usersEntity.getName());
        lblUserSurname.setText(usersEntity.getSurname());
        lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());

        enableUserFields();
        lblUserCode.setDisable(true);

        isSearchingToEdit = false;
        isEdit = true;
    }

    private void readUser() {
        UsersEntity usersEntity = databaseManager.retrieveUserByID(lblUserCode.getText());
        lblUserName.setText(usersEntity.getName());
        lblUserSurname.setText(usersEntity.getSurname());
        lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());
        isRead = false;
    }

    private void addUser() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setCode(lblUserCode.getText());
        usersEntity.setName(lblUserName.getText());
        usersEntity.setSurname(lblUserSurname.getText());
        if(lblUserBirthdate.getValue() != null)
            usersEntity.setBirthdate(Date.valueOf(lblUserBirthdate.getValue()));
        databaseManager.saveUser(usersEntity);
        cleanUserFields();
        disableUserFields();
        changePanelFromConfirmToStandard();
        isAdd = false;
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

    void enableRentReturnFields() {
        iconSearchUserCode.setDisable(false);
        iconSearchUserCode.setVisible(true);
        txfFoundUserName.setDisable(false);
        iconSearchIsbn.setDisable(false);
        iconSearchIsbn.setVisible(true);
        txfFoundBookName.setDisable(false);
    }

    void disableRentReturnFields() {
        iconSearchUserCode.setDisable(true);
        iconSearchUserCode.setVisible(false);
        txfFoundUserName.setDisable(true);
        iconSearchIsbn.setDisable(true);
        iconSearchIsbn.setVisible(false);
        txfFoundBookName.setDisable(true);
    }

    void cleanRentReturnFields() {
        txfSearchUserCode.clear();
        txfFoundUserName.clear();
        txfSearchIsbn.clear();
        txfFoundBookName.clear();
    }

    void openModalWindowBooks(List<BooksEntity> booksEntityList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listview-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 255, 408);
        ((ListviewController) fxmlLoader.getController()).setBooksEntities(booksEntityList);
        ((ListviewController) fxmlLoader.getController()).setLibraryController(this);
        stage.setTitle("Choose a book");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    void openModalWindowUsers(List<UsersEntity> usersEntities) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listview-view.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 255, 408);
        ((ListviewController) fxmlLoader.getController()).setUsersEntities(usersEntities);
        ((ListviewController) fxmlLoader.getController()).setLibraryController(this);
        stage.setTitle("Choose user");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    void openAlertDialog(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    Optional<ButtonType> openConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    private void getEffect() {
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.3);
        lighting.setSpecularExponent(20.0);
        lighting.setSurfaceScale(1.5);
        Shadow shadow = new Shadow();
        shadow.setWidth(21.0);
        shadow.setHeight(21.0);
        shadow.setRadius(10.0);
        lighting.setBumpInput(shadow);
        effect = new ColorAdjust();
        effect.setInput(lighting);
    }
}

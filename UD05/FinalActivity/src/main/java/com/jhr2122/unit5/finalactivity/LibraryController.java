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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * This is the controller of the main application, it manages the view and interaction while it makes calls to the
 * database manager and spring manager methods when needed.
 */
public class LibraryController {
    DatabaseManager databaseManager;
    SpringManager springManager;
    boolean isRead;
    boolean isAdd;
    boolean isDelete;
    boolean isEdit;
    boolean isSearchingToEdit;
    boolean isBorrowing;
    boolean isReturning;
    boolean isSearchingToDelete;
    ColorAdjust effect;
    @FXML
    private ImageView iconDelete;
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
    @FXML
    private GridPane bottomPanel2;

    /**
     * Main controller constructor. It disables hibernate warnings, instantiates the postgresql database manager,
     * the spring petitions manager, creates the effect for the selected panel and initialises the state machine
     * booleans.
     */
    public LibraryController() {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger =
                org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate") .setLevel(Level.OFF);
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        setEffect();

        databaseManager = new DatabaseManager(sessionFactory);
        springManager = new SpringManager();
        isRead = false;
        isAdd = false;
        isDelete = false;
        isEdit = false;
        isSearchingToEdit = false;
        isBorrowing = false;
        isReturning = false;
        isSearchingToDelete = false;
    }

    public TextField getTxfSearchIsbn() {
        return txfSearchIsbn;
    }

    public TextField getTxfSearchUserCode() {
        return txfSearchUserCode;
    }

    public TextField getTxfFoundBookName() {
        return txfFoundBookName;
    }

    public TextField getTxfFoundUserName() {
        return txfFoundUserName;
    }

    public ImageView getIconSearchIsbn() {
        return iconSearchIsbn;
    }

    public ImageView getIconSearchUserCode() {
        return iconSearchUserCode;
    }

    @FXML
    void iconDeleteListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        isSearchingToDelete = true;
        if (paneUser.isVisible()) {
            enableUserFields();
            cleanUserFields();
        } else if (paneBook.isVisible()) {
            enableBookFields();
            cleanBookFields();
        }
    }

    /**
     * Shows the user panel to search, edit and add users.
     * @param event
     */
    @FXML
    void userIconListener(MouseEvent event) {
        paneBook.setVisible(false);
        paneLent.setVisible(false);
        paneUser.setVisible(true);
        iconRead.setVisible(true);
        iconEdit.setVisible(true);
        iconDelete.setVisible(true);

        isBorrowing = false;
        isReturning = false;

        iconUser.setEffect(effect);
        iconBook.setEffect(null);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(null);

        bottomPanel2.setVisible(false);
        bottomPanel.setVisible(true);
    }

    /**
     * Shows the book panel to edit, search and add books.
     * @param event
     */
    @FXML
    void bookIconListener(MouseEvent event) {
        paneUser.setVisible(false);
        paneLent.setVisible(false);
        paneBook.setVisible(true);
        iconRead.setVisible(true);
        iconEdit.setVisible(true);
        iconDelete.setVisible(true);

        isBorrowing = false;
        isReturning = false;

        iconUser.setEffect(null);
        iconBook.setEffect(effect);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(null);

        bottomPanel2.setVisible(false);
        bottomPanel.setVisible(true);
    }

    /**
     * Shows the lending tab to manage new lendings
     * @param event
     */
    @FXML
    void lentIconListener(MouseEvent event) {
        lblBorrowOrReturnState.setText("Borrow menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        iconRead.setVisible(false);
        iconEdit.setVisible(false);
        iconDelete.setVisible(false);
        isBorrowing = true;
        isReturning = false;

        iconUser.setEffect(null);
        iconBook.setEffect(null);
        iconLentBook.setEffect(effect);
        iconReturnBook.setEffect(null);
        bottomPanel2.setVisible(true);
        bottomPanel.setVisible(false);
    }

    /**
     * Shows the returnal tab to manage books returning.
     * @param event
     */
    @FXML
    void returnIconListener(MouseEvent event) {
        lblBorrowOrReturnState.setText("Return menu");
        paneUser.setVisible(false);
        paneBook.setVisible(false);
        paneLent.setVisible(true);
        iconRead.setVisible(false);
        iconEdit.setVisible(false);
        iconDelete.setVisible(false);

        isBorrowing = false;
        isReturning = true;

        iconUser.setEffect(null);
        iconBook.setEffect(null);
        iconLentBook.setEffect(null);
        iconReturnBook.setEffect(effect);

        bottomPanel2.setVisible(true);
        bottomPanel.setVisible(false);
    }

    /**
     * Asks the user confirmation to exit the application
     * @param event
     */
    @FXML
    void iconExitListener(MouseEvent event) {
        Optional<ButtonType> result = openConfirmationDialog("Attention", "Confirm",
                "Do you want to close the application?");
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Allows partial searching of users, opening a listview window to select one of the matching results.
     * @param event
     */
    @FXML
    void iconSearchUserCodeListener(MouseEvent event) {
        try {
            openModalWindowUsers(databaseManager.retrieveUsersByWildcard(txfFoundUserName.getText()));
        } catch (Exception e) {
            openAlertDialog(Alert.AlertType.ERROR, "Error", "Searching", getExceptionCause(e));
        }
    }
    /**
     * Allows partial searching of books, opening a listview window to select one of the matching results. If
     * called during a returning operation, listview will hold only the books borrowed by the specified user
     * or will give an alert dialog notifying that the user has no books to be returned.
     * @param event
     */
    @FXML
    void iconSearchIsbnListener(MouseEvent event) {
        try {
            if (isBorrowing)
                openModalWindowBooks(databaseManager.retrieveBooksByWildcard(txfFoundBookName.getText()));
            else if (isReturning) {
                List<BooksEntity> booksToBeReturned = new ArrayList<>();
                for (LendingEntity lending : databaseManager.retrieveFirstUserByID(txfSearchUserCode.getText()).getLentBooks()) {
                    booksToBeReturned.add(lending.getBook());
                }
                if (booksToBeReturned.isEmpty())
                    throw new HibernateException("This user has no books to be returned");
                else
                    openModalWindowBooks(booksToBeReturned);
            }
        } catch (Exception e) {
            openAlertDialog(Alert.AlertType.ERROR, "Error", "Searching", getExceptionCause(e));
            isAdd = false;
            disableRentReturnFields();
            cleanRentReturnFields();
            changePanelFromConfirmToStandard();
        }
    }

    /**
     * Alows to exit any ongoing operation.
     * @param event
     */
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

    /**
     * Enables the searching operation for users or books.
     * @param event
     */
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

    /**
     * Enables the adding operation for users, books, lendings and returnings.
     * @param event
     */
    @FXML
    void iconAddListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        isAdd = true;
        if (paneUser.isVisible()) {
            enableUserFields();
            cleanUserFields();
        } else if (paneBook.isVisible()) {
            enableBookFields();
            cleanBookFields();
        } else if (isBorrowing) {
            enableRentReturnFields();
        } else if (isReturning) {
            enableRentReturnFields();
        }
    }

    /**
     * Enables the editing operation for users and books.
     * @param event
     */
    @FXML
    void iconEditListener(MouseEvent event) {
        changePanelFromStandardToConfirm();
        if (paneUser.isVisible()) {
            isSearchingToEdit = true;
            lblUserCode.setDisable(false);
            cleanUserFields();
        } else if (paneBook.isVisible()) {
            isSearchingToEdit = true;
            txfISBN.setDisable(false);
            cleanBookFields();
        }
    }

    /**
     * Allows confirmation and commit of the current operation, while notifying the user of operation errors or
     * confirmations.
     * @param event
     */
    @FXML
    void iconConfirmListener(MouseEvent event) {
        if (paneUser.isVisible() && isAdd) {
            if (!lblUserCode.getText().isEmpty() && !lblUserName.getText().isEmpty() && !lblUserSurname.getText().isEmpty())
            {
                try
                {
                    addUser();
                    openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Users",
                            "User stored successfully");
                }
                catch(Exception exception)
                {
                    openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                            getExceptionCause(exception));
                }
            }
            else
                openAlertDialog(Alert.AlertType.WARNING, "Attention", "Some fields are blank",
                        "Id, name and surname are mandatory");
        } else if (paneUser.isVisible() && isRead) {
            try
            {
                readUser();
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Users",
                        "User found successfully");
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "User not found",
                        getExceptionCause(exception));
                cleanUserFields();
                changePanelFromConfirmToStandard();
            }
        } else if (paneUser.isVisible() && isSearchingToEdit) {
            try
            {
                searchingUserToEdit();
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }
        } else if (paneUser.isVisible() && isEdit) {
            try{
                editUser();
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Users",
                        "User edited successfully");
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }

        }


        /**
         * EXAM USER DELETE OPERATIONS
         */
        else if (paneUser.isVisible() && isSearchingToDelete) {
            try
            {
                searchingUserToDelete();
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }
        } else if (paneUser.isVisible() && isDelete) {
            try{
                deleteUser();
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Users",
                        "User deleted successfully");
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }

        }
        /**
         * EXAM BOOK DELETE OPERATION
         */
        else if (paneBook.isVisible() && isSearchingToDelete) {
            try
            {
                searchingBookToDelete();
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }
        } else if (paneBook.isVisible() && isDelete) {
            try{
                deleteBook();
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Books",
                        "Book deleted successfully");
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }

        }



        else if (paneBook.isVisible() && isAdd) {
            if(!txfISBN.getText().isEmpty() && !txfTitle.getText().isEmpty() && !txfPublisher.getText().isEmpty())
            {
                try
                {
                    addBook();
                    openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Books",
                            "Book stored successfully");
                }
                catch(Exception exception)
                {
                    openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                            getExceptionCause(exception));
                }
            }
            else
                openAlertDialog(Alert.AlertType.WARNING, "Attention", "Some fields are blank",
                        "ISBN, tittle and publisher are mandatory");
        } else if (paneBook.isVisible() && isRead) {
            readBook();
            openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Books",
                    "Book found successfully");
        } else if (paneBook.isVisible() && isSearchingToEdit) {
            try
            {
                searchBookToEdit();
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }
        } else if (paneBook.isVisible() && isEdit) {
            try
            {
                editBook();
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Books",
                        "Book edited successfully");
            }
            catch(Exception exception)
            {
                openAlertDialog(Alert.AlertType.ERROR, "Attention", "Aborting operation",
                        getExceptionCause(exception));
            }
        } else if (isBorrowing && isAdd) {
            addLending();
        } else if (isReturning && isAdd) {
            addReturning();
        }
    }

    /**
     * EXAM BOOK
     * @throws Exception
     */
    private void deleteBook() throws Exception {
        BooksEntity booksEntity = databaseManager.retrieveFirstBookByID(txfISBN.getText());
        springManager.deleteBook(booksEntity);
        isDelete = false;
        changePanelFromConfirmToStandard();
        cleanBookFields();
    }

    /**
     * EXAM BOOK
     */
    private void searchingBookToDelete()
    {
        BooksEntity booksEntity = databaseManager.retrieveFirstBookByID(txfISBN.getText());
        txfISBN.setText(booksEntity.getIsbn());
        txfTitle.setText(booksEntity.getTitle());
        sliderCopies.setValue(booksEntity.getCopies());
        txfCover.setText(booksEntity.getCover());
        txfOutline.setText(booksEntity.getOutline());
        txfPublisher.setText(booksEntity.getPublisher());

        disableBookFields();

        isSearchingToDelete = false;
        isDelete = true;
    }

    /**
     * EXAM USER
     */
    private void searchingUserToDelete() {
        UsersEntity usersEntity = databaseManager.retrieveFirstUserByID(lblUserCode.getText());
        lblUserCode.setText(usersEntity.getCode());
        lblUserName.setText(usersEntity.getName());
        lblUserSurname.setText(usersEntity.getSurname());
        if(usersEntity.getBirthdate() != null)
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());

        disableUserFields();

        isSearchingToDelete = false;
        isDelete = true;
    }

    /**
     * EXAM USER
     * @throws Exception
     */
    private void deleteUser() throws Exception {
        UsersEntity usersEntity = databaseManager.retrieveFirstUserByID(lblUserCode.getText());
        databaseManager.deleteUser(usersEntity);
        isDelete = false;
        changePanelFromConfirmToStandard();
        cleanUserFields();
    }

    /**
     * If the mandatory fields are filled, allows to save a book returning, fines the user if the book is returned late
     * and informs of the user that has a reservation on the book returned.
     */
    private void addReturning() {
        if (!txfSearchUserCode.getText().isEmpty() && !txfSearchIsbn.getText().isEmpty()) {
            try {
                LendingEntity lendingEntity = databaseManager.retrieveLendingByIDAAndISBN
                        (databaseManager.retrieveFirstUserByID(txfSearchUserCode.getText()),
                                databaseManager.retrieveFirstBookByID(txfSearchIsbn.getText()));
                databaseManager.saveReturn(lendingEntity);
                openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Returnal",
                        "The book was returned successfully");
                if (lendingEntity.getBorrower().getFined() != null
                        && lendingEntity.getBorrower().getFined().toLocalDate().isAfter(LocalDate.now())) {
                    openAlertDialog(Alert.AlertType.ERROR, "Error", "Late returning",
                            "User was fined until " + lendingEntity.getBorrower().getFined());
                }
                if (!databaseManager.retrieveReservationsByBook(lendingEntity.getBook()).isEmpty())
                    openAlertDialog(Alert.AlertType.INFORMATION, "Attention", "A user has a reservation " +
                            "of this book", "Please, notify " +
                            databaseManager.retrieveReservationsByBook(lendingEntity.getBook()).get(0).getBorrower().toString());
            } catch (Exception e) {
                openAlertDialog(Alert.AlertType.ERROR, "Error", e.getClass().toString(), getExceptionCause(e));
            }
            isAdd = false;
            disableRentReturnFields();
            cleanRentReturnFields();
            changePanelFromConfirmToStandard();
        }
    }

    /**
     * Allows book lending to users if there are enough copies, or allows to make a reservations if there are not.
     * If the are copies to be lent, but there are more reservations than copies available, only those whose have a
     * reservation may borrow the book
     */
    private void addLending() {
        if (!txfSearchUserCode.getText().isEmpty() && !txfSearchIsbn.getText().isEmpty()) {
            LendingEntity lendingEntity = new LendingEntity();
            lendingEntity.setBorrower(databaseManager.retrieveFirstUserByID(txfSearchUserCode.getText()));
            lendingEntity.setBook(databaseManager.retrieveFirstBookByID(txfSearchIsbn.getText()));
            lendingEntity.setLendingdate(Date.valueOf(LocalDate.now()));

            if (lendingEntity.getBook().getCopies() < 1) {
                Optional<ButtonType> result = openConfirmationDialog("Attention", "The are no copies available",
                        "Do you want to make a reservation?");
                if (result.get() == ButtonType.OK) {
                    addReservation(lendingEntity);
                    openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Reservation",
                            "Book reserved successfully");
                }
            } else if (!lendingEntity.getBook().getReservedBy().isEmpty()) {
                List<ReservationsEntity> reservationsOfThisBook = databaseManager.
                        retrieveReservationsByBook(lendingEntity.getBook());
                List<UsersEntity> usersWhoCanBorrow = new ArrayList<>();
                for(int i = 0; i < reservationsOfThisBook.size() && i < lendingEntity.getBook().getCopies(); i++)
                {
                    usersWhoCanBorrow.add(reservationsOfThisBook.get(i).getBorrower());
                }
                if (usersWhoCanBorrow.contains(lendingEntity.getBorrower()) || lendingEntity.getBook().getCopies() >
                        reservationsOfThisBook.size()) {
                    try {
                        databaseManager.saveLending(lendingEntity);
                        try
                        {
                            springManager.deleteReservation(reservationsOfThisBook.get(0));
                            openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Borrowing",
                                    "The reserved book was borrowed successfully");
                        }
                        catch(Exception e)
                        {
                            databaseManager.deleteReservation(reservationsOfThisBook.get(0));
                            openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Borrowing",
                                    "The reserved book was borrowed successfully");
                        }
                    } catch (Exception e) {
                        openAlertDialog(Alert.AlertType.ERROR, "Attention", "Error", getExceptionCause(e));
                    }
                } else {
                    if(reservationsOfThisBook.stream().anyMatch(reservation -> reservation.getBorrower().equals(lendingEntity.getBorrower())))
                    {
                        openAlertDialog(Alert.AlertType.INFORMATION, "Attention", "Reservations",
                                "this user has a reservation already, but the are no copies avaiable");
                    }
                    else
                    {
                        Optional<ButtonType> result = openConfirmationDialog("Attention", "The available copies are reserved",
                                "Do you want to make a reservation?");
                        if (result.get() == ButtonType.OK) {
                            addReservation(lendingEntity);
                            openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Reservation",
                                    "Book reserved successfully");
                        }
                    }
                }
            } else {
                try {
                    databaseManager.saveLending(lendingEntity);
                    openAlertDialog(Alert.AlertType.INFORMATION, "Operation successful", "Borrowing",
                            "Book borrowed successfully");
                } catch (Exception e) {
                    openAlertDialog(Alert.AlertType.ERROR, "Attention", "Error", getExceptionCause(e));
                }
            }
            isAdd = false;
            disableRentReturnFields();
            cleanRentReturnFields();
            changePanelFromConfirmToStandard();
        }
    }

    /**
     * Attemps to save a new reservation through Spring, if Spring is not running at the current moment, it will
     * save the reservation through PostgreSql.
     * @param lendingEntity
     */
    private void addReservation(LendingEntity lendingEntity) {
        ReservationsEntity reservationsEntity = new ReservationsEntity();
        reservationsEntity.setBorrower(lendingEntity.getBorrower());
        reservationsEntity.setBook(lendingEntity.getBook());
        reservationsEntity.setDate(Date.valueOf(LocalDate.now()));
        try {
            springManager.PostReservation(reservationsEntity);
        } catch (Exception e) {
            databaseManager.saveReservation(reservationsEntity);
        }
    }

    /**
     * Allows editing an existing book from the database.
     */
    private void editBook() {
        BooksEntity booksEntity = databaseManager.retrieveFirstBookByID(txfISBN.getText());
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

    /**
     * Allows a partial search of book by name that will be edited afterwards.
     */
    private void searchBookToEdit() {
        BooksEntity booksEntity = databaseManager.retrieveFirstBookByID(txfISBN.getText());
        txfISBN.setText(booksEntity.getIsbn());
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

    /**
     * Makes a partial search of book by ISBN and prints its information in the panel's textfields.
     */
    private void readBook() {
        try
        {
            txfISBN.setDisable(true);
            BooksEntity booksEntity = databaseManager.retrieveFirstBookByID(txfISBN.getText());
            txfISBN.setText(booksEntity.getIsbn());
            txfTitle.setText(booksEntity.getTitle());
            sliderCopies.setValue(booksEntity.getCopies());
            txfCover.setText(booksEntity.getCover());
            txfOutline.setText(booksEntity.getOutline());
            txfPublisher.setText(booksEntity.getPublisher());
        }
        catch(Exception exception)
        {
            openAlertDialog(Alert.AlertType.ERROR, "Attention", "Book not found", getExceptionCause(exception));
            cleanBookFields();
        }
        isRead = false;
        changePanelFromConfirmToStandard();
    }

    /**
     * Read the information from the textfields of the panels to create a new book entity and saves it in the
     * database.
     */
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

    /**
     * Allows editing a user with the new information written in the panel's textfield.
     */
    private void editUser() {
        UsersEntity usersEntity = databaseManager.retrieveFirstUserByID(lblUserCode.getText());
        usersEntity.setName(lblUserName.getText());
        usersEntity.setSurname(lblUserSurname.getText());
        if(usersEntity.getBirthdate() != null)
            usersEntity.setBirthdate(Date.valueOf(lblUserBirthdate.getValue()));
        databaseManager.updateUser(usersEntity);

        cleanUserFields();
        disableUserFields();
        changePanelFromConfirmToStandard();

        isEdit = false;
    }

    /**
     * Makes a partial search of user by id to make possible editing int afterwards.
     */
    private void searchingUserToEdit() {
        UsersEntity usersEntity = databaseManager.retrieveFirstUserByID(lblUserCode.getText());
        lblUserCode.setText(usersEntity.getCode());
        lblUserName.setText(usersEntity.getName());
        lblUserSurname.setText(usersEntity.getSurname());
        if(usersEntity.getBirthdate() != null)
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());

        enableUserFields();
        lblUserCode.setDisable(true);

        isSearchingToEdit = false;
        isEdit = true;
    }




    /**
     * Makes a partial search of users by id, returning the first match.
     */
    private void readUser() {
        lblUserCode.setDisable(true);
        UsersEntity usersEntity = databaseManager.retrieveFirstUserByID(lblUserCode.getText());
        lblUserCode.setText(usersEntity.getCode());
        lblUserName.setText(usersEntity.getName());
        lblUserSurname.setText(usersEntity.getSurname());
        if(usersEntity.getBirthdate() != null)
            lblUserBirthdate.setValue(usersEntity.getBirthdate().toLocalDate());
        isRead = false;

        changePanelFromConfirmToStandard();
    }

    /**
     * Saves a new user in the database with the information read from the panel's textfields.
     */
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

    /**
     * Hides the confirmation o cancel panel from the bottom of the window to the standard search, edit and add panel.
     */
    void changePanelFromConfirmToStandard() {
        bottomConfirmPane.setVisible(false);
        upperPane.setDisable(false);
        if(paneUser.isVisible() || paneBook.isVisible())
            bottomPanel.setVisible(true);
        else if(paneLent.isVisible())
            bottomPanel2.setVisible(true);
    }

    /**
     * Hides the bottom standard panel with the read, edit and add buttons to show the confirmation and cancel panel.
     */
    void changePanelFromStandardToConfirm() {
        bottomPanel.setVisible(false);
        bottomPanel2.setVisible(false);
        bottomConfirmPane.setVisible(true);
        upperPane.setDisable(true);
    }

    /**
     * Enables the textfields from the users panel
     */
    void enableUserFields() {
        lblUserCode.setDisable(false);
        lblUserName.setDisable(false);
        lblUserSurname.setDisable(false);
        lblUserBirthdate.setDisable(false);
    }

    /**
     * Enables the textfields from the books panel
     */
    void enableBookFields() {
        txfISBN.setDisable(false);
        txfTitle.setDisable(false);
        sliderCopies.setDisable(false);
        txfCover.setDisable(false);
        txfOutline.setDisable(false);
        txfPublisher.setDisable(false);
    }

    /**
     * Disables the textfields from the books panel
     */
    void disableBookFields() {
        txfISBN.setDisable(true);
        txfTitle.setDisable(true);
        sliderCopies.setDisable(true);
        txfCover.setDisable(true);
        txfOutline.setDisable(true);
        txfPublisher.setDisable(true);
    }

    /**
     * Cleans all text from the book's textfields
     */
    void cleanBookFields() {

        txfISBN.clear();
        txfTitle.clear();
        sliderCopies.setValue(1);
        txfCover.clear();
        txfOutline.clear();
        txfPublisher.clear();
    }

    /**
     * Disables the textfields from the user panel
     */
    void disableUserFields() {
        lblUserCode.setDisable(true);
        lblUserName.setDisable(true);
        lblUserSurname.setDisable(true);
        lblUserBirthdate.setDisable(true);
    }

    /**
     * Cleans all text from the user's textfields
     */
    void cleanUserFields() {
        lblUserCode.clear();
        lblUserName.clear();
        lblUserSurname.clear();
        lblUserBirthdate.setValue(null);
    }

    /**
     * Enables the id and isbn textfields from the lending-returning panel
     */
    void enableRentReturnFields() {
        iconSearchUserCode.setDisable(false);
        iconSearchUserCode.setVisible(true);
        txfFoundUserName.setDisable(false);
        iconSearchIsbn.setDisable(false);
        iconSearchIsbn.setVisible(true);
        txfFoundBookName.setDisable(false);
    }

    /**
     * Disables the id and isbn textfields from the lending-returning panel
     */
    void disableRentReturnFields() {
        iconSearchUserCode.setDisable(true);
        iconSearchUserCode.setVisible(false);
        txfFoundUserName.setDisable(true);
        iconSearchIsbn.setDisable(true);
        iconSearchIsbn.setVisible(false);
        txfFoundBookName.setDisable(true);
    }

    /**
     * Cleans all textfields from the lending-returning panel.
     */
    void cleanRentReturnFields() {
        txfSearchUserCode.clear();
        txfFoundUserName.clear();
        txfSearchIsbn.clear();
        txfFoundBookName.clear();
    }

    /**
     * It opens a new window with a listview loaded with the books available to be borrowed or returned.
     * The isbn of the selected book will be printed in the isbn textfield from the lending-returning panel
     * after clicking the confirmation button.
     * @param booksEntityList
     * @throws IOException
     */
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

    /**
     * Opens a new window with a listview loaded with the users from the database. The id of the selected user will
     * be printed in the id textfield from the lending-returning panel after clicking the confirmation button.
     * @param usersEntities
     * @throws IOException
     */
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

    /**
     * It will open an alert dialog to inform the user of successful operations, exceptions, and more.
     * @param alertType
     * @param title
     * @param headerText
     * @param contentText
     */
    void openAlertDialog(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * It opens a confirmation dialog to ask user for confirmation before proceeding with some operations, like
     * closing the application.
     * @param title
     * @param headerText
     * @param contentText
     * @return
     */
    Optional<ButtonType> openConfirmationDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }

    /**
     * Set ups the efect that will have the imageView selected from the top panel
     */
    private void setEffect() {
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

    /**
     * It unwraps the exception thrown to get specific cause of the error
     * @param exception
     * @return
     */
    private String getExceptionCause(Throwable exception)
    {
        while(exception.getCause() != null)
        {
            exception = exception.getCause();
        }
        return  exception.getMessage();
    }
}

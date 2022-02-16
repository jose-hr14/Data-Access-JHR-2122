package com.jhr2122.unit5.finalactivity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class DatabaseManager {
    Session session;

    public DatabaseManager(Session session) {
        this.session = session;
    }

    public List<UsersEntity> retrieveUserList() {
        session.clear();
        Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity");
        return myQuery.list();
    }

    public List<BooksEntity> retrieveBooksList() {
        Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity");
        return myQuery.list();
    }

    public List<ReservationsEntity> retrieveReservationsByBook(BooksEntity booksEntity) {
        Query<ReservationsEntity> myQuery = session.createQuery(("from com.jhr2122.unit5.finalactivity.ReservationsEntity " + "where book.isbn = '" + booksEntity.getIsbn() + "' order by date"));
        return myQuery.list();
    }

    public UsersEntity retrieveUserByID(String id) throws HibernateException {
        Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " + "id = '" + id + "'");
        if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
        else throw new HibernateException("User not found");
    }

    public List<UsersEntity> retrieveUsersByWildcard(String name) {
        Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " + "upper(name) like upper('%" + name + "%') or upper(surname) like upper('%" + name + "%')");
        if (!myQuery.list().isEmpty()) return myQuery.list();
        else throw new HibernateException("User not found");
    }

    public List<BooksEntity> retrieveBooksByWildcard(String tittle) {
        Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "upper(title) like upper('%" + tittle + "%')");
        if (!myQuery.list().isEmpty()) return myQuery.list();
        else throw new HibernateException("User not found");
    }

    public BooksEntity retrieveBookByID(String isbn) throws HibernateException {
        Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "isbn = '" + isbn + "'");
        if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
        else throw new HibernateException("Book not found");

    }

    public LendingEntity retrieveLendingByIDAAndISBN(UsersEntity usersEntity, BooksEntity booksEntity) throws HibernateException {
        Query<LendingEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.LendingEntity where " + "book.isbn = '" + booksEntity.getIsbn() + "' and borrower.code = '" + usersEntity.getCode() + "'" + "and returningdate = null");
        if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
        else throw new HibernateException("Lending not found");

    }

    public void saveUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.save(usersEntity);
        transaction.commit();
        session.refresh(usersEntity);
    }

    public void saveBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.save(booksEntity);
        transaction.commit();
        session.refresh(booksEntity);
    }

    public void saveLending(LendingEntity lendingEntity) throws Exception {
        List<LendingEntity> asdf = lendingEntity.getBorrower().getLentBooks();
        if(lendingEntity.getBorrower().getLentBooks().stream().anyMatch(lending -> lending.getBook().equals
                        (lendingEntity.getBook())))
            throw new Exception("Book already borowed by this user");
        if(lendingEntity.getBorrower().getLentBooks().size() == 3)
            throw new Exception("This user has already borrowed three books");
        lendingEntity.getBook().setCopies(lendingEntity.getBook().getCopies() - 1);
        this.updateBook(lendingEntity.getBook());
        Transaction transaction = session.beginTransaction();
        session.save(lendingEntity);
        transaction.commit();
        session.refresh(lendingEntity.getBorrower());
    }

    public void saveReturn(LendingEntity lendingEntity) {
        lendingEntity.setReturningdate(Date.valueOf(LocalDate.now()));
        this.updateLending(lendingEntity);
        lendingEntity.getBook().setCopies(lendingEntity.getBook().getCopies() + 1);
        this.updateBook(lendingEntity.getBook());
        if (lendingEntity.getLendingdate().toLocalDate().plusDays(7).isAfter(LocalDate.now())) {
            lendingEntity.getBorrower().setFined(Date.valueOf(LocalDate.now().plusDays(7)));
        }
        session.refresh(lendingEntity.getBorrower());
    }

    public void saveReservation(ReservationsEntity reservationsEntity) {
        Transaction transaction = session.beginTransaction();
        session.save(reservationsEntity);
        transaction.commit();
        session.refresh(reservationsEntity);
    }

    public void updateUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(usersEntity);
        transaction.commit();
        session.refresh(usersEntity);
    }

    public void updateBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(booksEntity);
        transaction.commit();
        session.refresh(booksEntity);
    }

    public void updateLending(LendingEntity lendingEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(lendingEntity);
        transaction.commit();
        session.refresh(lendingEntity);
    }

    public void deleteUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.delete(usersEntity);
        transaction.commit();
        session.refresh(usersEntity);
    }

    public void deleteBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.delete(booksEntity);
        transaction.commit();
        session.refresh(booksEntity);
    }

    public void deleteReservation(ReservationsEntity reservationsEntity) {
        Transaction transaction = session.beginTransaction();
        session.delete(reservationsEntity);
        transaction.commit();
        session.refresh(reservationsEntity);
    }

    public boolean bookIsReserved(BooksEntity booksEntity) {
        List<ReservationsEntity> reservationsEntity = booksEntity.getReservedBy();
        if (reservationsEntity.size() >= booksEntity.getCopies()) return true;
        else return false;
    }
}

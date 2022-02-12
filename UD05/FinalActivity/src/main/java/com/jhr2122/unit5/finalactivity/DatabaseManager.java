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
        Query<UsersEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity");
        return myQuery.list();
    }

    public List<BooksEntity> retrieveBooksList() {
        Query<BooksEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity");
        return myQuery.list();
    }

    public UsersEntity retrieveUserByID(String id) throws HibernateException {
        Query<UsersEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " +
                        "id = '" + id + "'");
        if (!myQuery.list().isEmpty())
            return myQuery.list().get(0);
        else
            throw new HibernateException("User not found");
    }

    public List<UsersEntity> retrieveUsersByWildcard(String name)
    {
        Query<UsersEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " +
                        "upper(name) like upper('%" + name + "%') or upper(surname) like upper('%" + name + "%')");
        if (!myQuery.list().isEmpty())
            return myQuery.list();
        else
            throw new HibernateException("User not found");
    }

    public List<BooksEntity> retrieveBooksByWildcard(String tittle)
    {
        Query<BooksEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " +
                        "upper(title) like upper('%" + tittle + "%')");
        if (!myQuery.list().isEmpty())
            return myQuery.list();
        else
            throw new HibernateException("User not found");
    }

    public BooksEntity retrieveBookByID(String isbn) throws HibernateException {
        Query<BooksEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " +
                        "isbn = '" + isbn + "'");
        if (!myQuery.list().isEmpty())
            return myQuery.list().get(0);
        else
            throw new HibernateException("Book not found");

    }

    public LendingEntity retrieveLendingByIDAAndISBN(UsersEntity usersEntity, BooksEntity booksEntity) throws HibernateException {
        Query<LendingEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.LendingEntity where " +
                        "book.isbn = '" + booksEntity.getIsbn() + "' and borrower.code = '" + usersEntity.getCode() + "'"
                        + "and returningdate = null");
        if (!myQuery.list().isEmpty())
            return myQuery.list().get(0);
        else
            throw new HibernateException("Lending not found");

    }

    public void saveUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.save(usersEntity);
        transaction.commit();
    }

    public void saveBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.save(booksEntity);
        transaction.commit();
    }

    public boolean bookIsReserved(BooksEntity booksEntity)
    {
        Query<ReservationsEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.ReservationsEntity where " +
                        "book.isbn = '" + booksEntity.getIsbn() + "'");
        if(myQuery.list().size() >= booksEntity.getCopies())
            return true;
        else
            return false;
    }

    public void saveLending(LendingEntity lendingEntity) throws Exception {
        if(lendingEntity.getBorrower().getFined() != null
                && lendingEntity.getBorrower().getFined().toLocalDate().isAfter(LocalDate.now()))
            throw new Exception("User is fined and cannot rent books until " +
                    lendingEntity.getBorrower().getFined().toLocalDate());
        if (lendingEntity.getBook().getCopies() < 1)
            throw new Exception("There are no copies available to borrow");
        lendingEntity.getBook().setCopies(lendingEntity.getBook().getCopies() - 1);
        this.updateBook(lendingEntity.getBook());
        Transaction transaction = session.beginTransaction();
        session.save(lendingEntity);
        transaction.commit();
    }

    public void saveReturn(LendingEntity lendingEntity) {
        lendingEntity.setReturningdate(Date.valueOf(LocalDate.now()));
        this.updateLending(lendingEntity);
        lendingEntity.getBook().setCopies(lendingEntity.getBook().getCopies() + 1);
        this.updateBook(lendingEntity.getBook());
        if (lendingEntity.getLendingdate().toLocalDate().plusDays(7).isAfter(LocalDate.now())) {
            lendingEntity.getBorrower().setFined(Date.valueOf(LocalDate.now()));
        }
    }

    public void saveReservation(ReservationsEntity reservationsEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.save(reservationsEntity);
        transaction.commit();
    }

    public void updateUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(usersEntity);
        transaction.commit();
    }

    public void updateBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(booksEntity);
        transaction.commit();
    }

    public void updateLending(LendingEntity lendingEntity) {
        Transaction transaction = session.beginTransaction();
        session.update(lendingEntity);
        transaction.commit();
    }

    public void deleteUser(UsersEntity usersEntity) {
        Transaction transaction = session.beginTransaction();
        session.delete(usersEntity);
        transaction.commit();
    }

    public void deleteBook(BooksEntity booksEntity) {
        Transaction transaction = session.beginTransaction();
        session.delete(booksEntity);
        transaction.commit();
    }
}

package com.jhr2122.unit5.finalactivity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class DatabaseManager {
    SessionFactory sessionFactory;

    public DatabaseManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<UsersEntity> retrieveUserList() {
        try (Session session = sessionFactory.openSession())
        {
            Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity");
            return myQuery.list();
        }
    }

    public List<BooksEntity> retrieveBooksList() {
        try (Session session = sessionFactory.openSession())
        {
            Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity");
            return myQuery.list();
        }
    }

    public List<ReservationsEntity> retrieveReservationsByBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Query<ReservationsEntity> myQuery = session.createQuery(("from com.jhr2122.unit5.finalactivity.ReservationsEntity " +
                    "where book.isbn = '" + booksEntity.getIsbn() + "' order by date"));
            return myQuery.list();
        }
    }

    public UsersEntity retrieveFirstUserByID(String id) throws HibernateException {
        try (Session session = sessionFactory.openSession())
        {
            Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " + "id LIKE '" + id + "%'" +
                    "ORDER BY id");
            if (!myQuery.list().isEmpty())
                return myQuery.list().get(0);
            else
                throw new HibernateException("User not found");
        }
    }

    public List<UsersEntity> retrieveUsersByWildcard(String name) {
        try (Session session = sessionFactory.openSession())
        {
            Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " + "upper(name) like upper('%" + name + "%') or upper(surname) like upper('%" + name + "%')");
            if (!myQuery.list().isEmpty()) return myQuery.list();
            else throw new HibernateException("User not found");
        }
    }

    public List<BooksEntity> retrieveBooksByWildcard(String tittle) {
        try (Session session = sessionFactory.openSession())
        {
            Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "upper(title) like upper('%" + tittle + "%')");
            if (!myQuery.list().isEmpty()) return myQuery.list();
            else throw new HibernateException("Book not found");
        }
    }

    public BooksEntity retrieveFirstBookByID(String isbn) throws HibernateException {
        try (Session session = sessionFactory.openSession())
        {
            Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "isbn LIKE '" + isbn + "%'" +
                    "ORDER BY isbn");
            if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
            else throw new HibernateException("Book not found");
        }
    }

    public LendingEntity retrieveLendingByIDAAndISBN(UsersEntity usersEntity, BooksEntity booksEntity) throws HibernateException {
        try (Session session = sessionFactory.openSession())
        {
            Query<LendingEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.LendingEntity where " +
                    "book.isbn = '" + booksEntity.getIsbn() + "' and borrower.code = '" + usersEntity.getCode() + "'" +
                    "and returningdate = null");
            if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
            else throw new HibernateException("Lending not found");
        }
    }

    public void saveUser(UsersEntity usersEntity){
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(usersEntity);
            transaction.commit();
            session.refresh(usersEntity);
        }
    }

    public void saveBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(booksEntity);
            transaction.commit();
            session.refresh(booksEntity);
        }
    }

    public void saveLending(LendingEntity lendingEntity) throws Exception {
        try (Session session = sessionFactory.openSession())
        {
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
    }

    public void saveReturn(LendingEntity lendingEntity) {
        try (Session session = sessionFactory.openSession())
        {
            lendingEntity.setReturningdate(Date.valueOf(LocalDate.now()));
            this.updateLending(lendingEntity);
            lendingEntity.getBook().setCopies(lendingEntity.getBook().getCopies() + 1);
            this.updateBook(lendingEntity.getBook());
            if (LocalDate.now().isAfter(lendingEntity.getLendingdate().toLocalDate().plusDays(7))) {
                lendingEntity.getBorrower().setFined(Date.valueOf(LocalDate.now().plusDays(7)));
            }
        }
    }

    public void saveReservation(ReservationsEntity reservationsEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(reservationsEntity);
            transaction.commit();
        }
    }

    public void updateUser(UsersEntity usersEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(usersEntity);
            transaction.commit();
        }
    }

    public void updateBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(booksEntity);
            transaction.commit();
        }
    }

    public void updateLending(LendingEntity lendingEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(lendingEntity);
            transaction.commit();
        }
    }

    public void deleteUser(UsersEntity usersEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.delete(usersEntity);
            transaction.commit();
        }
    }

    public void deleteBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.delete(booksEntity);
            transaction.commit();
        }
    }

    public void deleteReservation(ReservationsEntity reservationsEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.delete(reservationsEntity);
            transaction.commit();
        }
    }
}

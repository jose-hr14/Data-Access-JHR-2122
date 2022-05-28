package com.jhr2122.unit5.finalactivity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * This class interacts with the PostgreSql database. It holds all the methods related with de CRUD of the database.
 */
public class DatabaseManager {
    SessionFactory sessionFactory;

    /**
     * The database manager is instantiated with an instantiation of the session factory to do its job.
     * @param sessionFactory
     */
    public DatabaseManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * It returns the reservations of a specific book
     * @param booksEntity
     * @return
     */
    public List<ReservationsEntity> retrieveReservationsByBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Query<ReservationsEntity> myQuery = session.createQuery(("from com.jhr2122.unit5.finalactivity.ReservationsEntity " +
                    "where book.isbn = '" + booksEntity.getIsbn() + "' order by date"));
            return myQuery.list();
        }
    }

    /**
     * It makes a partial search of the user by id, and returns the first match,
     * @param id
     * @return
     * @throws HibernateException
     */
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

    /**
     * Retrieve a list of users matching a partial search by user name
     * @param name
     * @return
     */
    public List<UsersEntity> retrieveUsersByWildcard(String name) {
        try (Session session = sessionFactory.openSession())
        {
            Query<UsersEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " + "upper(name) like upper('%" + name + "%') or upper(surname) like upper('%" + name + "%')");
            if (!myQuery.list().isEmpty()) return myQuery.list();
            else throw new HibernateException("User not found");
        }
    }

    /**
     * Retrieves a book list matching a partial search by tittle and returns it
     * @param tittle
     * @return
     */
    public List<BooksEntity> retrieveBooksByWildcard(String tittle) {
        try (Session session = sessionFactory.openSession())
        {
            Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "upper(title) like upper('%" + tittle + "%')");
            if (!myQuery.list().isEmpty()) return myQuery.list();
            else throw new HibernateException("Book not found");
        }
    }

    /**
     * Makes a partial search of book by id, and returns its first match.
     * @param isbn
     * @return
     * @throws HibernateException
     */
    public BooksEntity retrieveFirstBookByID(String isbn) throws HibernateException {
        try (Session session = sessionFactory.openSession())
        {
            Query<BooksEntity> myQuery = session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " + "isbn LIKE '" + isbn + "%'" +
                    "ORDER BY isbn");
            if (!myQuery.list().isEmpty()) return myQuery.list().get(0);
            else throw new HibernateException("Book not found");
        }
    }

    /**
     * It takes a book and user to find and return a specific lending.
     * @param usersEntity
     * @param booksEntity
     * @return
     * @throws HibernateException
     */
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

    /**
     * Stores a user in the database
     * @param usersEntity
     */
    public void saveUser(UsersEntity usersEntity){
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(usersEntity);
            transaction.commit();
            session.refresh(usersEntity);
        }
    }

    /**
     * Stores a book in the database
     * @param booksEntity
     */
    public void saveBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(booksEntity);
            transaction.commit();
            session.refresh(booksEntity);
        }
    }

    /**
     * Stores a lending in the database
     * @param lendingEntity
     * @throws Exception
     */
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

    /**
     * Stores a return in the database, seting the lending's returning date to null.
     * @param lendingEntity
     */
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

    /**
     * Stores a new reservation in the database.
     * @param reservationsEntity
     */
    public void saveReservation(ReservationsEntity reservationsEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.save(reservationsEntity);
            transaction.commit();
        }
    }

    /**
     * Updates the specified user.
     * @param usersEntity
     */
    public void updateUser(UsersEntity usersEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(usersEntity);
            transaction.commit();
        }
    }

    /**
     * Updates the specified book.
     * @param booksEntity
     */
    public void updateBook(BooksEntity booksEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(booksEntity);
            transaction.commit();
        }
    }

    /**
     * Updates the specific lending
     * @param lendingEntity
     */
    public void updateLending(LendingEntity lendingEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.update(lendingEntity);
            transaction.commit();
        }
    }

    /**
     * Deletes the specific reservations
     * @param reservationsEntity
     */
    public void deleteReservation(ReservationsEntity reservationsEntity) {
        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.delete(reservationsEntity);
            transaction.commit();
        }
    }

    /**
     * EXAM USER
     * @param user
     * @throws Exception
     */
    public void deleteUser(UsersEntity user) throws Exception {
        if(user.getReservedBooks().isEmpty() && user.getLentBooks().isEmpty())
        {
            try (Session session = sessionFactory.openSession())
            {
                Transaction transaction = session.beginTransaction();
                session.delete(user);
                transaction.commit();
            }
        }
        else
            throw new Exception("This user has reserved books or borrowed books");
    }


}

package com.jhr2122.unit5.finalactivity;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DatabaseManager {
    Session session;

    public DatabaseManager(Session session) {
        this.session = session;
    }

    public List<UsersEntity> retrieveUserList()
    {
        session.clear();
        Query<UsersEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity");
        return myQuery.list();
    }

    public List<BooksEntity> retrieveBooksList()
    {
        Query<BooksEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity");
        return myQuery.list();
    }

    public UsersEntity retrieveUserByID(String id) throws HibernateException
    {
        Query<UsersEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.UsersEntity where " +
                        "id = '" + id + "'");
        if(!myQuery.list().isEmpty())
            return  myQuery.list().get(0);
        else
        {
            throw new HibernateException("User not found");
        }
    }

    public BooksEntity retrieveBookByID(String isbn) throws HibernateException
    {
        Query<BooksEntity> myQuery =
                session.createQuery("from com.jhr2122.unit5.finalactivity.BooksEntity where " +
                        "isbn = '" + isbn + "'");
        if(!myQuery.list().isEmpty())
            return  myQuery.list().get(0);
        else
        {
            throw new HibernateException("Book not found");
        }
    }

    public void saveUser(UsersEntity usersEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.save(usersEntity);
        transaction.commit();
    }
    public void saveBook(BooksEntity booksEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.save(booksEntity);
        transaction.commit();
    }
    public void updateUser(UsersEntity usersEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.update(usersEntity);
        transaction.commit();
    }

    public void updateBook(BooksEntity booksEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.update(booksEntity);
        transaction.commit();
    }

    public void deleteUser(UsersEntity usersEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.delete(usersEntity);
        transaction.commit();
    }

    public void deleteBook(BooksEntity booksEntity)
    {
        Transaction transaction = session.beginTransaction();
        session.delete(booksEntity);
        transaction.commit();
    }

}

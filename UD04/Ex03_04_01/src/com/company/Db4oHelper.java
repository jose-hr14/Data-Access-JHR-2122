package com.company;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Db4oHelper
{
    String databaseName = "museum.dat";
    ObjectContainer db;

    public Db4oHelper()
    {

    }

    public void storeAuthor(Author author)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        db.store(author);
        db.commit();
        db.close();
    }

    public void storeArtwork(Artwork artwork)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        db.store(artwork);
        db.commit();
        db.close();
    }

    public void storePainting(Painting painting)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        db.store(painting);
        db.commit();
        db.close();
    }

    public void storeSculpture(Sculpture sculpture)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        db.store(sculpture);
        db.commit();
        db.close();
    }

    public boolean autorExits(String authorCode)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        ObjectSet persons = db.queryByExample(new Author(authorCode, null, null));
        if(persons.hasNext())
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }
}

package com.company;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

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

    public List<Artwork> listArtworksByAuthor(String authorCode)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        ObjectSet artworks = db.queryByExample(new Artwork(authorCode));
        List<Artwork> artworkList = new ArrayList<>();

        while(artworks.hasNext())
        {
            artworkList.add((Artwork) artworks.next());
        }
        db.close();
        return artworkList;
    }

    public List<Artwork> listArtworksByCategory(String style)
    {
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
        Artwork artwork = new Artwork();
        artwork.setStyle(Styles.valueOf(style));
        ObjectSet artworks = db.queryByExample(artwork);
        List<Artwork> artworkList = new ArrayList<>();

        while(artworks.hasNext())
        {
            artworkList.add((Artwork) artworks.next());
        }
        db.close();
        return artworkList;
    }
    public Author retriveAuthorByName(String authorName)
    {
        try
        {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet authors = db.queryByExample(new Author(null, authorName, null));
            if(authors.hasNext())
            {
                return (Author) authors.next();
            }
            return null;
        }
        finally {
            db.close();
        }
    }
}

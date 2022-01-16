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
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            db.store(author);
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public void storeArtwork(Artwork artwork)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            db.store(artwork);
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public void storePainting(Painting painting)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            db.store(painting);
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public void storeSculpture(Sculpture sculpture)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            db.store(sculpture);
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public boolean autorExits(String authorCode)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet persons = db.queryByExample(new Author(authorCode, null, null));
            return persons.hasNext();
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public boolean artworkExits(int artworkCode)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet artworks = db.queryByExample(new Artwork(artworkCode));
            return artworks.hasNext();
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public List<Artwork> listArtworksByAuthor(String authorCode)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet artworks = db.queryByExample(new Artwork(null, null, null, authorCode));
            List<Artwork> artworkList = new ArrayList<>();

            while(artworks.hasNext())
            {
                artworkList.add((Artwork) artworks.next());
            }
            return artworkList;
        }
        finally {
            db.commit();
            db.close();
        }
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
            db.commit();
            db.close();
        }
    }

    public List<Artwork> listArtworksByStyle(Styles style)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet artworks = db.queryByExample(new Artwork(null, null, style, null));
            List<Artwork> artworkList = new ArrayList<>();
            while(artworks.hasNext())
            {
                artworkList.add((Artwork) artworks.next());
            }
            return artworkList;
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public List<Artwork> listArtworksByPaintingType(PaintingTypes paintingType)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet paintings = db.queryByExample(new Painting(paintingType, null));
            List<Artwork> paintingsList = new ArrayList<>();
            while(paintings.hasNext())
            {
                paintingsList.add((Painting) paintings.next());
            }
            return paintingsList;
        }
        finally {
            db.commit();
            db.close();
        }
    }

    public List<Artwork> listArtworksByMaterialType(MaterialTypes materialType)
    {
        try {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), databaseName);
            ObjectSet sculptures = db.queryByExample(new Sculpture(materialType));
            List<Artwork> sculptureList = new ArrayList<>();
            while(sculptures.hasNext())
            {
                sculptureList.add((Sculpture) sculptures.next());
            }
            return sculptureList;
        }
        finally {
            db.commit();
            db.close();
        }
    }
}

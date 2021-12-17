package com.company;


import com.db4o.Db4o;
import com.db4o.ObjectContainer;

//store en lugar de set, y retrieve en lugar del get
public class Main {
    public static void main(String[] args) {
        ObjectContainer db = Db4o.openFile("persona.dat");
        db.close();
    }
}

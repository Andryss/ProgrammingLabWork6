package Server;

import MovieObjects.Movie;

import java.util.Hashtable;

public class ServerINFO implements Cloneable {

    private Hashtable<Integer,Movie> collection;

    private String collectionFilename;

    public void setCollection(Hashtable<Integer, Movie> collection) {
        this.collection = collection;
    }

    public void setCollectionFilename(String collectionFilename) {
        this.collectionFilename = collectionFilename;
    }

    public Hashtable<Integer, Movie> getCollection() {
        return collection;
    }

    public String getCollectionFilename() {
        return collectionFilename;
    }

    public ServerINFO clone() {
        ServerINFO clone = new ServerINFO();
        clone.setCollection((Hashtable<Integer, Movie>) collection.clone());
        clone.setCollectionFilename(collectionFilename);
        return clone;
    }
}

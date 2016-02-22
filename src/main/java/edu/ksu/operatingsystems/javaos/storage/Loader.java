package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/22/16.
 */
public interface Loader {

    boolean load(String fileName);
    DefaultDisk getDefaultDisk();

}

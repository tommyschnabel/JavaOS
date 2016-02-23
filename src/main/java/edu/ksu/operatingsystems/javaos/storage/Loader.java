package edu.ksu.operatingsystems.javaos.storage;

public interface Loader {

    boolean load(String fileName);
    Disk getDefaultDisk();

}

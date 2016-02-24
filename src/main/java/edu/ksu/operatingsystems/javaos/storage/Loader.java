package edu.ksu.operatingsystems.javaos.storage;

public interface Loader {

    /**
     * Loads hex values into the disk
     * @param fileName the file that we are adding information from
     * @return returns a boolean on whether or not the process was successful
     */
    boolean load(String fileName);

    /**
     * Fetches the disk that we will be loading data into.
     * @return The disk we will load data into
     */
    Disk getDefaultDisk();

}

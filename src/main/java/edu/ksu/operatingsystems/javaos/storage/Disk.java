package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/22/16.
 */
public interface Disk {

    char [] getDisk();
    void addToDisk(String s);
    void addProgramToProgramList(DefaultProgram defaultProgram);
    void displayDisk();  //This method is just used so I can visualize the DefaultDisk
    DefaultProgram findProgram(int programID);
    int getCurrentPositionOnDisk();

}

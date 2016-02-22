package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/22/16.
 */
public interface Ram {

    void addProgramToMemoryByProcessID(int processID, DefaultDisk defaultDisk);
    void addProgramToPCBList(DefaultProgram defaultProgram);
    void displayRAM(); //Provides ability to visualize Ram
    char [] getDisk();
    DefaultProgram[] getProcessArray();
    DefaultProgram getProcessByID(int ID);
    int getCurrentPositionInMemory();
}

package edu.ksu.operatingsystems.javaos.storage;

public interface Ram {

    void addProgramToMemoryByProcessID(int processID, Disk defaultDisk);
    void addProgramToPCBList(Program program);
    void displayRAM(); //Provides ability to visualize Ram
    char [] getDisk();
    Program[] getProcessArray();
    Program getProcessByID(int ID);
    int getCurrentPositionInMemory();
}

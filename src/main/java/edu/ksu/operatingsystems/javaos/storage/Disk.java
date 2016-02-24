package edu.ksu.operatingsystems.javaos.storage;

public interface Disk {

    char [] getDisk();
    void addToDisk(String s);
    void addProgramToProgramList(ProcessControlBlock processControlBlock);
    void displayDisk();  //This method is just used so I can visualize the DefaultDisk
    ProcessControlBlock findProgram(int programID);
    int getCurrentPositionOnDisk();

}

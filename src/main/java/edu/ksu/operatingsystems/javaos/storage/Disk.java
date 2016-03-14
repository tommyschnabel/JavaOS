package edu.ksu.operatingsystems.javaos.storage;

public interface Disk {

    /**
     * Fetches the contents of the disk
     * @return the char array that makes up the disk
     */
    char [] getDisk();

    /**
     * Adds data onto the Disk
     * @param s a string of hex values that will be added to the disk
     */
    void addToDisk(String s);

    /**
     * Adds a process to the list of processes currently on the disk
     * @param processControlBlock The process that will be added to the process list
     */
    void addProgramToProgramList(ProcessControlBlock processControlBlock);

    /**
     * Displays the contents of the disk
     */
    void displayDisk();  //This method is just used so I can visualize the DefaultDisk

    /**
     * Fetches a process with the specified ID
     * @param programID The ID of the process we want
     * @return a process with the ID specified
     */
    ProcessControlBlock findProgram(int programID);

    /**
     * I am currently finding out where I should add new items onto the disk by an iterator.
     * ***This will need to be changed out later by a smarter way to find blank spaces.
     * @return current location of the iterator on the disk
     */
    int getCurrentPositionOnDisk();

    /**
     * Gets an array of all the processes on disk
     * @return The process array
     */
    ProcessControlBlock[] getProcesses();

}

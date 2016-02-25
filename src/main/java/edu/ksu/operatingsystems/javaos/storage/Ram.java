package edu.ksu.operatingsystems.javaos.storage;

public interface Ram {

    /**
     * Adds instruction and data set to memory
     * @param processID The ID of the process we are adding
     * @param defaultDisk The Disk we are adding the process from
     */
    void addProcessControlBlockToMemoryByProcessID(int processID, Disk defaultDisk);

    /**
     * Adds the Process to the PCB List in Ram
     * @param processControlBlock The process we are adding to the list
     */
    void addProcessControlBlockToPCBList(ProcessControlBlock processControlBlock);


    /**
     * Displays items inside of memory
     */
    void displayRAM();

    /**
     * Fetches the disk's char array to the user
     * @return returns the disk's char array
     */

    /**
     * Displays process list in memory
     */
    void displayPCBList();

    char [] getDisk();

    /**
     * Fetches the array of PCBs on Ram
     * @return an array of PCBs
     */
    ProcessControlBlock[] getProcessArray();

    /**
     * Fetches the process by ID specified
     * @param ID the ID of the process we want to fetch
     * @return the PCB of the process with the specified ID
     */
    ProcessControlBlock getProcessByID(int ID);
    int getCurrentPositionInMemory();

    /**
     * Writes a value to an address
     * @param address The address to write to
     * @param value The value to write
     */
    void writeValueToAddress(Integer address, Integer value);

    /**
     * Reads a value from an address
     * @param address The address to read from
     * @return The value stored in the address
     */
    Integer readValueFromAddress(Integer address);

    /**
     * Removes a process from the RAM with the specified ID
     * @param ID the ID of the process we wish to remove
     */
    void removeProcessFromMemory(Integer ID);

    /**
     * Finds a spot in Memory where the Process specified will fit
     * @param PCB The Process that we need to find a spot for
     * @return an int of the start location where the process will fit
     */
    int findSpotForProcess(ProcessControlBlock PCB);

    void defrag();


}

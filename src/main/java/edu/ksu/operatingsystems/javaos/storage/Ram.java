package edu.ksu.operatingsystems.javaos.storage;

public interface Ram {

    void addProgramToMemoryByProcessID(int processID, Disk defaultDisk);
    void addProgramToPCBList(ProcessControlBlock processControlBlock);
    void displayRAM(); //Provides ability to visualize Ram
    char [] getDisk();
    ProcessControlBlock[] getProcessArray();
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
}

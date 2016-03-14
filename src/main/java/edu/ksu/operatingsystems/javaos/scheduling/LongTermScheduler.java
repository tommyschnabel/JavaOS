package edu.ksu.operatingsystems.javaos.scheduling;

public interface LongTermScheduler {

    /**
     * Loads the process into the first found space in RAM ( currently )
     * @param processID The ID of the process to be added to RAM
     */
    void loadProcessInMemory(int processID);

    void scheduleIfNecessary();
}

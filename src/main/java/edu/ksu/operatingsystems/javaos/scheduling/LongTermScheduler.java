package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface LongTermScheduler {

    /**
     * Loads the process into the first found space in RAM ( currently )
     * @param processID The ID of the process to be added to RAM
     */
    void loadProcessInMemory(int processID);

    void scheduleIfNecessary();
}

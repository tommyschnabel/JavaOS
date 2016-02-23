package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface LongTermScheduler {

    void loadProcessInMemory(int processID, Disk disk, Ram ram);
}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.*;

public class LongTermScheduler {

    public void loadProcessInMemory(int processID, Disk disk, Ram ram)
    {
        ram.addProgramToMemoryByProcessID(processID, disk);
    }

}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.*;

public class DefaultLongTermScheduler implements LongTermScheduler {

    public void loadProcessInMemory(int processID, DefaultDisk defaultDisk, DefaultRam defaultRAM)
    {
        defaultRAM.addProgramToMemoryByProcessID(processID, defaultDisk);
    }

}

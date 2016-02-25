package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class FIFOLongTermScheduler implements LongTermScheduler {

    @Override
    public void loadProcessInMemory(int processID, Disk disk, Ram ram) {
        ram.addProcessControlBlockToMemoryByProcessID(processID, disk);
        ram.addProcessControlBlockToPCBList(disk.findProgram(processID));
    }
}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class FIFOLongTermScheduler implements LongTermScheduler {

    private Disk disk;
    private Ram ram;

    public FIFOLongTermScheduler(Disk disk, Ram ram) {
        this.disk = disk;
        this.ram = ram;
    }

    @Override
    public void loadProcessInMemory(int processID) {
        ram.addProcessControlBlockToMemoryByProcessID(processID, disk);
    }

    @Override
    public void scheduleIfNecessary() {
        for (ProcessControlBlock pcb : disk.getProcesses()) {
            if (!pcb.inMemory() && ram.isRoomForProcess(pcb)) {
                ram.addProcessControlBlockToMemoryByProcessID(pcb.getID(), disk);
            }
        }
    }

}

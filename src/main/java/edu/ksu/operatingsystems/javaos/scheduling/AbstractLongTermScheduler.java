package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public abstract class AbstractLongTermScheduler implements LongTermScheduler {

    protected Disk disk;
    protected Ram ram;

    public AbstractLongTermScheduler(Disk disk, Ram ram) {
        this.disk = disk;
        this.ram = ram;
    }

    @Override
    public void loadProcessInMemory(int processID) {
        ram.addProcessControlBlockToMemoryByProcessID(processID, disk);
    }

    @Override
    public abstract void scheduleIfNecessary();

    @Override
    public boolean allProcessesFinished() {
        for (ProcessControlBlock pcb : disk.getProcesses()) {
            if (!pcb.isFinished()) {
                return false;
            }
        }

        return true;
    }
}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class FIFOLongTermScheduler extends AbstractLongTermScheduler {

    private boolean startedWaiting = false;

    public FIFOLongTermScheduler(Disk disk, Ram ram) {
        super(disk, ram);
    }

    @Override
    public void scheduleIfNecessary() {
        if (!startedWaiting) {
            startedWaiting = true;
            for (ProcessControlBlock pcb : disk.getProcesses()) {
                pcb.addedToWaitQueue();
            }
        }

        for (ProcessControlBlock pcb : disk.getProcesses()) {
            if (!pcb.inMemory() && !pcb.isFinished() && ram.isRoomForProcess(pcb)) {
                //System.out.println("Adding process " + pcb.getID() + " to queue from Long Term Scheduler");
                loadProcessInMemory(pcb.getID());
            }
        }
    }

}

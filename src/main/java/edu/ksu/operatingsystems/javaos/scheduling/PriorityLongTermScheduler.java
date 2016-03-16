package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.ArrayList;
import java.util.List;

public class PriorityLongTermScheduler extends AbstractLongTermScheduler {

    private Integer currentPriorityAllowed = 1;
    private List<ProcessControlBlock> waitQueue;

    public PriorityLongTermScheduler(Disk disk, Ram ram) {
        super(disk, ram);
        waitQueue = new ArrayList<ProcessControlBlock>();
    }

    @Override
    public void scheduleIfNecessary() {
        if (waitQueue.isEmpty() && isOneOrMoreProcessesWaiting()) {
            populateReadyQueue();
        }

        incrementPriorityIfAllowed();

        int numberAdded = 0;
        for (ProcessControlBlock pcb : waitQueue) {
            if (pcb.getPriority() > currentPriorityAllowed) {
                break; //Got to the next priority
            }

            if (ram.isRoomForProcess(pcb)) {
                ram.addProcessControlBlockToMemoryByProcessID(pcb.getID(), disk);
                numberAdded++;
            }
        }

        for (int i = numberAdded; i > 0; --i) {
            waitQueue.remove(0);
        }
    }

    private void incrementPriorityIfAllowed() {
        for (ProcessControlBlock pcb : disk.getProcesses()) {
            if (isProcessWaiting(pcb) && pcb.getPriority() == currentPriorityAllowed) {
                return;
            }
        }

        currentPriorityAllowed++;
    }

    @SuppressWarnings("ConstantConditions")
    private void populateReadyQueue() {
        Integer highestPriority = null;
        ProcessControlBlock[] processes = disk.getProcesses();

        if (processes.length == 0) {
            throw new RuntimeException("Processes not loaded yet");
        }

        //Get the highest priority
        for (ProcessControlBlock pcb : processes) {
            if (highestPriority == null) {
                highestPriority = pcb.getPriority();
                continue;
            }

            if (pcb.getPriority() < highestPriority) {
                highestPriority = pcb.getPriority();
            }
        }

        //Populate the wait queue
        while (waitQueue.size() < processes.length) {
            for (ProcessControlBlock pcb : processes) {
                if (pcb.getPriority() == highestPriority) {
                    waitQueue.add(pcb);
                    pcb.addedToWaitQueue();
                }
            }

            highestPriority++;
        }
    }

    private boolean isOneOrMoreProcessesWaiting() {
        for (ProcessControlBlock pcb : disk.getProcesses()) {

            if (isProcessWaiting(pcb)) {
                return true;
            }
        }

        return false;
    }

    private boolean isProcessWaiting(ProcessControlBlock pcb) {
        return !pcb.isFinished() && !pcb.inMemory();
    }
}

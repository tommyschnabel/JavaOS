package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.ArrayList;
import java.util.List;

public class SJFLongTermScheduler extends AbstractLongTermScheduler {

    private List<ProcessControlBlock> waitQueue;

    public SJFLongTermScheduler(Disk disk, Ram ram) {
        super(disk, ram);
        waitQueue = new ArrayList<ProcessControlBlock>();
    }

    @Override
    public void scheduleIfNecessary() {
        if (waitQueue.isEmpty() && isOneOrMoreProcessesWaiting()) {
            populateReadyQueue();
        }

        int numberAdded = 0;
        for (ProcessControlBlock pcb : waitQueue) {
            if (ram.isRoomForProcess(pcb)) {
                ram.addProcessControlBlockToMemoryByProcessID(pcb.getID(), disk);
                numberAdded++;
            }
        }

        for (int i = numberAdded; i > 0; --i) {
            waitQueue.remove(0);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void populateReadyQueue() {
        ProcessControlBlock[] processes = disk.getProcesses();

        if (processes.length == 0) {
            throw new RuntimeException("Processes not loaded yet");
        }

        for (ProcessControlBlock pcb : processes) {
            for(int i = 0; i < waitQueue.size(); i++){
                if(waitQueue.isEmpty())
                    waitQueue.add(pcb);
                else if(waitQueue.get(i).getInstructionSize() >= pcb.getInstructionSize()
                        && !waitQueue.contains(pcb))
                    waitQueue.add(i,pcb);

            }
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
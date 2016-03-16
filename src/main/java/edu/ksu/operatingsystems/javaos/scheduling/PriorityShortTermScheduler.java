package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class PriorityShortTermScheduler extends AbstractShortTermScheduler {

    private Integer currentPriorityAllowed = 1;

    public PriorityShortTermScheduler(Ram ram, Dispatcher dispatcher) {
        super(ram, dispatcher);
    }

    @Override
    public void schedule(ProcessControlBlock[] pcbArray) {

        for (ProcessControlBlock pcb : ram.getProcesses()) {
            if (pcb != null
                    && pcb.getPriority() <= currentPriorityAllowed
                    && pcb.inMemory()
                    && !readyQueue.contains(pcb)
                    && !pcb.equals(currentProcess)) {

                addToReadyQueue(pcb);
            }
        }

        incrementPriorityIfAllowed();
    }

    private void incrementPriorityIfAllowed() {
        for (ProcessControlBlock pcb : ram.getProcesses()) {
            if (pcb != null
                    && pcb.inMemory()
                    && readyQueue.contains(pcb)
                    && pcb.getPriority() == currentPriorityAllowed) {
                return;
            }
        }

        currentPriorityAllowed++;
    }
}

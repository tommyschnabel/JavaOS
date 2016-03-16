package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.List;

public class PriorityShortTermScheduler extends AbstractShortTermScheduler {

    private Integer currentPriorityAllowed = 1;

    public PriorityShortTermScheduler(Ram ram, Dispatcher dispatcher, Cpu[] cpus) {
        super(ram, dispatcher, cpus);
    }

    @Override
    public void schedule(ProcessControlBlock[] pcbArray, List<ProcessControlBlock> currentProcesses) {

        for (ProcessControlBlock pcb : ram.getProcesses()) {
            if (pcb != null
                    && pcb.getPriority() <= currentPriorityAllowed
                    && pcb.inMemory()
                    && !readyQueue.contains(pcb)
                    && !currentProcesses.contains(pcb)) {

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

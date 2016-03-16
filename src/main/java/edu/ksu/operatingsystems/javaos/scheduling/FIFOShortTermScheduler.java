package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class FIFOShortTermScheduler extends AbstractShortTermScheduler {

    public FIFOShortTermScheduler(Ram ram, Dispatcher dispatcher) {
        super(ram, dispatcher);
        this.ram = ram;
        this.dispatcher = dispatcher;
    }

    @Override
    public void schedule(ProcessControlBlock[] pcbArray) {
        for (ProcessControlBlock pcb : pcbArray) {
            if (pcb != null
                    && !pcb.isFinished()
                    && pcb.inMemory()
                    && !readyQueue.contains(pcb)
                    && !pcb.equals(currentProcess)) {

                addToReadyQueue(pcb);
            }
        }
    }

}

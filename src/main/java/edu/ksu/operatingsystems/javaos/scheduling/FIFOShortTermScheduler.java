package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.List;

public class FIFOShortTermScheduler extends AbstractShortTermScheduler {

    public FIFOShortTermScheduler(Ram ram, Dispatcher dispatcher, Cpu[] cpus) {
        super(ram, dispatcher, cpus);
        this.ram = ram;
        this.dispatcher = dispatcher;
    }

    @Override
    public void schedule(ProcessControlBlock[] pcbArray, List<ProcessControlBlock> currentProcesses) {
        for (ProcessControlBlock pcb : pcbArray) {
            if (pcb != null
                    && !pcb.isFinished()
                    && pcb.inMemory()
                    && !readyQueue.contains(pcb)
                    && !currentProcesses.contains(pcb)) {

                addToReadyQueue(pcb);
            }
        }
    }

}

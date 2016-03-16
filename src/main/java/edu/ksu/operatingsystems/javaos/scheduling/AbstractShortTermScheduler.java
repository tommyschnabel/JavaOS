package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.LinkedList;

public abstract class AbstractShortTermScheduler implements ShortTermScheduler {

    protected LinkedList<ProcessControlBlock> readyQueue = new LinkedList<ProcessControlBlock>();

    protected Dispatcher dispatcher;
    protected Ram ram;

    protected ProcessControlBlock currentProcess;

    public AbstractShortTermScheduler(Ram ram, Dispatcher dispatcher) {
        this.ram = ram;
        this.dispatcher = dispatcher;
    }

    @Override
    public abstract void schedule(ProcessControlBlock[] pcbArray);

    @Override
    public void cleanupFinishedProcesses() {
        ProcessControlBlock[] processes = ram.getProcesses();
        for (int i = processes.length - 1; i >= 0; --i) {
            ProcessControlBlock process = processes[i];

            //Remove from memory if finished
            if (process != null && process.isFinished()) {
                System.out.println(
                        String.format(
                                "Process %s is finished, removing from memory. Finish value: %s",
                                process.getID(),
                                ram.readValueFromAddress(process.getOutputBufferLocation(), process.getOutputBufferLength(), process)
                        )
                );

                ram.removeProcessFromMemory(process.getID());
            }
        }
    }

    @Override
    public void scheduleIfNecessary() {
        cleanupFinishedProcesses();
        schedule(ram.getProcesses());

        if (currentProcess == null || currentProcess.isFinished()) {
            currentProcess = dispatcher.dispatchProcessToCPU(readyQueue);
            currentProcess.executionStarted();
        }
    }

    @Override
    public void addToReadyQueue(ProcessControlBlock pcb){
        readyQueue.add(pcb);
        pcb.addedToReadyQueue();
    }
}

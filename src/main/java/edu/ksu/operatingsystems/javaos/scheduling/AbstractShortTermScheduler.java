package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractShortTermScheduler implements ShortTermScheduler {

    protected LinkedList<ProcessControlBlock> readyQueue = new LinkedList<ProcessControlBlock>();

    protected Dispatcher dispatcher;
    protected Ram ram;
    protected Cpu[] cpus;

    public AbstractShortTermScheduler(Ram ram, Dispatcher dispatcher, Cpu[] cpus) {
        this.ram = ram;
        this.dispatcher = dispatcher;
        this.cpus = cpus;
    }

    @Override
    public void cleanupFinishedProcesses() {
        ProcessControlBlock[] processes = ram.getProcesses();
        for (int i = processes.length - 1; i >= 0; --i) {
            ProcessControlBlock process = processes[i];

            //Remove from memory if finished
            if (process != null && process.isFinished()) {
//                System.out.println(
//                        String.format(
//                                "Process %s is finished, removing from memory. Finish value: %s",
//                                process.getID(),
//                                ram.readValueFromAddress(process.getOutputBufferLocation(), process.getOutputBufferLength(), process)
//                        )
//                );

                ram.removeProcessFromMemory(process.getID());
            }
        }
    }

    @Override
    public void scheduleIfNecessary() {
        cleanupFinishedProcesses();

        List<ProcessControlBlock> currentProcesses = new ArrayList<ProcessControlBlock>();
        for (Cpu cpu : cpus) {
            currentProcesses.add(cpu.getCurrentProcess());
        }

        schedule(ram.getProcesses(), currentProcesses);

        for (Cpu cpu : cpus) {
            ProcessControlBlock currentProcess = cpu.getCurrentProcess();

            if ((currentProcess == null || currentProcess.isFinished()) && !readyQueue.isEmpty()) {
                currentProcess = dispatcher.dispatchProcessToCPU(readyQueue, cpu);

                currentProcess.executionStarted();
                cpu.setCurrentProcess(currentProcess);
            }

            if (currentProcess != null && currentProcess.isFinished()) {
                cpu.setCurrentProcess(null);
            }
        }
    }

    @Override
    public void addToReadyQueue(ProcessControlBlock pcb){
        readyQueue.add(pcb);
        pcb.addedToReadyQueue();
    }
}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

import java.util.LinkedList;

public interface Dispatcher {
    
    /**
     * Sends a PCB to the CPU through the fetcher
     * @param readyQueue The readyQueue from the ShortTermScheduler
     */
    ProcessControlBlock dispatchProcessToCPU(LinkedList<ProcessControlBlock> readyQueue, Cpu cpu);
    
    /**
     * Preforms a contextSwitch on the process in the CPU
     * @param offCPU The process coming off the CPU
     * @param readyQueue The readyQueue from the ShortTermScheduler
     */
    void contextSwitch(
            ProcessControlBlock offCPU,
            LinkedList<ProcessControlBlock> readyQueue,
            Cpu cpu
    );
}

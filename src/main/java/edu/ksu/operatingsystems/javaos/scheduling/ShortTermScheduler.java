package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

import java.util.List;

public interface ShortTermScheduler {
    
    /**
     * Schedules all processes in need of scheduling
     * @param pcbArray The array of PCBs in the RAM
     */
    void schedule(ProcessControlBlock[] pcbArray, List<ProcessControlBlock> currentProcesses);
    
    /**
     * Adds a pcb to the ready queue
     * @param pcb The pcb to be added to the readyQueue
     */
    void addToReadyQueue(ProcessControlBlock pcb);

    /**
     * Runs regular scheduling cycle. Checks to see if the current process
     * is finished, and removes it from memory. Also adds processes in memory
     * to the ready queue if they are not already there
     */
    void scheduleIfNecessary();

    /**
     * Removes any finished processes from memory
     */
    void cleanupFinishedProcesses();
}

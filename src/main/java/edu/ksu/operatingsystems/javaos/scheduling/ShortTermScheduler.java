package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface ShortTermScheduler {
    
    /**
     * Schedules all processes in need of scheduling
     * @param pcbArray The array of PCBs in the RAM
     */
    void schedule(ProcessControlBlock[] pcbArray);
    
    /**
     * Adds a pcb to the ready queue
     * @param pcb The pcb to be added to the readyQueue
     */
    public void addToReadyQueue(ProcessControlBlock pcb);
    
    /**
     * Adds instruction and data set to memory
     * @param pcb The pcb to be added to the waitQueue
     */
    public void addToWaitQueue(ProcessControlBlock pcb);
    
    
    /**
     * Finds the processes that need to be put in the ready queue
     * @param ram The ram from which we will grab the processes
     * @return 
     */
    public ProcessControlBlock[] getProcesses(Ram ram);

    void scheduleIfNecessary();
}

package edu.ksu.operatingsystems.javaos.scheduling;

import java.util.*;
import edu.ksu.operatingsystems.javaos.storage.*;

public interface Dispatcher {
    
    /**
     * Sends a PCB to the CPU through the fetcher
     * @param readyQueue The readyQueue from the ShortTermScheduler
     */
    public ProcessControlBlock dispatchProcessToCPU(LinkedList<ProcessControlBlock> readyQueue);
    
    /**
     * Preforms a contextSwitch on the process in the CPU
     * @param offCPU The process coming off the CPU
     * @param readyQueue The readyQueue from the ShortTermScheduler
     */
    public void contextSwitch(ProcessControlBlock offCPU, LinkedList<ProcessControlBlock> readyQueue);
}

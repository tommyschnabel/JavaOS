package edu.ksu.operatingsystems.javaos.scheduling;

import java.util.LinkedList;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

public class DefaultDispatcher implements Dispatcher{
    
    /*@Override
    */
    public ProcessControlBlock dispatchProcessToCPU(LinkedList<ProcessControlBlock> readyQueue){
        ProcessControlBlock sendProcess;
        sendProcess = readyQueue.getFirst();
        readyQueue.pop();
        return sendProcess;
    }
    
    /*@Override
    */
    public void contextSwitch(ProcessControlBlock offCPU, LinkedList<ProcessControlBlock> readyQueue){
        readyQueue.add(offCPU);
        dispatchProcessToCPU(readyQueue);
    }
}

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.*;
import java.util.*;

public class DefaultShortTermScheduler implements ShortTermScheduler {
    
    private LinkedList<ProcessControlBlock> newQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> suspendQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> readyQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> waitQueue = new LinkedList<>();
    
    /*@Override
    */
    public void schedule(ProcessControlBlock[] pcbArray){
        for(int i = 0; i < pcbArray.length; i++){
            if(pcbArray[i] != null)
                addToReadyQueue(pcbArray[i]);
        }
       
    }
    
    /*@Override
    */
    public void addToReadyQueue(ProcessControlBlock pcb){
        readyQueue.add(pcb);
        
       /* for(int i = 0; i < readyQueue.size(); i++)
        {
            if(pcb.getPriority() < readyQueue.get(i).getPriority()){
                readyQueue.add(i,pcb);
                return;
            }
        } */ //This is for use when we do priority based scheduling.
    }
    
    /*@Override
    */
    public void addToWaitQueue(ProcessControlBlock pcb){
        waitQueue.add(pcb);
    }

    @Override
    public ProcessControlBlock[] getProcesses(Ram ram) {
        return new ProcessControlBlock[0];
    }

    @Override
    public void scheduleIfNecessary() {

    }

    public LinkedList<ProcessControlBlock> getNewQueue(){
        return newQueue;
    }
    
    public LinkedList<ProcessControlBlock> getSuspendQueue(){
        return suspendQueue;
    }
    
    public LinkedList<ProcessControlBlock> getReadyQueue(){
        return readyQueue;
    }
    
    public LinkedList<ProcessControlBlock> getWaitQueue(){
        return waitQueue;
    }
    
    public void setNewQueue(LinkedList<ProcessControlBlock> newQueue){
        this.newQueue = newQueue;
    }
    
    public void setSuspendQueue(LinkedList<ProcessControlBlock> newSuspendQueue){
        suspendQueue = newSuspendQueue;
    }
    
    public void setReadyQueue(LinkedList<ProcessControlBlock> newReadyQueue){
        readyQueue = newReadyQueue;
    }
    
    public void setWaitQueue(LinkedList<ProcessControlBlock> newWaitQueue){
        waitQueue = newWaitQueue;
    }
    
    ProcessControlBlock copyPCBAtIndex(int i, LinkedList<ProcessControlBlock> List){
        ProcessControlBlock copy = List.get(i);
        List.remove(i);
        return copy;
    }
}

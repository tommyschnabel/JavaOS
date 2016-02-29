/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.*;
import java.util.*;

/**
 *
 * @author DefaultPC
 */
public class DefaultShortTermScheduler implements ShortTermScheduler {
    
    private LinkedList<ProcessControlBlock> newQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> suspendQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> readyQueue = new LinkedList<>();
    private LinkedList<ProcessControlBlock> waitQueue = new LinkedList<>();
    private ProcessControlBlock processToAdd; //For later use if we decide to use SJF or another scheduling system.
    
    /*@Override
    */
    public void schedule(ProcessControlBlock[] pcbArray){
        for(int i = 0; i < pcbArray.length; i++){
            addToReadyQueue(pcbArray[i]);
        }
       
    }
    
    /*@Override
    */
    public void addToReadyQueue(ProcessControlBlock pcb){
        readyQueue.add(pcb);
    }
    
    /*@Override
    */
    public void addToWaitQueue(ProcessControlBlock pcb){
        waitQueue.add(pcb);
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
    
}

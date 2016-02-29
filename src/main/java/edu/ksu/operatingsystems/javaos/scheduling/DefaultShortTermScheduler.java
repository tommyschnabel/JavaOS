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
    
    LinkedList<ProcessControlBlock> newQueue = new LinkedList<>();
    LinkedList<ProcessControlBlock> suspendQueue = new LinkedList<>();
    LinkedList<ProcessControlBlock> readyQueue = new LinkedList<>();
    LinkedList<ProcessControlBlock> waitQueue = new LinkedList<>();
    ProcessControlBlock processToAdd; //For later use if we decide to use SJF or another scheduling system.
    
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
    
    
}

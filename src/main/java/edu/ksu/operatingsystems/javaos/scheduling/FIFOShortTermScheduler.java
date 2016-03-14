package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.LinkedList;

public class FIFOShortTermScheduler implements ShortTermScheduler {
    
    private LinkedList<ProcessControlBlock> newQueue = new LinkedList<ProcessControlBlock>();
    private LinkedList<ProcessControlBlock> suspendQueue = new LinkedList<ProcessControlBlock>();
    private LinkedList<ProcessControlBlock> readyQueue = new LinkedList<ProcessControlBlock>();
    private LinkedList<ProcessControlBlock> waitQueue = new LinkedList<ProcessControlBlock>();

    private Dispatcher dispatcher;
    private Ram ram;

    private ProcessControlBlock currentProcess;

    public FIFOShortTermScheduler(Ram ram, Cpu cpu) {
        this.ram = ram;
        this.dispatcher = new DefaultDispatcher(cpu);
    }
    
    @Override
    public void schedule(ProcessControlBlock[] pcbArray){
        for (ProcessControlBlock pcb : pcbArray) {
            if (pcb != null && !readyQueue.contains(pcb)) {
                addToReadyQueue(pcb);
            }
        }
    }
    
    @Override
    public void addToReadyQueue(ProcessControlBlock pcb){
        readyQueue.add(pcb);
    }
    
    @Override
    public void addToWaitQueue(ProcessControlBlock pcb){
        waitQueue.add(pcb);
    }

    @Override
    public ProcessControlBlock[] getProcesses(Ram ram) {
        return new ProcessControlBlock[0];
    }

    @Override
    public void scheduleIfNecessary() {
        ProcessControlBlock[] processes = ram.getProcesses();
        for (int i = processes.length - 1; i >= 0; --i) {
            ProcessControlBlock process = processes[i];
            if (process != null && process.isFinished()) {
                ram.removeProcessFromMemory(process.getID());
            }
        }

        schedule(ram.getProcesses());

        if (currentProcess == null || currentProcess.isFinished()) {
            dispatcher.dispatchProcessToCPU(readyQueue);
        }
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

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

import java.util.LinkedList;

public class DefaultDispatcher implements Dispatcher {

    private Cpu cpu;

    public DefaultDispatcher(Cpu cpu) {
        this.cpu = cpu;
    }

    @Override
    public ProcessControlBlock dispatchProcessToCPU(LinkedList<ProcessControlBlock> readyQueue) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        ProcessControlBlock sendProcess = readyQueue.pop();

        loadPCBStateIntoRegisters(sendProcess);
        cpu.setCurrentProcess(sendProcess);

        return sendProcess;
    }
    
    @Override
    public void contextSwitch(ProcessControlBlock offCPU, LinkedList<ProcessControlBlock> readyQueue) {
        loadRegisterStateIntoPCB(offCPU);
        readyQueue.add(offCPU);

        dispatchProcessToCPU(readyQueue);
    }

    private void loadPCBStateIntoRegisters(ProcessControlBlock pcb) {
        if (pcb.getProcessState() == null) {
            return;
        }

        Integer[] registers = cpu.getRegisters();

        for (int i = 0; i < registers.length - 1; i++) {
            registers[i] = pcb.getProcessState()[i];
        }
    }

    private void loadRegisterStateIntoPCB(ProcessControlBlock pcb) {
        Integer[] registers = cpu.getRegisters();

        if (registers == null) {
            return;
        }

        Integer[] processState;

        if (pcb.getProcessState() == null) {
            processState = new Integer[registers.length];
            pcb.setProcessState(processState);
        } else {
            processState = pcb.getProcessState();
        }

        for (int i = 0; i < registers.length - 1; i++) {
            processState[i] = registers[i];
        }
    }
}

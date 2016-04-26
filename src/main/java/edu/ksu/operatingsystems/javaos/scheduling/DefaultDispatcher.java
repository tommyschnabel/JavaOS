package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

import java.util.LinkedList;

public class DefaultDispatcher implements Dispatcher {

    @Override
    public ProcessControlBlock dispatchProcessToCPU(LinkedList<ProcessControlBlock> readyQueue, Cpu cpu) {
        if (readyQueue.isEmpty()) {
            return null;
        }

        ProcessControlBlock sendProcess = readyQueue.pop();

        loadPCBStateIntoRegisters(sendProcess, cpu);
        cpu.setCurrentProcess(sendProcess);
//        System.out.println("Executing process " + sendProcess.getID());

        return sendProcess;
    }
    
    @Override
    public void contextSwitch(ProcessControlBlock offCPU, LinkedList<ProcessControlBlock> readyQueue, Cpu cpu) {
        loadRegisterStateIntoPCB(offCPU, cpu);
        readyQueue.add(offCPU);

        dispatchProcessToCPU(readyQueue, cpu);
    }

    private void loadPCBStateIntoRegisters(ProcessControlBlock pcb, Cpu cpu) {
        if (pcb.getProcessState() == null) {
            return;
        }

        long[] registers = cpu.getRegisters();

        for (int i = 0; i < registers.length - 1; i++) {
            registers[i] = pcb.getProcessState()[i];
        }
    }

    private void loadRegisterStateIntoPCB(ProcessControlBlock pcb, Cpu cpu) {
        long[] registers = cpu.getRegisters();
        Long[] processState;

        if (pcb.getProcessState() == null) {
            processState = new Long[registers.length];
            pcb.setProcessState(processState);
        } else {
            processState = pcb.getProcessState();
        }

        for (int i = 0; i < registers.length - 1; i++) {
            processState[i] = registers[i];
        }
    }
}

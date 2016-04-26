package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

import java.util.List;

public class SJFShortTermScheduler extends AbstractShortTermScheduler {

    public SJFShortTermScheduler(Ram ram, Dispatcher dispatcher, Cpu[] cpus) {
        super(ram, dispatcher, cpus);
    }

    @Override
    public void schedule(ProcessControlBlock[] pcbArray, List<ProcessControlBlock> currentProcesses) {

        for (ProcessControlBlock pcb : ram.getProcesses()) {
            if (pcb != null
                    && pcb.inMemory()
                    && !readyQueue.contains(pcb)
                    && !currentProcesses.contains(pcb)) {

                for(int i = 0; i < readyQueue.size(); i++){
                    if(readyQueue.isEmpty())
                        addToReadyQueue(pcb);
                    else if(readyQueue.get(i).getInstructionSize() >= pcb.getInstructionSize()
                            && !readyQueue.contains(pcb))
                        readyQueue.add(i,pcb);
                }
            }
        }
    }

}
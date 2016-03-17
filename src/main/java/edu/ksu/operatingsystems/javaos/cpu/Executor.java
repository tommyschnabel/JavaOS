package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

public interface Executor {

    /**
     * Executes the instruction that is loaded in the registers.
     * @param instruction The instruction to execute (each is 32 bits long)
     */
    void execute(String instruction);

    /**
     * Sets the process that's currently executing
     * @param process The process
     */
    void setProcess(ProcessControlBlock process);

    CpuCache getCpuCache();
}

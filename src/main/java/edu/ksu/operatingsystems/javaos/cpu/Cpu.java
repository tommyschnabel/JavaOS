package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

public interface Cpu {

    /**
     * Run one cpu cycle with whatever is loaded in the cpu
     */
    void run();

    /**
     * @return The cpu's registers
     */
    long[] getRegisters();

    /**
     * Set the process that is loaded into the registers
     * @param currentProcess The process
     */
    void setCurrentProcess(ProcessControlBlock currentProcess);

    /**
     * Gets the PCB for the process that's currently running
     * @return The current process
     */
    ProcessControlBlock getCurrentProcess();
}

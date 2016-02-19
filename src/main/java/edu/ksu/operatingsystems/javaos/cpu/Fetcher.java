package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.common.ProcessControlBlock;

public interface Fetcher {

    /**
     * Fetches the current instruction from memory.
     * Also responsible for incrementing the current instruction.
     * @param pcb The {@link ProcessControlBlock} for the process that's currently running
     * @return The Byte array with the instruction
     */
    Byte[] fetch(ProcessControlBlock pcb);
}

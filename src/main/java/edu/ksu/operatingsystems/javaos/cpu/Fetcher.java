package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

public interface Fetcher {

    /**
     * Fetches the current instruction from memory.
     * Also responsible for incrementing the current instruction.
     * @param processControlBlock The {@link edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock} that's currently running
     * @return The Byte array with the instruction
     */
    Byte[] fetch(ProcessControlBlock processControlBlock);
}

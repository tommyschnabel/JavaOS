package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface Fetcher {

    /**
     * Fetches the current instruction from memory.
     * Also responsible for incrementing the current instruction.
     * @param processControlBlock The {@link edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock} that's currently running
     * @return The Byte array with the instruction
     */
    byte[] fetch(ProcessControlBlock processControlBlock, CpuCache cache);
}

package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface Executor {

    /**
     * Executes the instruction that is loaded in the registers.
     * @param registers The registers, pre-loaded with the instruction to execute
     * @param ram The defaultRAM memory, since there may be operations that need to read/write from memory
     */
    void execute(Byte[] registers, Ram ram);
}

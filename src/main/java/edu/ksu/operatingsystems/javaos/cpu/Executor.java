package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.Ram;

public interface Executor {

    /**
     * Executes the instruction that is loaded in the registers.
     * @param instruction The instruction to execute (each is 32 bits long)
     * @param instructionPosition The position of the current instruction
     * @param ram The ram memory, since there may be operations that need to read/write from memory
     */
    void execute(Integer instruction, Integer instructionPosition, Ram ram);

    /**
     * Sets the buffers
     */
    void setBuffers(Integer inputBuffer, Integer outputBuffer, Integer tempBuffer);
}

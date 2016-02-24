package edu.ksu.operatingsystems.javaos.cpu;

<<<<<<< Updated upstream
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;
=======
import edu.ksu.operatingsystems.javaos.storage.DefaultRam;
>>>>>>> Stashed changes

public interface Executor {

    /**
     * Executes the instruction that is loaded in the registers.
     * @param instruction The instruction to execute (each is 32 bits long)
     * @param ram The ram memory, since there may be operations that need to read/write from memory
     */
<<<<<<< Updated upstream
    void execute(Integer instruction, Ram ram);

    /**
     * Sets the process that's currently executing
     * @param process The process
     */
    void setProcess(ProcessControlBlock process);
=======
    void execute(Byte[] registers, DefaultRam ram);
>>>>>>> Stashed changes
}

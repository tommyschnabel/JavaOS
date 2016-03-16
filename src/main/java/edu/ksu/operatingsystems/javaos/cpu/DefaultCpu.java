package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class DefaultCpu implements Cpu {

    /**
     * The cpu has 16 registers, each 32 bits long
     * (using {@link Long}s because 0xFFFFFFFF < {@link Integer.MAX_VALUE}
     * Register-0 is the accumulator
     * Register-1 is the Zero register
     * All other registers are general purpose registers
     */
    private long[] registers;
    private Fetcher fetcher;
    private Decoder decoder;
    private Executor executor;
    private Ram ram;

    private ProcessControlBlock currentProcess;
    
    public DefaultCpu(Ram ram) {
        registers = new long[16];
        fetcher = new DefaultFetcher();
        decoder = new DefaultDecoder();
        executor = new DefaultExecutor(ram, registers);
        this.ram = ram;
    }
    
    public void run() {
        if (currentProcess == null) {
            return;
        }

    	byte[] instruction = fetcher.fetch(currentProcess, ram);
    	String decodedInstruction = decoder.decode(instruction);
    	executor.setProcess(currentProcess);
    	executor.execute(decodedInstruction);
    }

    @Override
    public long[] getRegisters() {
        return registers;
    }

    @Override
    public void setCurrentProcess(ProcessControlBlock currentProcess) {
        this.currentProcess = currentProcess;
    }

    public ProcessControlBlock getCurrentProcess() {
        return currentProcess;
    }
}

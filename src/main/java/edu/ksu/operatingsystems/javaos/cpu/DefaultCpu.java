package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.DefaultRam;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

public class DefaultCpu implements Cpu {

    /**
     * The cpu has 16 registers, each 32 bits long
     * Register-0 is the accumulator
     * Register-1 is the Zero register
     * All other registers are general purpose registers
     */
    private Integer[] registers;
    private DefaultFetcher fetcher;
    private DefaultDecoder decoder;
    private DefaultExecutor executor;
    private DefaultRam ram;
    
    public DefaultCpu(DefaultRam ram) {
        registers = new Integer[16];
        fetcher = new DefaultFetcher();
        decoder = new DefaultDecoder();
        executor = new DefaultExecutor(registers);
        this.ram = ram;
    }
    
    public void run(ProcessControlBlock pcb){
    	byte[] instruction = fetcher.fetch(pcb, ram);
    	int decodedInstruction = decoder.decode(instruction);
    	executor.setProcess(pcb);
    	executor.execute(decodedInstruction, ram);
    }
    	
}

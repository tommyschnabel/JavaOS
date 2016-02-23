package edu.ksu.operatingsystems.javaos.cpu;

public class DefaultCpu implements Cpu {

    /**
     * The cpu has 16 registers, each 32 bits long
     * Register-0 is the accumulator
     * Register-1 is the Zero register
     * All other registers are general purpose registers
     */
    private Integer[] registers;

    public DefaultCpu() {
        registers = new Integer[16];
    }
}

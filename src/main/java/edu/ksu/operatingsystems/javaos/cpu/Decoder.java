package edu.ksu.operatingsystems.javaos.cpu;

public interface Decoder {

    /**
     * Decodes the given instruction and puts the decoded
     * instruction into the registers to be executed
     * @param instruction The instruction to decode
     */
    String decode(byte[] instruction);
}

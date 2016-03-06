package edu.ksu.operatingsystems.javaos.cpu;

public interface Decoder {

    /**
     * Decodes the given instruction and puts the decoded
     * instruction into the registers to be executed
     * @param instruction The instruction to decode
     * @param registers The registers to store the decoded instruction in
     */
    int decode(byte[] instruction);
}

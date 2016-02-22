package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/22/16.
 */
public interface Program {

    void setID(int id);
    void setInstructionLocationOnDisk(int startInstructionIndex);
    void setDataLocationOnDisk(int startDataIndex);
    void setPriority(int priority);
    void setInstructionSize(int instructionSize);
    void setInputBuffer(int inputBuffer);
    void setOutputBuffer(int outputBuffer);
    void setTemporaryBuffer(int tempBuffer);
    void setInMemory(boolean inMemory);
    void setInstructionLocationInMemory(int instructionLocationMemory);
    void setDataLocationInMemory(int dataLocationInMemory);
    Integer getID();
    int getInstructionLocationOnDisk();
    int getDataLocationOnDisk();
    int getPriority();
    int getInstructionSize();
    int getInputBuffer();
    int getOutputBuffer();
    int getTemporaryBuffer();
    boolean inMemory();
    int getInstructionLocationInMemory();
    int getDataLocationInMemory();
    void displayProgram();
}

package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

/**
 * Created by Calvin on 3/16/16.
 */
public interface CpuCache {

    int getCachePointer();

    void setCachePointer(int cachePointer);

    void writeValueToAddress(Integer startAddress, String value);

    String readValueFromAddress(Integer startAddress, int lengthToRead);

    void addProcessToCache(ProcessControlBlock process);

    String removeProcessFromCache();

    ProcessControlBlock getProcess();

    void setProcess(ProcessControlBlock process);

    int getDataLocationInMemory();

    int getInputBufferLocation();

    int getOutputBufferLocation();
}

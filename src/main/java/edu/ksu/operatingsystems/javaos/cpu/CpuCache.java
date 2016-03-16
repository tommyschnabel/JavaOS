package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;

/**
 * Created by Calvin on 3/16/16.
 */
public interface CpuCache {

    public int getCachePointer();

    public void setCachePointer(int cachePointer);

    public void writeValueToAddress(Integer startAddress, String value);

    public String readValueFromAddress(Integer startAddress, int lengthToRead);

    public void addProcessToCache(ProcessControlBlock process);

    public String removeProcessFromCache();
}

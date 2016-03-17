package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

/**
 * Created by Calvin on 3/16/16.
 */
public class DefaultCpuCache implements CpuCache {

    private char[] cache = new char[800]; // Creates an array of size 8192 ( 1024 words = 8192 hex values )

    private int cachePointer = 0;
    public ProcessControlBlock mProcess;
    boolean isEmpty = true;
    private Ram ram;

    public DefaultCpuCache(Ram ram) {
        this.ram = ram;
    }
    @Override
    public int getCachePointer() {
        return cachePointer;
    }
    @Override
    public void setCachePointer(int cachePointer) {
        this.cachePointer = cachePointer;
    }

    @Override
    public void writeValueToAddress(Integer startAddress, String value) {

        while (value.length() < 8) {
            value = "0" + value;
        }

        if (value == null) {
            System.out.println("The string passed in was null. No value was written.");
            return;
        }

        for (int i = 0; i < value.length(); i++) {
            cache[startAddress+i] = value.charAt(i);
        }

        mProcess.ioOperationMade();
    }
    @Override
    public String readValueFromAddress(Integer startAddress, int lengthToRead)
    {
        String val = "";
        for (int i = startAddress; i < startAddress + lengthToRead; i++) {
            val += cache[i];
        }

        mProcess.ioOperationMade();
        return val;
    }
    @Override
    public void addProcessToCache(ProcessControlBlock process)
    {
        if (!isEmpty)
        {
            System.out.println("There is no room for the process!");
            System.out.println("Process ID: " + mProcess.getID() + " already exists in cache");
            return;
        }
        String processFromRam = ram.readValueFromAddress(process.getOriginalInstructionLocationInMemory(),
                                                        process.getProcessSize(),
                                                        process);
        writeValueToAddress(0, processFromRam);
        mProcess = process;
        isEmpty = false;
    }
    @Override
    public String removeProcessFromCache()
    {
        String processInCache = readValueFromAddress(0, mProcess.getProcessSize());

        for (int i = 0; i < cache.length; i++ )
        {
            cache[i] = '\u0000'; // nulls out the chars
        }

        mProcess = null;
        isEmpty = true;
        return processInCache;
    }

    @Override
    public ProcessControlBlock getProcess() {
        return mProcess;
    }
    @Override
    public void setProcess(ProcessControlBlock process) {
        this.mProcess = process;
    }
    @Override
    public int getDataLocationInMemory()
    {
        System.out.println("Data location in memory: " + mProcess.getInstructionSize());
        return mProcess.getInstructionSize();
    }
    @Override
    public int getInputBufferLocation()
    {
        return getDataLocationInMemory(); //The input buffer is the first piece of data.
    }
    @Override
    public int getOutputBufferLocation()
    {
        return getDataLocationInMemory() + mProcess.getInputBufferLength();
    }


}

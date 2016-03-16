package edu.ksu.operatingsystems.javaos.storage;

public class DefaultRam implements Ram {

    private char[] diskArray = new char[8192]; // Creates an array of size 8192 ( 1024 words = 8192 hex values )
    private ProcessControlBlock[] processes = new ProcessControlBlock[30];
    private int currentPositionInMemory = 0;

    @Override
    public void addProcessControlBlockToMemoryByProcessID(int processID, Disk disk) {

        int instructionOnDiskStartsAt;
        int instructionOnDiskEndsAt;
        int dataOnDiskEndsAt;

        ProcessControlBlock programToAdd = disk.findProgram(processID);

        currentPositionInMemory = findSpotForProcess(programToAdd);

        instructionOnDiskStartsAt = programToAdd.getInstructionLocationOnDisk();
        instructionOnDiskEndsAt = instructionOnDiskStartsAt + programToAdd.getInstructionSize();
        dataOnDiskEndsAt = programToAdd.getInputBufferLength() +
                programToAdd.getOutputBufferLength() +
                programToAdd.getTemporaryBufferLength();

        programToAdd.setInstructionLocationInMemory(currentPositionInMemory);
        programToAdd.setOriginalInstructionLocationInMemory(currentPositionInMemory);

        //System.out.println("instruction start: " + instructionOnDiskStartsAt);
        //System.out.println("instruction end: " + instructionOnDiskEndsAt);
        //System.out.println("data start: " + dataOnDiskStartsAt);
        //System.out.println("data end: " + dataOnDiskEndsAt);

        programToAdd.setLastInstructionLocationInMemory(instructionOnDiskEndsAt);

        programToAdd.setDataLocationInMemory(currentPositionInMemory + programToAdd.getInstructionSize());
        programToAdd.setInMemory(true);

        char[] myDisk = disk.getDisk();

        for (int i = instructionOnDiskStartsAt; i < (instructionOnDiskEndsAt + dataOnDiskEndsAt); i++) {
            diskArray[currentPositionInMemory++] = myDisk[i];
        }

        addProcessControlBlockToPCBList(programToAdd);
    }

    @Override
    public void addProcessControlBlockToPCBList(ProcessControlBlock program) {
        for (int i = 0; i < processes.length; i++) {
            if (processes[i] == null) {
                program.setInMemory(true);
                processes[i] = program;
                return;
            }
        }
    }

    @Override
    public void displayRAM() //Provides ability to visualize RAM
    {
        for (int i = 0; i < diskArray.length; i++) {
            if (i % 50 == 0) {
                System.out.println();
            }
            System.out.print(diskArray[i] + " ");
        }
    }

    @Override
    public void displayPCBList()
    {
        for (int i = 0; i < processes.length; i++)
        {
            if (processes[i] != null)
            {
                System.out.println("Process ID: " + processes[i].getID());
                System.out.println("Instruction Start: " + processes[i].getOriginalInstructionLocationInMemory());
                System.out.println("InputBuffer Size: " + processes[i].getInputBufferLength());
                System.out.println("OutputBuffer Size: " + processes[i].getOutputBufferLength());
                System.out.println("TempBuffer Size: " + processes[i].getTemporaryBufferLength());
                System.out.println("Process Size: " + processes[i].getProcessSize());
            }
        }
    }

    @Override
    public char[] getDisk() {
        return diskArray;
    }

    public ProcessControlBlock[] getProcesses() {
        return processes;
    }

    @Override
    public ProcessControlBlock getProcessByID(int ID) {

        for (int i = 0; i < processes.length; i++) {
            if (processes[i] != null) {
                if (processes[i].getID() == ID) {
                    return processes[i];
                }
            }
        }
        System.out.println("Failed to find process with ID: " + ID);
        return null;
    }



    @Override
    public int getCurrentPositionInMemory() {
        return currentPositionInMemory;
    }


    @Override
    public void writeValueToAddress(Integer startAddress, String value, ProcessControlBlock p) {

        if (value == null) {
            System.out.println("The string passed in was null. No value was written.");
            return;
        }

        if ( (startAddress + value.length()) > p.getOriginalInstructionLocationInMemory() + p.getProcessSize() || startAddress < p.getOriginalInstructionLocationInMemory()) {
            throw new RuntimeException("ERROR: Process " + p.getID() + " attempting to write to memory area of another process!!!");
        }

        System.out.println("Writing Value: " + value + " to address: " + startAddress );

        for (int i = 0; i < value.length(); i++) {
            diskArray[startAddress+i] = value.charAt(i);
        }
    }

    @Override
    public String readValueFromAddress(Integer startAddress, int lengthToRead, ProcessControlBlock p) {
        if (startAddress + lengthToRead > p.getOriginalInstructionLocationInMemory() + p.getProcessSize()
                    || startAddress < p.getOriginalInstructionLocationInMemory()) {

            throw new RuntimeException("ERROR: Process " + p.getID() + " attempting to read from memory area of another process!!!");
        }

        String val = "";
        for (int i = startAddress; i < startAddress + lengthToRead; i++) {
            val += diskArray[i];
        }

        return val;
    }

    @Override
    public void removeProcessFromMemory(Integer ID) {
        ProcessControlBlock processToRemove = getProcessByID(ID);
        processToRemove.setInMemory(false);

        //Null out process instructions and data in memory

        int startLocation = processToRemove.getOriginalInstructionLocationInMemory();
        int offset        = processToRemove.getProcessSize();

        //System.out.println("StartLocation = " + startLocation);
        //System.out.println("offset = " + offset);

        for (int i = startLocation; i < (startLocation + offset); i++)
        {
            diskArray[i] = '\u0000'; // '\u0000' = unicode for char null
        }

        //Null out process in the PCB list
        for (int i = 0; i < diskArray.length; i++)
        {
            if (processes[i] != null)
                if (processes[i].getID().equals(ID)) {
                    processes[i] = null;
                    break;
                }

        }
    }

    @Override
    public int findSpotForProcess(ProcessControlBlock PCB)
    {
        int startLocation = 0;
        int concurrentFreeSpotsAvailable = 0;
        int processSize = PCB.getProcessSize();

        for (int i = 0; i < diskArray.length; i++)
        {
            if (diskArray[i] == '\u0000')
            {
                concurrentFreeSpotsAvailable++;
                if (concurrentFreeSpotsAvailable >= processSize)
                {
                    return startLocation;
                }
            }
            else
            {
                startLocation = i + 1;
                concurrentFreeSpotsAvailable = 0;
            }
        }
        //System.out.println("Memory is FULL");
            throw new OutOfMemoryError(); //Got to the end of the method so there is no more room
    }

    @Override
    public boolean isRoomForProcess(ProcessControlBlock pcb) {
        try {
            findSpotForProcess(pcb);
            return true;
        } catch (OutOfMemoryError oom) {
            return false;
        }
    }

    @Override
    public void defrag()
    {
        System.out.println("Defragging memory");
        int amtOfSpaces = 0;
        for (int i = 0; i < diskArray.length; i++)
        {
            if (diskArray[i] == '\u0000') // '\u0000' = unicode for char null
            {
                amtOfSpaces++;
            }
            else
            {
                if (amtOfSpaces != 0)
                {
                    //defrag
                    //System.out.println("\nI found a space with " + amtOfSpaces + " spots that are null");
                    for (int j = 0; j < processes.length; j++)
                    {
                        if ( processes[j] != null )
                        {
                            if (processes[j].getOriginalInstructionLocationInMemory() >= i)
                            {
                                int newOriginalInstructionLocation = processes[j].getOriginalInstructionLocationInMemory() - amtOfSpaces;
                                int newInstructionLocation = processes[j].getInstructionLocationInMemory() - amtOfSpaces;
                                int newDataLocation        = processes[j].getDataLocationInMemory() - amtOfSpaces;
                                processes[j].setInstructionLocationInMemory(newInstructionLocation);
                                processes[j].setOriginalInstructionLocationInMemory(newOriginalInstructionLocation);
                                processes[j].setDataLocationInMemory(newDataLocation);
                            }
                        }
                    }
                    for (int k = i; k < diskArray.length; k++ )
                    {
                        diskArray[k-amtOfSpaces] = diskArray[k]; // transfers the char to the new spot
                        diskArray[k] = '\u0000'; // nulls out the chars after we transfer
                    }
                    amtOfSpaces = 0;
                }
            }
        }
    }

}
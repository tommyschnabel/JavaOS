package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/24/16.
 */
public class DefaultRam implements Ram {

    private char[] diskArray = new char[8192]; // Creates an array of size 8192 ( 1024 words = 8192 hex values )
    private ProcessControlBlock[] processArray = new ProcessControlBlock[30];
    int currentPositionInMemory = 0;

    @Override
    public void addProcessControlBlockToMemoryByProcessID(int processID, Disk disk) {

        int instructionStart;
        int instructionEnd;
        int dataStart;
        int dataEnd;

        ProcessControlBlock programToAdd = disk.findProgram(processID);

        currentPositionInMemory = findSpotForProcess(programToAdd);

        instructionStart = programToAdd.getInstructionLocationOnDisk();
        instructionEnd = instructionStart + programToAdd.getInstructionSize();
        dataStart = instructionEnd;
        dataEnd = programToAdd.getInputBuffer() +
                programToAdd.getOutputBuffer() +
                programToAdd.getTemporaryBuffer();

        programToAdd.setInstructionLocationInMemory(currentPositionInMemory);
        programToAdd.setDataLocationInMemory(dataStart);
        programToAdd.setInMemory(true);

        char[] myDisk = disk.getDisk();

        for (int i = instructionStart; i < (instructionEnd + dataEnd); i++) {
            diskArray[currentPositionInMemory++] = myDisk[i];
        }

    }

    @Override
    public void addProcessControlBlockToPCBList(ProcessControlBlock program) {
        for (int i = 0; i < processArray.length; i++) {
            if (processArray[i] == null) {
                program.setInMemory(true);
                processArray[i] = program;
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
        for (int i = 0; i < processArray.length; i++)
        {
            if (processArray[i] != null)
            {
                System.out.println("Process ID: " + processArray[i].getID());
                System.out.println("Instruction Start: " + processArray[i].getInstructionLocationInMemory());
                System.out.println("Process Size: " + processArray[i].getProcessSize());
            }
        }
    }

    @Override
    public char[] getDisk() {
        return diskArray;
    }

    @Override
    public ProcessControlBlock[] getProcessArray() {
        return processArray;
    }

    @Override
    public ProcessControlBlock getProcessByID(int ID) {

        for (int i = 0; i < processArray.length; i++) {
            if (processArray[i] != null) {
                if (processArray[i].getID() == ID) {
                    return processArray[i];
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
    public void writeValueToAddress(Integer startAddress, String value)
    {
        if (value == null)
        {
            System.out.println("The string passed in was null. No value was written.");
            return;
        }

        for (int i = 0; i < value.length(); i++)
        {
            diskArray[startAddress+i] = value.charAt(i);
        }
    }

    @Override
    public String readValueFromAddress(Integer startAddress, int lengthToRead)
    {
        String val = "";

        for (int i = startAddress; i < startAddress + lengthToRead; i++)
            val += diskArray[i];

        return val;
    }

    @Override
    public void removeProcessFromMemory(Integer ID)
    {
        ProcessControlBlock processToRemove = getProcessByID(ID);
        processToRemove.setInMemory(false);

        //Null out process instructions and data in memory

        int startLocation = processToRemove.getInstructionLocationInMemory();
        int offset        = processToRemove.getProcessSize();

        System.out.println("StartLocation = " + startLocation);
        System.out.println("offset = " + offset);

        for (int i = startLocation; i < (startLocation + offset); i++)
        {
            diskArray[i] = '\u0000'; // '\u0000' = unicode for char null
        }

        //Null out process in the PCB list
        for (int i = 0; i < diskArray.length; i++)
        {
            if (processArray[i] != null)
                if (processArray[i].getID().equals(ID)) {
                    processArray[i] = null;
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
        System.out.println("Memory is FULL");
            throw new OutOfMemoryError(); //Got to the end of the method so there is no more room
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
                    System.out.println("\nI found a space with " + amtOfSpaces + " spots that are null");
                    for (int j = 0; j < processArray.length; j++)
                    {
                        if ( processArray[j] != null )
                        {
                            if (processArray[j].getInstructionLocationInMemory() >= i)
                            {
                                int newInstructionLocation = processArray[j].getInstructionLocationInMemory() - amtOfSpaces;
                                int newDataLocation        = processArray[j].getDataLocationInMemory() - amtOfSpaces;
                                processArray[j].setInstructionLocationInMemory(newInstructionLocation);
                                processArray[j].setDataLocationInMemory(newDataLocation);
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
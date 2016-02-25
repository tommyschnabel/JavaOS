package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/24/16.
 */
public class DefaultRam implements Ram {

    private char[] diskArray = new char[8192]; // Creates an array of size 8192 ( 1024 words = 8192 hex values )
    private ProcessControlBlock[] processArray = new ProcessControlBlock[30];
    int currentPositionInMemory = 0;

    public void addProcessControlBlockToMemoryByProcessID(int processID, Disk disk) {

        int instructionStart;
        int instructionEnd;
        int dataStart;
        int dataEnd;

        ProcessControlBlock programToAdd = disk.findProgram(processID);

        currentPositionInMemory = findSpotForProcess(programToAdd);

        instructionStart = currentPositionInMemory;
        instructionEnd = instructionStart + programToAdd.getInstructionSize();
        dataStart = instructionEnd;
        dataEnd = programToAdd.getInputBuffer() +
                programToAdd.getOutputBuffer() +
                programToAdd.getTemporaryBuffer();

        programToAdd.setInstructionLocationInMemory(instructionStart);
        programToAdd.setDataLocationInMemory(dataStart);
        programToAdd.setInMemory(true);

        char[] myDisk = disk.getDisk();

        for (int i = instructionStart; i < (instructionEnd + dataEnd); i++) {
            diskArray[currentPositionInMemory++] = myDisk[i];
        }

    }

    public void addProcessControlBlockToPCBList(ProcessControlBlock program) {
        for (int i = 0; i < processArray.length; i++) {
            if (processArray[i] == null) {
                program.setInMemory(true);
                processArray[i] = program;
                return;
            }
        }
    }

    public void displayRAM() //Provides ability to visualize RAM
    {
        for (int i = 0; i < diskArray.length; i++) {
            if (i % 50 == 0) {
                System.out.println();
            }
            System.out.print(diskArray[i] + " ");
        }
    }

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

    public char[] getDisk() {
        return diskArray;
    }

    public ProcessControlBlock[] getProcessArray() {
        return processArray;
    }

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



    public int getCurrentPositionInMemory() {
        return currentPositionInMemory;
    }


    public void writeValueToAddress(Integer address, Integer value)
    {
        return;
    }

    public Integer readValueFromAddress(Integer address)
    {
        return 0;
    }

    public void removeProcessFromMemory(Integer ID)
    {
        ProcessControlBlock processToRemove = getProcessByID(ID);
        processToRemove.setInMemory(false);

        //Null out process instructions and data in memory

        int startLocation = processToRemove.getInstructionLocationInMemory();
        int offset        = processToRemove.getProcessSize();

        for (int i = startLocation; i < (startLocation + offset); i++)
        {
            diskArray[i] = '\u0000'; // '\u0000' = unicode for char null
        }

        //Null out process in the PCB list
        for (int i = 0; i < diskArray.length; i++)
        {
            if (processArray[i] != null)
                if (processArray[i].getID() == ID) {
                    processArray[i] = null;
                    break;
                }

        }

    }
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
        // We should call a disk refactor in this scenario
    }

    public void defrag()
    {
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
                        diskArray[k-amtOfSpaces] = diskArray[k];
                    }
                    for (int l = diskArray.length - amtOfSpaces; l < amtOfSpaces; l++)
                    {
                        diskArray[l] = '\u0000';
                    }
                    amtOfSpaces = 0;
                }
            }
        }
    }

}
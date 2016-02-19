package edu.ksu.operatingsystems.javaos.storage;

public class Ram {

    private char [] diskArray = new char[8192]; // Creates an array of size 8192 ( 1024 words = 8192 hex values )
    private Program [] processArray = new Program[30];
    int currentPositionInMemory = 0;

    public void addProgramToMemoryByProcessID(int processID, Disk disk)
    {

        int instructionStart;
        int instructionEnd;
        int dataStart;
        int dataEnd;

        Program programToAdd = disk.findProgram(processID);

        instructionStart = currentPositionInMemory;
        instructionEnd = instructionStart + programToAdd.getInstructionSize();
        dataStart = instructionEnd;
        dataEnd = programToAdd.getInputBuffer() +
                  programToAdd.getOutputBuffer() +
                  programToAdd.getTemporaryBuffer();

        programToAdd.setInstructionLocationInMemory(instructionStart);
        programToAdd.setDataLocationInMemory(dataStart);
        programToAdd.setInMemory(true);

        char [] myDisk = disk.getDisk();

        for (int i = instructionStart; i < (instructionEnd + dataEnd); i++)
        {
            diskArray[currentPositionInMemory++] = myDisk[i];
        }

    }
    public void addProgramToPCBList(Program program)
    {
        for (int i = 0; i < processArray.length; i++) {
            if (processArray[i] == null) {
                processArray[i] = program;
                return;
            }
        }
    }
    public void displayRAM() //Provides ability to visualize RAM
    {
        for (int i = 0; i < diskArray.length; i++) {
            if (i % 50 == 0)
            {
                System.out.println();
            }
            System.out.print(diskArray[i] + " ");
        }
    }

    public char [] getDisk()
    {
        return diskArray;
    }

    public Program [] getProcessArray()
    {
        return processArray;
    }

    public Program getProcessByID(int ID)
    {
        for (int i = 0; i < processArray.length; i++)
        {
            if (processArray[i].getID() == ID)
            {
                System.out.println("I found the program with ID: " + ID );
                return processArray[i];
            }
        }
        System.out.println("Failed to find process with ID: " + ID);
        return null;
    }
    public int getCurrentPositionInMemory()
    {
        return currentPositionInMemory;
    }
}

package edu.ksu.operatingsystems.javaos.storage;

public class DefaultDisk implements Disk {

    private char [] diskArray = new char[16384]; // Creates an array of size 16384 ( 2048 words = 16384 hex values )
    private ProcessControlBlock[] processControlBlockList = new ProcessControlBlock[30]; // Creates a list of Programs stored on defaultDisk
    int currentPositionOnDisk = 0;

    public char [] getDisk()
    {
        return diskArray;
    }
    public void addToDisk(String s)
    {
        // hex string in this format: 0xC050005C
        String hexVals = s.substring(2);
        for (int i = 0; i < hexVals.length(); i++)
        {
            char charVal = hexVals.charAt(i);
            diskArray[currentPositionOnDisk++] = charVal;
        }
    }
    public void addProgramToProgramList(ProcessControlBlock processControlBlock)
    {
        for (int i = 0; i < processControlBlockList.length; i++) {
            if (processControlBlockList[i] == null) {
                processControlBlockList[i] = processControlBlock;
                return;
            }
        }
    }
    public void displayDisk()  //This method is just used so I can visualize the DefaultDisk
    {
        for (int i = 0; i < diskArray.length; i++) {
            if (i % 50 == 0)
            {
                System.out.println();
            }
            System.out.print(diskArray[i] + " ");
        }
    }
    public ProcessControlBlock findProgram(int programID)
    {
        for (int i = 0; i < processControlBlockList.length; i++)
        {
            if (processControlBlockList[i].getID().equals(programID))
            {
                //System.out.println("I found the program with ID: " + programID );
                return processControlBlockList[i];
            }
        }
        System.out.println("Failed to find program with ID: " + programID);
        return null;
    }
    public int getCurrentPositionOnDisk()
    {
        return currentPositionOnDisk;
    }

}
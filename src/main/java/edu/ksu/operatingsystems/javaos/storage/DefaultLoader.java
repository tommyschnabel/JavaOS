package edu.ksu.operatingsystems.javaos.storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultLoader implements Loader {

    private Disk disk;
    private int ID;
    private int instructionSize;
    private int priority;
    private int inputBuffer;
    private int outputBuffer;
    private int tempBuffer;
    private int instructionStartPosition;
    private int dataStartPosition;

    public DefaultLoader(Disk disk) {
        this.disk = disk;
    }

    @Override
    public boolean load(String fileName) {

        try {

            InputStream fis = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            for (String s = br.readLine(); s != null; s = br.readLine())
            {
                if (s.startsWith("0x"))
                {
                    disk.addToDisk(s); //hex characters
                }
                else
                {
                    int currentDiskPosition = disk.getCurrentPositionOnDisk();

                    if (s.contains("JOB"))
                    {
                        String [] sVals = s.split(" ");
                        ID                       = Integer.decode("0x" + sVals[2])    ;
                        instructionSize          = Integer.decode("0x" + sVals[3]) * 8; //word = hex * 8
                        priority                 = Integer.decode("0x" + sVals[4])    ;
                        instructionStartPosition = currentDiskPosition;

                    }
                    else if (s.contains("Data"))
                    {

                        String [] sVals = s.split(" ");
                        inputBuffer      = Integer.decode("0x" + sVals[2]) * 8; //word = hex * 8
                        outputBuffer     = Integer.decode("0x" + sVals[3]) * 8;
                        tempBuffer       = Integer.decode("0x" + sVals[4]) * 8;
                        dataStartPosition = currentDiskPosition;
                    }
                    else if (s.contains("END"))
                    {
                        ProcessControlBlock processControlBlock = new ProcessControlBlock();

                        processControlBlock.setID(ID);
                        processControlBlock.setInstructionSize(instructionSize);
                        processControlBlock.setPriority(priority);

                        processControlBlock.setInputBuffer(inputBuffer);
                        processControlBlock.setOutputBuffer(outputBuffer);
                        processControlBlock.setTemporaryBuffer(tempBuffer);

                        processControlBlock.setInstructionLocationOnDisk(instructionStartPosition);
                        processControlBlock.setDataLocationOnDisk(dataStartPosition);

                        disk.addProgramToProgramList(processControlBlock);

                        //program.displayProgram();

                    }
                }
            }

            br.close();

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found.");
            return false;
        } catch (Exception e)
        {
            System.out.println("Something went wrong." + e);
            return false;
        }

        return true;
    }

}

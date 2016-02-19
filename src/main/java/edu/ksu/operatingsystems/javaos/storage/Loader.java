package edu.ksu.operatingsystems.javaos.storage;

import java.io.*;
import java.math.BigInteger;

public class Loader {

    Disk disk = new Disk();
    int ID;
    int instructionSize;
    int priority;
    int inputBuffer;
    int outputBuffer;
    int tempBuffer;
    int instructionStartPosition;
    int dataStartPosition;

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
                        Program program = new Program();

                        program.setID(ID);
                        program.setInstructionSize(instructionSize);
                        program.setPriority(priority);

                        program.setInputBuffer(inputBuffer);
                        program.setOutputBuffer(outputBuffer);
                        program.setTemporaryBuffer(tempBuffer);

                        program.setInstructionLocationOnDisk(instructionStartPosition);
                        program.setdataLocationOnDisk(dataStartPosition);

                        disk.addProgramToProgramList(program);

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

    public Disk getDisk()
    {
        return disk;
    }

}

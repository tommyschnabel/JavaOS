package edu.ksu.operatingsystems.javaos.storage;

/**
 * Created by Calvin on 2/19/16.
 */
public class DefaultProgram implements Program {

    Integer mID;
    int mInstructionLocationOnDisk;
    int mDataLocationOnDisk;
    int mPriority;
    int mInstructionSize; // number of words
    int mInputBuffer; // number of words
    int mOutputBuffer;
    int mTemporaryBuffer;

    boolean mInMemory = false;

    //initialized when in Ram

    int mInstructionLocationInMemory;
    int mDataLocationInMemory;


    public void setID(int id)
    {
        mID = id;
    }
    public void setInstructionLocationOnDisk(int startInstructionIndex)
    {
        mInstructionLocationOnDisk = startInstructionIndex;
    }
    public void setDataLocationOnDisk(int startDataIndex)
    {
        mDataLocationOnDisk = startDataIndex;
    }
    public void setPriority(int priority)
    {
        mPriority = priority;
    }
    public void setInstructionSize(int instructionSize)
    {
        mInstructionSize = instructionSize;
    }
    public void setInputBuffer(int inputBuffer)
    {
        mInputBuffer = inputBuffer;
    }
    public void setOutputBuffer(int outputBuffer)
    {
        mOutputBuffer = outputBuffer;
    }
    public void setTemporaryBuffer(int tempBuffer)
    {
        mTemporaryBuffer = tempBuffer;
    }
    public void setInMemory(boolean inMemory)
    {
        mInMemory = inMemory;
    }
    public void setInstructionLocationInMemory(int instructionLocationMemory)
    {
        mInstructionLocationInMemory = instructionLocationMemory;
    }
    public void setDataLocationInMemory(int dataLocationInMemory)
    {
        mDataLocationInMemory = dataLocationInMemory;
    }
    public Integer getID()
    {
        return mID;
    }
    public int getInstructionLocationOnDisk()
    {
        return mInstructionLocationOnDisk;
    }
    public int getDataLocationOnDisk()
    {
        return mDataLocationOnDisk;
    }
    public int getPriority()
    {
        return mPriority;
    }
    public int getInstructionSize()
    {
        return mInstructionSize;
    }
    public int getInputBuffer()
    {
        return mInputBuffer;
    }
    public int getOutputBuffer()
    {
        return mOutputBuffer;
    }
    public int getTemporaryBuffer()
    {
        return mTemporaryBuffer;
    }
    public boolean inMemory()
    {
        return mInMemory;
    }
    public int getInstructionLocationInMemory()
    {
        return mInstructionLocationInMemory;
    }
    public int getDataLocationInMemory()
    {
        return getInstructionLocationInMemory();
    }
    public void displayProgram()
    {
        System.out.println("ID = " + mID);
        System.out.println("Instruction location on defaultDisk = " + mInstructionLocationOnDisk);
        System.out.println("Data location on defaultDisk = " + mDataLocationOnDisk);
        System.out.println("Priority = " + mPriority);
        System.out.println("Instruction Size = " + mInstructionSize);
        System.out.println("Input Buffer = " + mInputBuffer);
        System.out.println("Output Buffer = " + mOutputBuffer);
        System.out.println("Temporary Buffer = " + mTemporaryBuffer);
        System.out.println("In Memory? = " + mInMemory);
        if (mInMemory) {
            System.out.println("Instruction In Memory Location = " + mInstructionLocationInMemory);
            System.out.println("Data In Memory Location = " + mDataLocationInMemory);
        }

    }

}

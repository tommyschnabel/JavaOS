package edu.ksu.operatingsystems.javaos.storage;

import edu.ksu.operatingsystems.javaos.util.ProcessStats;

public class ProcessControlBlock {

    private Integer mID;
    private int mInstructionLocationOnDisk;
    private int mDataLocationOnDisk;
    private int mPriority;
    private int mInstructionSize; // number of words
    private int mInputBufferLength; // number of words
    private int mOutputBufferLength;
    private int mTemporaryBufferLength;
    private int mProgramCounter;
    private int processSize;

    private boolean mInMemory = false;

    private Long[] processState;

    //Metrics
    private long whenAddedToWaitQueue;
    private long whenAddedToReadyQueue;
    private long whenExecutionStarted;
    private long whenExecutionFinished;
    private int ioOperationCount;

    /**
     * Initialized when in Ram
     */
    private Integer mOriginalInstructionLocationInMemory;
    private Integer mInstructionLocationInMemory;
    private int mDataLocationInMemory;
    private Integer lastInstructionLocationInMemory;

    public boolean isFinished() {

        if (mInstructionLocationInMemory == null || lastInstructionLocationInMemory == null) {
            return false;
        }
        if (mInstructionLocationInMemory.equals(-1)) {
            executionFinished();
            return true;
        }
        if (mInstructionLocationInMemory % 8 != 0) {
            throw new RuntimeException(String.format("Error, memory location for process %s is in the wrong spot: %s", mID, mInstructionLocationInMemory));
        }

        return false;
    }

    public void setID(Integer id) {
        mID = id;
    }

    public void setInstructionLocationOnDisk(int startInstructionIndex) {
        mInstructionLocationOnDisk = startInstructionIndex;
    }

    public void setDataLocationOnDisk(int startDataIndex) {
        mDataLocationOnDisk = startDataIndex;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public void setInstructionSize(int instructionSize) {
        mInstructionSize = instructionSize;
    }

    public void setInputBufferLength(int inputBuffer) {
        mInputBufferLength = inputBuffer;
    }

    public void setOutputBufferLength(int outputBuffer) {
        mOutputBufferLength = outputBuffer;
    }

    public void setTemporaryBufferLength(int tempBuffer) {
        mTemporaryBufferLength = tempBuffer;
    }

    public void setInMemory(boolean inMemory) {
        mInMemory = inMemory;
    }

    public void setInstructionLocationInMemory(int instructionLocationMemory) {
        mInstructionLocationInMemory = instructionLocationMemory;
    }
    public void setOriginalInstructionLocationInMemory(int instructionLocationInMemory)
    {
        mOriginalInstructionLocationInMemory = instructionLocationInMemory;
    }

    public void setDataLocationInMemory(int dataLocationInMemory) {
        mDataLocationInMemory = dataLocationInMemory;
    }

    public Integer getID() {
        return mID;
    }

    public int getInstructionLocationOnDisk() {
        return mInstructionLocationOnDisk;
    }

    public int getDataLocationOnDisk() {
        return mDataLocationOnDisk;
    }

    public int getPriority() {
        return mPriority;
    }

    public int getInstructionSize() {
        return mInstructionSize;
    }

    public int getInputBufferLength() {
        return mInputBufferLength;
    }

    public int getOutputBufferLength() {
        return mOutputBufferLength;
    }

    public int getTemporaryBufferLength() {
        return mTemporaryBufferLength;
    }

    public boolean inMemory() {
        return mInMemory;
    }

    public int getInstructionLocationInMemory() {
        return mInstructionLocationInMemory;
    }

    public int getOriginalInstructionLocationInMemory()
    {
        return mOriginalInstructionLocationInMemory;
    }

    public int getDataLocationInMemory() {
        return mDataLocationInMemory;
    }

    public int getProcessSize()
    {
        return mInstructionSize + mInputBufferLength + mOutputBufferLength + mTemporaryBufferLength;
    }
    public int getInputBufferLocation() { return  mDataLocationInMemory; }
    public int getOutputBufferLocation() { return mDataLocationInMemory + mInputBufferLength; }
    public int getTemporaryBufferLocation() { return mDataLocationInMemory + mInputBufferLength + mOutputBufferLength; }

    public int getLastInstructionLocationInMemory() {
        return lastInstructionLocationInMemory;
    }

    public void setLastInstructionLocationInMemory(int lastInstructionLocationInMemory) {
        this.lastInstructionLocationInMemory = lastInstructionLocationInMemory;
    }

    public Long[] getProcessState() {
        return processState;
    }

    public void setProcessState(Long[] processState) {
        this.processState = processState;
    }

    public void addedToWaitQueue() {
        whenAddedToWaitQueue = System.currentTimeMillis();
    }

    public void addedToReadyQueue() {
        whenAddedToReadyQueue = System.currentTimeMillis();
    }

    public void executionStarted() {
        whenExecutionStarted = System.currentTimeMillis();
    }

    public void executionFinished() {
        if (whenExecutionFinished == 0) {
            whenExecutionFinished = System.currentTimeMillis();
        }
    }

    public void ioOperationMade() {
        ioOperationCount++;
    }

    public ProcessStats generateStats() {

        return new ProcessStats.Builder()
                .setId(mID)
                .setPriority(mPriority)
                .setNumberOfIoOperationsMade(ioOperationCount)
                .setTimeSpentWaiting(whenAddedToReadyQueue - whenAddedToWaitQueue)
                .setTimeSpentReady(whenExecutionStarted - whenAddedToReadyQueue)
                .setTimeSpentExecuting(whenExecutionFinished - whenExecutionStarted)
                .setAmountOfRAMUsed(getProcessSize())
                .build();
    }

    @Override
    public String toString() {
        return "Program{" +
                "mID=" + mID +
                ", mInstructionLocationOnDisk=" + mInstructionLocationOnDisk +
                ", mDataLocationOnDisk=" + mDataLocationOnDisk +
                ", mPriority=" + mPriority +
                ", mInstructionSize=" + mInstructionSize +
                ", mInputBufferLength=" + mInputBufferLength +
                ", mOutputBufferLength=" + mOutputBufferLength +
                ", mTemporaryBufferLength=" + mTemporaryBufferLength +
                ", mInMemory=" + mInMemory +
                ", mInstructionLocationInMemory=" + mInstructionLocationInMemory +
                ", mDataLocationInMemory=" + mDataLocationInMemory +
                '}';
    }
}

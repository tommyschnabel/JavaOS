package edu.ksu.operatingsystems.javaos.storage;

public class ProcessControlBlock {

    private Integer mID;
    private int mInstructionLocationOnDisk;
    private int mDataLocationOnDisk;
    private int mPriority;
    private int mInstructionSize; // number of words
    private int mInputBuffer; // number of words
    private int mOutputBuffer;
    private int mTemporaryBuffer;
    private int mProgramCounter;
    private int processSize;

    private boolean mInMemory = false;

    private Integer[] processState;

    /**
     * Initialized when in Ram
     */
    private Integer mInstructionLocationInMemory;
    private int mDataLocationInMemory;
    private int lastInstructionLocationInMemory;

    public boolean isFinished() {
        return mInstructionLocationInMemory > lastInstructionLocationInMemory;
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

    public void setInputBuffer(int inputBuffer) {
        mInputBuffer = inputBuffer;
    }

    public void setOutputBuffer(int outputBuffer) {
        mOutputBuffer = outputBuffer;
    }

    public void setTemporaryBuffer(int tempBuffer) {
        mTemporaryBuffer = tempBuffer;
    }

    public void setInMemory(boolean inMemory) {
        mInMemory = inMemory;
    }

    public void setInstructionLocationInMemory(int instructionLocationMemory) {
        mInstructionLocationInMemory = instructionLocationMemory;
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

    public int getInputBuffer() {
        return mInputBuffer;
    }

    public int getOutputBuffer() {
        return mOutputBuffer;
    }

    public int getTemporaryBuffer() {
        return mTemporaryBuffer;
    }

    public boolean inMemory() {
        return mInMemory;
    }

    public int getInstructionLocationInMemory() {
        return mInstructionLocationInMemory;
    }

    public int getDataLocationInMemory() {
        return mDataLocationInMemory;
    }

    public int getProcessSize()
    {
        return mInstructionSize + mInputBuffer + mOutputBuffer + mTemporaryBuffer;
    }

    public int getLastInstructionLocationInMemory() {
        return lastInstructionLocationInMemory;
    }

    public void setLastInstructionLocationInMemory(int lastInstructionLocationInMemory) {
        this.lastInstructionLocationInMemory = lastInstructionLocationInMemory;
    }

    public Integer[] getProcessState() {
        return processState;
    }

    public void setProcessState(Integer[] processState) {
        this.processState = processState;
    }

    @Override
    public String toString() {
        return "Program{" +
                "mID=" + mID +
                ", mInstructionLocationOnDisk=" + mInstructionLocationOnDisk +
                ", mDataLocationOnDisk=" + mDataLocationOnDisk +
                ", mPriority=" + mPriority +
                ", mInstructionSize=" + mInstructionSize +
                ", mInputBuffer=" + mInputBuffer +
                ", mOutputBuffer=" + mOutputBuffer +
                ", mTemporaryBuffer=" + mTemporaryBuffer +
                ", mInMemory=" + mInMemory +
                ", mInstructionLocationInMemory=" + mInstructionLocationInMemory +
                ", mDataLocationInMemory=" + mDataLocationInMemory +
                '}';
    }
}

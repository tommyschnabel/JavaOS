package edu.ksu.operatingsystems.javaos.scheduling;

public interface LongTermScheduler {

    /**
     * Loads the process into the first found space in RAM ( currently )
     * @param processID The ID of the process to be added to RAM
     */
    void loadProcessInMemory(int processID);

    /**
     * Runs regular scheduling cycle. Checks to see if there is room
     * for any processes and moves them over if there is
     */
    void scheduleIfNecessary();

    /**
     * Sees whether all the processes on disk are finished
     * @return Whether all processes are finished
     */
    boolean allProcessesFinished();
}

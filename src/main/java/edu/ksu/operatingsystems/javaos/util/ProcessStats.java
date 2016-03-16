package edu.ksu.operatingsystems.javaos.util;

import edu.ksu.operatingsystems.javaos.storage.Ram;

public class ProcessStats {

    //NOTE: If you add something here don't forget to regenerate #toString
    private int id;
    private int priority;
    private long timeSpentReady;
    private long timeSpentWaiting;
    private long timeSpentExecuting;
    private long lifetime;
    private int numberOfIoOperationsMade;
    private double ramSpaceUsed;

    private ProcessStats(
            int id,
            int priority,
            long timeSpentReady,
            long timeSpentWaiting,
            long timeSpentExecuting,
            int numberOfIoOperationsMade,
            double ramSpaceUsed
    ) {
        this.id = id;
        this.priority = priority;
        this.timeSpentWaiting = timeSpentWaiting;
        this.timeSpentReady = timeSpentReady;
        this.timeSpentExecuting = timeSpentExecuting;
        this.numberOfIoOperationsMade = numberOfIoOperationsMade;
        this.ramSpaceUsed = ramSpaceUsed;
        this.lifetime = timeSpentWaiting + timeSpentReady + timeSpentExecuting;
    }

    public static class Builder {

        private int id;
        private int priority;
        private long timeSpentReady;
        private long timeSpentWaiting;
        private long timeSpentExecuting;
        private int numberOfIoOperationsMade;
        private double ramSpaceUsed;

        public ProcessStats build() {
            return new ProcessStats(
                    id,
                    priority,
                    timeSpentReady,
                    timeSpentWaiting,
                    timeSpentExecuting,
                    numberOfIoOperationsMade,
                    ramSpaceUsed
            );
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setTimeSpentReady(long timeSpentReady) {
            this.timeSpentReady = timeSpentReady;
            return this;
        }

        public Builder setTimeSpentWaiting(long timeSpentWaiting) {
            this.timeSpentWaiting = timeSpentWaiting;
            return this;
        }

        public Builder setTimeSpentExecuting(long timeSpentExecuting) {
            this.timeSpentExecuting = timeSpentExecuting;
            return this;
        }

        public Builder setNumberOfIoOperationsMade(int numberOfIoOperationsMade) {
            this.numberOfIoOperationsMade = numberOfIoOperationsMade;
            return this;
        }

        //TODO: Once we move to using pages this will need to be refactored. However, for now we are pulling the entire program into RAM so this calculation is simple.

        public Builder setAmountOfRAMUsed(int ramSpaceUsed) {
            this.ramSpaceUsed = ((double)ramSpaceUsed/8192 * 100);
            return this;
        }
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public long getTimeSpentReady() {
        return timeSpentReady;
    }

    public long getTimeSpentWaiting() {
        return timeSpentWaiting;
    }

    public long getTimeSpentExecuting() {
        return timeSpentExecuting;
    }

    public int getNumberOfIoOperationsMade() {
        return numberOfIoOperationsMade;
    }

    public double getPercentageOfRamSpaceUsed() { return ramSpaceUsed; }

    public long getLifetime() {
        return lifetime;
    }

    @Override
    public String toString() {
        return "ProcessStats{" +
                "id=" + id +
                ", priority=" + priority +
                ", timeSpentReady=" + timeSpentReady +
                ", timeSpentWaiting=" + timeSpentWaiting +
                ", timeSpentExecuting=" + timeSpentExecuting +
                ", lifetime=" + lifetime +
                ", numberOfIoOperationsMade=" + numberOfIoOperationsMade +
                ", percentageOfRamUsed=" + ramSpaceUsed + "%" +
                '}';
    }
}

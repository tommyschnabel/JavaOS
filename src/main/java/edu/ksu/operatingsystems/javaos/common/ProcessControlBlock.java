package edu.ksu.operatingsystems.javaos.common;

public class ProcessControlBlock {

    Integer pid;
    Integer currentInstructionLocation;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCurrentInstructionLocation() {
        return currentInstructionLocation;
    }

    public void setCurrentInstructionLocation(Integer currentInstructionLocation) {
        this.currentInstructionLocation = currentInstructionLocation;
    }
}

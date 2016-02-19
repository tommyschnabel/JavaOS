package edu.ksu.operatingsystems.javaos.storage;

public interface Ram {

    /**
     * Reads from the starting location into the buffer.
     * @param buffer The buffer to read into
     * @param startLocation The location in memory to start at
     */
    void read(Byte[] buffer, Integer startLocation);

    /**
     * Writes all of the {@link Byte}s from the buffer into memory
     * starting from the starting location to the end of the buffer
     * @param buffer The buffer to read from
     * @param startLocation The location in memory to start with
     */
    void write(Byte[] buffer, Integer startLocation);
}

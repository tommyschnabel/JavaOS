package edu.ksu.operatingsystems.javaos.util;

import edu.ksu.operatingsystems.javaos.driver.DriverType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class StatHelper {

    private static final String OUTPUT_FILE = "results.txt";

    private long averageWaitTime;
    private long averageReadyTime;
    private long averageExecutionTime;
    private long averageLifetime;
    private long averageNumberOfIoOperations;

    private DriverType driverType;

    public void totalStatsAndWriteToFile(
            DriverType type,
            List<ProcessStats> results,
            boolean overwriteFile
    ) {

        this.driverType = type;

        averageWaitTime = 0;
        averageReadyTime = 0;
        averageExecutionTime = 0;
        averageLifetime = 0;
        averageNumberOfIoOperations = 0;

        for (ProcessStats stat : results) {
            averageWaitTime += stat.getTimeSpentWaiting();
            averageReadyTime += stat.getTimeSpentReady();
            averageExecutionTime += stat.getTimeSpentExecuting();
            averageLifetime += stat.getLifetime();
            averageNumberOfIoOperations += stat.getNumberOfIoOperationsMade();
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(type+"."+OUTPUT_FILE, !overwriteFile));

            if (overwriteFile) {
                out.println("NOTE: All times are in milliseconds");
                out.println();
                out.println();
            }

            out.println(getAveragesString());
            out.println();
            for (ProcessStats stat : results) {
                out.println(stat);
            }

            out.println();
            out.println();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getAveragesString() {
        return "AverageTime{" +
                "driverType=" + driverType +
                ", averageWaitTime=" + averageWaitTime +
                ", averageReadyTime=" + averageReadyTime +
                ", averageExecutionTime=" + averageExecutionTime +
                ", averageLifetime=" + averageLifetime +
                ", averageNumberOfIoOperations=" + averageNumberOfIoOperations +
                '}';
    }
}

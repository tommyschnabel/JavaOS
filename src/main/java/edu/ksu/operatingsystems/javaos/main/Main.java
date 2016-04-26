package edu.ksu.operatingsystems.javaos.main;

import edu.ksu.operatingsystems.javaos.driver.DriverType;
import edu.ksu.operatingsystems.javaos.driver.OSDriver;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.util.StatHelper;

public class Main {

    public static void main(String[] args) {
        DriverType driverType = DriverType.FIFO;
        int numberOfCores = 1;
        OSDriver driver = new OSDriver(driverType, numberOfCores);
        executeDriver(driver);
        StatHelper statHelper = new StatHelper();
        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);

        driverType = DriverType.FIFO;
        numberOfCores = 4;
        driver = new OSDriver(driverType, numberOfCores);
        executeDriver(driver);
        statHelper = new StatHelper();
        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);

        driverType = DriverType.Priority;
        numberOfCores = 1;
        driver = new OSDriver(driverType, numberOfCores);
        executeDriver(driver);
        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);

        driverType = DriverType.Priority;
        numberOfCores = 4;
        driver = new OSDriver(driverType, numberOfCores);
        executeDriver(driver);
        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);

//        driverType = DriverType.SJF;
//        numberOfCores = 1;
//        driver = new OSDriver(driverType, numberOfCores);
//        executeDriver(driver);
//        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);
//
//        driverType = DriverType.SJF;
//        numberOfCores = 4;
//        driver = new OSDriver(driverType, numberOfCores);
//        executeDriver(driver);
//        statHelper.totalStatsAndWriteToFile(driverType, numberOfCores, driver.generateStats(), true);
//        System.out.println("Check the different results.txt files for statistics on the run.");
    }

    private static void executeDriver(OSDriver driver) {

        driver.execute();

        for (ProcessControlBlock pcb : driver.getDisk().getProcesses()) {
            if (pcb != null) {
//                System.out.println(pcb.generateStats());
                pcb.generateStats();
            }
        }
        System.out.println("Finished executing with driver type= "
                + driver.getDriverType()
                + " and number of cores= "
                + driver.getNumberOfCores());
    }
}

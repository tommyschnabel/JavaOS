package edu.ksu.operatingsystems.javaos.main;

import edu.ksu.operatingsystems.javaos.driver.DriverType;
import edu.ksu.operatingsystems.javaos.driver.OSDriver;
import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.util.StatHelper;

public class Main {

    public static void main(String[] args) {
        DriverType driverType = DriverType.FIFO;
        OSDriver driver = new OSDriver(driverType, 4);
        executeDriver(driver);
        StatHelper statHelper = new StatHelper();
        statHelper.totalStatsAndWriteToFile(driverType, driver.generateStats(), true);

        driverType = DriverType.Priority;
        driver = new OSDriver(driverType, 4);
        executeDriver(driver);
        statHelper.totalStatsAndWriteToFile(driverType, driver.generateStats(), true);

//        driverType = DriverType.SJF;
//        driver = new OSDriver(driverType);
//        executeDriver(driver);
//        statHelper.totalStatsAndWriteToFile(driverType, driver.generateStats(), true);

        System.out.println("Check the different results.txt files for statistics on the run.");
    }

    private static void executeDriver(OSDriver driver) {

        driver.execute();

        for (ProcessControlBlock pcb : driver.getDisk().getProcesses()) {
            if (pcb != null) {
//                System.out.println(pcb.generateStats());
                pcb.generateStats();
            }
        }
    }
}

package edu.ksu.operatingsystems.javaos.driver;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.cpu.DefaultCpu;
import edu.ksu.operatingsystems.javaos.scheduling.*;
import edu.ksu.operatingsystems.javaos.storage.*;
import edu.ksu.operatingsystems.javaos.util.ProcessStats;

import java.util.ArrayList;
import java.util.List;

public class OSDriver {

    private static int NUBMER_OF_CORES = 4;
	
	private Loader loader;
	private LongTermScheduler longTermScheduler;
	private ShortTermScheduler shortTermScheduler;
	private Cpu[] cpus;
    private Disk disk;
    private Ram ram;

    public OSDriver(DriverType driverType) {

        disk = new DefaultDisk();
        ram = new DefaultRam();

        cpus = new Cpu[NUBMER_OF_CORES];
        for (int i = 0; i < NUBMER_OF_CORES; i++) {
            cpus[i] = new DefaultCpu(ram);
        }

        Dispatcher dispatcher = new DefaultDispatcher();

        loader = new DefaultLoader(disk);

        if (driverType == DriverType.FIFO) {

            longTermScheduler = new FIFOLongTermScheduler(disk, ram);
            shortTermScheduler = new FIFOShortTermScheduler(ram, dispatcher, cpus);

        } else if (driverType == DriverType.Priority) {

            longTermScheduler = new PriorityLongTermScheduler(disk, ram);
            shortTermScheduler = new PriorityShortTermScheduler(ram, dispatcher, cpus);
        }
	}

    public void execute() {

        if (!loader.load("Program-File.txt")) {
            throw new RuntimeException("Couldn't load file");
        }

        while (!longTermScheduler.allProcessesFinished()) {
            longTermScheduler.scheduleIfNecessary();
            shortTermScheduler.scheduleIfNecessary();
            for (Cpu cpu : cpus) {
                cpu.run();
            }
            shortTermScheduler.cleanupFinishedProcesses(); //This writes out the results for the last finished process.
        }
    }

    public List<ProcessStats> generateStats() {
        List<ProcessStats> stats = new ArrayList<ProcessStats>();
        for (ProcessControlBlock pcb : disk.getProcesses()) {
            stats.add(pcb.generateStats());
        }

        return stats;
    }

    public Loader getLoader() {
        return loader;
    }

    public LongTermScheduler getLongTermScheduler() {
        return longTermScheduler;
    }

    public ShortTermScheduler getShortTermScheduler() {
        return shortTermScheduler;
    }

    public Cpu[] getCpus() {
        return cpus;
    }

    public Disk getDisk() {
        return disk;
    }

    public Ram getRam() {
        return ram;
    }
}

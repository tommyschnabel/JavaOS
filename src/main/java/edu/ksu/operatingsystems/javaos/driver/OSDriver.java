package edu.ksu.operatingsystems.javaos.driver;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.cpu.DefaultCpu;
import edu.ksu.operatingsystems.javaos.scheduling.*;
import edu.ksu.operatingsystems.javaos.storage.*;
import edu.ksu.operatingsystems.javaos.util.ProcessStats;

import java.util.ArrayList;
import java.util.List;

public class OSDriver {
	
	private Loader loader;
	private LongTermScheduler longTermScheduler;
	private ShortTermScheduler shortTermScheduler;
	private Cpu cpu;
    private Disk disk;
    private Ram ram;

    public OSDriver(DriverType driverType) {

        disk = new DefaultDisk();
        ram = new DefaultRam();

        cpu = new DefaultCpu(ram);
        Dispatcher dispatcher = new DefaultDispatcher(cpu);

        loader = new DefaultLoader(disk);

        if (driverType == DriverType.FIFO) {

            longTermScheduler = new FIFOLongTermScheduler(disk, ram);
            shortTermScheduler = new FIFOShortTermScheduler(ram, dispatcher);

        } else if (driverType == DriverType.Priority) {

            longTermScheduler = new PriorityLongTermScheduler(disk, ram);
            shortTermScheduler = new PriorityShortTermScheduler(ram, dispatcher);
        }
	}

    public void execute() {

        if (!loader.load("Program-File.txt")) {
            throw new RuntimeException("Couldn't load file");
        }

        while (!longTermScheduler.allProcessesFinished()) {
            longTermScheduler.scheduleIfNecessary();
            shortTermScheduler.scheduleIfNecessary();
            cpu.run();
        }
    }

    public List<ProcessStats> generateStats() {
        List<ProcessStats> stats = new ArrayList<>();
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

    public Cpu getCpu() {
        return cpu;
    }

    public Disk getDisk() {
        return disk;
    }

    public Ram getRam() {
        return ram;
    }
}

package edu.ksu.operatingsystems.javaos.driver;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.cpu.DefaultCpu;
import edu.ksu.operatingsystems.javaos.scheduling.FIFOLongTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.FIFOShortTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.LongTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.ShortTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.*;

public class OSDriver {
	
	private Loader loader;
	private LongTermScheduler longTermScheduler;
	private ShortTermScheduler shortTermScheduler;
	private Cpu cpu;

    private OSDriver(){
        Disk disk = new DefaultDisk();
        Ram ram = new DefaultRam();

        cpu = new DefaultCpu(ram);
        loader = new DefaultLoader(disk);
        longTermScheduler = new FIFOLongTermScheduler(disk, ram);
        shortTermScheduler = new FIFOShortTermScheduler(ram, cpu);
	}

    public static void main(String[] args) {

        OSDriver osDriver = new OSDriver();
        if (!osDriver.getLoader().load("Program-File.txt")) {
            throw new RuntimeException("Couldn't load file");
        }

        while (true) {
            osDriver.getLongTermScheduler().scheduleIfNecessary();
            osDriver.getShortTermScheduler().scheduleIfNecessary();
            osDriver.getCpu().run();
    	}
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public LongTermScheduler getLongTermScheduler() {
        return longTermScheduler;
    }

    public void setLongTermScheduler(LongTermScheduler longTermScheduler) {
        this.longTermScheduler = longTermScheduler;
    }

    public ShortTermScheduler getShortTermScheduler() {
        return shortTermScheduler;
    }

    public void setShortTermScheduler(ShortTermScheduler shortTermScheduler) {
        this.shortTermScheduler = shortTermScheduler;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }
}

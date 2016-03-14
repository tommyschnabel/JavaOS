package edu.ksu.operatingsystems.javaos.driver;

import edu.ksu.operatingsystems.javaos.cpu.Cpu;
import edu.ksu.operatingsystems.javaos.cpu.DefaultCpu;
import edu.ksu.operatingsystems.javaos.scheduling.DefaultShortTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.FIFOLongTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.LongTermScheduler;
import edu.ksu.operatingsystems.javaos.scheduling.ShortTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.DefaultLoader;
import edu.ksu.operatingsystems.javaos.storage.Disk;
import edu.ksu.operatingsystems.javaos.storage.Loader;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class OSDriver {
	
	private Loader loader;
	private LongTermScheduler longTermScheduler;
	private ShortTermScheduler shortTermScheduler;
	private Cpu cpu;

    private Disk disk;
    private Ram ram;

	private OSDriver(){
		loader = new DefaultLoader();
		longTermScheduler = new FIFOLongTermScheduler();
		shortTermScheduler = new DefaultShortTermScheduler();
		cpu = new DefaultCpu();
	}

    public static void main(String[] args) {

    	// TODO Fix syntax to work with the rest of the project.
        OSDriver osDriver = new OSDriver();
        if (!osDriver.getLoader().load("Program-File.txt")) {
            throw new RuntimeException("Couldn't load file");
        }

        while (true) {
            osDriver.getLongTermScheduler().scheduleIfNecessary();
            osDriver.getShortTermScheduler().scheduleIfNecessary();
//            osDriver.getCpu().
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

package edu.ksu.operatingsystems.javaos.scheduling;

import edu.ksu.operatingsystems.javaos.storage.DefaultDisk;
import edu.ksu.operatingsystems.javaos.storage.DefaultRam;

/**
 * Created by Calvin on 2/22/16.
 */
public interface LongTermScheduler {

    void loadProcessInMemory(int processID, DefaultDisk defaultDisk, DefaultRam defaultRAM);

}

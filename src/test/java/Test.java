/**
 * Created by Calvin on 2/18/16.
 */

import edu.ksu.operatingsystems.javaos.scheduling.LongTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.*;

public class Test {

    public static void main(String[] args) {

        Loader myLoader = new Loader();
        Ram myRam = new Ram();
        myLoader.load("Program-File.txt");
        Disk myDisk = myLoader.getDisk();

        System.out.println("\nDISK\n____");
        myDisk.displayDisk();

        LongTermScheduler myLongTermScheduler = new LongTermScheduler();
        myLongTermScheduler.loadProcessInMemory(1, myDisk, myRam);
        myLongTermScheduler.loadProcessInMemory(2, myDisk, myRam);
        myLongTermScheduler.loadProcessInMemory(6, myDisk, myRam);
        myLongTermScheduler.loadProcessInMemory(4, myDisk, myRam);

        System.out.println("\n");
        Program myProgram = myDisk.findProgram(1);
        myProgram.displayProgram();

        System.out.println("\n\n\n\n\n\n\nMemory\n______");
        myRam.displayRAM();

    }
}

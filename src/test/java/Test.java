/**
 * Created by Calvin on 2/18/16.
 */

import edu.ksu.operatingsystems.javaos.scheduling.DefaultLongTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.*;

public class Test {

    public static void main(String[] args) {

        DefaultLoader myDefaultLoader = new DefaultLoader();
        DefaultRam myDefaultRAM = new DefaultRam();
        myDefaultLoader.load("Program-File.txt");
        DefaultDisk myDefaultDisk = myDefaultLoader.getDefaultDisk();

        System.out.println("\nDISK\n____");
        myDefaultDisk.displayDisk();

        DefaultLongTermScheduler myDefaultLongTermScheduler = new DefaultLongTermScheduler();
        myDefaultLongTermScheduler.loadProcessInMemory(1, myDefaultDisk, myDefaultRAM);
        myDefaultLongTermScheduler.loadProcessInMemory(2, myDefaultDisk, myDefaultRAM);
        myDefaultLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myDefaultRAM);
        myDefaultLongTermScheduler.loadProcessInMemory(4, myDefaultDisk, myDefaultRAM);

        /*
        System.out.println("\n");
        DefaultProgram myDefaultProgram = myDefaultDisk.findProgram(1);
        myDefaultProgram.displayProgram();
        */

        System.out.println("\n\n\n\n\n\n\nMemory\n______");
        myDefaultRAM.displayRAM();

    }
}

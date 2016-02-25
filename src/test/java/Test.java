import edu.ksu.operatingsystems.javaos.scheduling.FIFOLongTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.*;

public class Test {

    public static void main(String[] args) {

        DefaultLoader myDefaultLoader = new DefaultLoader();
        DefaultRam myRAM = new DefaultRam();

        myDefaultLoader.load("Program-File.txt");
        Disk myDefaultDisk = myDefaultLoader.getDefaultDisk();

        System.out.println("\nDISK\n____");
        myDefaultDisk.displayDisk();

        FIFOLongTermScheduler myFIFOLongTermScheduler = new FIFOLongTermScheduler();
        myFIFOLongTermScheduler.loadProcessInMemory(1, myDefaultDisk, myRAM);
        /*myFIFOLongTermScheduler.loadProcessInMemory(2, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(4, myDefaultDisk, myRAM);*/

        /*
        System.out.println("\n");
        Program myProgram = myDefaultDisk.findProgram(1);
        myProgram.displayProgram();
        */

        System.out.println("\n\n\n\n\n\n\nMemory\n______");
        myRAM.displayRAM();

        //myRAM.removeProcessFromMemory(1);
        //myRAM.displayRAM();

    }
}

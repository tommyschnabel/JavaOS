import edu.ksu.operatingsystems.javaos.scheduling.FIFOLongTermScheduler;
import edu.ksu.operatingsystems.javaos.storage.*;

public class Test {

    public static void main(String[] args) {

        DefaultLoader myDefaultLoader = new DefaultLoader();
        DefaultRam myRAM = new DefaultRam();

        myDefaultLoader.load("Program-File.txt");
        Disk myDefaultDisk = myDefaultLoader.getDefaultDisk();

        //System.out.println("\nDISK\n____");
        //myDefaultDisk.displayDisk();

        FIFOLongTermScheduler myFIFOLongTermScheduler = new FIFOLongTermScheduler();
        myFIFOLongTermScheduler.loadProcessInMemory(1, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(2, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(4, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(9, myDefaultDisk, myRAM);

        /*
        System.out.println("\n\n\n\n\n\n\nMemory\n______");
        myRAM.displayPCBList();
        myRAM.displayRAM();
        */

        System.out.println("\n\nMemory with processes with ID 1 and 6 removed\n");
        //myRAM.removeProcessFromMemory(1);
        myRAM.removeProcessFromMemory(6);
        myRAM.removeProcessFromMemory(4);
        myRAM.displayPCBList();
        myRAM.displayRAM();

        myRAM.defrag();

        System.out.println("\n\nPCB list after drag\n");
        myRAM.displayPCBList();

        myRAM.displayRAM();

    }
}

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
        myFIFOLongTermScheduler.loadProcessInMemory(2, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(4, myDefaultDisk, myRAM);

        System.out.println("\n\n\n\n\n\n\nMemory\n______");
        myRAM.displayRAM();

        System.out.println("\n\nMemory with processes with ID 1 and 6 removed\n");
        myRAM.removeProcessFromMemory(1);
        myRAM.removeProcessFromMemory(6);
        myRAM.displayRAM();

        System.out.println("\n\nMemory with processes with ID 1 and 6 re-added. Plus ID 3 and 5\n");
        myFIFOLongTermScheduler.loadProcessInMemory(3, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(5, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myRAM);
        //myFIFOLongTermScheduler.loadProcessInMemory(1, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(13, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(15, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(16, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(11, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(14, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(17, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(23, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(25, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(26, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(30, myDefaultDisk, myRAM);
        //myFIFOLongTermScheduler.loadProcessInMemory(21, myDefaultDisk, myRAM);
        //myFIFOLongTermScheduler.loadProcessInMemory(20, myDefaultDisk, myRAM);
        myRAM.displayRAM();

    }
}

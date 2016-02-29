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

        ProcessControlBlock bugProcess = myDefaultDisk.findProgram(6);
        System.out.println("start location on disk of process 6 " + bugProcess.getInstructionLocationOnDisk());

        FIFOLongTermScheduler myFIFOLongTermScheduler = new FIFOLongTermScheduler();
        myFIFOLongTermScheduler.loadProcessInMemory(1, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(6, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(3, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(11, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(16, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(13, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(21, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(26, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(23, myDefaultDisk, myRAM);


        //System.out.println("\n\n\n\n\n\n\nMemory\n______");
        //myRAM.displayPCBList();
        //myRAM.displayRAM();

        //System.out.println("\n\nMemory with processes with ID 6 removed\n");
        myRAM.removeProcessFromMemory(6);
        myRAM.removeProcessFromMemory(11);
        myRAM.removeProcessFromMemory(16);
        myRAM.removeProcessFromMemory(13);
        myRAM.removeProcessFromMemory(26);
        //myRAM.displayPCBList();
        //myRAM.displayRAM();

        System.out.println("\n\nMemory with processes with ID 15, 17, 2\n");
        myFIFOLongTermScheduler.loadProcessInMemory(15, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(17, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(2, myDefaultDisk, myRAM);
        myRAM.displayPCBList();
        myRAM.displayRAM();


        System.out.println("\n\nMemory with processes with ID 5, 22, 13, 26, 11, 16, 29, 30\n");
        myFIFOLongTermScheduler.loadProcessInMemory(5, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(22, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(13, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(26, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(11, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(16, myDefaultDisk, myRAM);
        myFIFOLongTermScheduler.loadProcessInMemory(29, myDefaultDisk, myRAM);
        //myRAM.defrag();
        //myFIFOLongTermScheduler.loadProcessInMemory(30, myDefaultDisk, myRAM);
        myRAM.displayPCBList();
        myRAM.displayRAM();


        System.out.println("\n\n Trying again but defragging first.");
        myRAM.defrag();
        myFIFOLongTermScheduler.loadProcessInMemory(30, myDefaultDisk, myRAM);
        myRAM.displayPCBList();
        myRAM.displayRAM();
    }
}

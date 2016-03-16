package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class DefaultFetcher implements Fetcher {

	public byte[] fetch(ProcessControlBlock processControlBlock, Ram ram) {
		int address = processControlBlock.getInstructionLocationInMemory();
		String instruction = ram.readValueFromAddress(address, 8, processControlBlock);
		return instruction.getBytes();
	}

}

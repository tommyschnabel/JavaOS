package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;

public class DefaultFetcher implements Fetcher {

	public byte[] fetch(ProcessControlBlock processControlBlock, CpuCache cache) {
		//todo: this fetch probably needs to be pulling from cache memory
		int address = processControlBlock.getCachePointer();
		String instruction = cache.readValueFromAddress(address, 8);
		return instruction.getBytes();
	}

}

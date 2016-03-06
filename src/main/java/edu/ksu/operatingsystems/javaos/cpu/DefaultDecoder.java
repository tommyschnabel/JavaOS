package edu.ksu.operatingsystems.javaos.cpu;

public class DefaultDecoder implements Decoder{

	@Override
	//Assuming byte[] is ascii chars (i.e. 1 is dec 49)
	public int decode(byte[] instruction) {
		int decoded = 0;
		
		//Turning instruction into string value hex
		String hex = new String(instruction);
		
		//Parsing hex as a base 16 number to integer
		decoded = Integer.parseInt(hex,16);
		
		return decoded;
	}

}

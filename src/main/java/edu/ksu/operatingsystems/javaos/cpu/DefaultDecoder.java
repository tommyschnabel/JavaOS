package edu.ksu.operatingsystems.javaos.cpu;


public class DefaultDecoder implements Decoder{

	@Override
	//Assuming byte[] is ascii chars (i.e. 1 is dec 49)
	public String decode(byte[] instruction) {
		
		//Turning instruction into string value hex
		String hex = new String(instruction);
		System.out.println("Instruction: " + hex);

        return addZeroIfNecessary(
                hex,
                Long.toBinaryString(Long.parseLong(hex, 16))
        );
	}

    /**
     * {@link Long#toBinaryString(long)} gets rid of leading 0's.
     * But we depend on there being one if the first char of the
     * instruction is less than 8
     */
    private String addZeroIfNecessary(String instruction, String binary) {
        if (binary.length() < 32) {
            for (int i = 32 - binary.length(); i > 0; i--) {
                binary = "0" + binary;
            }
        }

        return binary;
    }

}

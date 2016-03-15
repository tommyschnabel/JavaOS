package edu.ksu.operatingsystems.javaos.cpu;


public class DefaultDecoder implements Decoder{

	@Override
	//Assuming byte[] is ascii chars (i.e. 1 is dec 49)
	public String decode(byte[] instruction) {
		
		//Turning instruction into string value hex
		String hex = new String(instruction);
		System.out.println("Instruction: " + hex);
		//Parsing hex as a base 16 number to integer
		//int val = Integer.parseInt(hex, 16);


		/* Logic to convert hex to decimal
		String totalString = "";
		String toAdd = "";


		for (int i = 0; i < hex.length(); i++)
		{
			toAdd = "" + Integer.decode("0x" + String.valueOf(hex.charAt(i)));
			if (toAdd.length() == 1)
			{
				toAdd = "0" + toAdd;
			}
			totalString += toAdd;
		}

		return totalString;
		*/

		/* logic to convert hex to binary */
		String s = Integer.toBinaryString(Integer.parseInt(hex, 16));

		return s;


	}

}

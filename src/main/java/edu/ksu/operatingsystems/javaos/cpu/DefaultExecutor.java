package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;
import edu.ksu.operatingsystems.javaos.util.Pair;

public class DefaultExecutor implements Executor {

    private static int WORD_HEX_LENGTH = 8;

    private int[] registers;
    private ProcessControlBlock process;
    private Ram ram;

    public DefaultExecutor(
            Ram ram,
            int[] registers
    ) {
        this.ram = ram;
        this.registers = registers;
    }

    @Override
    public void setProcess(ProcessControlBlock process) {
        this.process = process;
    }

    @Override
    public void execute(String instruction) {
        Pair<Integer, String> result = readFromRight(instruction, 2);
        Integer arithmeticType = result.getFirst();
        instruction = result.getSecond();

        Integer address = null;
        Integer destRegister = null;
        Integer firstRegister = null;
        Integer secondRegister = null;
        Integer baseRegister = null;
        Integer opCode = null;

        switch (arithmeticType) {
            case 0:

                //Get the op code
                result = readFromRight(instruction, 6);
                opCode = result.getFirst();
                instruction = result.getSecond();

                //Get the source register for the first operand
                result = readFromRight(instruction, 4);
                firstRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the source register for the second operand
                result = readFromRight(instruction, 4);
                secondRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the destination register
                result = readFromRight(instruction, 4);
                destRegister = result.getFirst();
                instruction = result.getSecond(); // Not really needed, but for good measure

                doOp(opCode, ram, firstRegister, secondRegister, destRegister, null);
                break;
            case 1:

                //Get the op code
                result = readFromRight(instruction, 6);
                opCode = result.getFirst();
                instruction = result.getSecond();

                //Get the source register for the first operand
                result = readFromRight(instruction, 4);
                baseRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the destination register
                result = readFromRight(instruction, 4);
                destRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the address
                result = readFromRight(instruction, 16);
                address = result.getFirst();
                instruction = result.getSecond(); // Not really needed, but for good measure

                doOp(opCode, ram, baseRegister, destRegister, address);
                break;
            case 2:

                //Get the op code
                result = readFromRight(instruction, 6);
                opCode = result.getFirst();
                instruction = result.getSecond();

                //Get the address
                result = readFromRight(instruction, 24);
                address = result.getFirst();
                instruction = result.getSecond(); // Not really needed, but for good measure

                doOp(opCode, ram, address);
                break;
            case 3:

                //Get the op code
                result = readFromRight(instruction, 6);
                opCode = result.getFirst();
                instruction = result.getSecond();

                //Get the first register
                result = readFromRight(instruction, 4);
                firstRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the second register
                result = readFromRight(instruction, 4);
                secondRegister = result.getFirst();
                instruction = result.getSecond();

                //Get the address
                result = readFromRight(instruction, 16);
                address = result.getFirst();
                instruction = result.getSecond(); // Not really needed, but for good measure

                doOp(opCode, ram, firstRegister, secondRegister, address);
                break;
            default:

                //If an integer bit-shifted 30 bits isn't one of the above we have much bigger problems
                throw new IllegalArgumentException("Unsupported arithmetic operation: " + arithmeticType);
        }

        //Increment the instruction
        //process.setInstructionLocationInMemory(process.getInstructionLocationInMemory() + WORD_HEX_LENGTH);
        process.setInstructionLocationInMemory(process.getInstructionLocationInMemory() + WORD_HEX_LENGTH);

    }

    /**
     * Processes the opCode and does the given operation.
     * @param op The op code
     * @param ram The system ram
     * @param registerAddresses The register addresses, with the address part of the instruction as the last entry, if available
     */
    private void doOp(int op, Ram ram, Integer... registerAddresses) {

        //Arithmetic operation
        if ((op >=4 && op <= 10) || op == 16) {
            int firstOperandRegisterAddress = registerAddresses[0];
            int secondOperandRegisterAddress = registerAddresses[1];
            int destinationRegisterAddress = registerAddresses[2];

            switch (op) {
                case 4: //MOV
                    if (firstOperandRegisterAddress != 0) {
                        registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress];
                    } else {
                        registers[destinationRegisterAddress] = registers[secondOperandRegisterAddress];
                    }
                    return;
                case 5: //ADD
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] + registers[secondOperandRegisterAddress];
                    return;
                case 6: //SUB
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] - registers[secondOperandRegisterAddress];
                    return;
                case 7: //MUL
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] * registers[secondOperandRegisterAddress];
                    return;
                case 8: //DIV
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] / registers[secondOperandRegisterAddress];
                    return;
                case 9: //AND
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] & registers[secondOperandRegisterAddress];
                    return;
                case 10: //OR
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] | registers[secondOperandRegisterAddress];
                    return;
                case 16: //SLT
                    registers[destinationRegisterAddress] = registers[firstOperandRegisterAddress] < registers[secondOperandRegisterAddress] ? 1 : 0;
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        //Conditional and Immediate Branch
        if (op == 2 || op == 3 || (op >= 11 && op <= 15) || op == 17 || op == 19 || (op >= 21 && op <= 26)) {
            int baseRegisterAddress = registerAddresses[0];
            int destinationRegisterAddress = registerAddresses[1];

            Integer lastBits = registerAddresses[2];

            switch (op) {
                case 2: //ST
                   ram.writeValueToAddress(lastBits, Integer.toHexString(registers[baseRegisterAddress]));
                    return;
                case 3: //LW
                    registers[destinationRegisterAddress] = Integer.parseInt(
                        ram.readValueFromAddress(
                            effectiveAddress(registers[baseRegisterAddress], lastBits),
                            8 //Read 8 hex values (32 bits)
                        ),
                        16 //Convert from base 16 (hex)
                    );
                    return;
                case 11: //MOVI
                    if (destinationRegisterAddress == 0) {
                        registers[destinationRegisterAddress] = effectiveAddress(registers[baseRegisterAddress], lastBits);
                    } else {
                        registers[destinationRegisterAddress] = registers[baseRegisterAddress];
                    }
                    return;
                case 12: //ADDI
                    registers[destinationRegisterAddress] += lastBits;
                    return;
                case 13: //MULI
                    registers[destinationRegisterAddress] *= lastBits;
                    return;
                case 14: //DIVI
                    registers[destinationRegisterAddress] /= lastBits;
                    return;
                case 15: //LDI
                    if (baseRegisterAddress == 0) {
                        registers[destinationRegisterAddress] = effectiveAddress(registers[baseRegisterAddress], lastBits);
                    } else {
                        registers[destinationRegisterAddress] = registers[baseRegisterAddress];
                    }
                    return;
                case 17: //SLTI
                    registers[destinationRegisterAddress] = registers[baseRegisterAddress] < lastBits ? 1 : 0;
                    return;
                case 19: //NOP
                    //Intentionally blank
                    return;
                case 21: //BEQ
                    if (registers[baseRegisterAddress] == destinationRegisterAddress) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                case 22: //BNE
                    if (registers[baseRegisterAddress] != destinationRegisterAddress) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                case 23: //BEZ
                    if (registers[baseRegisterAddress] == 0) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                case 24: //BNZ
                    if (registers[baseRegisterAddress] != 0) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                case 25: //BGZ
                    if (registers[baseRegisterAddress] > 0) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                case 26: //BLZ
                    if (registers[baseRegisterAddress] < 0) {
                        process.setInstructionLocationInMemory(effectiveAddress(registers[baseRegisterAddress], lastBits));
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        //Unconditional jump
        if (op == 18 || op == 20) {
            int address = registerAddresses[0];

            switch (op) {
                case 18: //HLT
                    process.setInstructionLocationInMemory(-1);
                    return;
                case 20: //JMP
                    process.setInstructionLocationInMemory(effectiveAddress(0, address));
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        //IO
        if (op == 0 || op == 1) {
            int accumulatorPosition = 0;

            switch (op) {
                case 0: //RD
                    registers[accumulatorPosition] = process.getInputBuffer();
                    return;
                case 1: //WR
                    ram.writeValueToAddress(
                            process.getOutputBuffer(),
                            String.valueOf(registers[accumulatorPosition])
                    );
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        throw new IllegalArgumentException("Unsupported operation: " + op);
    }

    private Integer effectiveAddress(Integer baseRegister, Integer address) {
        return baseRegister + address + process.getDataLocationInMemory();
    }

    private Pair<Integer, String> readFromRight(String readFrom, int numberOfCharacters) {
        Integer value = Integer.parseInt(readFrom.substring(0, numberOfCharacters), 2);
        String result = readFrom.substring(numberOfCharacters);
        return new Pair<Integer, String>(value, result);
    }
}

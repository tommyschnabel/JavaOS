package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.ProcessControlBlock;
import edu.ksu.operatingsystems.javaos.storage.Ram;
import edu.ksu.operatingsystems.javaos.util.Pair;

public class DefaultExecutor implements Executor {

    private static int WORD_HEX_LENGTH = 8;

    private Integer[] registers;
    private ProcessControlBlock process;
    private Ram ram;

    public DefaultExecutor(
            Ram ram,
            Integer[] registers
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
        process.setInstructionLocationInMemory(process.getInstructionLocationInMemory() + WORD_HEX_LENGTH);
    }

    /**
     * Processes the opCode and does the given operation.
     * @param op The op code
     * @param ram The system ram
     * @param registerAddresses The register addresses, with the address part of the instruction as the last entry, if available
     */
    private void doOp(int op, Ram ram, Integer... registerAddresses) {
        Integer instructionPosition = process.getInstructionLocationInMemory();

        //Arithmetic operation
        if ((op >=4 && op <= 10) || op == 16) {
            int firstOperandRegisterAddress = registerAddresses[0];
            int secondOperandRegisterAddress = registerAddresses[1];
            int destinationRegisterAddress = registerAddresses[2];

            Integer first = registers[firstOperandRegisterAddress];
            Integer second = registers[secondOperandRegisterAddress];
            Integer destination = registers[destinationRegisterAddress];

            switch (op) {
                case 4: //MOV
                    if (firstOperandRegisterAddress != 0) {
                        destination = first;
                    } else {
                        destination = second;
                    }
                    return;
                case 5: //ADD
                    destination = first + second;
                    return;
                case 6: //SUB
                    destination = first - second;
                    return;
                case 7: //MUL
                    destination = first * second;
                    return;
                case 8: //DIV
                    destination = first / second;
                    return;
                case 9: //AND
                    destination = first & second;
                    return;
                case 10: //OR
                    destination = first | second;
                    return;
                case 16: //SLT
                    destination = first < second ? 1 : 0;
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        //Conditional and Immediate Branch
        if ((op >= 11 && op <= 15) || op == 17 || op == 19 || (op >= 21 && op <= 26)) {
            int baseRegisterAddress = registerAddresses[0];
            int destinationRegisterAddress = registerAddresses[1];

            Integer baseRegister = registers[baseRegisterAddress];
            Integer destinationRegister = registers[destinationRegisterAddress];
            Integer lastBits = registerAddresses[2];

            switch (op) {
                case 2: //ST
                   ram.writeValueToAddress(lastBits, Integer.toHexString(baseRegister));
                    return;
                case 3: //LW
                    destinationRegister = Integer.parseInt(
                        ram.readValueFromAddress(
                            effectiveAddress(baseRegister, lastBits),
                            8 //Read 8 hex values (32 bits)
                        ),
                        16 //Convert from base 16 (hex)
                    );
                    return;
                case 11: //MOVI
                    if (baseRegisterAddress == 0) {
                        destinationRegister = effectiveAddress(baseRegister, lastBits);
                    } else {
                        destinationRegister = baseRegister;
                    }
                    return;
                case 12: //ADDI
                    destinationRegister += lastBits;
                    return;
                case 13: //MULI
                    destinationRegister *= lastBits;
                    return;
                case 14: //DIVI
                    destinationRegister /= lastBits;
                    return;
                case 15: //LDI
                    if (baseRegisterAddress == 0) {
                        destinationRegister = effectiveAddress(baseRegister, lastBits);
                    } else {
                        destinationRegister = baseRegister;
                    }
                    return;
                case 17: //SLTI
                    destinationRegister = baseRegister < lastBits ? 1 : 0;
                    return;
                case 19: //NOP
                    //Intentionally blank
                    return;
                case 21: //BEQ
                    if (baseRegister.equals(destinationRegisterAddress)) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
                    }
                    return;
                case 22: //BNE
                    if (!baseRegister.equals(destinationRegisterAddress)) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
                    }
                    return;
                case 23: //BEZ
                    if (baseRegister.equals(0)) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
                    }
                    return;
                case 24: //BNZ
                    if (!baseRegister.equals(0)) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
                    }
                    return;
                case 25: //BGZ
                    if (baseRegister > 0) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
                    }
                    return;
                case 26: //BLZ
                    if (baseRegister < 0) {
                        instructionPosition = effectiveAddress(baseRegister, lastBits);
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
                    instructionPosition = -1;
                    return;
                case 20: //JMP
                    instructionPosition = effectiveAddress(0, address);
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        //IO
        if (op == 0 || op == 1) {
            Integer accumulator = registers[0];

            switch (op) {
                case 0: //RD
                    accumulator = process.getInputBuffer();
                    return;
                case 1: //WR
                    process.setOutputBuffer(accumulator);
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
        return new Pair<>(value, result);
    }
}

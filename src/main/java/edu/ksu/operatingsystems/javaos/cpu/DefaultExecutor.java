package edu.ksu.operatingsystems.javaos.cpu;

import edu.ksu.operatingsystems.javaos.storage.Ram;

public class DefaultExecutor implements Executor {

    private Integer[] registers;
    private Integer inputBuffer;
    private Integer outputBuffer;
    private Integer tempBuffer;

    public DefaultExecutor(Integer[] registers) {
        this.registers = registers;
    }

    public void setBuffers(Integer inputBuffer, Integer outputBuffer, Integer tempBuffer) {
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        this.tempBuffer = tempBuffer;
    }

    @Override
    public void execute(Integer instruction, Integer instructionPosition, Ram ram) {
        Integer arithmeticType = instruction >> 30;

        Integer address;
        Integer destRegister;
        Integer firstRegister;
        Integer secondRegister;
        Integer baseRegister;
        Integer opCode;

        //Increment the instruction
        //noinspection UnusedAssignment
        instructionPosition++;

        switch (arithmeticType) {
            case 0:

                //Last 12 bits never used
                address = readXBits(instruction, 12);
                instruction = instruction >> 12;

                //Get the destination register
                destRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the source register for the second operand
                secondRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the source register for the first operand
                firstRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the op code
                opCode = readXBits(instruction, 6);
                instruction = instruction >> 6; // Not really needed, but for good measure

                doOp(opCode, ram, instructionPosition, firstRegister, secondRegister, destRegister, address);
                return;
            case 1:

                //Get the address
                address = readXBits(instruction, 16);
                instruction = instruction >> 16;

                //Get the destination register
                destRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the source register for the first operand
                baseRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the op code
                opCode = readXBits(instruction, 6);
                instruction = instruction >> 6; // Not really needed, but for good measure

                doOp(opCode, ram, instructionPosition, baseRegister, destRegister, address);
                return;
            case 2:

                //Get the address
                address = readXBits(instruction, 24);
                instruction = instruction >> 24;

                //Get the op code
                opCode = readXBits(instruction, 6);
                instruction = instruction >> 6; // Not really needed, but for good measure

                doOp(opCode, ram, instructionPosition, address);
                return;
            case 3:

                //Get the address
                address = readXBits(instruction, 16);
                instruction = instruction >> 16;

                //Get the second register
                secondRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the first register
                firstRegister = readXBits(instruction, 4);
                instruction = instruction >> 4;

                //Get the op code
                opCode = readXBits(instruction, 6);
                instruction = instruction >> 6; // Not really needed, but for good measure

                doOp(opCode, ram, instructionPosition, firstRegister, secondRegister, address);
                return;
            default:

                //If an integer bit-shifted 30 bits isn't one of the above we have much bigger problems
                throw new IllegalArgumentException("Unsupported arithmetic operation: " + arithmeticType);
        }
    }

    /**
     * Processes the opCode and does the given operation.
     * @param op The op code
     * @param ram The system ram
     * @param registerAddresses The register addresses, with the address part of the instruction as the last entry, if available
     */
    //TODO Finish this
    private void doOp(int op, Ram ram, Integer instructionPosition, Integer... registerAddresses) {

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
                    // TODO Ask prof about this, specs are unclear
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

            switch (op) {
                case 2: //ST
                    return;
                case 3: //LW
                    return;
                case 11: //MOVI
                    return;
                case 12: //ADDI
                    return;
                case 13: //MULI
                    return;
                case 14: //DIVI
                    return;
                case 15: //LDI
                    return;
                case 17: //SLTI
                    return;
                case 19: //NOP
                    return;
                case 21: //BEQ
                    return;
                case 22: //BNE
                    return;
                case 23: //BEZ
                    return;
                case 24: //BNZ
                    return;
                case 25: //BGZ
                    return;
                case 26: //BLZ
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
                    instructionPosition = address;
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
                    accumulator = inputBuffer;
                    return;
                case 1: //WR
                    outputBuffer = accumulator;
                    return;
                default:
                    throw new IllegalArgumentException("Looks like the condition for op distribution is wrong");
            }
        }

        throw new IllegalArgumentException("Unsupported operation: " + op);
    }

    /**
     * Reads a number of bits off the right side of an integer
     * @param from The int to read from
     * @param numBits The number of bits to read
     * @return The result
     */
    private int readXBits(int from, int numBits) {
        return from - (from >> numBits << numBits);
    }
}

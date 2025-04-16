package bytecode_parser.bytecode.instruction.type;

import bytecode_parser.bytecode.instruction.BytecodeInstruction;
import bytecode_parser.bytecode.instruction.FinalInstruction;

public enum FinalBytecodeInstructionType implements BytecodeInstructionType {
    // byte primitives
    U1, U2, U4;

    private final BytecodeInstruction instruction;

    FinalBytecodeInstructionType() {
        this.instruction = new FinalInstruction(this);
    }

    @Override
    public BytecodeInstruction instruction() {
        return instruction;
    }
}

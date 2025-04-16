package bytecode_parser.bytecode.instruction;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

@Getter
public final class SingleInstruction extends BytecodeInstruction {
    private final BytecodeInstruction instruction;

    public SingleInstruction(BytecodeInstructionType instructionType, BytecodeInstruction instruction) {
        super(instructionType);
        this.instruction = instruction;
    }
}

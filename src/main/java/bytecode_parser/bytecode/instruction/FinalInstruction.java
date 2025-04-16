package bytecode_parser.bytecode.instruction;

import bytecode_parser.bytecode.instruction.type.FinalBytecodeInstructionType;
import lombok.Getter;

@Getter
public final class FinalInstruction extends BytecodeInstruction {
    public final FinalBytecodeInstructionType finalInstructionType;
    public FinalInstruction(FinalBytecodeInstructionType finalInstructionType) {
        super(null);
        this.finalInstructionType = finalInstructionType;
    }
}

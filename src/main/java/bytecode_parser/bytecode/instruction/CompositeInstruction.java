package bytecode_parser.bytecode.instruction;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

import java.util.List;

@Getter
public final class CompositeInstruction extends BytecodeInstruction {
    private final List<BytecodeInstruction> instructionList;

    public CompositeInstruction(BytecodeInstructionType instructionType, List<BytecodeInstruction> instructionList) {
        super(instructionType);
        this.instructionList = instructionList;
    }
}

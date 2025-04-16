package bytecode_parser.bytecode.instruction;


import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

@Getter
public abstract sealed class BytecodeInstruction
        permits ArrayInstruction, FinalInstruction, CompositeInstruction, SingleInstruction, SwitchInstruction {
    public final BytecodeInstructionType instructionType;

    public BytecodeInstruction(BytecodeInstructionType instructionType) {
        this.instructionType = instructionType;
    }
}

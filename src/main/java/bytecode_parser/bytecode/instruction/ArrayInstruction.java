package bytecode_parser.bytecode.instruction;

import bytecode_parser.bytecode.component.CompositeBytecodeComponent;
import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

import java.util.function.Function;

@Getter
public final class ArrayInstruction extends BytecodeInstruction {
    public ArrayInstruction(BytecodeInstructionType instructionType,
                            Function<CompositeBytecodeComponent, Integer> lengthAttributeFunction) {
        super(instructionType);
        this.lengthAttributeFunction = lengthAttributeFunction;
    }

    private final Function<CompositeBytecodeComponent, Integer> lengthAttributeFunction;
}

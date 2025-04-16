package bytecode_parser.bytecode.instruction;

import bytecode_parser.bytecode.component.SingleBytecodeComponent;
import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

import java.util.function.Function;

@Getter
public final class SwitchInstruction extends BytecodeInstruction {
    public SwitchInstruction(BytecodeInstructionType instructionType,
                             SingleInstruction switchAttributeInstruction,
                             Function<SingleBytecodeComponent, BytecodeInstruction> switchResultFunction) {
        super(instructionType);
        this.switchAttributeInstruction = switchAttributeInstruction;
        this.switchResultFunction = switchResultFunction;
    }

    private final SingleInstruction switchAttributeInstruction;

    private final Function<SingleBytecodeComponent, BytecodeInstruction> switchResultFunction;
}

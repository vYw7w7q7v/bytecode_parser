package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;

public abstract sealed class BytecodeComponent
        permits CompositeBytecodeComponent, FinalBytecodeComponent,
        ArrayBytecodeComponent, SingleBytecodeComponent {
    public final BytecodeInstructionType componentType;
    public BytecodeComponent(BytecodeInstructionType componentType) {
        this.componentType = componentType;
    }

}

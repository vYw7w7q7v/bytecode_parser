package bytecode_parser.bytecode.instruction.type;

import bytecode_parser.bytecode.instruction.BytecodeInstruction;

public sealed interface BytecodeInstructionType permits ComplexBytecodeInstructionType,
        FinalBytecodeInstructionType,
        CPConstantBytecodeInstructionType {
    //String instructionName();
    BytecodeInstruction instruction();

    default ByteInfoType byteInfoType() {
        return ByteInfoType.UNDEFINED_INFO;
    }
}

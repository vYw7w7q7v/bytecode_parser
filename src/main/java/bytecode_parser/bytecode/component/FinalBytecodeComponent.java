package bytecode_parser.bytecode.component;


import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import bytecode_parser.byte_buffer.ByteUtils;

import static bytecode_parser.byte_buffer.ByteUtils.bytesAsString;

public final class FinalBytecodeComponent extends BytecodeComponent {

    private final byte[] bytes;

    public FinalBytecodeComponent(byte[] bytes, BytecodeInstructionType componentType) {
        super(componentType);
        this.bytes = bytes;
    }

    public int asNumber() {
        return ByteUtils.asNumber(hexFormat());
    }

    public String hexFormat() {
        return bytesAsString(bytes);
    }

    @Override
    public String toString() {
        return hexFormat();
    }
}

package bytecode_parser.bytecode.component;


import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import bytecode_parser.bytes.ByteUtils;

import static bytecode_parser.bytes.ByteUtils.bytesAsString;
import static java.text.MessageFormat.format;

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

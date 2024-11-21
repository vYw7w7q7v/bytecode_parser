package byte_code_parser.bytecode;


import lombok.Getter;

import static byte_code_parser.bytes.ByteUtils.bytesAsString;
import static java.text.MessageFormat.format;

public sealed class SimpleBytecodeComponent implements BytecodeComponent
        permits NumericBytecodeComponent {
    protected final byte[] bytes;
    protected final BytecodeComponentType componentType;

    public SimpleBytecodeComponent(byte[] bytes, BytecodeComponentType componentType) {
        this.bytes = bytes;
        this.componentType = componentType;
    }

    public String hexFormat() {
        return bytesAsString(bytes);
    }

    @Override
    public String toString() {
        return format("{0}: {1}", componentType, hexFormat());
    }
}

package bytecode_parser.bytecode.component;


import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

import static bytecode_parser.bytes.ByteUtils.bytesAsString;
import static java.text.MessageFormat.format;

public sealed class SimpleBytecodeComponent extends BytecodeComponent
        permits NumericBytecodeComponent {

    protected final byte[] bytes;

    public SimpleBytecodeComponent(byte[] bytes, DefinedBytecodeComponentType componentType) {
        super(componentType);
        this.bytes = bytes;
    }

    public String hexFormat() {
        return bytesAsString(bytes);
    }

    @Override
    public String toString() {
        return format("{0}: {1}", componentType, hexFormat());
    }
}

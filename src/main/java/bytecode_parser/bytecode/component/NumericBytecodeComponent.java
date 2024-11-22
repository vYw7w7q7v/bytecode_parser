package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;
import bytecode_parser.bytes.ByteUtils;

import static java.text.MessageFormat.format;

public final class NumericBytecodeComponent extends SimpleBytecodeComponent {
    public NumericBytecodeComponent(byte[] bytes, DefinedBytecodeComponentType componentType) {
        super(bytes, componentType);
    }

    public int asNumber() {
        return ByteUtils.asNumber(hexFormat());
    }

    @Override
    public String toString() {
        return format("{0}: {1} ({2})", componentType, hexFormat(), asNumber());
    }
}
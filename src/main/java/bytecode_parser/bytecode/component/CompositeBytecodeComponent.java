package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

import java.text.MessageFormat;
import java.util.EnumMap;

public sealed class CompositeBytecodeComponent extends BytecodeComponent permits JavaProgram {
    protected final EnumMap<DefinedBytecodeComponentType, BytecodeComponent> components = new EnumMap<>(DefinedBytecodeComponentType.class);

    public void put(DefinedBytecodeComponentType key, BytecodeComponent value) {
        components.put(key, value);
    }

    public CompositeBytecodeComponent(DefinedBytecodeComponentType componentType) {
        super(componentType);
    }

    public Object get(DefinedBytecodeComponentType bytecodeComponentType) {
        return components.get(bytecodeComponentType);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(MessageFormat.format("""
                <{0}>[
                """, componentType));
        for (BytecodeComponent component : components.values()) {
            builder.append(component).append("\n");
        }
        builder.append("] =====");
        return builder.toString();
    }


}

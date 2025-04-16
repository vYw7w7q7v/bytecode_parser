package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

import static bytecode_parser.utils.StringUtils.addPrefixForEachLine;

public sealed class CompositeBytecodeComponent extends BytecodeComponent permits JavaProgram {

    @Getter
    protected final SequencedMap<BytecodeInstructionType, BytecodeComponent> components = new LinkedHashMap<>();

    public void put(BytecodeComponent value) {
        components.put(value.componentType, value);
    }

    public CompositeBytecodeComponent(BytecodeInstructionType componentType) {
        super(componentType);
    }

    public Object get(BytecodeInstructionType compositeBytecodeBytecodeInstructionType) {
        return components.get(compositeBytecodeBytecodeInstructionType);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(MessageFormat.format("{0}:\n", componentType));
        for (BytecodeComponent component : components.values()) {
            builder.append(addPrefixForEachLine(component.toString(), "-")).append("\n");
        }
        return builder.toString();
    }

}

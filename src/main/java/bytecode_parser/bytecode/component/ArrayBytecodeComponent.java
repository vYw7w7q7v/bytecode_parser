package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.instruction.type.ByteInfoType;
import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import bytecode_parser.bytecode.instruction.type.ComplexBytecodeInstructionType;
import bytecode_parser.bytecode.instruction.type.FinalBytecodeInstructionType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static bytecode_parser.utils.StringUtils.addPrefixForEachLine;

public final class ArrayBytecodeComponent extends BytecodeComponent {
    public ArrayBytecodeComponent(BytecodeInstructionType instructionType) {
        super(instructionType);
    }

    private final List<BytecodeComponent> components = new ArrayList<>();

    public void add(BytecodeComponent component) {
        components.add(component);
    }
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(MessageFormat.format(
                "{0}[{1}]:\n",
                componentType,
                components.size())
        );

        if (componentType.byteInfoType() == ByteInfoType.UTF8_INFO) {
            for (BytecodeComponent component : components)
                builder.append(((FinalBytecodeComponent) component).utf8());
            builder.append("\n");
        } else {
            for (int i = 0; i < components.size(); i++) {
                builder.append(addPrefixForEachLine(MessageFormat.format(
                        "<{0}> {1}", i, components.get(i).toString()
                ), "-")).append("\n");
            }
        }

        return builder.toString();
    }
}

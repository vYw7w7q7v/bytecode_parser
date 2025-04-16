package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;
import bytecode_parser.bytecode.instruction.type.ComplexBytecodeInstructionType;
import bytecode_parser.bytecode.instruction.type.FinalBytecodeInstructionType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

//        if (componentType instanceof FinalBytecodeInstructionType) {
//            builder.append("  ");
//            for (BytecodeComponent component : components) {
//                builder.append(component);
//            }
//        }

        for (BytecodeComponent component : components) {
            builder.append("##").append(component).append("\n");
        }
        return builder.toString();
    }
}

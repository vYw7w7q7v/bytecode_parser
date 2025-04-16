package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.instruction.type.BytecodeInstructionType;

import java.text.MessageFormat;

public final class SingleBytecodeComponent extends BytecodeComponent {
    private final BytecodeComponent component;

    public SingleBytecodeComponent(BytecodeComponent component,
                                   BytecodeInstructionType componentType) {
        super(componentType);
        this.component = component;
    }

    public int asNumber() {
        if (component instanceof FinalBytecodeComponent finalComponent) {
            return finalComponent.asNumber();
        } else {
            throw new RuntimeException("component boxed in SingleBytecodeComponent is not Final");
        }
    }
    @Override
    public String toString() {
        return MessageFormat.format(
                component instanceof FinalBytecodeComponent ?
                "{0}: {1}" :
                       """
                       {0}:
                           {1}
                       """,
                componentType,
                componentType.isNumeric() ? asNumber() : component);
    }
}

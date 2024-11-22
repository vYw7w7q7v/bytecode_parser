package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public final class  ListBytecodeComponent extends BytecodeComponent {
    public ListBytecodeComponent(DefinedBytecodeComponentType componentType) {
        super(componentType);
    }

    private final List<BytecodeComponent> components = new ArrayList<>();

    public void add(BytecodeComponent component) {
        components.add(component);
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(MessageFormat.format("""
                <{0}[]:>
                #######
                """, componentType));
        for (BytecodeComponent component : components) {
            builder.append(component).append("\n");
        }
        builder.append("#######");
        return builder.toString();
    }
}

package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

public final class JavaProgram extends CompositeBytecodeComponent {

    public JavaProgram() {
        super(DefinedBytecodeComponentType.JAVA_PROGRAM);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("""
                JavaBytecode
                ============
                """);
        for (BytecodeComponent component : components.values()) {
            builder.append(component).append("\n");
        }
        builder.append("============");
        return builder.toString();
    }
}
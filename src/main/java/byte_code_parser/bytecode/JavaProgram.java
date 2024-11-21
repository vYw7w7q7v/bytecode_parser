package byte_code_parser.bytecode;

import byte_code_parser.bytecode.classfile.ClassFileComponent;

import java.util.EnumMap;

public class JavaProgram {

    public final EnumMap<ClassFileComponent, BytecodeComponent> componentMap = new EnumMap<>(ClassFileComponent.class);

    public static JavaProgram of() {
        return new JavaProgram();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("""
                JavaBytecode
                ============
                """);
        for (BytecodeComponent component : componentMap.values()) {
            builder.append(component).append("\n");
        }
        builder.append("============");
        return builder.toString();
    }
}

package bytecode_parser.bytecode.structure;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

import java.util.List;

import static bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType.*;

public final class JavaProgramStructure {

    private JavaProgramStructure() {}

    public final static List<DefinedBytecodeComponentType> classfileStructure = List.of(
            magic, // CAFE BABE
            minor_version,
            major_version,
            constant_pool_count,
            constant_pool, // constant_pool[constant_pool_count-1]
            access_flags,
            this_class,
            super_class,
            interfaces_count,
            interfaces, // interfaces[interfaces_count]
            fields_count,
            fields, // fields[fields_count]
            methods_count,
            methods, // methods[methods_count]
            attributes_count,
            attributes // attributes[attributes_count]
    );
}

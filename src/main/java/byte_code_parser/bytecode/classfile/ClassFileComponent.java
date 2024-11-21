package byte_code_parser.bytecode.classfile;


import byte_code_parser.bytecode.BytecodeComponentType;
import byte_code_parser.bytecode.BytecodeEntity;

import static byte_code_parser.bytecode.BytecodeEntity.*;

public enum ClassFileComponent implements BytecodeComponentType {
    magic(U4), // CAFE BABE
    minor_version(U2),
    major_version(U2),
    constant_pool_count(U2),
    constant_pool(CONSTANT_POOL_INFO_ARR), // constant_pool[constant_pool_count-1]
    access_flags(U2),
    this_class(U2),
    super_class(U2),
    interfaces_count(U2),
    interfaces(INTERFACE_ARR), // interfaces[interfaces_count]
    fields_count(U2),
    fields(FIELD_INFO_ARR), // fields[fields_count]
    methods_count(U2),
    methods(METHOD_INFO_ARR), // methods[methods_count]
    attributes_count(U2),
    attributes(ATTRIBUTE_INFO_ARR); // attributes[attributes_count]

    private final BytecodeEntity bytecodeEntity;

    public BytecodeEntity bytecodeEntity() {
        return bytecodeEntity;
    }

    ClassFileComponent(BytecodeEntity bytecodeEntity) {
        this.bytecodeEntity = bytecodeEntity;
    }


}

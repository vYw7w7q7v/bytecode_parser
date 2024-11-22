package bytecode_parser.bytecode.component.type;


import static bytecode_parser.bytecode.component.type.BytecodeEntityType.*;
// Class file component type
public enum DefinedBytecodeComponentType {

    // TODO: remove enums with param null from DefinedBytecodeComponentType
    //  + implement ifc BytecodeComponentType for both enums

    //// CLASS FILE
    magic(U4), // CAFE BABE
    minor_version(U2),
    major_version(U2),
    constant_pool_count(U2),
    constant_pool(CONSTANT_POOL_INFO_ARR), // constant_pool[constant_pool_count-1]
    CONSTANT_POOL_INFO(null),
    access_flags(U2),
    this_class(U2),
    super_class(U2),
    interfaces_count(U2),
    interfaces(INTERFACE_INFO_ARR), // interfaces[interfaces_count]
    fields_count(U2),
    fields(FIELD_INFO_ARR), // fields[fields_count]
    methods_count(U2),
    methods(METHOD_INFO_ARR), // methods[methods_count]
    attributes_count(U2),
    attributes(ATTRIBUTE_INFO_ARR), // attributes[attributes_count]

    //// CONSTANT POOL

    TAG(U1),
    name_index(U2),
    class_index(U2),
    name_and_type_index(U2),
    string_index(U2),
    bytes(U4),
    high_bytes(U4),
    low_bytes(U4),
    descriptor_index(U2),
    length_of_byte_arr(U2),
    byte_sequence(BYTE_ARR), // bytes[length]

    byte_element(U1),
    reference_kind(U1),
    reference_index(U2),
    bootstrap_method_attr_index(U2),

    // OTHER

    JAVA_PROGRAM(null);

    public final BytecodeEntityType bytecodeEntityType;

    DefinedBytecodeComponentType(BytecodeEntityType bytecodeEntityType) {
        this.bytecodeEntityType = bytecodeEntityType;
    }

}

package bytecode_parser.bytecode.component.type;

//primitives or fields that can`t be definitely defined
public enum BytecodeEntityType {
    // byte primitives
    U1, U2, U4,

    // info records
    CONSTANT_POOL_INFO,
    INTERFACE_INFO,
    FIELD_INFO,
    METHOD_INFO,

    // arrays

    CONSTANT_POOL_INFO_ARR,
    INTERFACE_INFO_ARR,
    FIELD_INFO_ARR,
    METHOD_INFO_ARR,
    ATTRIBUTE_INFO_ARR,
    BYTE_ARR
}

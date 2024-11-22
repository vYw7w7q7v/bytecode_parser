package bytecode_parser.bytecode.structure.constant_pool;

import bytecode_parser.bytecode.component.NumericBytecodeComponent;

import java.util.HashMap;
import java.util.Map;

public enum ConstantType {
    CONSTANT_UTF8_INFO(1),
    CONSTANT_INTEGER_INFO(3),
    CONSTANT_FLOAT_INFO(4),
    CONSTANT_LONG_INFO(5),
    CONSTANT_DOUBLE_INFO(6),
    /**<pre>
     * CONSTANT_Class_info {
     *      u1 tag;
     *      u2 name_index;
     * }</pre>
     */
    CONSTANT_CLASS_INFO(7),
    CONSTANT_STRING_INFO(8),
    CONSTANT_FIELD_REF_INFO(9),
    CONSTANT_METHOD_REF_INFO(10),
    CONSTANT_INTERFACE_METHOD_REF_INFO(11),
    CONSTANT_NAME_AND_TYPE_INFO(12),
    CONSTANT_METHOD_HANDLE_INFO(15),
    CONSTANT_METHOD_TYPE_INFO(16),
    CONSTANT_INVOKE_DYNAMIC_INFO(18);

    private final int tag;

    ConstantType(int tag) {
        this.tag = tag;
    }

    public int tag() {
        return tag;
    }

    private static final Map<Integer, ConstantType> tag2ConstantTypeMap;

    static {
        tag2ConstantTypeMap = new HashMap<>(20);
        for (ConstantType constantType : values()) {
            tag2ConstantTypeMap.put(constantType.tag, constantType);
        }
    }

    public static ConstantType get(Integer tag) {
        return tag2ConstantTypeMap.get(tag);
    }
}

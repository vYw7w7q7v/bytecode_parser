package bytecode_parser.bytecode.instruction.type;

import bytecode_parser.bytecode.instruction.BytecodeInstruction;
import bytecode_parser.bytecode.instruction.CompositeInstruction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static bytecode_parser.bytecode.instruction.type.ComplexBytecodeInstructionType.*;

// constant from constant pool
public enum CPConstantBytecodeInstructionType implements BytecodeInstructionType {
    CONSTANT_UTF8_INFO(1, length_of_byte_sequence, byte_sequence),
    CONSTANT_INTEGER_INFO(3, bytes),
    CONSTANT_FLOAT_INFO(4, bytes),
    CONSTANT_LONG_INFO(5, high_bytes, low_bytes),
    CONSTANT_DOUBLE_INFO(6, high_bytes, low_bytes),

    /**<pre>
     * CONSTANT_Class_info {
     *      u1 tag; (parsed as switch attribute)
     *      u2 name_index;
     * }</pre>
     */
    CONSTANT_CLASS_INFO(7, name_index),
    CONSTANT_STRING_INFO(8, string_index),
    CONSTANT_FIELD_REF_INFO(9, class_index, name_and_type_index),
    CONSTANT_METHOD_REF_INFO(10, class_index, name_and_type_index),
    CONSTANT_INTERFACE_METHOD_REF_INFO(11, class_index, name_and_type_index),
    CONSTANT_NAME_AND_TYPE_INFO(12, name_index, descriptor_index),
    CONSTANT_METHOD_HANDLE_INFO(15, reference_kind, reference_index),
    CONSTANT_METHOD_TYPE_INFO(16, descriptor_index),
    CONSTANT_INVOKE_DYNAMIC_INFO(18, bootstrap_method_attr_index, name_and_type_index);

    private final int tag;
    private final CompositeInstruction instruction;

    CPConstantBytecodeInstructionType(int tag, BytecodeInstructionType... instructionTypeList) {
        this.tag = tag;
        this.instruction = new CompositeInstruction(this,
                Arrays.stream(instructionTypeList)
                        .map(BytecodeInstructionType::instruction)
                        .collect(Collectors.toList())
        );
    }

    private static final Map<Integer, CPConstantBytecodeInstructionType> tag2ConstantTypeMap;

    static {
        tag2ConstantTypeMap = new HashMap<>(20);
        for (CPConstantBytecodeInstructionType constantType : values()) {
            tag2ConstantTypeMap.put(constantType.tag, constantType);
        }
    }

    public static CPConstantBytecodeInstructionType get(Integer tag) {
        return tag2ConstantTypeMap.get(tag);
    }

    @Override
    public BytecodeInstruction instruction() {
        return instruction;
    }
}

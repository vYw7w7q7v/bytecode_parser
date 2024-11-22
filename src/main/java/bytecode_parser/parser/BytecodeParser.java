package bytecode_parser.parser;

import bytecode_parser.bytecode.structure.JavaProgramStructure;
import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;
import bytecode_parser.bytecode.component.*;
import bytecode_parser.bytecode.structure.constant_pool.ConstantType;
import bytecode_parser.bytes.ByteBuffer;

import java.io.*;

import static bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType.*;
import static bytecode_parser.bytes.ByteUtils.readBytesFromFile;


public class BytecodeParser {
    public static JavaProgram parseJavaBytecode(File file) {
        ByteBuffer byteBuffer = readBytesFromFile(file);
        var javaProgram = new JavaProgram();

        parseClassFileComponents(javaProgram, byteBuffer);
        System.out.println(javaProgram);
        return javaProgram;
    }

    private static void parseClassFileComponents(
            JavaProgram parentComponent,
            ByteBuffer byteBuffer
    ) {
        for (DefinedBytecodeComponentType componentType : JavaProgramStructure.classfileStructure) {
            parentComponent.put(
                    componentType,
                    parseBytecodeEntity(parentComponent, byteBuffer, componentType)
            );
        }
    }

    private static BytecodeComponent parseBytecodeEntity(
            CompositeBytecodeComponent parent, ByteBuffer byteBuffer,
            DefinedBytecodeComponentType type
    ) {
        return switch (type.bytecodeEntityType) {
            case U1 -> new NumericBytecodeComponent(parse_u1(byteBuffer), type);
            case U2 -> new NumericBytecodeComponent(parse_u2(byteBuffer), type);
            case U4 -> new NumericBytecodeComponent(parse_u4(byteBuffer), type);
            case BYTE_ARR -> {
                int length;
                if (parent.get(length_of_byte_arr)
                        instanceof NumericBytecodeComponent numericBytecodeComponent) {
                    length = numericBytecodeComponent.asNumber();
                } else {
                    throw new RuntimeException("length must be parsed and be a NumericBytecodeComponent");
                }
                var byte_arr = new ListBytecodeComponent(byte_sequence);

                for (int i = 0; i < length; i++)
                    byte_arr.add(parseBytecodeEntity(null, byteBuffer, byte_element));

                yield byte_arr;
            }
            case CONSTANT_POOL_INFO -> null;
            case INTERFACE_INFO -> null;
            case FIELD_INFO -> null;
            case METHOD_INFO -> null;
            case CONSTANT_POOL_INFO_ARR -> parseConstantPool(parent, byteBuffer);
            case INTERFACE_INFO_ARR -> null;
            case FIELD_INFO_ARR -> null;
            case METHOD_INFO_ARR -> null;
            case ATTRIBUTE_INFO_ARR -> null;
        };
    }

    private static void parseAndPutBytecodeEntity(
            CompositeBytecodeComponent parentComponent, ByteBuffer byteBuffer,
            DefinedBytecodeComponentType type
    ) {
        parentComponent.put(type, parseBytecodeEntity(parentComponent, byteBuffer, type));
    }

    private static byte[] parse_u4(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(4);
    }
    private static byte[] parse_u2(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(2);
    }
    private static byte[] parse_u1(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(1);
    }

    private static ListBytecodeComponent parseConstantPool(
            CompositeBytecodeComponent parent, ByteBuffer byteBuffer
    ) {
        // TODO: create common parseArray method
        int constantPoolCount;
        if (parent.get(constant_pool_count)
                instanceof NumericBytecodeComponent numericBytecodeComponent) {
            constantPoolCount = numericBytecodeComponent.asNumber();
        } else {
            throw new RuntimeException("constantPoolCount must be parsed and be a NumericBytecodeComponent");
        }

        var constantPool = new ListBytecodeComponent(constant_pool);
        BytecodeComponent constantPoolInfo;
        // IMPORTANT constantPoolCount == count of real constants + 1  ->  i = 0...constantPoolCount - 1
        for (int i = 0; i < constantPoolCount - 1; i++) {
            constantPoolInfo = parseConstantPoolInfo(byteBuffer);
            constantPool.add(constantPoolInfo);
        }
        return constantPool;
    }

    private static CompositeBytecodeComponent parseConstantPoolInfo(ByteBuffer byteBuffer) {
        NumericBytecodeComponent tag = new NumericBytecodeComponent(parse_u1(byteBuffer), TAG);

        CompositeBytecodeComponent constantPoolInfo = new CompositeBytecodeComponent(CONSTANT_POOL_INFO);
        constantPoolInfo.put(TAG, tag);

        switch (ConstantType.get(tag.asNumber())) {
            case CONSTANT_UTF8_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, length_of_byte_arr);
                // TODO: parse byte sequence
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, byte_sequence);
            }

            case CONSTANT_INTEGER_INFO, CONSTANT_FLOAT_INFO ->
                    parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, bytes);

            case CONSTANT_LONG_INFO, CONSTANT_DOUBLE_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, high_bytes);
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, low_bytes);
            }

            case CONSTANT_CLASS_INFO -> parseAndPutBytecodeEntity(
                    constantPoolInfo, byteBuffer, name_index
            );

            case CONSTANT_STRING_INFO ->
                    parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, string_index);

            case CONSTANT_FIELD_REF_INFO, CONSTANT_METHOD_REF_INFO,
                    CONSTANT_INTERFACE_METHOD_REF_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, class_index);
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, name_and_type_index);
            }

            case CONSTANT_NAME_AND_TYPE_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, name_index);
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, descriptor_index);
            }

            case CONSTANT_METHOD_HANDLE_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, reference_kind);
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, reference_index);
            }

            case CONSTANT_METHOD_TYPE_INFO ->
                    parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, descriptor_index);

            case CONSTANT_INVOKE_DYNAMIC_INFO -> {
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, bootstrap_method_attr_index);
                parseAndPutBytecodeEntity(constantPoolInfo, byteBuffer, name_and_type_index);
            }
        }
        return constantPoolInfo;
    }

    private static NumericBytecodeComponent parse_tag() {
        //return new NumericBytecodeComponent(parse_u1(), TAG);
        return null;
    }

}

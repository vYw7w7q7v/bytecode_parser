package bytecode_parser.parser;

import bytecode_parser.bytecode.instruction.*;
import bytecode_parser.bytecode.component.*;
import bytecode_parser.bytecode.instruction.type.ComplexBytecodeInstructionType;
import bytecode_parser.byte_buffer.ByteBuffer;

import java.io.*;

import static bytecode_parser.bytecode.instruction.type.FinalBytecodeInstructionType.*;
import static bytecode_parser.byte_buffer.ByteUtils.readBytesFromFile;


public class BytecodeParser {

    public static BytecodeComponent parseJavaBytecode(File file) {
        if (!(ComplexBytecodeInstructionType.JAVA_PROGRAM.instruction()
                instanceof CompositeInstruction javaInstruction)) {
            throw new RuntimeException("java program must be CompositeBytecodeInstruction!!");
        }

        ByteBuffer byteBuffer = readBytesFromFile(file);
        BytecodeComponent javaProgram = parseCompositeInstruction(byteBuffer, javaInstruction);

        System.out.println(javaProgram);
        return javaProgram;
    }

    private static BytecodeComponent parseInstruction(ByteBuffer buf, BytecodeInstruction instruction,
                                                      CompositeBytecodeComponent context) {
        return switch (instruction) {
            case FinalInstruction finalInstruction -> parseFinalInstruction(buf, finalInstruction);

            case SingleInstruction singleInstruction -> parseSingleInstruction(buf, singleInstruction);

            case CompositeInstruction compositeInstruction -> parseCompositeInstruction(buf, compositeInstruction);

            case ArrayInstruction arrayInstruction -> parseArrayInstruction(buf, arrayInstruction, context);

            case SwitchInstruction switchInstruction -> parseSwitchInstruction(buf, switchInstruction);
        };
    }


    private static FinalBytecodeComponent parseFinalInstruction(ByteBuffer buf,
                                                           FinalInstruction instruction) {
        return switch (instruction.getFinalInstructionType()) {
            case U1 -> new FinalBytecodeComponent(parse_u1(buf), U1);
            case U2 -> new FinalBytecodeComponent(parse_u2(buf), U2);
            case U4 -> new FinalBytecodeComponent(parse_u4(buf), U4);
        };
    }

    private static SingleBytecodeComponent parseSingleInstruction(ByteBuffer buf,
                                                                SingleInstruction singleInstruction) {
        return new SingleBytecodeComponent(
                parseInstruction(buf, singleInstruction.getInstruction(), null),
                singleInstruction.instructionType
        );
    }

    private static CompositeBytecodeComponent parseCompositeInstruction(ByteBuffer buf,
                                                                        CompositeInstruction instruction) {
        CompositeBytecodeComponent compositeComponent =
                new CompositeBytecodeComponent(instruction.instructionType);
        for (BytecodeInstruction bytecodeInstruction : instruction.getInstructionList()) {
            compositeComponent.put(parseInstruction(buf, bytecodeInstruction, compositeComponent));
        }
        return compositeComponent;
    }

    private static ArrayBytecodeComponent parseArrayInstruction(ByteBuffer buf,
                                                                ArrayInstruction arrayInstruction,
                                                                CompositeBytecodeComponent context) {
        int length = arrayInstruction.getLengthAttributeFunction().apply(context);

        var arr = new ArrayBytecodeComponent(arrayInstruction.instructionType);
        for (int i = 0; i < length; i++)
            arr.add(parseInstruction(buf, arrayInstruction.instructionType.instruction(), null));
        return arr;
    }

    private static CompositeBytecodeComponent parseSwitchInstruction(ByteBuffer buf,
                                                                     SwitchInstruction switchInstruction) {
        SingleBytecodeComponent switchAttribute = parseSingleInstruction(
                buf, switchInstruction.getSwitchAttributeInstruction()
        );

        // TODO: add additional ways with non-composite switchResultingInstruction
        // !!!! check that switch instruction is composite
        CompositeInstruction switchResultingInstruction = (CompositeInstruction)
                switchInstruction.getSwitchResultFunction().apply(switchAttribute);

        CompositeBytecodeComponent parsedSwitchComponent =
                parseCompositeInstruction(buf, switchResultingInstruction);

        CompositeBytecodeComponent res = new CompositeBytecodeComponent(parsedSwitchComponent.componentType);
        res.put(switchAttribute);

        for (BytecodeComponent component : parsedSwitchComponent.getComponents().values()) {
            res.put(component);
        }
        return res;
    }

//    private static BytecodeComponent parseInstruction(CompositeBytecodeComponent parent,
//                                                      ByteBuffer byteBuffer,
//                                                      InstructionType instruction) {
//        BytecodeInstruction bytecodeStructure = instruction.structure();
//        if (!bytecodeStructure.isArray()) {
//            return parseSingleInstruction(byteBuffer, instruction); // <instruction>
//        } else {
//            int length; // <instruction>[]
//            InstructionType lengthMarker = bytecodeStructure.getLengthMarker();
//            Object lengthComponent = parent.get(lengthMarker);
//
//            if (lengthComponent == null)
//                throw new RuntimeException("cannot find length component with marker " + lengthMarker);
//            if (lengthComponent instanceof SingleBytecodeComponent singleComponent) {
//                length = singleComponent.asNumber();
//            } else {
//                throw new RuntimeException("length must be a SingleBytecodeComponent");
//            }
//            int actualLength = bytecodeStructure.getLengthFunction().apply(length);
//
//            var arr = new ListBytecodeComponent(instruction);
//            for (int i = 0; i < actualLength; i++)
//                arr.add(parseSingleInstruction(byteBuffer, instruction));
//            return arr;
//        }
//    }
//
//    private static BytecodeComponent parseSingleInstruction(ByteBuffer byteBuffer,
//                                                            InstructionType instruction) {
//        return switch (instruction) {
//            case FinalInstructionType finalInstruction ->
//                    parseFinalInstruction(byteBuffer,finalInstruction);
//
//            case ComplexInstructionType compositeBytecodeElement -> {
//                var instructions = compositeBytecodeElement.innerInstructions();
//                if (instructions.size() == 1) {
//                    yield new SingleBytecodeComponent(
//                            parseInstruction(null, byteBuffer, instructions.getFirst()), instruction
//                    );
//                } else {
//                    CompositeBytecodeComponent res = new CompositeBytecodeComponent(instruction);
//                    for (InstructionType innerInstruction : compositeBytecodeElement.innerInstructions())
//                        res.put(parseInstruction(res, byteBuffer, innerInstruction));
//                    yield res;
//                }
//            }
//        };
//    }
//
//    private static BytecodeComponent parseFinalInstruction(ByteBuffer byteBuffer,
//                                                           FinalInstructionType finalInstruction) {
//        return switch (finalInstruction) {
//            case U1 -> new FinalBytecodeComponent(parse_u1(byteBuffer), finalInstruction);
//            case U2 -> new FinalBytecodeComponent(parse_u2(byteBuffer), finalInstruction);
//            case U4 -> new FinalBytecodeComponent(parse_u4(byteBuffer), finalInstruction);
//            ////
//            case CONSTANT_POOL_INFO -> parseConstantPoolInfo(byteBuffer);
//            case INTERFACE_INFO -> null;
//            case FIELD_INFO -> null;
//            case METHOD_INFO -> null;
//            case ATTRIBUTE_INFO -> null;
//        };
//    }
//
//    private static CompositeBytecodeComponent parseConstantPoolInfo(ByteBuffer byteBuffer) {
//        FinalBytecodeComponent tag = new FinalBytecodeComponent(parse_u1(byteBuffer), TAG); // u1 tag
//        CompositeBytecodeComponent constantPoolInfo = new CompositeBytecodeComponent(CONSTANT_POOL_INFO);
//        constantPoolInfo.put(tag);
//
//        switch (ConstantPoolConstantType.get(tag.asNumber())) {
//            case CONSTANT_UTF8_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, length_of_byte_arr);
//                parseAndPutElement(constantPoolInfo, byteBuffer, byte_sequence);
//            }
//            case CONSTANT_INTEGER_INFO, CONSTANT_FLOAT_INFO ->
//                    parseAndPutElement(constantPoolInfo, byteBuffer, bytes);
//            case CONSTANT_LONG_INFO, CONSTANT_DOUBLE_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, high_bytes);
//                parseAndPutElement(constantPoolInfo, byteBuffer, low_bytes);
//            }
//            case CONSTANT_CLASS_INFO -> parseAndPutElement(constantPoolInfo, byteBuffer, name_index);
//            case CONSTANT_STRING_INFO ->
//                    parseAndPutElement(constantPoolInfo, byteBuffer, string_index);
//            case CONSTANT_FIELD_REF_INFO, CONSTANT_METHOD_REF_INFO,
//                    CONSTANT_INTERFACE_METHOD_REF_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, class_index);
//                parseAndPutElement(constantPoolInfo, byteBuffer, name_and_type_index);
//            }
//            case CONSTANT_NAME_AND_TYPE_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, name_index);
//                parseAndPutElement(constantPoolInfo, byteBuffer, descriptor_index);
//            }
//            case CONSTANT_METHOD_HANDLE_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, reference_kind);
//                parseAndPutElement(constantPoolInfo, byteBuffer, reference_index);
//            }
//            case CONSTANT_METHOD_TYPE_INFO ->
//                    parseAndPutElement(constantPoolInfo, byteBuffer, descriptor_index);
//            case CONSTANT_INVOKE_DYNAMIC_INFO -> {
//                parseAndPutElement(constantPoolInfo, byteBuffer, bootstrap_method_attr_index);
//                parseAndPutElement(constantPoolInfo, byteBuffer, name_and_type_index);
//            }
//        }
//        return constantPoolInfo;
//    }
//
//    private static void parseAndPutElement(CompositeBytecodeComponent parentComponent,
//                                           ByteBuffer byteBuffer,
//                                           ComplexInstructionType compositeInstruction) {
//        parentComponent.put(parseInstruction(parentComponent, byteBuffer, compositeInstruction));
//    }

    private static byte[] parse_u4(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(4);
    }
    private static byte[] parse_u2(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(2);
    }
    private static byte[] parse_u1(ByteBuffer byteBuffer) {
        return byteBuffer.readBytes(1);
    }

//    private static ListBytecodeComponent parseConstantPool(CompositeBytecodeComponent parent,
//                                                           ByteBuffer byteBuffer) {
//        // TODO: create common parseArray method
//        int constantPoolCount;
//        if (parent.get(constant_pool_count)
//                instanceof FinalBytecodeComponent numericBytecodeComponent) {
//            constantPoolCount = numericBytecodeComponent.asNumber();
//        } else {
//            throw new RuntimeException("constantPoolCount must be parsed and be a NumericBytecodeComponent");
//        }
//        var constantPool = new ListBytecodeComponent(constant_pool);
//        // IMPORTANT constantPoolCount == count of real constants + 1  ->  i = 0...constantPoolCount - 1
//        for (int i = 0; i < constantPoolCount - 1; i++) constantPool.add(parseConstantPoolInfo(byteBuffer));
//        return constantPool;
//    }

}

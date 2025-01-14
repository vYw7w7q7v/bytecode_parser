package byte_code_parser.parser;

import byte_code_parser.bytecode.*;
import byte_code_parser.bytecode.classfile.ClassFileComponent;
import byte_code_parser.bytes.ByteBuffer;

import java.io.*;

import static byte_code_parser.bytecode.classfile.ClassFileComponent.*;
import static byte_code_parser.bytes.ByteUtils.readBytesFromFile;


public class BytecodeParser {
    public static JavaProgram parseJavaBytecode(File file) {

        ByteBuffer byteBuffer = readBytesFromFile(file);
        var javaProgram = JavaProgram.of();

        parseClassFileComponents(javaProgram, byteBuffer);

        //components.add(new SimpleBytecodeComponent(parse_u4(), "magic_bytes"));
        //components.add(new NumericBytecodeComponent(parse_u2(), "minor_version"));
        //components.add(new NumericBytecodeComponent(parse_u2(), "major_version"));

        //var constantPoolCount = new NumericBytecodeComponent(parse_u2(), "constant_pool_count");
        //components.add(constantPoolCount);

        //BytecodeComponent constantPool = parseConstantPool(constantPoolCount.asNumber());
        //System.out.println(constantPool);

        //JavaBytecode res = JavaBytecode.builder().components(components).build();
        System.out.println(javaProgram);

        return javaProgram;
    }

    private static void parseClassFileComponents(JavaProgram javaProgram, ByteBuffer byteBuffer) {
        for (ClassFileComponent classFileComponent : ClassFileComponent.values()) {
            javaProgram.componentMap.put(
                    classFileComponent,
                    parseClassFileComponent(classFileComponent, byteBuffer)
            );
        }
    }

    private static BytecodeComponent parseClassFileComponent(ClassFileComponent classFileComponent, ByteBuffer byteBuffer) {
        return switch (classFileComponent) {
            case magic -> new SimpleBytecodeComponent(parse_u4(byteBuffer), magic);
            case minor_version -> new NumericBytecodeComponent(parse_u2(byteBuffer), minor_version);
            case major_version -> new NumericBytecodeComponent(parse_u2(byteBuffer), major_version);
            case constant_pool_count -> new NumericBytecodeComponent(parse_u2(byteBuffer), constant_pool_count);
            case constant_pool -> null;
            case access_flags -> null;
            case this_class -> null;
            case super_class -> null;
            case interfaces_count -> null;
            case interfaces -> null;
            case fields_count -> null;
            case fields -> null;
            case methods_count -> null;
            case methods -> null;
            case attributes_count -> null;
            case attributes -> null;
        };
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

    private static NumericBytecodeComponent parse_tag() {
        //return new NumericBytecodeComponent(parse_u1(), TAG);
        return null;
    }

    private static BytecodeComponent parseConstantPool(int constantPoolSize) {
        return null;
    }

}

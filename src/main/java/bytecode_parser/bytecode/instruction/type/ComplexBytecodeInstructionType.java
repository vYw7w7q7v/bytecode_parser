package bytecode_parser.bytecode.instruction.type;


import bytecode_parser.bytecode.component.CompositeBytecodeComponent;
import bytecode_parser.bytecode.component.SingleBytecodeComponent;
import bytecode_parser.bytecode.instruction.*;

import java.util.Arrays;
import java.util.function.Function;

import static bytecode_parser.bytecode.instruction.type.ByteInfoType.*;
import static bytecode_parser.bytecode.instruction.type.FinalBytecodeInstructionType.*;

// Class file component type
public enum ComplexBytecodeInstructionType implements BytecodeInstructionType {

    //// CLASS FILE
    magic(U4), // CAFE BABE
    minor_version(NUMERIC_INFO, U2),
    major_version(NUMERIC_INFO, U2),
    constant_pool_count(NUMERIC_INFO, U2),
    TAG(NUMERIC_INFO, U1),
    CONSTANT_POOL_INFO(
            switchAttribute -> CPConstantBytecodeInstructionType.get(switchAttribute.asNumber()).instruction(),
            TAG
    ),

    // constant_pool[constant_pool_count-1]
    constant_pool(
            CONSTANT_POOL_INFO,
            // !!!! actual length == constant_pool_count-1 !!!!
            context -> ((SingleBytecodeComponent) context.get(constant_pool_count)).asNumber() - 1
    ),
    access_flags(U2),
    this_class(U2),
    super_class(U2),
    interfaces_count(NUMERIC_INFO, U2),
    //INTERFACE_INFO(),
    //interfaces(), // interfaces[interfaces_count]
    fields_count(U2),
    //FIELD_INFO(),
    //fields(), // fields[fields_count]
    methods_count(U2),
    //METHOD_INFO(),
    //methods(), // methods[methods_count]
    attributes_count(U2),
    //ATTRIBUTE_INFO(),
    //attributes(), // attributes[attributes_count]

    //// CONSTANT POOL

    name_index(U2),
    class_index(U2),
    name_and_type_index(U2),
    string_index(U2),
    bytes(U4),
    high_bytes(U4),
    low_bytes(U4),
    descriptor_index(U2),
    length_of_byte_sequence(U2),
    byte_sequence(U1, context -> ((SingleBytecodeComponent) context.get(length_of_byte_sequence)).asNumber()),
    byte_element(U1),
    reference_kind(U1),
    reference_index(U2),
    bootstrap_method_attr_index(U2),

    // MAIN

    JAVA_PROGRAM(
            magic, // CAFE BABE
            minor_version,
            major_version,
            constant_pool_count,
            constant_pool, // constant_pool[constant_pool_count-1]
            access_flags,
            this_class,
            super_class,
            interfaces_count//,
            //interfaces, // interfaces[interfaces_count]
            //fields_count,
            //fields, // fields[fields_count]
            //methods_count,
            //methods, // methods[methods_count]
            //attributes_count,
            //attributes // attributes[attributes_count]
    );

    public final BytecodeInstruction instruction;
    private ByteInfoType byteInfoType = BYTE_INFO;

    ComplexBytecodeInstructionType(BytecodeInstructionType... instructionTypes) {
        if (instructionTypes.length < 1) {
            instruction = null; // throw ex
        } else if (instructionTypes.length == 1) {
            instruction = new SingleInstruction(this, instructionTypes[0].instruction());
        } else {
            instruction = new CompositeInstruction(
                    this,
                    Arrays.stream(instructionTypes)
                            .map(BytecodeInstructionType::instruction)
                            .toList()
            );
        }
    }

    ComplexBytecodeInstructionType(ByteInfoType byteInfoType, BytecodeInstructionType... instructionTypes) {
        this(instructionTypes);
        this.byteInfoType = byteInfoType;
    }

    ComplexBytecodeInstructionType(BytecodeInstructionType arrayMemberInstructionType,
                                   Function<CompositeBytecodeComponent, Integer> lengthAttributeFunction) {
        this.instruction = new ArrayInstruction(arrayMemberInstructionType, lengthAttributeFunction);
    }

    ComplexBytecodeInstructionType(Function<SingleBytecodeComponent, BytecodeInstruction> switchAttributeFunction,
                                   BytecodeInstructionType singleInstructionType) {
        this.instruction = new SwitchInstruction(
                this,
                (SingleInstruction) singleInstructionType.instruction(), /// unchecked cast :(
                switchAttributeFunction
        );
    }

    @Override
    public BytecodeInstruction instruction() {
        return instruction;
    }

    @Override
    public ByteInfoType byteInfoType() {
        return byteInfoType;
    }


}

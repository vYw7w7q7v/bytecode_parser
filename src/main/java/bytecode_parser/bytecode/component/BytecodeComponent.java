package bytecode_parser.bytecode.component;

import bytecode_parser.bytecode.component.type.DefinedBytecodeComponentType;

public abstract sealed class BytecodeComponent
        permits CompositeBytecodeComponent, ListBytecodeComponent, SimpleBytecodeComponent {
    protected final DefinedBytecodeComponentType componentType;

    public BytecodeComponent(DefinedBytecodeComponentType componentType) {
        this.componentType = componentType;
    }

}

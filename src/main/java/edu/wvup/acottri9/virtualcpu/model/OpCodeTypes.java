package edu.wvup.acottri9.virtualcpu.model;


/**
 * Operation code types for a Virtual CPU. The following opcodes have been
 * implemented.
 * <p>
 * BRK = Break
 * <p>
 * LDA = Load the value in the next memory address into register A
 * <p>
 * LDX = Load the value in the next memory address into register X
 * <p>
 * ADC = Add the value in the next memory address into register A
 * <p>
 * STA = Store the value of register A into the memory location specified by
 * the value in the next memory address.
 * <p>
 * LDX = Load the value in the next memory address into register X
 * <p>
 * INX = Increment the value in register X by one
 * <p>
 * CMY = Compare the value in register Y to the value in the next
 * memory address and store the result in the equal flag
 * <p>
 * BNE = Branch if not equal. This operation will add the value in the
 * next memory address to the program counter if the equal
 * flag is not set
 * <p>
 * STA_X = Store the value in the A register in the memory location
 * pointed to by the X register.
 * <p>
 * DEY = Decrement the value in the Y register by one
 * <p>
 * LDY = Load the value in the next memory address into register Y
 */
public enum OpCodeTypes {
    BRK, LDA, ADC, STA, LDX, INX, CMY, BNE, STA_X, DEY, LDY
}

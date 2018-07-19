package edu.wvup.acottri9;


/**
 * Operation code types for a Virtual CPU. The following opcodes exist already.
 *
 * BRK = Break
 *
 * LDA = Load the value in the next memory address into register A
 *
 * LDX = Load the value in the next memory address into register X
 *
 * ADC = Add the value in the next memory address into register A
 *
 * STA = Store the value of register A into the memory location specified by
 * the value in the next memory address.
 *
 * LDX = Load the value in the next memory address into register X
 *
 * INX = Increment the value in register X by one
 *
 * CMY = Compare the value in register Y to the value in the next
 * memory address and store the result in the equal flag
 *
 * BNE = Branch if not equal. This operation will add the value in the
 * next memory address to the program counter if the equal
 * flag is not set
 *
 * STA_X = Store the value in the A register in the memory location
 * pointed to by the X register.
 *
 * DEY = Decrement the value in the Y register by one
 *
 * LDY = Load the value in the next memory address into register Y
 */
public enum OpCodeTypes
{
    BRK, LDA, ADC, STA, LDX, INX, CMY, BNE, STA_X, DEY, LDY

}

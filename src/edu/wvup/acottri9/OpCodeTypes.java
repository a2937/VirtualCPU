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
 */
public enum OpCodeTypes
{
    BRK, LDA, ADC, STA, LDX

}

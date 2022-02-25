package edu.wvup.acottri9.virtualcpu.model;

import java.util.Objects;

/**
 * A representation of an OpCode for a virtual CPU.
 * It stores the type of the opcode as well as the length.
 */
public class OpCode implements Comparable<OpCode> {
    private final OpCodeTypes opcode;
    private byte length = 0;
 
    /**
     * Instantiates a new Op code
     * with an operation type and length.
     *
     * @param length the length
     * @param opcode the opcode
     */
    public OpCode(byte length, OpCodeTypes opcode) {
        this.length = length;
        this.opcode = opcode;
    }

    /**
     * Instantiates a new Op code
     * with an operation type and length.
     *
     * @param length the length
     * @param opcode the opcode
     */
    public OpCode(int length, OpCodeTypes opcode) {
        this.length = (byte) length;
        this.opcode = opcode;
    }

    /**
     * Gets the length of the op code.
     *
     * @return the length of the op code
     */
    public byte getLength() {
        return length;
    }

    /**
     * Gets opcode.
     *
     * @return the opcode
     */
    public OpCodeTypes getOpcode() {
        return opcode;
    }

    /**
     * Compares this opcode with another opcode. If the other opcode has a
     * type that appears earlier on the list than this one; the method
     * will return negative. If the two opcodes are of the same value, then
     * it will return equal. If the other opcode has a
     * type that appears later on the list than this one; the method
     * will return positive.
     *
     * @param otherCode the other opcode
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(OpCode otherCode) {
        return this.getOpcode().ordinal() - otherCode.getOpcode().ordinal();
    }

    /**
     * Compares this opcode with another object for equality. If the other object
     * is null or not of the same class, then it will return false. If these are the same
     * instance; then it will return true. Otherwise, the length and the type will be used
     * to determine equality.
     */
    @Override
    public boolean equals(Object otherCode) {
        if (this == otherCode) return true;
        if (otherCode == null || getClass() != otherCode.getClass()) return false;
        OpCode opCode = (OpCode) otherCode;
        return length == opCode.length && opcode == opCode.opcode;
    }

    /**
     * Hashes the opcode according to the length and opcode
     * type.
     */
    @Override
    public int hashCode() {
        return Objects.hash(length, opcode);
    }

    /**
     * Gets the name of the opcode.
     *
     * @return The name of the opcode type in uppercase.
     */
    public String toString() {
        return getOpcode().name().toUpperCase();
    }
}

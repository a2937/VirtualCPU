package edu.wvup.acottri9.virtualcpu;

import edu.wvup.acottri9.virtualcpu.model.OpCode;
import edu.wvup.acottri9.virtualcpu.model.OpCodeTypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * A representation of a virtual CPU. It has three
 * different counters; an "a" counter, a "x" counter, and
 * a "y" counter. All values fed into it are assumed to be hex.
 */
public class VirtualCPU
{
    protected static final Logger logger = LogManager.getLogger();
    private final Pattern hexDigitPattern = Pattern.compile("([0-9A-Z]+)", Pattern.CASE_INSENSITIVE);
    private final ArrayList<OpCode> opCodes = new ArrayList<>();
    private final byte[] memory = new byte[256];
    private byte programCounter = 0;
    private byte a = 0;
    private byte x = 0;
    private byte y = 0;
    private boolean equalBit = false;

    /**
     * Instantiates a new Virtual cpu without
     * adding any new instructions.
     */
    public VirtualCPU()
    {
        initializeOpCodes();
    }

    /*
     * Used internally to get a file contents
     * @param fileName : the complete name of the file including the extension and path
     * @return a string representing everything inside the file.
     */
    private static String readFile(String fileName)
    {
        File file = new File(fileName);
        StringBuilder completeString = new StringBuilder();
        try
        {
            scanFile(file, completeString);
        }
        catch (FileNotFoundException e)
        {
            logger.error(String.format("%s not found", fileName));
        }

        return completeString.toString();
    }

    private static void scanFile(File file, StringBuilder completeString) throws FileNotFoundException
    {
        Scanner newScanner;
        newScanner = new Scanner(file);
        while (newScanner.hasNextLine())
        {
            completeString.append(newScanner.nextLine()).append("\n");
        }
        newScanner.close();
    }

    /**
     * Gets value in register a.
     *
     * @return the value in register a
     */
    public byte getValueInRegisterA()
    {
        return a;
    }

    /**
     * Returns the value in register y
     *
     * @return The value in register Y
     */
    public byte getValueInRegisterY()
    {
        return y;
    }

    /**
     * Returns the program counter; aka the length
     * of how many instructions have been executed.
     *
     * @return the program counter
     */
    public byte getProgramCounter()
    {
        return programCounter;
    }

    /**
     * Gets a copy of every value stored in memory.
     *
     * @return A byte array representing the program memory.
     */
    public byte[] getMemory()
    {
        return Arrays.copyOf(memory, 256);
    }

    public boolean isEqualBitTrue()
    {
        return equalBit;
    }

    private void initializeOpCodes()
    {
        opCodes.add(new OpCode(1, OpCodeTypes.BRK)); //Break
        opCodes.add(new OpCode(2, OpCodeTypes.LDA)); // Load register a
        opCodes.add(new OpCode(2, OpCodeTypes.STA)); //Store register a
        opCodes.add(new OpCode(2, OpCodeTypes.ADC)); //Add register a
        opCodes.add(new OpCode(2, OpCodeTypes.LDX)); // Loads the value of next memory address into register x
        opCodes.add(new OpCode(1, OpCodeTypes.INX)); // Increment register X
        opCodes.add(new OpCode(2, OpCodeTypes.CMY)); // Compare register y to the current value and store the result
        opCodes.add(new OpCode(2, OpCodeTypes.BNE)); // add the value in the next memory address to the program counter if the equal flag is not set
        opCodes.add(new OpCode(1, OpCodeTypes.STA_X)); // Store the value in the A register in the memory location pointed to by the X register
        opCodes.add(new OpCode(1, OpCodeTypes.DEY)); // Decrement register y
        opCodes.add(new OpCode(2, OpCodeTypes.LDY)); // Load the value of the next memory address into register Y
    }

    /**
     * Reads an entire file into memory and then
     * performs an operation.
     *
     * @param fileName the file name
     */
    public void processFile(String fileName)
    {
        String fileContents = readFile(fileName);
        String[] fileLines = fileContents.split("\n");
        for (String line : fileLines)
        {
            if (processLine(line))
            {
                return;
            }
        }
    }

    /**
     * Reads an entire file into memory and then
     * performs an operation.
     *
     * @param fileToRead the file to read
     * @throws FileNotFoundException : If the file object is not actually found on the disk
     */
    public void processFile(File fileToRead) throws FileNotFoundException
    {
        StringBuilder stringBuilder = new StringBuilder();
        scanFile(fileToRead, stringBuilder);
        String fileContents = stringBuilder.toString();
        String[] fileLines = fileContents.split("\n");
        for (String line : fileLines)
        {
            if (processLine(line))
            {

                return;
            }
        }
    }

    private String getHexDigits(String expression)
    {
        Matcher numberInfo = hexDigitPattern.matcher(expression);
        if (numberInfo.find())
        {
            return numberInfo.group(1);
        }
        else
        {
            return "";
        }
    }

    /**
     * Evaluates the given expression.
     *
     * @param fileLine The line to be read
     * @return Whether the file should keep being read
     */
    private boolean processLine(String fileLine)
    {
        String[] words = fileLine.split("\\s+");
        OpCode operation = null;

        for (OpCode opCode :
            opCodes)
        {
            if (opCode.toString().equalsIgnoreCase(words[0]))
            {
                operation = opCode;
            }
        }

        if (operation != null)
        {
            programCounter += operation.getLength();
            if (operation.getOpcode() == OpCodeTypes.BRK)
            {
                //Break
                return true;
            }
            performOperationsOnACounter(words, operation);
            if (operation.getOpcode() == OpCodeTypes.LDX)
            {
                //Load value into the "x" counter.
                try
                {
                    int valueToLoad = hexDecimalToDecimal(getHexDigits(words[1]));
                    x = (byte) valueToLoad;
                }
                catch (NumberFormatException nfe)
                {
                    logger.error(nfe.getMessage());
                }
            }
            else if (operation.getOpcode() == OpCodeTypes.INX)
            {
                //Increment the "x" counter
                ++x;
            }
            else if (operation.getOpcode() == OpCodeTypes.DEY)
            {
                //Decrement the "y" counter
                y--;
            }
            else if (operation.getOpcode() == OpCodeTypes.CMY)
            {
                //Compare register y to the value in the next
                //memory address and store the result in the equal flag
                equalBit = (y == hexDecimalToDecimal(getHexDigits(words[1])));
            }
        }
        return false;
    }

    private void performOperationsOnACounter(String[] words, OpCode operation)
    {
        if (operation.getOpcode() == OpCodeTypes.LDA)
        {
            try
            {
                int valueToLoad = hexDecimalToDecimal(getHexDigits(words[1]));
                a = (byte) valueToLoad;
            }
            catch (NumberFormatException nfe)
            {
                logger.error(nfe.getMessage());
            }
        }
        else if (operation.getOpcode() == OpCodeTypes.ADC)
        {
            //Add a value to the "A" counter.
            try
            {
                int valueToLoad = hexDecimalToDecimal(words[1].substring(2));
                a += valueToLoad;

            }
            catch (NumberFormatException nfe)
            {
                logger.error(nfe.getMessage());
            }
        }
        else if (operation.getOpcode() == OpCodeTypes.STA)
        {
            //Store "a" in a memory address
            try
            {
                int index = hexDecimalToDecimal(words[1].substring(2));
                memory[index] = a;
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                logger.error(ex.getMessage());
                //Store "a" into a valid memory address
                for (int i = 0; i < 16; ++i)
                {
                    if (memory[i] == 0)
                    {
                        memory[i] = a;
                    }
                }
            }
            catch (NumberFormatException nfe)
            {
                logger.error(nfe.getMessage());
            }
        }
    }

    private int hexDecimalToDecimal(String hexNumber)
    {
        if (hexNumber.trim().equals(""))
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(hexNumber, 16);
        }

    }

    /**
     * Returns the value in register X
     *
     * @return The value in register X
     */
    public byte getValueInRegisterX()
    {
        return x;
    }


}

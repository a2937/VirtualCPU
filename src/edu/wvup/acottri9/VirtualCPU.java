package edu.wvup.acottri9;

import com.sun.org.apache.xpath.internal.compiler.OpCodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The type Virtual cpu.
 */
public class VirtualCPU
{

    private ArrayList<OpCode> opCodes = new ArrayList<>();

    private byte programCounter = 0;

    private byte a = 0;

    private byte x = 0;

    private byte[] memory = new byte[16];

    /**
     * Instantiates a new Virtual cpu.
     *
     * @param fileName the file name
     */
    public VirtualCPU(String fileName)
    {


        initializeOpCodes();

        ProcessFile(fileName);
    }

    /**
     * Instantiates a new Virtual cpu.
     */
    public VirtualCPU()
    {
        initializeOpCodes();
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
     * Gets program counter.
     *
     * @return the program counter
     */
    public byte getProgramCounter()
    {
        return programCounter;
    }

    /**
     * Get memory byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getMemory()
    {
       return Arrays.copyOf(memory,16);
    }

    private void initializeOpCodes()
    {
        opCodes.add(new OpCode(1,OpCodeTypes.BRK)); //Break
        opCodes.add(new OpCode(2,OpCodeTypes.LDA)); // Load
        opCodes.add(new OpCode(2,OpCodeTypes.STA)); //Store
        opCodes.add(new OpCode(2,OpCodeTypes.ADC)); //Add
    }


    /**
     * Process file.
     *
     * @param fileName the file name
     */
    public void ProcessFile(String fileName)
    {
        String fileContents = readFile(fileName);

        String[] fileLines = fileContents.split("\n" );

        for (String line: fileLines)
        {
            ProcessLine(line);
        }


    }


    private void ProcessLine(String fileLine)
    {
        String words[] = fileLine.split("\\s+");
        OpCode operation = null;

        for (OpCode opCode:
             opCodes)
        {
            if(opCode.toString().equalsIgnoreCase(words[0]))
            {
                operation = opCode;

            }
        }

        if(operation != null)
        {
            programCounter += operation.getLength();

            if(operation.getOpcode() == OpCodeTypes.BRK)
            {
                //Break
                System.exit(0);
            }
            else if(operation.getOpcode() == OpCodeTypes.LDA)
            {
                //Load value into a
                try
                {

                    int valueToLoad = HexDecimalToDecimal(words[1].substring(2));
                    a = (byte)valueToLoad;
                    System.out.println(a);
                }
                catch(NumberFormatException nfe)
                {
                    System.err.println(nfe.getLocalizedMessage());
                }
            }
            else if(operation.getOpcode() == OpCodeTypes.ADC)
            {
                //Add value to A
                try
                {
                    int valueToLoad = HexDecimalToDecimal(words[1].substring(2));
                    a += valueToLoad;
                    System.out.println(a);
                }
                catch(NumberFormatException nfe)
                {
                    System.err.println(nfe.getLocalizedMessage());
                }
            }
            else if(operation.getOpcode() == OpCodeTypes.STA)
            {
                //Store the value in a memory address
                try
                {
                    int index = HexDecimalToDecimal(words[1].substring(2));
                    memory[index] = a;
                    System.out.println(memory[index]);
                }

                catch(ArrayIndexOutOfBoundsException ex)
                {
                    System.err.println(ex.getLocalizedMessage());
                    //Store a into a valid memory address
                   for(int i = 0; i < 16; ++i)
                   {
                       if(memory[i] == 0)
                       {
                            memory[i] = a;

                       }
                   }
                }
                catch(NumberFormatException nfe)
                {
                    System.err.println(nfe.getLocalizedMessage());
                }
            }
        }
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
        Scanner newScanner;
        try
        {
            newScanner = new Scanner(file);
            while ( newScanner.hasNextLine( ) )
            {
                completeString.append(newScanner.nextLine()).append("\n");
            }
            newScanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(fileName + " not found");
        }

        return completeString.toString();
    }



    private int HexDecimalToDecimal(String hexNumber)
    {
        return Integer.parseInt(hexNumber, 16);
    }


}

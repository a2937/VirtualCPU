package edu.wvup.acottri9.virtualcpu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class VirtualCPUTests
{
    private VirtualCPU cpu = new VirtualCPU();

    @AfterEach
    public void AfterEach()
    {
        cpu = new VirtualCPU();
    }

    @Test
    void canLoadXCorrectly()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("edu/wvup/acottri9/virtualCPU/loadX.txt").getFile());
        try
        {
            cpu.processFile(file);
            Assertions.assertEquals(100, cpu.getValueInRegisterX());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void canFindHexDigits() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method method = VirtualCPU.class.getDeclaredMethod("getHexDigits", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(cpu, "#$64");
        Assertions.assertEquals("64", result);
    }

    @Test
    void canFindHexLetters() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method method = VirtualCPU.class.getDeclaredMethod("getHexDigits", String.class);
        method.setAccessible(true);
        String result = (String) method.invoke(cpu, "#$FF");
        Assertions.assertEquals("FF", result);
    }


    @Test
    void canLoadACorrectly()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("edu/wvup/acottri9/virtualCPU/loadA.txt").getFile());
        try
        {
            cpu.processFile(file);
            Assertions.assertEquals(100, cpu.getValueInRegisterA());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void canAddToACorrectly()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("edu/wvup/acottri9/virtualCPU/addA.txt").getFile());
        try
        {
            cpu.processFile(file);
            Assertions.assertEquals(107, cpu.getValueInRegisterA());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void canIncrementXCorrectly()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("edu/wvup/acottri9/virtualCPU/incX.txt").getFile());
        try
        {
            cpu.processFile(file);
            Assertions.assertEquals(101, cpu.getValueInRegisterX());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}

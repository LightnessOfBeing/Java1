package ru.academic.university.mit.vishnyakov;
import static org.mockito.Mockito.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testReversePolishNotation() {
        String input = "1+(2-(3))*(2/4)";

        Stack<Double> mockedStackOperands = mock(Stack.class);
        Stack<Character> mockedStackOperators = mock(Stack.class);

        when(mockedStackOperators.empty())
                .thenReturn(true)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        when(mockedStackOperators.top())
                .thenReturn('(')
                .thenReturn('(')
                .thenReturn('-')
                .thenReturn('(')
                .thenReturn('+')
                .thenReturn('(')
                .thenReturn('/')
                .thenReturn('(');

        when(mockedStackOperators.pop())
                .thenReturn('(')
                .thenReturn('-')
                .thenReturn('(')
                .thenReturn('/')
                .thenReturn('(')
                .thenReturn('*')
                .thenReturn('+');

        Calculator calculator = new Calculator(mockedStackOperands, mockedStackOperators);
        assertEquals("wrong expression", "123-24/*+", calculator.parseToRPN(input));

        verify(mockedStackOperators, times(7))
                .push(anyChar());
        verifyZeroInteractions(mockedStackOperands);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCalculate() {
        String input = "123-24/*+";

        Stack<Character> mockedStackOperators = mock(Stack.class);
        Stack<Double> mockedStackOperands = mock(Stack.class);

        when(mockedStackOperands.pop())
                .thenReturn(3.0)
                .thenReturn(2.0)
                .thenReturn(4.0)
                .thenReturn(2.0)
                .thenReturn(0.5)
                .thenReturn(-1.0)
                .thenReturn(-0.5)
                .thenReturn(1.0);

        when(mockedStackOperands.top())
                .thenReturn(0.5);

        Calculator calculator = new Calculator(mockedStackOperands, mockedStackOperators);
        assertEquals("wrong value", 0.5, calculator.calculate(input), 1e-9);

        verify(mockedStackOperands, times(9))
                .push(anyDouble());
        verifyZeroInteractions(mockedStackOperators);
    }
}
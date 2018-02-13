package ru.academic.university.mit.vishnyakov;
import static org.mockito.Mockito.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testReversePolishNotation() {
        String input = "65 + (57 - (35)) * (200 / 10)";

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
        assertEquals("65 57 35 - 200 10 / * + ", calculator.parseToRPN(input));

        verify(mockedStackOperators, times(7)).push(anyChar());
        verifyZeroInteractions(mockedStackOperands);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCalculate() {
        String input = "65 57 35 - 200 10 / * + ";

        Stack<Character> mockedStackOperators = mock(Stack.class);
        Stack<Double> mockedStackOperands = mock(Stack.class);

        when(mockedStackOperands.pop())
                .thenReturn(35.0)
                .thenReturn(57.0)
                .thenReturn(10.0)
                .thenReturn(200.0)
                .thenReturn(20.0)
                .thenReturn(22.0)
                .thenReturn(440.0)
                .thenReturn(65.0);

        when(mockedStackOperands.top()).thenReturn(505.0);

        Calculator calculator = new Calculator(mockedStackOperands, mockedStackOperators);
        assertEquals(505.0, calculator.calculate(input), 1e-9);

        verify(mockedStackOperands, times(24)).push(anyDouble());
        verifyZeroInteractions(mockedStackOperators);
    }
}
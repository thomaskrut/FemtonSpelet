import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testUpdateButtonArray() {

        Grid g = new Grid(true);
        List<Integer> testList = g.generateFixedListOfNumbers();
        g.gameBoard = g.generateBoardArray(testList);
        System.out.println(testList);
        assertFalse(g.updateButtonArray(new JButton("10"))); //ska returnera false, 10 går inte att flytta
        assertTrue(g.updateButtonArray(new JButton("4"))); // ska returnera true, 4 går att flytta


    }

    @Test
    void checkForWinningPosition() {
    }
}
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GridTest {

    @Test
    void testUpdateButtonArray() {

        Grid g = new Grid(true);
        g.rows = 4;
        g.columns = 4;
        List<Integer> testList = g.generateListOfNumbers(true);
        g.gameBoard = g.generateBoardArray(testList);
        System.out.println(testList);
        assertEquals(testList.get(5), 0);
        assertEquals(g.gameBoard[1][1], 0);
        assertFalse(g.updateButtonArray(new JButton("10"))); // ska returnera false, 10 går inte att flytta
        assertTrue(g.updateButtonArray(new JButton("4"))); // ska returnera true, 4 går att flytta
        assertEquals(g.gameBoard[1][0], 0);
        assertEquals(g.gameBoard[1][1], 4); // 4:an har flyttats


    }

    @Test
    void testCheckForWinningPosition() {
        Grid g = new Grid(true);

        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i < g.columns * g.rows; i++) {
            listOfNumbers.add(i);
        }

        listOfNumbers.add(0);
        System.out.println(listOfNumbers);
        g.gameBoard = g.generateBoardArray(listOfNumbers);
        assertTrue(g.checkForWinningPosition());
        g.updateButtonArray(new JButton("15"));
        System.out.println(listOfNumbers);
        assertFalse(g.checkForWinningPosition());
        g.updateButtonArray(new JButton("11"));
        assertFalse(g.checkForWinningPosition());
        g.updateButtonArray(new JButton("11"));



    }
}
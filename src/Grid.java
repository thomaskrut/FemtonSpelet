import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class Grid implements ActionListener {

    JFrame frame = new JFrame();
    JPanel gamePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton[][] buttonArray;
    JButton newGame = new JButton("Nytt spel");
    JButton cheatButton = new JButton("Fuskknappen");
    int turnCounter = 0;
    int rows = 4;
    int columns = 4;
    List<Integer> listOfNumbers;
    int[][] gameBoard;
    Stopwatch timer = new Stopwatch();


    public Grid(boolean testing) {

        if (!testing) {
            initGame();
        }

    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void initGame() {

        frame.setTitle("Femtonspelet");
        frame.setLayout(new BorderLayout());
        gamePanel.setLayout(new GridLayout(rows, columns));
        frame.add(gamePanel);
        frame.add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.add(newGame);
        buttonPanel.add(cheatButton);
        newGame.addActionListener(this);
        cheatButton.addActionListener(this);

        listOfNumbers = generateListOfNumbers(false);
        gameBoard = generateBoardArray(listOfNumbers);
        buttonArray = generateButtonArray(gameBoard);

        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        timer.start();
    }

    public List<Integer> generateListOfNumbers(boolean fixed) {

        if (!fixed) {
            List<Integer> listOfNumbers = new ArrayList<>();
            for (int i = 1; i < columns * rows; i++) {
                listOfNumbers.add(i);
            }
            Collections.shuffle(listOfNumbers);
            listOfNumbers = makeSolvable(listOfNumbers);
            return listOfNumbers;

        } else {

            List<Integer> listOfNumbers = new ArrayList<>();
            for (int i = 0; i < columns * rows; i++) {
                listOfNumbers.add(i);
            }
            Collections.swap(listOfNumbers, 0, 1);
            Collections.swap(listOfNumbers, 5, 1);

            return listOfNumbers;
        }
    }

    // Returnerar en array av integer som motsvarar knapparnas
    // värden.
    public int[][] generateBoardArray(List<Integer> listOfNumbers) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }
        return gameBoardArray;
    }

    // Returnerar en array av JButtons vars utseende, text och
    // actionListener sätts i en for-loop. Knapp med texten "0"
    // blir osynlig.
    public JButton[][] generateButtonArray(int[][] gameBoard) {
        JButton[][] buttonArray = new JButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j] = new JButton();
                buttonArray[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttonArray[i][j].addActionListener(this);
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                buttonArray[i][j].setFocusable(false);

                if (gameBoard[i][j] == 0) {
                    buttonArray[i][j].setVisible(false);
                }
                gamePanel.add(buttonArray[i][j]);
            }
        }
        return buttonArray;
    }


    // Ritar upp alla knappar. Anropas då knappar bytt
    // text efter att användaren klickat, samt vid
    // ny spelomgång.
    public void updateButtonsDisplay() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                buttonArray[i][j].setVisible(gameBoard[i][j] != 0);
            }
        }
    }

    // Metoden tar emot en JButton, läser av texten på den, hittar den
    // i grid-arrayen och kollar sedan om någon intilliggande knapp
    // är "tom", i så fall byter knapparna text och metoden returnerar
    // true, annars returnerar metoden false.
    public boolean updateButtonArray(JButton buttonPressed) {
        int number = Integer.parseInt(buttonPressed.getText());
        int x = 0;
        int y = 0;

        //hittar x och y för den siffra man klickat på
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] == number) {
                    x = i;
                    y = j;
                }
            }
        }

        // kollar knappen till vänster om den man klickat på
        if (x > 0) {
            if (gameBoard[x - 1][y] == 0) {
                gameBoard[x - 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen till höger om den man klickat på
        if (x < columns - 1) {
            if (gameBoard[x + 1][y] == 0) {
                gameBoard[x + 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen ovanför den man klickat på
        if (y > 0) {
            if (gameBoard[x][y - 1] == 0) {
                gameBoard[x][y - 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen nedanför den man klickat på
        if (y < rows - 1) {
            if (gameBoard[x][y + 1] == 0) {
                gameBoard[x][y + 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //returnerar false om man tryckte på en knapp som
        //inte hade en tom knapp bredvid
        return false;
    }

    // Metoden returnerar true om siffrorna ligger i nummerordning
    // och om den tomma rutan är först eller sist.
    public boolean checkForWinningPosition() {

        int counter = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] == counter) {
                    counter++;
                }
            }
        }

        if (counter == rows * columns && (gameBoard[0][0] == 0 || gameBoard[rows - 1][columns - 1] == 0)) {
            return true;
        } else {
            return false;
        }

    }

    // Metoden tar emot en lista och räknar antalet "inversions", dvs
    // par av siffror där en efterliggande siffran är högra än den
    // som står först. Om antalet inversion är udda måste den tomma rutan
    // ligga på en jämn rad nerifrån räknat; om antalet inversions är jämnt
    // måste den tomma rutan ligga på en udda rad nerifrån räknat. Metoden
    // stoppar in den tomma rutan (0:an) på rätt rad och returnerar listan.
    // Koden är vår egen, men logiken har vi hittat på denna websida:
    // https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
    public List<Integer> makeSolvable(List<Integer> list) {

        int numberOfInversions = 0;
        Random rand = new Random();
        int rowToPutZero = rand.nextInt(rows);
        int columnToPutZero = rand.nextInt(columns);
        int indexOfZeroInList;

        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    numberOfInversions++;
                }
            }

        }

        if (numberOfInversions % 2 == 0) { // jämnt antal inversions
            while (rowToPutZero % 2 == 0) {
                rowToPutZero = rand.nextInt(rows);
            }
        }
        if (numberOfInversions % 2 != 0) { // udda antal inversions
            while (rowToPutZero % 2 != 0) {
                rowToPutZero = rand.nextInt(rows);
            }
        }

        indexOfZeroInList = columnToPutZero + (rowToPutZero * rows);
        list.add(indexOfZeroInList, 0);

        return list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(newGame) || buttonPressed.equals(cheatButton)) {

            if (buttonPressed.equals(newGame)) {

                listOfNumbers = generateListOfNumbers(false);

            } else if (buttonPressed.equals(cheatButton)) {

                listOfNumbers = generateListOfNumbers(true);

            }

            turnCounter = 0;
            timer.reset();
            gameBoard = generateBoardArray(listOfNumbers);
            updateButtonsDisplay();

        } else {

            if (updateButtonArray(buttonPressed)) {
                updateButtonsDisplay();
                turnCounter++;
                if (checkForWinningPosition()) {

                    timer.stop();

                    String winMessage = "Grattis, du löste pusslet!\n\nAntal förflyttningar: " + getTurnCounter() + "\nTid: " + timer.getTimeString() + "\n\n";
                    String[] choices = {"Nytt spel", "Avsluta"};

                    int userChoice = JOptionPane.showOptionDialog(null, winMessage, "Grattis!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);

                    if (userChoice == 0) {
                        newGame.doClick();

                    } else {
                        System.exit(0);
                    }
                }

            }


        }
    }
}



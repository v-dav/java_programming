import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.net.URI;
import java.util.List;

public class Calculator extends JFrame {

    private static final int WINDOW_WIDTH = 340;
    private static final int WINDOW_HEIGHT = 600;
    private static final int SQUARE_BUTTON_SIZE = 60;
    private final InfixToPostfix infixToPostfix;

    private static final Color BACKGROUND_COLOR = new Color(22, 22, 22);
    private static final Color DISPLAY_BACKGROUND = new Color(32, 32, 32);
    private static final Color NUMBER_BUTTON_COLOR = new Color(50, 50, 50);
    private static final Color NUMBER_BUTTON_HOVER = new Color(70, 70, 70);
    private static final Color OPERATOR_BUTTON_COLOR = new Color(255, 149, 0);
    private static final Color OPERATOR_BUTTON_HOVER = new Color(255, 169, 20);
    private static final Color SPECIAL_BUTTON_COLOR = new Color(165, 165, 165);
    private static final Color SPECIAL_BUTTON_HOVER = new Color(185, 185, 185);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color OPERATOR_TEXT_COLOR = Color.WHITE;
    private static final Color SPECIAL_TEXT_COLOR = Color.BLACK;

    public Calculator() {
        super("Calculator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        infixToPostfix = new InfixToPostfix();
        initialize();
        setVisible(true);
    }

    // Custom Round Button Class
    private class RoundButton extends JButton {
        private Color backgroundColor;
        private Color hoverColor;
        private Color currentColor;
        private boolean isHovered = false;
        private boolean isPressed = false;

        public RoundButton(String text, Color bgColor, Color hoverColor, Color textColor) {
            super(text);
            this.backgroundColor = bgColor;
            this.hoverColor = hoverColor;
            this.currentColor = bgColor;

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(textColor);
            setFont(new Font("SF Pro Display", Font.BOLD, 18));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    currentColor = hoverColor;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    currentColor = backgroundColor;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    currentColor = backgroundColor.darker();
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    currentColor = isHovered ? hoverColor : backgroundColor;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create circular shape
            int diameter = Math.min(getWidth(), getHeight()) - 4;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            Ellipse2D circle = new Ellipse2D.Double(x, y, diameter, diameter);

            // Fill the circle with current color
            g2d.setColor(currentColor);
            g2d.fill(circle);

            // Add subtle shadow effect
            if (!isPressed) {
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fill(new Ellipse2D.Double(x + 2, y + 2, diameter, diameter));
                g2d.setColor(currentColor);
                g2d.fill(circle);
            }

            // Draw the text
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (getWidth() - fm.stringWidth(getText())) / 2;
            int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2d.drawString(getText(), textX, textY);

            g2d.dispose();
        }

        @Override
        public boolean contains(int x, int y) {
            // Only respond to clicks within the circular area
            int diameter = Math.min(getWidth(), getHeight()) - 4;
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = diameter / 2;

            double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
            return distance <= radius;
        }
    }

    private void initialize() {
        // Create display background panel
        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(DISPLAY_BACKGROUND);
        displayPanel.setBounds(15, 10, 310, 60);
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        displayPanel.setLayout(null);

        JLabel resultLabel = new JLabel();
        resultLabel.setName("ResultLabel");
        resultLabel.setText("0");
        resultLabel.setBounds(120, 5, 170, 50);
        resultLabel.setFont(new Font("SF Pro Display", Font.BOLD, 36));
        resultLabel.setForeground(TEXT_COLOR);
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel equationLabel = new JLabel();
        equationLabel.setName("EquationLabel");
        equationLabel.setBounds(10, 5, 160, 50);
        equationLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
        equationLabel.setForeground(new Color(153, 153, 153));
        equationLabel.setHorizontalAlignment(SwingConstants.LEFT);

        displayPanel.add(resultLabel);
        displayPanel.add(equationLabel);

        addButtons(equationLabel, resultLabel);
        setLayout(null);

        add(displayPanel);
    }

    private RoundButton createRoundButton(String text, Color bgColor, Color hoverColor, Color textColor) {
        return new RoundButton(text, bgColor, hoverColor, textColor);
    }

    private void addButtons(JLabel equationLabel, JLabel resultLabel) {
        // Number buttons
        RoundButton zeroButton = createRoundButton("0", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        zeroButton.setName("Zero");
        zeroButton.setBounds(20, 90, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        zeroButton.addActionListener(event -> addText(equationLabel, zeroButton.getText()));
        add(zeroButton);

        RoundButton oneButton = createRoundButton("1", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        oneButton.setName("One");
        oneButton.setBounds(100, 90, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        oneButton.addActionListener(event -> addText(equationLabel, oneButton.getText()));
        add(oneButton);

        RoundButton twoButton = createRoundButton("2", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        twoButton.setName("Two");
        twoButton.setBounds(180, 90, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        twoButton.addActionListener(event -> addText(equationLabel, twoButton.getText()));
        add(twoButton);

        RoundButton threeButton = createRoundButton("3", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        threeButton.setName("Three");
        threeButton.setBounds(260, 90, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        threeButton.addActionListener(event -> addText(equationLabel, threeButton.getText()));
        add(threeButton);

        RoundButton fourButton = createRoundButton("4", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        fourButton.setName("Four");
        fourButton.setBounds(20, 170, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        fourButton.addActionListener(event -> addText(equationLabel, fourButton.getText()));
        add(fourButton);

        RoundButton fiveButton = createRoundButton("5", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        fiveButton.setName("Five");
        fiveButton.setBounds(100, 170, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        fiveButton.addActionListener(event -> addText(equationLabel, fiveButton.getText()));
        add(fiveButton);

        RoundButton sixButton = createRoundButton("6", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        sixButton.setName("Six");
        sixButton.setBounds(180, 170, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        sixButton.addActionListener(event -> addText(equationLabel, sixButton.getText()));
        add(sixButton);

        RoundButton sevenButton = createRoundButton("7", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        sevenButton.setName("Seven");
        sevenButton.setBounds(260, 170, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        sevenButton.addActionListener(event -> addText(equationLabel, sevenButton.getText()));
        add(sevenButton);

        RoundButton eightButton = createRoundButton("8", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        eightButton.setName("Eight");
        eightButton.setBounds(20, 250, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        eightButton.addActionListener(event -> addText(equationLabel, eightButton.getText()));
        add(eightButton);

        RoundButton nineButtom = createRoundButton("9", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        nineButtom.setName("Nine");
        nineButtom.setBounds(100, 250, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        nineButtom.addActionListener(event -> addText(equationLabel, nineButtom.getText()));
        add(nineButtom);

        // Operator buttons
        RoundButton addButton = createRoundButton("+", OPERATOR_BUTTON_COLOR, OPERATOR_BUTTON_HOVER, OPERATOR_TEXT_COLOR);
        addButton.setName("Add");
        addButton.setBounds(20, 330, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        addButton.addActionListener(event -> addText(equationLabel, addButton.getText()));
        add(addButton);

        RoundButton substractButton = createRoundButton("−", OPERATOR_BUTTON_COLOR, OPERATOR_BUTTON_HOVER, OPERATOR_TEXT_COLOR);
        substractButton.setName("Subtract");
        substractButton.setBounds(100, 330, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        substractButton.addActionListener(event -> addText(equationLabel, "-"));
        add(substractButton);

        RoundButton divideButton = createRoundButton("÷", OPERATOR_BUTTON_COLOR, OPERATOR_BUTTON_HOVER, OPERATOR_TEXT_COLOR);
        divideButton.setName("Divide");
        divideButton.setBounds(180, 330, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        divideButton.addActionListener(event -> addText(equationLabel, "\u00F7"));
        add(divideButton);

        RoundButton multiplyButton = createRoundButton("×", OPERATOR_BUTTON_COLOR, OPERATOR_BUTTON_HOVER, OPERATOR_TEXT_COLOR);
        multiplyButton.setName("Multiply");
        multiplyButton.setBounds(260, 330, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        multiplyButton.addActionListener(event -> addText(equationLabel, "\u00D7"));
        add(multiplyButton);

        // Special function buttons
        RoundButton solveButton = createRoundButton("=", OPERATOR_BUTTON_COLOR, OPERATOR_BUTTON_HOVER, OPERATOR_TEXT_COLOR);
        solveButton.setName("Equals");
        solveButton.setBounds(260, 250, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        solveButton.addActionListener(event -> solve(equationLabel, resultLabel));
        add(solveButton);

        RoundButton clearButton = createRoundButton("C", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        clearButton.setName("Clear");
        clearButton.setBounds(180, 490, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        clearButton.addActionListener(event -> clear(equationLabel, resultLabel));
        add(clearButton);

        RoundButton deleteButton = createRoundButton("⌫", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        deleteButton.setName("Delete");
        deleteButton.setBounds(260, 490, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        deleteButton.addActionListener(event -> delete(equationLabel));
        add(deleteButton);

        RoundButton decimalButton = createRoundButton(".", NUMBER_BUTTON_COLOR, NUMBER_BUTTON_HOVER, TEXT_COLOR);
        decimalButton.setName("Dot");
        decimalButton.setBounds(180, 250, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        decimalButton.addActionListener(event -> addText(equationLabel, decimalButton.getText()));
        add(decimalButton);

        // Advanced function buttons
        RoundButton parentheses = createRoundButton("( )", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        parentheses.setName("Parentheses");
        parentheses.setBounds(20, 490, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        parentheses.addActionListener(event -> addText(equationLabel, parentheses.getText()));
        add(parentheses);

        RoundButton squareRoot = createRoundButton("√", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        squareRoot.setName("SquareRoot");
        squareRoot.setBounds(260, 410, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        squareRoot.addActionListener(event -> addText(equationLabel, squareRoot.getText() + "("));
        add(squareRoot);

        RoundButton plusMinus = createRoundButton("±", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        plusMinus.setName("PlusMinus");
        plusMinus.setBounds(20, 410, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        plusMinus.addActionListener(event -> addText(equationLabel, "(-"));
        add(plusMinus);

        RoundButton powerTwo = createRoundButton("x²", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        powerTwo.setName("PowerTwo");
        powerTwo.setBounds(100, 410, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        powerTwo.addActionListener(event -> addText(equationLabel, "^(2)"));
        add(powerTwo);

        RoundButton powerY = createRoundButton("x^y", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        powerY.setName("PowerY");
        powerY.setBounds(180, 410, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        powerY.addActionListener(event -> addText(equationLabel, "^("));
        add(powerY);

        RoundButton authorButton = createRoundButton("☕", SPECIAL_BUTTON_COLOR, SPECIAL_BUTTON_HOVER, SPECIAL_TEXT_COLOR);
        authorButton.setBounds(100, 490, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        authorButton.setFont(new Font("SF Pro Display", Font.PLAIN, 30));
        authorButton.addActionListener(event -> openLinkedIn());
        authorButton.setToolTipText("Created with ❤\uFE0F by Vladimir Davidov");
        add(authorButton);
    }

    private void delete(JLabel equationLabel) {
        String existingText = equationLabel.getText();
        if (!existingText.isEmpty()) {
            String newText = existingText.substring(0, existingText.length() - 1);
            equationLabel.setText(newText);
            equationLabel.setForeground(Color.GREEN.darker());
        }
    }

    private void clear(JLabel equationLabel, JLabel resultLabel) {
        equationLabel.setText("");
        equationLabel.setForeground(Color.GREEN.darker());
        resultLabel.setText("0");
    }

    private void addText(JLabel equationLabel, String symbol) {
        String existingText = equationLabel.getText();

        // Handle square root at the start
        if (existingText.isBlank() && symbol.startsWith("\u221A")) {
            equationLabel.setText(symbol);
            return;
        }

        if (existingText.isBlank() && isOperator(symbol)) {
            return;
        }

        String newText;
        if (!existingText.isBlank() &&
                isOperator(symbol) &&
                isOperator(String.valueOf(existingText.charAt(existingText.length() - 1)))) {
            newText = existingText.substring(0, existingText.length() - 1) + symbol;
        } else {
            if (isOperator(symbol)) {
                String formattedText = formatLastNumber(existingText);
                newText = formattedText + symbol;
            } else if ("( )".equals(symbol)) {
                newText = existingText + parenthese(existingText);
            } else if ("(-".equals(symbol)) {
                newText = handleNegation(existingText);
            } else if (symbol.equals("^(2)") || symbol.equals("^(")) {
                // Power operators need a number before them
                if (existingText.isEmpty() || isOperator(String.valueOf(existingText.charAt(existingText.length() - 1)))) {
                    return;
                }
                newText = existingText + symbol;
            } else {
                newText = existingText + symbol;
            }
        }
        equationLabel.setText(newText);
    }

    private String handleNegation(String existingText) {
        // If empty, just add (-
        if (existingText.isEmpty()) {
            return "(-";
        }

        // Check if we already have (- at the beginning
        if (existingText.startsWith("(-")) {
            // Toggle: remove the negation
            return existingText.substring(2);
        }

        // Check if the last two characters are (-
        if (existingText.length() >= 2 &&
                existingText.substring(existingText.length() - 2).equals("(-")) {
            // Remove the trailing (-
            return existingText.substring(0, existingText.length() - 2);
        }

        // Find the last number and wrap it in negation
        int lastNumberStart = findLastNumberStartForNegation(existingText);

        // If we found a number to negate
        if (lastNumberStart < existingText.length() &&
                Character.isDigit(existingText.charAt(lastNumberStart))) {
            String beforeNumber = existingText.substring(0, lastNumberStart);
            String number = existingText.substring(lastNumberStart);
            return beforeNumber + "(-" + number;
        }

        // Default: just append (-
        return existingText + "(-";
    }

    private int findLastNumberStartForNegation(String text) {
        // Find the start of the last number
        for (int i = text.length() - 1; i >= 0; i--) {
            char ch = text.charAt(i);
            if (isOperator(String.valueOf(ch)) || ch == '(' || ch == ')') {
                return i + 1;
            }
        }
        return 0; // Number starts at beginning
    }

    private String parenthese(String expression) {
        int nbLeftParentheses = 0;
        int nbRightParentheses = 0;

        if (expression.isBlank()) {
            return "(";
        }

        String lastCharacter = expression.substring(expression.length() - 1);

        for (String s : expression.split("")) {
            if ("(".equals(s)) {
                nbLeftParentheses++;
            } else if (")".equals(s)) {
                nbRightParentheses++;
            }
        }

        if (nbLeftParentheses == nbRightParentheses ||
                "(".equals(lastCharacter) ||
                isOperator(lastCharacter) ||
                lastCharacter.equals("\u221A")) {
            return "(";
        }
        return ")";
    }

    private String formatLastNumber(String text) {
        // Find the start of the last number by looking for the last operator
        int lastNumberStart = findLastNumberStart(text);
        String lastNumber = text.substring(lastNumberStart);

        // Handle numbers that start with a dot (like ".05" -> "0.05")
        if (lastNumber.startsWith(".")) {
            lastNumber = "0" + lastNumber;
        }

        // Handle numbers that end with a dot (like "7." -> "7.0")
        if (lastNumber.endsWith(".")) {
            lastNumber = lastNumber + "0";
        }

        return text.substring(0, lastNumberStart) + lastNumber;
    }

    private int findLastNumberStart(String text) {
        // Find the position after the last operator (or 0 if no operator found)
        for (int i = text.length() - 1; i >= 0; i--) {
            if (isOperator(String.valueOf(text.charAt(i))) || text.charAt(i) == '(' || text.charAt(i) == ')') {
                return i + 1;
            }
        }
        return 0; // No operator found, number starts at beginning
    }

    private void solve(JLabel equationLabel, JLabel resultLabel) {
        List<String> splitEquation = infixToPostfix.splitNumbersOpsAndParentheses(equationLabel);
        if (!infixToPostfix.infixIsCorrect(splitEquation)) {
            equationLabel.setForeground(Color.RED.darker());
            return;
        }
        String postfix = infixToPostfix.infixToPostfix(splitEquation);
        Float result;
        try {
            result = infixToPostfix.evaluatePostfix(postfix);
        } catch (ArithmeticException e) {
            equationLabel.setForeground(Color.RED.darker());
            return;
        }

        // Reset color on successful calculation
        equationLabel.setForeground(Color.GREEN.darker());

        boolean isResultFloat = checkZeroDecimal(result);

        if (!isResultFloat) {
            int resultInt = Math.round(result);
            resultLabel.setText(String.valueOf(resultInt));
        } else {
            resultLabel.setText(String.valueOf(result));
        }
    }

    private boolean checkZeroDecimal(Float result) {
        String s = String.valueOf(result);
        String[] array = s.split("\\.");
        return array.length > 1 && (array[1].length() != 1 || !"0".equals(array[1]));
    }

    private boolean isOperator(String operator) {
        return List.of("+", "-", "×", "÷", "^").contains(operator);
    }

    private void openLinkedIn() {
        try {
            String linkedInURL = "https://www.linkedin.com/in/v-dav/";
            Desktop.getDesktop().browse(new URI(linkedInURL));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Visit my LinkedIn: https://www.linkedin.com/in/v-dav/",
                    "Author",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
package four;

import javax.swing.*;
import java.awt.*;

class Cell extends JButton {
    public Cell(String label) {
        super(label);
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setBackground(new Color(144, 188, 144));
        setOpaque(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Paper extends JPanel {
    private Color corAtual = Color.BLACK;                 // Cor atual do pincel
    private int tamPincel = 10;                           // Tamanho atual do pincel
    private BufferedImage image;                          // Tela do desenho
    private Graphics2D g2d;                               // Contexto da Tela
    private int xMouse, yMouse;                           // Coordenadas do mouse

    JLabel numeroSize = new JLabel(String.valueOf(tamPincel));

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  // Dimensao da tela



    public Paper() {
        this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        this.setBackground(Color.WHITE);

        image = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setColor(corAtual);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                xMouse = e.getX();
                yMouse = e.getY();
                g2d.setColor(corAtual);
                g2d.setStroke(new BasicStroke(tamPincel, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(xMouse, yMouse, e.getX(), e.getY());
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                g2d.setColor(corAtual);
                g2d.setStroke(new BasicStroke(tamPincel, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(xMouse, yMouse, e.getX(), e.getY());
                xMouse = e.getX();
                yMouse = e.getY();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public JButton createColorButton(JButton b, Color color, int x, int y) {
        b.setForeground(color);
        b.setBounds(x, y, 100, 100);
        b.addActionListener(actionEvent -> { corAtual = color; });

        return b;
    }

    public void ClearPaper() {
        g2d.setPaint(Color.white);
        g2d.fillRect(0,0,screenSize.width,screenSize.height);
        g2d.setPaint(corAtual);
        repaint();
    }

    public void upSize() {
        tamPincel += 5;
        numeroSize.setText(String.valueOf(tamPincel));
    }

    public void downSize() {
        if (tamPincel > 5) {
            tamPincel -= 5;
            numeroSize.setText(String.valueOf(tamPincel));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Paper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Paper paper = new Paper();
        frame.getContentPane().add(paper);

        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);

        /*  Buttons Colors  */
        JButton blue = new JButton("Blue");
        JButton red = new JButton("red");
        JButton green = new JButton("green");
        JButton black = new JButton("black");
        JButton white = new JButton("white");
        JButton clear = new JButton("clear");
        JButton mais = new JButton("+");
        JButton menos = new JButton("-");

        /* Config Buttons*/
        paper.createColorButton(blue, Color.BLUE, 10, 0);
        paper.createColorButton(red, Color.RED, 120, 0);
        paper.createColorButton(green, Color.GREEN, 230, 0);
        paper.createColorButton(black, Color.BLACK, 340, 0);
        paper.createColorButton(white, Color.WHITE, 450, 0);

        clear.setBounds(560, 0, 100, 100);
        clear.addActionListener(actionEvent -> { paper.ClearPaper(); });

        mais.setBounds(670, 0, 100, 100);
        mais.addActionListener(actionEvent -> { paper.upSize(); });

        menos.setBounds(780, 0, 100, 100);
        menos.addActionListener(actionEvent -> { paper.downSize(); });

        /* Add Buttons */
        paper.add(blue);
        paper.add(red);
        paper.add(green);
        paper.add(black);
        paper.add(white);
        paper.add(clear);
        paper.add(mais);
        paper.add(paper.numeroSize);
        paper.add(menos);
    }

}

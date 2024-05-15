import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Paper extends JPanel {
    private List<Point> retangulo = new ArrayList<>(); // Lista para armazenar as coordenadas dos retangulos
    private List<Color> cores = new ArrayList<>(); // Lista para armazenar as cores dos retangulos
    private Point currentPoint; // Coordenada atual do mouse
    private static int sizeA = 10; // Tamanho das linhas
    private List<Integer> sizes = new ArrayList<>(); // Tamanhos antigos das linhas

    public Paper() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                retangulo.add(e.getPoint()); // Adiciona a coordenada inicial do retangulo
                cores.add(getCorAtual());
                sizes.add(sizeA);
                currentPoint = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentPoint = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentPoint != null) {
                    int dx = e.getX() - currentPoint.x;
                    int dy = e.getY() - currentPoint.y;
                    int steps = Math.max(Math.abs(dx), Math.abs(dy));
                    double xIncrement = (double) dx / steps;
                    double yIncrement = (double) dy / steps;

                    double x = currentPoint.x;
                    double y = currentPoint.y;
                    for (int i = 0; i < steps; i++) {
                        x += xIncrement;
                        y += yIncrement;
                        retangulo.add(new Point((int) x, (int) y));
                        cores.add(getCorAtual());
                        sizes.add(sizeA);
                    }
                }
                currentPoint = e.getPoint();
                repaint();
            }
        });
    }

    private Color getCorAtual() {
        return cores.size() > 0 ? cores.get(cores.size() - 1) : Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < retangulo.size(); i++) {
            g.setColor(cores.get(i));
            Point circle = retangulo.get(i);
            sizeA = sizes.get(i);
            g.fillRect(circle.x - 5, circle.y - 5, sizeA, sizeA);
        }
        if (currentPoint != null) {
            g.setColor(getCorAtual());
            g.fillRect(currentPoint.x - 5, currentPoint.y - 5, sizeA, sizeA);
        }
    }

    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int width = screenSize.width;        // Largura
        int height = screenSize.height;      // Altura

        JFrame frame = new JFrame();                            // JFRAME
        frame.setSize(width, height);                           // Tamanho da tela
        frame.setVisible(true);                                 // Visibilidade da tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Poder fechar a tela
        frame.setLayout(new BorderLayout());                    // Layout do frame

        Paper panel = new Paper();
        panel.setPreferredSize(new Dimension(width, height - 500));     // Tamanho do panel
        panel.setBackground(Color.white);                                      // Cor
        frame.add(panel);                                                      // Adicionando o panel no frame
        panel.setLayout(null);

        /*       Botao Azul       */
        JButton colorBlue = new JButton("Blue");
        colorBlue.setBounds(10, 10, 100, 30);
        colorBlue.setForeground(Color.BLUE);
        colorBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.setCorAtual(Color.BLUE);
            }
        });

        /*       Botao Vermelho       */
        JButton colorRed = new JButton("Vermelho");
        colorRed.setBounds(120, 10, 100, 30);
        colorRed.setForeground(Color.RED);
        colorRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.setCorAtual(Color.RED);
            }
        });

        /*       Botao Verde       */
        JButton colorGreen = new JButton("Verde");
        colorGreen.setBounds(230, 10, 100, 30);
        colorGreen.setForeground(Color.GREEN);
        colorGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.setCorAtual(Color.GREEN);
            }
        });

        /*       Botao Preto       */
        JButton colorBlack = new JButton("Preto");
        colorBlack.setBounds(340, 10, 100, 30);
        colorBlack.setForeground(Color.BLACK);
        colorBlack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.setCorAtual(Color.BLACK);
            }
        });

        /*       Botao Branco       */
        JButton colorWhite = new JButton("Branco");
        colorWhite.setBounds(450, 10, 100, 30);
        colorWhite.setForeground(Color.WHITE);
        colorWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panel.setCorAtual(Color.WHITE);
            }
        });

        /*       Aumentar Pincel       */
        JButton aumentar = new JButton("+");
        aumentar.setBounds(560, 10, 100, 30);
        aumentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sizeA++;
            }
        });

        /*       Diminuir Pincel       */
        JButton diminuir = new JButton("-");
        diminuir.setBounds(670, 10, 100, 30);
        diminuir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (sizeA > 1)  {
                    sizeA--;
                }
            }
        });

        panel.add(colorRed);
        panel.add(colorBlue);
        panel.add(colorGreen);
        panel.add(colorBlack);
        panel.add(colorWhite);
        panel.add(aumentar);
        panel.add(diminuir);
        frame.setVisible(true);
    }

    public void setCorAtual(Color cor) {
        cores.add(cor);
    }
}

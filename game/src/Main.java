import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import static javax.swing.JOptionPane.showMessageDialog;

public class Main {

    public static void main(String[] args) {
        new Window();
    }
}

class Texture3 {

    protected double x, y;
    protected String fileName;

    protected BufferedImage  image = null;
    boolean noImage = false;

    public Texture3(double x, double y, String fileName)  {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
    }

    public void load() {
        if (image == null) {
            File f = new File(fileName);
            try {
                image = ImageIO.read(f);
            } catch (Exception e) {
                noImage = true;
            }
        }
    }

    public void paint(Graphics g) {
        load();
        if(noImage) {
            g.setColor(Color.RED);
            g.fillOval( (int) x, (int) y, 100, 100);
        }
        else {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }


}


class Texture2 {

    protected double x, y;

    protected BufferedImage  image = null;
    boolean noImage = false;

    public Texture2(double x, double y, String fileName)  {
        this.x = x;
        this.y = y;

        File f = new File(fileName);


        try {
            image = ImageIO.read(f);
        }
        catch (Exception e) {
            noImage = true;
        }
    }

    public void paint(Graphics g) {
        if(noImage) {
            g.setColor(Color.RED);
            g.fillOval( (int) x, (int) y, 100, 100);
        }
        else {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }


}




class Texture {

    protected double x, y;

    protected BufferedImage  image = null;

    public Texture(double x, double y, String fileName)  {
        this.x = x;
        this.y = y;

        File f = new File(fileName);

        try {
            image = ImageIO.read(f);
            // int n = 1/0;
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не могу загрузить " + fileName);
            System.exit(1);

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Исключение другого типа");
            System.exit(1);

        }
    }

    public void paint(Graphics g) {
        g.drawImage(image, (int)x, (int)y, null );
    }


}


class Window extends JFrame {

    public Window()  {
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("Мэйджик Бол");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Panel p = new Panel();

        add(p);
        addKeyListener(p);
        revalidate();
    }
}

class Panel extends JPanel implements KeyListener {

    Texture2 skin, badskin;
    Random random = new Random();
    int position = 200;
    int badpos = 900;
    int badposy = random.nextInt(381) + 10;
    int counted = 1;

    int badspeed = 3;
    int myspeed = 5;

    public Panel() {
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLACK);
        showMessageDialog(null, "Управляйте птицей стрелками вверх и вниз.");
        skin = new Texture2(100, 200, "player_frame.png");
        badskin = new Texture2(900, badposy, "player_frame_reverse.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        skin.paint(g);
        badpos -= badspeed;
        if (badpos < 50) {
            badpos = 900;
            counted += 1;
            badposy = random.nextInt(381) + 10;
        }
        if (Math.abs(badpos - 100) < 30 && Math.abs(position - badposy) < 50) {
            showMessageDialog(null, "Вы проиграли. Ваш счёт " + counted);
            System.exit(0);
        }
        badskin = new Texture2(badpos, badposy, "player_frame_reverse.png");
        badskin.paint(g);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && position > 10) {
            position -= 10 * myspeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && position < 390) {
            position += 10 * myspeed;
        }
        skin = new Texture2(100, position, "player_frame.png");
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}


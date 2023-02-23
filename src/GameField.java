import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    /*JPanel - контейнер, который может хранить группу компонентов(для создания пользовательского интерфейса)
     * */
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;//рамзер в пикселях, одна ячейка будет занимать 16х16 пикселей
    private final int ALL_DOTS = 400; //всего ячеек в окне, размер в ячейках 20х20 (так как 320/16=20 ячеек)

    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots; //размер змейки в ячейках
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());//добавляем обработчик событий
        setFocusable(true);//чтобы фокус(воздействие клавиш) был на игровом поле
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();

    }

    public void createApple() {
        appleX = new Random().nextInt(19) * DOT_SIZE;
        appleY = new Random().nextInt(19) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();

        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        //метод отрисовывает игровое поле
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i=0; i<dots; i++){
                g.drawImage(dot, x[i], y[i], this);//this - это тот, кто отвечает за картинку
            }
        } else {
            String str = "Game over!";
            //Font f = new Font("Arial", 16, Font.BOLD);
            g.setColor(Color.WHITE);
            //g.setFont(f);
            g.drawString(str, 125, SIZE/4);

        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if (x[0]==appleX && y[0]==appleY){
            dots++;
            createApple();
        }

    }
    public void checkCollisions(){
        for (int i = dots; i>0; i--){
            if (i>4 && x[0] == x[i] && y[0]==y[i]){
                inGame=false;

            }
        }
        if (x[0]>=SIZE || x[0]<0) inGame = false;
        if (y[0]>=SIZE || y[0]<0) inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //будет вызываться каждый раз, когда тикает таймер каждые 250 миллисек

        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();//перерисовываем поле

    }


    class FieldKeyListener extends KeyAdapter {
        //класс для обработки нажатия клавиш


        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();//код клавиши, которая была нажата
            if (key ==KeyEvent.VK_LEFT && !right){
                left=true;
                up = false;
                down = false;
            }
            if (key ==KeyEvent.VK_RIGHT && !left){
                right=true;
                up = false;
                down = false;
            }
            if (key ==KeyEvent.VK_UP && !down){
                right=false;
                left=false;
                up = true;
            }
            if (key ==KeyEvent.VK_DOWN && !up){
                right=false;
                left=false;
                down = true;
            }

        }
    }
}

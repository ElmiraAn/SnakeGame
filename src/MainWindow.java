import javax.swing.*;

public class MainWindow extends JFrame {
    /*
     JFrame - окно, используемое в библиотеке Swing
     окно с рамкой, заголовком, системным меню, кнопок управления (закрыть, свернуть)
     */


    public MainWindow(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //кнопка крестик - нажатие - это завершение работы программы
        setSize(320, 345); //размер окна
        setLocation(400, 400); //позиция окна на экране
        add(new GameField());//
        setVisible(true);//видмое состояние - отображение окна на экране
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
}
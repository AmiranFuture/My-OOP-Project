package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DalelAmirGame extends JFrame {

    private static DalelAmirGame game_window;
    private static Image background;
    private static Image My_Face;
    private static Image restart;
    private static Image game_over;

    private static long frame_time;
    private static float drop_y;
    private static float drop_x;
    private static float drop_v = 230;



    public static void main(String[] args) throws IOException { //add method which add window
        background = ImageIO.read(DalelAmirGame.class.getResourceAsStream("background.jpg")); //give background for window
        My_Face = ImageIO.read(DalelAmirGame.class.getResourceAsStream("My_Face.png"));
        game_over = ImageIO.read(DalelAmirGame.class.getResourceAsStream("game_over.png"));
        restart = ImageIO.read(DalelAmirGame.class.getResourceAsStream("restart.png"));
        game_window = new DalelAmirGame();
        game_window.setSize(1200, 750); //give size of window
        game_window.setResizable(false); // cant change size of window
        frame_time = System.nanoTime();
        game_window.setTitle("70% please"); // add title of the top of window
        game_window.setDefaultCloseOperation(EXIT_ON_CLOSE); // for closing the window
        game_window.setLocation(100, 0); // add place of appearance of the window
        GameField game_field = new GameField();
        game_window.add(game_field);
        game_window.setVisible(true); // to make window visible

    }
    public static void Repaint(Graphics g){ //method for drawing in window
        g.drawImage(background, 0, 0 , null);
        long current_time = System.nanoTime();
        float delta_time = (current_time-frame_time)*0.000000001f;
        frame_time = current_time;
        drop_y = drop_y + drop_v * delta_time;
        g.drawImage(My_Face, (int)drop_x, (int)drop_y, null);
        if(drop_y > game_window.getHeight()){
            g.drawImage(game_over, 400, 150, null);
            g.drawImage(restart, 525, 470, null);
        }


    }
    private static class GameField extends JPanel{ //method with game field
        @Override
        protected void paintComponent(Graphics g) {// adding repainting when click on pic
            super.paintComponent(g);
            Repaint(g);
            repaint();
        }
    }}

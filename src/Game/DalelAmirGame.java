package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class DalelAmirGame extends JFrame {

    private static DalelAmirGame game_window; 
    private static Image background; //added picture on window
    private static Image My_Face; 
    private static Image restart;
    private static Image game_over;

    private static long frame_time;  //added some variables
    private static float drop_y = -150;
    private static float drop_x;
    private static float drop_v = 230;
    private static int score = 0;
    private static float restart_x = 525;
    private static float restart_y = 475;



    public static void main(String[] args) throws IOException { //add method which add window
        background = ImageIO.read(DalelAmirGame.class.getResourceAsStream("background.jpg")); //give background for window
        My_Face = ImageIO.read(DalelAmirGame.class.getResourceAsStream("My_Face.png")); //added icon for click
        game_over = ImageIO.read(DalelAmirGame.class.getResourceAsStream("game_over.png")); //added icon of gameover
        restart = ImageIO.read(DalelAmirGame.class.getResourceAsStream("restart.png")); //aded icon for restart the game
        game_window = new DalelAmirGame(); 
        JLabel record = new JLabel("Record: " +score); //added panel of counter

        record.setSize(228, 150); //give size of counter panel
        record.setPreferredSize(new Dimension(100, 50)); 
        record.setFont(new Font("Record: " +score, Font.PLAIN , 19)); //give font to panel
        record.setOpaque(true);
        record.setBackground(Color.RED); //give color of background

        game_window.setSize(1200, 750); //give size of window
        game_window.setResizable(false); // cant change size of window
        frame_time = System.nanoTime();
        game_window.setTitle("70% please"); // add title of the top of window
        game_window.setDefaultCloseOperation(EXIT_ON_CLOSE); // for closing the window
        game_window.setLocation(100, 0); // add place of appearance of the window
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { //method for clic with mouse 
                int x = e.getX();
                int y = e.getY();
                float drop_x_right = drop_x + My_Face.getWidth(null);
                float drop_t_bottom = drop_y + My_Face.getHeight(null);
                boolean is_drop = x>=drop_x && x<=drop_x_right && y>=drop_y && y<=drop_t_bottom;
                if (is_drop){
                    drop_y = -100;
                    drop_x = (int) (Math.random()*(game_field.getWidth()- My_Face.getHeight(null))); //icon of Face appear in random place 
                    game_window.setTitle("Record: " +score); 
                    record.setText("Record: " +score); //adding counter
                    score++;
                    drop_v = drop_v+30; //added parametr after each click add descent speed

                }
                    else if (is_drop == false){
                        if (score<=0){
                            score--;
                        }
                }
                float restart_x_left = restart_x + restart.getWidth(null);
                float restart_y_bottom = restart_y + restart.getHeight(null);
                boolean is_restart = x>=restart_x && x<=restart_x_left && y>=restart_y && y<=restart_y_bottom;
                if (is_restart){ //when push restart, game starts again
                    drop_y = -100;
                    drop_x = (int) (Math.random()*(game_field.getWidth() - My_Face.getHeight(null)));
                    score = 0; drop_v = 200;
                    game_window.setTitle("Record: " +score);
                    record.setText("Record: " +score);
                }

            }
        });
        game_window.add(game_field);
        game_field.add(record);
        game_window.setVisible(true); // to make window visible


    }
    public static void Repaint(Graphics g){ //method for drawing in window
        g.drawImage(background, 0, 0 , null);
        long current_time = System.nanoTime();
        float delta_time = (current_time-frame_time)*0.000000001f;
        frame_time = current_time;
        drop_y = drop_y + drop_v * delta_time;
        g.drawImage(My_Face, (int)drop_x, (int)drop_y, null);
        if(drop_y > game_window.getHeight()){ //when icon of Face reaches the end of the window on the y axis output icon game_over and restart button
            g.drawImage(game_over, 400, 150, null); 
            g.drawImage(restart,(int) restart_x,(int) restart_y, null); 
        }


    }
    private static class GameField extends JPanel{ //method with game field
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Repaint(g);
            repaint();
        }
    }}

package first;
import javax.swing.*;
import java.awt.*;

public class quiz implements QuizJunit {

   int x = 20 ;
    int y = 20 ;
    public static void main(String [] args){
        quiz gui = new quiz();
        gui.go();
    }
    void go(){

        JFrame frame = new JFrame();
        draw D = new draw();
        frame.getContentPane().add(D);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
        for (int i = 0 ; i < 200 ; i ++){
                 x ++;
                 y ++;
               D.repaint();
            try{
                Thread.sleep(50);
            }catch (Exception EX){
            }
        }

    }
     class draw extends JPanel{
       public void paintComponent(Graphics g){
          g.setColor(Color.black);
          g.fillRect(0,0,this.getWidth(),this.getHeight());
          g.setColor(Color.white);
          g.fillOval(x,y,20,20);

       }
    }

}

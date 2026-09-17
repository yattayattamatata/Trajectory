package LineDrawing;

import java.awt.Color;
import java.awt.Graphics;

public class LiningPanel extends javax.swing.JPanel {

    public LiningPanel() { }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        
        double lines = 15.0;

        for(int i = 0; i < lines; i++)
        {
            int w2 = (int)((i/lines)*w); 
            int h2 = (int)((i/lines)*h); 
            
            g.drawLine(0,  h2, w2, h);
        }
        
    }
    
}

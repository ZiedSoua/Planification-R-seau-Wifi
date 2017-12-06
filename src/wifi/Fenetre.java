/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi;
import java.awt.* ; import java.awt.event.*;import java.awt.geom.*;
import java.text.NumberFormat;
import java.util.* ;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Fenetre extends JFrame {
    JPanel pan = new JPanel ();
    JPanel pan1 = new JPanel();
    JPanel pan2 = new JPanel();
    JButton p0 = new JButton ("placer les PAs");
    JButton p1 = new JButton ("placer les Obstacles");
    JButton p2 = new JButton ("Vérification");
    JLabel la = new JLabel();
    JFormattedTextField tx = new JFormattedTextField(NumberFormat.getIntegerInstance());
    JFormattedTextField ty = new JFormattedTextField(NumberFormat.getIntegerInstance());
    String [] typeobs = {"Bois", "Plastique","Verre","Verre teinté","Eau","Etre vivants","Briques","Platre","Céramique","Papier","Béton","Verre Blindé","Métal"};
    double [] affobs = {0.1 , 0.2 , 0.3 , 0.5 , 0.5 , 0.5 , 0.5 , 0.5 , 0.7 , 0.7 , 0.85 , 0.85 , 0.9};
    JComboBox b = new JComboBox (typeobs);
    dessinCellulesAttinuation frame1 ;

    Fenetre ()
       {
       //pan.setLayout(new BorderLayout());
       pan.setSize(100,100);
       //pan1.setLayout(new BorderLayout());
       pan.setSize(100,100);
       b.setPreferredSize(new Dimension (100,20));
       pan1.add(b);
       pan1.add(p1);
       p0.setEnabled(false);
       pan.add(p0);
       p0.addActionListener(new action());
       p1.addActionListener(new action1());
       pan2.add(p2);
       p2.addActionListener(new action2());
       tx.setPreferredSize(new Dimension(150,30));
       ty.setPreferredSize(new Dimension(150,30));
       la.setText("Résultat");
       pan2.add(tx);
       pan2.add(ty);
       pan2.add(la,BorderLayout.SOUTH);
       this.setLayout(new BorderLayout());
       this.add(pan,BorderLayout.NORTH);
       this.add(pan1,BorderLayout.SOUTH);
       this.add(pan2,BorderLayout.CENTER);
       this.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){ System.exit(0);}});
       this.setSize(500,500);
       this.setVisible(true);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }
 boolean wishes= false;
class action1 implements ActionListener {
      
       int nbclikob =0 ;
       int tabx [] = new int [2];  
       int taby [] = new int [2];
    public void actionPerformed(ActionEvent e) {
           nbclikob ++ ;
        wishes = false ; 
        if (nbclikob==1)
        {
        frame1 = new dessinCellulesAttinuation();
        frame1.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){ System.exit(0);}});
        frame1.setSize(500, 500); 
        frame1.setVisible(true);
        p0.setEnabled(true);
        frame1.addMouseListener(new mouseac1());
        
        }
        else 
        {
          frame1.addMouseListener(new mouseac1());
            
        }
        }
    class mouseac1 implements MouseListener
    {
        int nbpt=0;
        public void mouseClicked(MouseEvent e) {
         }
        public void mousePressed(MouseEvent e) {
         }
        public void mouseReleased(MouseEvent e) {
            if (wishes == false)
            {
            Graphics g = frame1.getGraphics();
            tabx[nbpt]=e.getX();
            taby[nbpt]=e.getY();
            if(nbpt == 1)
            {
                
                Point p1 = new Point (tabx[nbpt-1],taby[nbpt-1]);
                Point p2 = new Point(tabx[nbpt],taby[nbpt]);
                Obstacle o = new Obstacle (p1,p2,Color.BLACK,affobs[b.getSelectedIndex()]);
                System.out.println("Un Obstacle d'affaiblissement "+affobs[b.getSelectedIndex()]+" a été inséré");
                frame1.obs.add(o);
                System.out.println("p1:"+tabx[0]+taby[0]+"p2:"+tabx[1]+taby[1]);
                o.Affichage(g);
                nbpt= 0 ;
            }
            else if (nbpt == 0 ) 
            {
            nbpt++ ;
            System.out.print(tabx[0]+" "+taby[0]);
            }
            }
        }
        public void mouseEntered(MouseEvent e) {
           }
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
class action implements ActionListener {
        int nbPA =0 ;
        public void actionPerformed(ActionEvent e) {
        if (e.getSource()== p0)
        {
        wishes = true ;
        frame1.addMouseListener(new mouseac());
        }
     }
    
    class mouseac implements MouseListener
    {
     
        public void mouseClicked(MouseEvent e) {
         }
        public void mousePressed(MouseEvent e) {
         }
        public void mouseReleased(MouseEvent e) {
           if (wishes == true )
           {
           Graphics g = frame1.getGraphics();
           Graphics2D g2;
           g2 = (Graphics2D) g;//le même graphic c mais on l'ajoute des couleurs dégradés
           int x,y;
            //si le click est venu du boutton gauche
           x = e.getX();//position x du click
           y = e.getY();
           frame1.Affichage_AccessPoints(g2, x, y, 50 );
           frame1.DessinerCercle(g2, x-1, y-1, 3);
           frame1.APs.add(new AccessPoint(x,y,nbPA++));
        }
        }
        public void mouseEntered(MouseEvent e) {
           }
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
}

class action2 implements ActionListener {
     int k = 0 ;
    public void actionPerformed(ActionEvent e) {
        int xr = Integer.parseInt(tx.getText());
        int yr = Integer.parseInt(ty.getText());
        String s = new String("");
        String s1 = new String("");
        boolean wi = false ;
            while (k <frame1.APs.size())
        {
            if (frame1.distance(frame1.APs.get(k).x,frame1.APs.get(k).y, xr, yr) <= 50)
            {
                wi = true ;
                s ="PA"+k;
            }
            s1=s1+" "+s ;
            k++ ;
        }
        if (wi== true)
        la.setText("la point: x: "+xr+" y:"+yr+" appartient à:"+s1);
        else
            la.setText("la point: x:"+xr+" y:"+yr+" n'appartient à aucun PA");
    }
    
} 



    
}

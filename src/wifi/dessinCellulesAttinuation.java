/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dell
 */
public class dessinCellulesAttinuation extends JFrame  {
    ArrayList<AccessPoint> APs = new ArrayList<AccessPoint>();
    ArrayList<Obstacle> obs = new ArrayList<Obstacle>();
    JPanel cont = new JPanel (); 
    dessinCellulesAttinuation (){
        this.setLayout(new BorderLayout());
        cont.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
        cont.setBackground(Color.WHITE);
        this.add(cont,BorderLayout.CENTER);
        this.setTitle("");
        this.setVisible(true);
    }
    double Facteur_Attinuation(double xc, double yc, double r, double x, double y) {
        double f = 1.0 - distance(xc, yc, x, y) / ((double) r);
        if (f < 0) {
            return (0);
        }
        return (f);
    }
    
    public void Affichage_AccessPoints(Graphics2D ga, int xclick, int yclick, int r) {
        if (obs.isEmpty()) {
            System.out.println("Nombre d'obstacle total est null");
            CelluleDegraderEmpty(ga, xclick, yclick, r);
        } else {
            System.out.println("Nombre d'obstacle total est strictement positif");
            CelluleDegraderNotEmpty(ga, xclick, yclick, r);
        }
    }
    void CelluleDegraderEmpty(Graphics2D ga, int xc, int yc, int r) {
        //on fait un parcours sur le carré de rayon r
        for (int i=0;i<2*r;i++) {
            for (int j=0;j<2*r;j++) {
                int x = (xc - r) + i;
                int y = (yc - r) + j;
                double f = Facteur_Attinuation(xc, yc, r, x, y);//on calcule le facteur d'atténuation par rapport à la distance
                int A = (int) (255 - f * 255);//dégradation de la couleur en fonction de l'atténuation
                if (distance(xc, yc, x, y) <=r && (f > 0)) {
                        couverture(ga, A, 255-A, 0, x, y);
                }
            }
        }
    }
    
    void CelluleDegraderNotEmpty(Graphics2D ga, int xc, int yc, int r) {
        Point p = new Point(xc, yc);
        Obstacle ob;
        for (int x = xc - r; x <= xc + r; x++) {
            for (int y = yc - r; y <= yc + r; y++) {
                Point p1 = new Point(x, y);
                double af = 1;
                for (int obi = 0; obi < obs.size(); obi++) {
                    ob = obs.get(obi);//pour chaque obstacle
                    if (ob.Si_intersection_segment(p, p1)) {
                        af = (1.0 - ob.affaiblissement);
                    }
                    if (distance2(p, p1) <= (r * r * af * af)) {//si la distance entre le rayon et le point est inférieur à la longueur du rayon affaiblit par l'affaiblissement
                        double f = Facteur_Attinuation(xc, yc, r * af, x, y);
                        int A = (int) (255.0 - f * 255.0);
                        if (distance(xc, yc, x, y) <=r && (f > 0)) {
                            couverture(ga, A, 255-A, 0, x, y);
                        }
                    }
                }
            }
        }
    }


     public void DessinerCercle(Graphics2D ga, int x, int y, int r) {
        Shape circle = new Ellipse2D.Float(x, y, r, r);
        ga.draw(circle);
        Color C = new Color(255,255, 255, 128);// le dernier paramètre est la transparence des couleurs, les autres c'est le RGB (Red, Green, Blue)
        ga.setPaint(C);
        ga.setColor(C);
        ga.fill(circle);
    }
    public void couverture(Graphics2D g2, int rouge, int vert, int bleu, int x, int y) {
            Color C = new Color(rouge, vert, bleu,128);
            Shape square = new Rectangle2D.Double(x, y, 1, 1);
            g2.setPaint(C);
            g2.fill(square);
    }

    double distance(double x1, double y1, double x, double y) {
        return Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y));
    }
    
    double distance2(Point p1, Point p2) {
        return (p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y);
    }

    
    
    }
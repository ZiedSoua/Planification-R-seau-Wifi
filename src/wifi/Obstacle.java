/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author dell
 */
public class Obstacle {
    Point p1,p2;
    Color c; 
    double affaiblissement;
    
Obstacle (Point p1, Point p2, Color c, double affaiblissement) {
	this.p1=p1; this.p2=p2; this.c= c;this.affaiblissement=affaiblissement;
    }
    
double produit_scalaire(Point p1, Point p2, Point p3, Point p4 )
	{ double p=(p2.x-p1.x)*(p4.x-p3.x)+(p2.y-p1.y)*(p4.y-p3.y); 
	
		return p;
	}
   
boolean Si_intersection_segment(Point p3, Point p4){
		double m1,b1,m2,b2; // droites : y=m1*x+b1   et y=m2*x+b2
		m1=(p2.y-p1.y)/(p2.x-p1.x);
		m2=(p4.y-p3.y)/(p4.x-p3.x);
		b1=p1.y-m1*p1.x;
		b2=p3.y-m2*p3.x;
		
		if (Math.abs(m1)==Math.abs(m2)){return false;}
		// d�terminer l'intersection des deux droites
		double yi,xi;
		xi=(b2-b1)/(m1-m2);
		yi= m1*xi+b1;
		
		Point pi= new Point(xi,yi);
			
		// si le point pi est dans le segment [p1, P2]
		if (this.produit_scalaire(pi, p1,   pi, p2)<0 && this.produit_scalaire(pi, p3,   pi, p4)<0) {return true;}
		else {return false;}
			  
	}    
void Affichage(Graphics g){
		Graphics2D  ga = (Graphics2D)g;
		ga.setStroke(new BasicStroke(8));
		ga.setColor(c);
		Shape segment = new Line2D.Double(p1.x, p1.y,p2.x,p2.y);
		ga.draw(segment);
	}
}

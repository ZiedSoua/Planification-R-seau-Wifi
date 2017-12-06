/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
/**
 *
 * @author dell
 */
public class AccessPoint {
    int x,y ;
    int Rc = 50 ;
    int puissace ;
    int num ;
    
public AccessPoint (int x , int y , int num)
{
    this.x=x;
    this.y=y;
    this.num=num;
    
}
public void aff ()
{
   System.out.println("PA:x= "+x+" y= "+y);
}


}


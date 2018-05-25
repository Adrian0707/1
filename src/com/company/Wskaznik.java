package com.company;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Wskaznik extends Obiekty {
    public double x,y;
    public int stan;
    public double postawa;
    public boolean kier;
    private Group root;

    Circle element = new Circle(6);

    public Wskaznik (int x, int y, Group root, boolean kier){
        this.x=x;
        this.y=y;
        this.root=root;
        //element=new Circle();
        element.setFill(Color.color(0.2,0.2,0.8, 0.4));
        element.setRadius(6);
        this.kier=kier;
        this.root.getChildren().add(element);
        Obiekty.lista.add(this);

    }
    public void wyswietl(GraphicsContext a,int x, int y) {

        if (kier) {
            this.x=x+50*cos(this.postawa/14);
            this.y=y-10-50*sin(this.postawa/14);}
        else{
            this.x=x+50*cos(this.postawa/14+PI);
            this.y=y-10-50*sin(this.postawa/14+ PI);

        }

        element.setCenterX(this.x);
        element.setCenterY(this.y);

       /* for(int k=0;k<20;k++)
        {
            Spark b=new Spark(x,y,root,obiekt, Color.BLUE);
        }*/


    }
}

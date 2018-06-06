package com.company;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static java.lang.Math.*;

public class Wskaznik extends Obiekty {
    public double x, y;
    public int stan;
    public double postawa;
    public boolean kier;
    private Group root;
    double time;
   double live;
    int k=1;
    public double xt,yt;
    Circle element = new Circle(6);
    Circle pole = new Circle(60);


    public Wskaznik(int x, int y, Group root, boolean kier) {
        this.x = x;
        this.y = y;
        this.root = root;
        //element=new Circle();
        element.setFill(Color.color(0.2, 0.2, 0.8, 0.4));
        pole.setOpacity(0);
        pole.setFill(Color.BLUEVIOLET);
        pole.setBlendMode(BlendMode.DIFFERENCE);
        pole.setStrokeWidth(6);
        pole.setStroke(Color.RED);
        //pole.setStrokeType(StrokeType.CENTERED);
       // pole.setStrokeDashOffset(400);
        this.kier = kier;
        this.root.getChildren().add(element);
        this.root.getChildren().add(pole);
        Obiekty.lista.add(this);

    }
    public void wyswietl(GraphicsContext a, int x, int y) {

        if (kier) {
            this.x = x + 50 * cos(this.postawa / 14);
            this.y = y - 10 - 50 * sin(this.postawa / 14);
        } else {
            this.x = x + 50 * cos(this.postawa / 14 + PI);
            this.y = y - 10 - 50 * sin(this.postawa / 14 + PI);

        }

        element.setCenterX(this.x);
        element.setCenterY(this.y);

        if (stan == 1) {
            pole.setOpacity(0.4);
            time = System.nanoTime();
            if(k==1)
                live=100;
            k=0;
            if (live <= 0) {
                pole.setOpacity(0);
                live = 3;
                stan = 0;
                k = 1;

                for (double alf = 0; alf < 360; alf += 360 / 360) {
                    //for(int i=0;i<3;i++)
                    Spark sp =new Spark(60 * sin(alf) + xt, 60 * cos(alf) + yt,
                            Color.color(random(), 0, 0),random()*3+3);
                    sp.live/=3;

                }
            }
                pole.setFill(Color.color(1-live/100,live/900 ,live/100 ));
            if (kier) {
                xt=x-25;
                yt=y;
            }
            else {
                xt=x+25;
                yt=y;



            }
            pole.setCenterY(yt);
            pole.setCenterX(xt);
        }

    }
    public Color kolorT (){
       return (Color)pole.getFill();
        }
}


       /* for(int k=0;k<20;k++)
        {
            Spark b=new Spark(x,y,root,obiekt, Color.BLUE);
        }*/





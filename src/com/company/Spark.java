package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

import static com.company.Klawisze.obiekt;
import static com.company.Klawisze.root;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Spark extends Obiekty implements Animowane{
    double x, y;
    double utrata = 0.8;
    int kier;
    Random rand = new Random();
    public Circle spar;
    double t = 0;
    double g = 0.04;
    double v;
    double live;
    Color color;

    public Spark(double x, double y, Color a, double r){
        this.x = x;
        this.y = y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        live = 100;
        spar = new Circle(r);
        spar.setFill(a);
        root.getChildren().add(spar);
        obiekt.lista.add(this);

    }

    public Spark(double x, double y, Color a, double r, double utrata, double fizyka){
        this.x = x;
        this.y = y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        live = 100;
        spar = new Circle(r);
        spar.setFill(a);
        root.getChildren().add(spar);
        obiekt.lista.add(this);
        this.utrata = utrata;
        this.g = fizyka;

    }

    public Spark(double x, double y, Color a, double r, double utrata){
        this.x = x;
        this.y = y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        live = 100;
        spar = new Circle(r);
        spar.setFill(a);
        root.getChildren().add(spar);
        obiekt.lista.add(this);
        this.utrata = utrata;

    }

    private double odl(double x1, double x2, double y1, double y2){
        return sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public void leć(){
        x = x + t * 0.5 * v * cos(kier);
        y = y + t * 1 * v * sin(kier) + (2 * g * t * t) / 2;
        if (y >= 481 && y <= 482){

            live = 60;
        }
        if (y >= 483){
            y = 483;
            g += 0.001;
            t /= 5;
            v -= v / 5;

        }
        if (live >= utrata){
            live -= utrata;
        }
        else{
            root.getChildren().remove(this.spar);
            obiekt.lista.remove(this);
        }

        t += 0.3;

    }

    @Override
    public void wyswietl(){
        spar.setCenterX(x);
        spar.setCenterY(y);
        spar.setFill(Color.color(color.getRed() - (1 - live / 100) * color.getRed() * 0.8,
                                 color.getGreen() - (1 - live / 100) * 0.6 * color.getGreen(),
                                 color.getBlue(),
                                 live / 100));
        //spar.setFill(Color.color(1-(1-live/100)*0.8,0.7-(1-live/100)*0.6,0,live/100));
        leć();

    }
}

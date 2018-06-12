package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.company.Main.obiekt;
import static java.lang.Math.*;
import static com.company.Main.root;

/**
 * klasa odpowiedzialna za wyswietlanie celownika postaci
 */
public class Wskaznik extends Obiekty{
    private double x;
    private double y;
    private int stan;
    private double postawa;
    private boolean kier;
    private double time;
    private double live;
    private int k = 1;
    private double xt;
    private double yt;
    private Circle element = new Circle(6);
    private Circle pole = new Circle(60);

    public Wskaznik(int x, int y, boolean kier){
        this.setX(x);
        this.setY(y);
        getElement().setFill(Color.color(0.2, 0.2, 0.8, 0.4));
        getPole().setOpacity(0);
        getPole().setFill(Color.BLUEVIOLET);
        getPole().setBlendMode(BlendMode.DIFFERENCE);
        getPole().setStrokeWidth(6);
        getPole().setStroke(Color.RED);
        this.kier = kier;
        root.getChildren().add(getElement());
        root.getChildren().add(getPole());
        obiekt.lista.add(this);

    }

    /**
     * funkcja odpowiedzialna za wyswietlanie wskaznika na ekranie
     *
     * @param a graphic context na ktorym bedzie wyswietlany wskaznik
     * @param x polozenie postaci w osi x
     * @param y polozenie postaci w osi y
     */
    public void wyswietl(GraphicsContext a, int x, int y){

        if (kier){
            this.setX(x + 50 * cos(this.getPostawa() / 14));
            this.setY(y - 10 - 50 * sin(this.getPostawa() / 14));
        }
        else{
            this.setX(x + 50 * cos(this.getPostawa() / 14 + PI));
            this.setY(y - 10 - 50 * sin(this.getPostawa() / 14 + PI));

        }

        new Czasteczka(this.getX() + random(),
                       this.getY() + random(),
                       Color.color((random() + 2 * ((Color)(getElement().getFill())).getRed()) * 0.3,
                                   (random() + 2 * ((Color)(getElement().getFill())).getGreen()) * 0.3,
                                   (random() + 2 * ((Color)(getElement().getFill())).getBlue()) * 0.3,
                                   (random() + 2 * ((Color)(getElement().getFill())).getOpacity()) * 0.3),
                       random() * 3 + 2,
                       4,
                       -0.1);

        // o.live -= 10;
        getElement().setCenterX(this.getX());
        getElement().setCenterY(this.getY());

        if (getStan() == 1){
            getPole().setOpacity(0.4);
            for (double alf = 0; alf < 360; alf += 360 / 60){
                if (random() > 0.999){
                    for (int i = 0; i < 60; i += 2){
                        Czasteczka l = new Czasteczka(i * sin(alf) + getXt(),
                                                      i * cos(alf) + getYt(),
                                                      Color.color(0.5 * (getColor().getRed() + random()),
                                                                  0.5 * (random() + getColor().getGreen()),
                                                                  0.5 * (getColor().getBlue() + random())),
                                                      2,
                                                      10,
                                                      0);
                        l.getSpar().setOpacity(0.4);

                    }
                }
                if (random() > 0.97)
                    new Czasteczka(60 * sin(alf) + getXt(),
                                   60 * cos(alf) + getYt(),
                                   Color.color(0.5 * (getColor().getRed() + random()),
                                               0.5 * (random() + getColor().getGreen()),
                                               0.5 * (getColor().getBlue())),
                                   random() * 3 + 1,
                                   7,
                                   0);

            }
            time = System.nanoTime();
            if (k == 1)
                setLive(100);
            k = 0;
            if (getLive() <= 0){
                getPole().setOpacity(0);
                setLive(3);
                setStan(0);
                k = 1;

                for (double alf = 0; alf < 360; alf += 360 / 360){
                    for (int i = 0; i < 3; i++){
                        Czasteczka sp = new Czasteczka(60 * sin(alf) + getXt(),
                                                       60 * cos(alf) + getYt(),
                                                       Color.color(random(), 0, 0),
                                                       random() * 3 + 3);
                        sp.setLive(sp.getLive() / 3);
                    }

                }
            }

            getPole().setFill(Color.color(1 - getLive() / 100, getLive() / 900, getLive() / 100));
            if (random() > 0.9)
                getPole().setStroke(Color.color(1 % (1 - getLive() / 100 + random()),
                                                1 % (random() + getLive() / 900),
                                                1 % (getLive() / 100 + random())));
            if (kier){
                setXt(x - 25);
                setYt(y);
            }
            else{
                setXt(x + 25);
                setYt(y);

            }
            getPole().setCenterY(getYt());
            getPole().setCenterX(getXt());
        }

    }

    public Color getColor(){
        return (Color)getPole().getFill();
    }

    public double getX(){
        return x;
    }

    public Wskaznik setX(double x){
        this.x = x;
        return this;
    }

    public double getY(){
        return y;
    }

    public Wskaznik setY(double y){
        this.y = y;
        return this;
    }

    public int getStan(){
        return stan;
    }

    public Wskaznik setStan(int stan){
        this.stan = stan;
        return this;
    }

    public double getPostawa(){
        return postawa;
    }

    public Wskaznik setPostawa(double postawa){
        this.postawa = postawa;
        return this;
    }

    public double getLive(){
        return live;
    }

    public Wskaznik setLive(double live){
        this.live = live;
        return this;
    }

    public double getXt(){
        return xt;
    }

    public Wskaznik setXt(double xt){
        this.xt = xt;
        return this;
    }

    public double getYt(){
        return yt;
    }

    public Wskaznik setYt(double yt){
        this.yt = yt;
        return this;
    }

    public Circle getElement(){
        return element;
    }

    public Wskaznik setElement(Circle element){
        this.element = element;
        return this;
    }

    public Circle getPole(){
        return pole;
    }

    public Wskaznik setPole(Circle pole){
        this.pole = pole;
        return this;
    }
}






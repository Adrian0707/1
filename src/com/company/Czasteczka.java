package com.company;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

import static com.company.Main.obiekt;
import static com.company.Main.root;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * klasa odpowiedzialna za wyswietlanie czasteczek na ekranie
 */
public class Czasteczka extends Obiekty implements Animowane{
    protected int x, y;
    private double utrata = 0.8;
    protected int kier;
    private Random rand = new Random();
    private Circle spar;
    protected double t = 0;
    private double g = 0.04;
    protected double v;
    private double live;
    Color color;

    /**
     * konstruktor obiektu klasy Czasteczka
     *
     * @param x polozenie w osi x
     * @param y polozenie w odi y
     * @param a kolor obiektu Krew
     * @param r promien obiektu Krew
     */
    public Czasteczka(double x, double y, Color a, double r){
        this.x = (int)x;
        this.y = (int)y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        setLive(100);
        setSpar(new Circle(r));
        getSpar().setFill(a);
        root.getChildren().add(getSpar());
        obiekt.lista.add(this);

    }

    /**
     * konstruktor obiektu klasy Czasteczka
     *
     * @param x      polozenie w osi x
     * @param y      polozenie w odi y
     * @param a      kolor obiektu Krew
     * @param r      promien obiektu Krew
     * @param utrata zmianna decydujaca o tym jak szybko zanika czasteczka
     * @param fizyka zmienna dopowiedzialna za sile grawitacji
     */
    public Czasteczka(double x, double y, Color a, double r, double utrata, double fizyka){
        this.x = (int)x;
        this.y = (int)y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        setLive(100);
        setSpar(new Circle(r));
        getSpar().setFill(a);

        root.getChildren().add(getSpar());
        obiekt.lista.add(this);
        this.utrata = utrata;
        this.setG(fizyka);

    }

    /**
     * konstruktor obiektu klasy Czasteczka
     *
     * @param x      polozenie w osi x
     * @param y      polozenie w osi y
     * @param a      kolor obiektu Krew
     * @param r      promien obiektu Krew
     * @param utrata zmianna decydujaca o tym jak szybko zanika czasteczka
     */
    public Czasteczka(double x, double y, Color a, double r, double utrata){
        this.x = (int)x;
        this.y = (int)y;
        kier = rand.nextInt();
        this.color = a;
        v = 0.75 + rand.nextInt(1000) / 6000;
        setLive(100);
        setSpar(new Circle(r));
        getSpar().setFill(a);
        root.getChildren().add(getSpar());
        obiekt.lista.add(this);
        this.utrata = utrata;

    }

    /**
     * funkcja odpowiedzialna za tor ruchu obiektu
     */
    protected void lec(){
        x = (int)(x + t * 0.5 * v * cos(kier));
        y = (int)(y + t * 1 * v * sin(kier) + (2 * getG() * t * t) / 2);
        if (y >= 481 && y <= 482){

            setLive(60);
        }
        if (y >= 483){
            y = 483;
            setG(getG() + 0.001);
            t /= 5;
            v -= v / 5;

        }
        if (getLive() >= utrata){
            setLive(getLive() - utrata);
        }
        else{
            root.getChildren().remove(this.getSpar());
            obiekt.lista.remove(this);
        }

        t += 0.3;

    }

    /**
     * funkcja dopowiedzialna za wyswietlanie czasteczek
     */
    @Override
    public void wyswietl(){
        getSpar().setCenterX(x);
        getSpar().setCenterY(y);
        getSpar().setFill(Color.color(color.getRed() - (1 - getLive() / 100) * color.getRed() * 0.8,
                                      color.getGreen() - (1 - getLive() / 100) * 0.6 * color.getGreen(),
                                      color.getBlue(),
                                      getLive() / 100));
        //spar.setFill(Color.color(1-(1-live/100)*0.8,0.7-(1-live/100)*0.6,0,live/100));
        lec();

    }

    public Circle getSpar(){
        return spar;
    }

    public Czasteczka setSpar(Circle spar){
        this.spar = spar;
        return this;
    }

    public double getG(){
        return g;
    }

    public Czasteczka setG(double g){
        this.g = g;
        return this;
    }

    public double getLive(){
        return live;
    }

    public Czasteczka setLive(double live){
        this.live = live;
        return this;
    }
}

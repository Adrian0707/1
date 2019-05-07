package com.company;

import javafx.scene.effect.DisplacementMap;
import javafx.scene.paint.Color;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * klasa bedaca odpowiedzialna za wyswietlanie krwi
 */
public class Krew extends Czasteczka{
    /**
     * konstruktor obiektu klasy Krew
     * @param x polozenie w osi x
     * @param y polozenie w osi y
     * @param a kolor obiektu Krew
     * @param r promien obiektu Krew
     */
    public Krew(double x, double y, Color a, double r) {
        super(x, y, a, r);
        setG(0.1);
       // this.getSpar().setEffect(new javafx.scene.effect.GaussianBlur());
        //this.getSpar().setSmooth(true);
    }

    /**
     * funkcja odpowiedzialna za tor ruchu obiektu
     */
    @Override
    protected void lec() {
        x = (int)(x + t * 0.5 * v * cos(kier));
        y = (int)(y + t * 1 * v * sin(kier) + (2 * getG() * t * t) / 2);
        if (y >= 483) {
            y = 483;
            setG(getG() + 0.001);
            t /= 5;
            v -= v / 5;
        }
        t += 0.3;
    }
}

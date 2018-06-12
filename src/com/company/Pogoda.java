package com.company;

import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.company.Main.*;
import static java.lang.Math.random;

/**
 * klasa odpowiedzialna za wyswietlanie efektow pogodowych
 */
public class Pogoda{
    private Rectangle swiatlo;

    /**
     * konstruktor klasy Pogoda
     */
    public Pogoda(){
        swiatlo = new Rectangle(0, 0, WIDTH, HEIGHT);
        swiatlo.setFill(Color.gray(0.1));
        swiatlo.setBlendMode(BlendMode.HARD_LIGHT);
        swiatlo.setOpacity(0.5);
      //  swiatlo.setEffect(new Glow(0.8));
        root.getChildren().add(swiatlo);
    }

    /**
     * funkcja odpowiedzialna za wyswietlanie efektow deszczu
     */
    public void wyswietl(){
        for (int i = 0; i < 6; i++){
            new Czasteczka(random() * WIDTH, -3, Color.color(0.1, 0.1, 0.5, 1), random() * 2 + 1, 2, 0.5);

        }
    }
}

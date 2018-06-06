package com.company;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Krew extends Spark {

    public Krew(double x, double y, Group root, Obiekty obiekty, Color a, double r) {
        super(x, y, a, r);
        g = 0.1;
    }

    @Override
    public void leć() {
        x = x + t * 0.5 * v * cos(kier);
        y = y + t * 1 * v * sin(kier) + (2 * g * t * t) / 2;
        if (y >= 483) {
            y = 483;
            g += 0.001;
            t /= 5;
            v -= v / 5;
        }
        t += 0.3;
    }
}

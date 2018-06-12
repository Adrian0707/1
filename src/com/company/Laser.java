package com.company;

import javafx.scene.paint.Color;

import java.util.Random;

import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * klasa bedaca odpowiedzialna za wyswietlanie obiektow pocisk typu drugiego
 */
public class Laser extends Pocisk{
    private Random rand = new Random();

    /**
     * konstruktor klasy Laser
     *
     * @param a     promien kuli
     * @param wlas  obiekt klasy Postac bedacy odpowiedzialny za wyslanie pocisku
     * @param gracz obiekt klasy Postac bedacy celem pocisku
     * @param k     kolor pocisku
     */
    public Laser(int a, Postac wlas, Postac gracz, Color k){
        super(a, wlas, gracz, k);
    }

    /**
     * funkcja bedaca odpowiedzialna za tor ruchu obiektu
     */
    @Override
    protected void lec(){

        v = sqrt((sqrt((nadawca.getX() - odbiorca.getX()) * (nadawca.getX() - odbiorca.getX())) * g) / (sin(2 * poz))) * 0.8;
        if (kier){

            x += (int)(3 * getR() / 6);
            y -= (int)(sin(czas * 15 - 4 * poz - 10) * 5);

            czas += 0.01;
        }

        else{

            x -= (int)(3 * getR() / 6);
            y -= (int)(sin(czas * 15 - 4 * poz - 10) * 5);
            czas += 0.01;
        }
        if (y > 480){
            y = 478;
            czas += rand.nextInt() * 10;

        }

    }
}

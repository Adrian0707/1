package com.company;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javafx.scene.shape.*;

import static com.company.Klawisze.obiekt;
import static com.company.Klawisze.root;
import static com.company.Klawisze.gc;


import static java.lang.Math.cos;
import static java.lang.Math.random;
import static java.lang.Math.sin;


public class Postać extends Obiekty implements Animowane {
    private double zdrowie = 100;
    private double mana = 100;
    private Rectangle pasŻ;
    private Rectangle pasM;
    private int max;
    private int min;
    private int x = 0;
    private int y = 0;
    private boolean smierc = false;
    private boolean kier;
    private Image[] obraz = new Image[2];
    private int nr = 0;
    public Wskaznik wsk;

    public Postać(int i) {

        if (i == 1) {
            max = 400;
            min = 0;
            setX(200);
            setY(380);
            setKier(true);
            Wskaznik ws = new Wskaznik(getX() + 100,
                    getY(),
                    root,
                    isKier());
            wsk = ws;
            pasŻ = new Rectangle(20,
                    20,
                    getZdrowie() * 2,
                    40);
       /*   pasŻ.setStrokeLineCap(StrokeLineCap.ROUND);
          pasŻ.setStroke(pasŻ.getFill());
          pasŻ.setStrokeType(StrokeType.OUTSIDE);
          pasŻ.setStrokeLineJoin(StrokeLineJoin.ROUND);*/
            pasM = new Rectangle(220,
                    20,
                    getMana() * 2,
                    40);
        }
        else {
            max = 900;
            min = 500;
            setX(700);
            setY(380);
            setKier(false);
            Wskaznik ws = new Wskaznik(getX(),
                    getY(),
                    root,
                    isKier());
            wsk = ws;
            pasŻ = new Rectangle(580,
                    20,
                    getZdrowie() * 2,
                    40);
            pasM = new Rectangle(780,
                    20,
                    getMana() * 2,
                    40);
            // this.obiekt=obiekt;

        }

        pasŻ.setFill(Color.DARKRED);
        pasŻ.setOpacity(0.6);
        pasM.setFill(Color.DARKCYAN);
        pasM.setOpacity(0.6);
        root.getChildren()
                .add(pasM);
        root.getChildren()
                .add(pasŻ);
        obiekt.lista.add(this);


    }


    public void obrazenie(double dmg, double x, double y) {
        this.setZdrowie(this.getZdrowie() - dmg / 10);
        for (int i = 0; i < dmg; i++) {
            new Krew(x,
                    y,
                    root,
                    obiekt,
                    Color.DARKRED,
                    random() * 4);
        }
        if (getZdrowie() <= 0)
            smierc();

    }

    public void obrazTarcza(double dmg) {
        wsk.live -= dmg;

    }

    private void odzyskajZdrowie(double zdrow) {
        if (getZdrowie() + zdrow <= 100) {
            this.setZdrowie(this.getZdrowie() + zdrow);
            this.setMana(this.getMana() - zdrow);
            for (int i = 0; i < zdrow; i++)
                new Spark(pasŻ.getX() + (random() * pasŻ.getWidth()) % ((int) pasŻ.getWidth()),
                        pasŻ.getY() + (random() * pasŻ.getHeight()) % ((int) pasŻ.getHeight()),
                        (Color) pasŻ.getFill(),
                        random() * 3 + 3,
                        5,
                        0.03);
        }
    }

    public void odzyskajMane(double mana) {
        if (this.getMana() + getZdrowie() + mana <= 200) {
            this.setMana(this.getMana() + mana);
            for (int i = 0; i < mana; i++)
                new Spark(pasM.getX() + (random() * pasM.getWidth()) % ((int) pasM.getWidth()),
                        pasM.getY() + (random() * pasM.getHeight()) % ((int) pasM.getHeight()),
                        (Color) pasM.getFill(),
                        random() * 3 + 3,
                        5,
                        0.03);
        }
        else
            odzyskajZdrowie(0.03);
    }

    public void setObraz(Image obraz, int i) {
        this.getObraz()[i] = obraz;
    }

    public void wyswietl() {
        if (isSmierc()) {
            this.wsk.element.setFill(Color.color(0,
                    0,
                    0,
                    0));
        }
        else {
            if (isKier()) {

                gc.drawImage(getObraz()[nr],
                        this.getX(),
                        this.getY(),
                        150,
                        100);
                wsk.wyswietl(gc,
                        getX() + 70,
                        getY() + 50);

            }
            else {
                gc.drawImage(getObraz()[nr],
                        this.getX() + 100,
                        this.getY(),
                        -150,
                        100);
                wsk.wyswietl(gc,
                        getX() + 30,
                        getY() + 50);
            }
        }
        pasŻ.setWidth(getZdrowie() * 2);
        pasM.setX(pasŻ.getX() + getZdrowie() * 2);
        pasM.setWidth(getMana() * 2);
        if (getMana() + getZdrowie() == 200) {

        }
        else
            pasM.setBlendMode(pasŻ.getBlendMode());


    }

    public void pozycjaUP() {
        if (wsk.postawa < 20)
            wsk.postawa += 0.6;
    }

    public void pozycjaDOWN() {
        if (wsk.postawa > -20)
            wsk.postawa -= 0.6;
    }

    public void tarczaUP() {
        if (getMana() - 10 >= 0) {
            this.wsk.stan = 1;
            setMana(getMana() - 10);
        }
    }

    public void right() {
        if (getX() < max)
            setX(getX() + 5);
        nr = 0;
    }

    public void left() {
        if (getX() > min)
            setX(getX() - 5);
        nr = 0;
    }

    public void nic() {
        nr = 1;
    }

    public void strzel(Color a, Postać gracz) {
        if (getMana() - 10 >= 0) {
            this.nic();
            Pocisk poc = new Pocisk(5,
                    this,
                    gracz,
                    a);
            obiekt.lista.add(poc);
            setMana(getMana() - 10);
        }
    }

    public void strzel2(Color a, Postać gracz) {
        if (getMana() - 0.5 >= 0) {
            this.nic();
            Laser poc = new Laser(5,
                    this,
                    gracz,
                    a);
            obiekt.lista.add(poc);
            setMana(getMana() - 0.5);
        }
    }

    public void smierc() {
        setSmierc(true);
        if (isKier()) {
            wsk.xt = getX() + 50;
            wsk.yt = getY() + 50;
        }
        else {
            wsk.xt = getX();
            wsk.yt = getY() + 50;


        }
        for (int i = 0; i < 40; i++) {
            double k;
            for (double alf = 0; alf < 360; alf += 360 / 10) {
                if (i < 30) {
                    k = random() * 0.8 + 0.2;
                    new Krew(i * sin(alf) + wsk.xt,
                            i * cos(alf) * 1.5 + wsk.yt,
                            root,
                            obiekt,
                            Color.color(k,
                                    k,
                                    k,
                                    1),
                            random() / 4 * i);
                }

                new Spark(i * sin(alf) + wsk.xt,
                        i * cos(alf * 1.5) + wsk.yt,
                        Color.DARKRED,
                        random() * 3 + 1,
                        0,
                        0.05);


            }
            //  new Krew(x+i+random()*i,y+i+random()*i,root,obiekt,Color.WHITESMOKE,);
            //   new Krew(x-i-random()*i,y-i-random()*i,root,obiekt,Color.WHITESMOKE,random()*i/4);
        }
    }

    public double getZdrowie() {
        return zdrowie;
    }

    public Postać setZdrowie(double zdrowie) {
        this.zdrowie = zdrowie;
        return this;
    }

    public double getMana() {
        return mana;
    }

    public Postać setMana(double mana) {
        this.mana = mana;
        return this;
    }

    public int getX() {
        return x;
    }

    public Postać setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Postać setY(int y) {
        this.y = y;
        return this;
    }

    public boolean isSmierc() {
        return smierc;
    }

    public Postać setSmierc(boolean smierc) {
        this.smierc = smierc;
        return this;
    }

    public boolean isKier() {
        return kier;
    }

    public Postać setKier(boolean kier) {
        this.kier = kier;
        return this;
    }

    public Image[] getObraz() {
        return obraz;
    }
}


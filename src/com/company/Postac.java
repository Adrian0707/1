package com.company;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javafx.scene.shape.*;

import static com.company.Main.*;

import static java.lang.Math.cos;
import static java.lang.Math.random;
import static java.lang.Math.sin;

/**
 * klasa odpowiedzialna za wyswietlanie postaci oraz zwiazanych z nia elementow
 */

public class Postac extends Obiekty implements Animowane{
    private double zdrowie = 100;
    private double mana = 100;
    private Rectangle pasz;
    private Rectangle pasM;
    private int max;
    private int min;
    private int x = 0;
    private int y = 0;
    private boolean smierc = false;
    private boolean kier;
    private Image[] obraz = new Image[2];
    private int nr = 0;
    private Wskaznik wsk;

    /**
     * konstruktor klasy Postac
     *
     * @param i nr. gracza
     */

    public Postac(int i){

        if (i == 1){
            max = 400;
            min = 0;
            setX(200);
            setY(380);
            setKier(true);
            Wskaznik ws = new Wskaznik(getX() + 100, getY(), getKier());
            setWsk(ws);
            pasz = new Rectangle(20, 20, getZdrowie() * 2, 40);
       /*   pasz.setStrokeLineCap(StrokeLineCap.ROUND);
          pasz.setStroke(pasz.getFill());
          pasz.setStrokeType(StrokeType.OUTSIDE);
          pasz.setStrokeLineJoin(StrokeLineJoin.ROUND);*/
            pasM = new Rectangle(220, 20, getMana() * 2, 40);
        }
        else{
            max = 900;
            min = 500;
            setX(700);
            setY(380);
            setKier(false);
            Wskaznik ws = new Wskaznik(getX(), getY(), getKier());
            setWsk(ws);
            pasz = new Rectangle(580, 20, getZdrowie() * 2, 40);
            pasM = new Rectangle(780, 20, getMana() * 2, 40);
            // this.obiekt=obiekt;

        }

        pasz.setFill(Color.DARKRED);
        pasz.setOpacity(0.6);
        pasM.setFill(Color.DARKCYAN);
        pasM.setOpacity(0.6);
        root.getChildren().add(pasM);
        root.getChildren().add(pasz);
        obiekt.lista.add(this);

    }

    /**
     * funkcja odpowiedzialna za odnoszenie obrazen oraz wyswietlanie efektow zwiazanych z otrzymaniem obrazen.
     *
     * @param dmg ilosc odniesionych obrazen
     * @param x   wspolrzedna x miejsca w ktorym doszlo do kolizji
     * @param y   wspolrzedna y miejsca w ktorym doszlo do kolizji
     */
    public void obrazenie(double dmg, double x, double y){
        this.setZdrowie(this.getZdrowie() - dmg / 10);
        for (int i = 0; i < dmg; i++){
            new Krew(x, y, Color.color(0.4, 0.00, 0.01, 1), random() * 4);
        }
        if (getZdrowie() <= 0)
            smierc();

    }

    /**
     * funkajca odpowiedzialna za otrzymywanie obrazen przez tarcze
     *
     * @param dmg ilosc obrazen
     */
    public void obrazTarcza(double dmg){
        getWsk().setLive(getWsk().getLive() - dmg);

    }

    /**
     * funkcja odpowiedzialna za odzyskiwanie zdrowia
     *
     * @param zdrow ilosc pkt. zdrowia jakie zostaly odzyskane
     */
    public void odzyskajZdrowie(double zdrow){
        if (getZdrowie() + zdrow <= 100){
            this.setZdrowie(this.getZdrowie() + zdrow);
            this.setMana(this.getMana() - zdrow);
            for (int i = 0; i < zdrow; i++)
                new Czasteczka(pasz.getX() + (random() * pasz.getWidth()) % ((int)pasz.getWidth()),
                               pasz.getY() + (random() * pasz.getHeight()) % ((int)pasz.getHeight()),
                               (Color)pasz.getFill(),
                               random() * 3 + 3,
                               5,
                               0.03);
        }
    }

    /**
     * funkcja odpowiedzialna za odzyskiwanie many
     *
     * @param mana ilosc pkt. many jakie zostaly odzyskane
     */
    public void odzyskajMane(double mana){
        if (this.getMana() + getZdrowie() + mana <= 200){
            this.setMana(this.getMana() + mana);
            for (int i = 0; i < mana; i++)
                new Czasteczka(pasM.getX() + (random() * pasM.getWidth()) % ((int)pasM.getWidth()),
                               pasM.getY() + (random() * pasM.getHeight()) % ((int)pasM.getHeight()),
                               (Color)pasM.getFill(),
                               random() * 3 + 3,
                               4,
                               0.03);
        }
        else
            odzyskajZdrowie(0.015);
    }

    /**
     * funkcja odpowiedzialna za dodawanie obrazow do tablicy obrazow postaci
     *
     * @param obraz obraz dodawany do tablicy
     * @param i     nr miejsca w tablicy
     */
    public void setObraz(Image obraz, int i){
        this.getObraz()[i] = obraz;
    }

    /**
     * funkcja odpowiedzialana za wyswietlanie postaci i zwiazanych z nia efektow
     */
    public void wyswietl(){
        if (getSmierc()){
            this.getWsk().getElement().setOpacity(0);
        }
        else{
            if (getKier()){

                gc.drawImage(getObraz()[nr], this.getX(), this.getY(), 150, 100);
                getWsk().wyswietl(gc, getX() + 70, getY() + 50);

            }
            else{
                gc.drawImage(getObraz()[nr], this.getX() + 100, this.getY(), -150, 100);
                getWsk().wyswietl(gc, getX() + 30, getY() + 50);
            }
        }
        pasz.setWidth(getZdrowie() * 2);
        pasM.setX(pasz.getX() + getZdrowie() * 2);
        pasM.setWidth(getMana() * 2);
        if (getMana() + getZdrowie() == 200){

        }
        else
            pasM.setBlendMode(pasz.getBlendMode());

    }

    /**
     * funkcja odpowiedzialna za podnoszenie celownika
     */
    public void pozycjaUP(){
        if (getWsk().getPostawa() < 20)
            getWsk().setPostawa(getWsk().getPostawa() + 0.6);
    }

    /**
     * funkcja odpowiedzialna za opuszczanie celownika
     */

    public void pozycjaDOWN(){
        if (getWsk().getPostawa() > -20)
            getWsk().setPostawa(getWsk().getPostawa() - 0.6);
    }

    /**
     * funkcja odpowiedzialana za aktywacje tarczy
     */
    public void tarczaUP(){
        if (getMana() - 30 >= 0 && this.getWsk().getStan() == 0){
            this.getWsk().setStan(1);
            setMana(getMana() - 30);
        }
    }

    /**
     * funkcja odpowiedzialna za zmiane pozycji gracza w prawa strone
     */
    public void right(){
        if (getX() < max)
            setX(getX() + 5);
        nr = 0;
    }

    /**
     * funkcja odpowiedzialna za zamiane pozycji gracza w lewa strone
     */
    public void left(){
        if (getX() > min)
            setX(getX() - 5);
        nr = 0;
    }

    /**
     * funkcja odpowiedzialna za przejscie w stan bezczynnosci postaci
     */
    public void nic(){
        nr = 1;
    }

    /**
     * funlkcja odpowiedzialna za wystrzelenie pierwszego rodzaju pocisku
     *
     * @param a     kolor pocisku
     * @param gracz gracz ktory wystrzelil pocisk
     */
    public void strzel(Color a, Postac gracz){
        if (getMana() - 10 >= 0){
            this.nic();
            Pocisk poc = new Pocisk(5, this, gracz, a);
            obiekt.lista.add(poc);
            setMana(getMana() - 10);
        }
    }

    /**
     * funkcja odpowiada za wystrzelenie drugiego rodzaju pocisku
     *
     * @param a     kolor pocisku
     * @param gracz gracz ktory wystrzelil pocisk
     */
    public void strzel2(Color a, Postac gracz){
        if (getMana() - 1 >= 0){
            this.nic();
            Laser poc = new Laser(5, this, gracz, a);
            obiekt.lista.add(poc);
            setMana(getMana() - 1);
        }
    }

    /**
     * funkcja odpowiadajaca za zmiane statusu gracza gdy hp=0
     */
    public void smierc(){
        setSmierc(true);
        if (getKier()){
            getWsk().setXt(getX() + 50);
            getWsk().setYt(getY() + 50);
        }
        else{
            getWsk().setXt(getX());
            getWsk().setYt(getY() + 50);

        }
        for (int i = 0; i < 40; i += 4){
            double k;
            for (double alf = 0; alf < 360; alf += 360 / 10){
                if (i < 30){
                    k = random() * 0.6 + 0.2;
                    new Krew(i * sin(alf) + getWsk().getXt(),
                             i * cos(alf) * 1.5 + getWsk().getYt(),
                             Color.color(k, k, k, 1),
                             random() / 4 * i);
                }

                new Czasteczka(i * sin(alf) + getWsk().getXt(),
                               i * cos(alf * 1.5) + getWsk().getYt(),
                               Color.color(0.4, 0.00, 0.01),
                               random() * 3 + 2,
                               0,
                               0.05);
                new Czasteczka(i * sin(alf) + getWsk().getXt(),
                               i * cos(alf * 1.5) + getWsk().getYt(),
                               Color.color(0.4, 0.00, 0.01),
                               random() * 2 + 1,
                               0,
                               0.04);
                //pogoda.wyswietl();

            }
        }
    }

    public double getZdrowie(){
        return zdrowie;
    }

    public Postac setZdrowie(double zdrowie){
        this.zdrowie = zdrowie;
        return this;
    }

    public double getMana(){
        return mana;
    }

    public Postac setMana(double mana){
        this.mana = mana;
        return this;
    }

    public int getX(){
        return x;
    }

    public Postac setX(int x){
        this.x = x;
        return this;
    }

    public int getY(){
        return y;
    }

    public Postac setY(int y){
        this.y = y;
        return this;
    }

    public boolean getSmierc(){
        return smierc;
    }

    public Postac setSmierc(boolean smierc){
        this.smierc = smierc;
        return this;
    }

    public boolean getKier(){
        return kier;
    }

    public Postac setKier(boolean kier){
        this.kier = kier;
        return this;
    }

    public Image[] getObraz(){
        return obraz;
    }

    public Wskaznik getWsk(){
        return wsk;
    }

    public Postac setWsk(Wskaznik wsk){
        this.wsk = wsk;
        return this;
    }
}


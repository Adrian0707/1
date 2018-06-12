package com.company;

import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.company.Main.obiekt;
import static com.company.Main.root;
import static java.lang.Math.*;

/**
 *
 */
public class Pocisk extends Obiekty implements Animowane{

    protected double czas;
    private Circle kula = new Circle();
    private int nr;
    protected Postac nadawca, odbiorca;
    protected double poz;
    protected int g = 10;
    protected double v;

    protected int x, y;
    protected boolean kier;

    /**
     * konstruktor klasy Pocisk
     *
     * @param a        promien kuli
     * @param nadawca  obiekt klasy Postac bedacy odpowiedzialny za wyslanie pocisku
     * @param odbiorca obiekt klasy Postac bedacy celem pocisku
     * @param b        kolor pocisku
     */
    public Pocisk(int a, Postac nadawca, Postac odbiorca, Color b){
        kier = nadawca.getKier();
        if (kier)
            poz = (nadawca.getWsk().getPostawa() + 21) / 45;
        else
            poz = (-nadawca.getWsk().getPostawa() + 21) / 45;
        setR(a);

        x = (int)nadawca.getWsk().getX();
        y = (int)nadawca.getWsk().getY();
        nr = 0;
        czas = 0;
        this.odbiorca = odbiorca;
        this.nadawca = nadawca;

        getKula().setFill(b);
        getKula().setOpacity(0.4);

        if (this instanceof Laser)
            ;

        else
            getKula().setBlendMode(BlendMode.HARD_LIGHT);
        root.getChildren().add(getKula());

    }

    /**
     * funkcja bedaca odpowiedzialna za tor ruchu obiektu
     */
    protected void lec(){

        v = sqrt((sqrt((nadawca.getX() - odbiorca.getX()) * (nadawca.getX() - odbiorca.getX())) * g) / (sin(2 * poz))) * 0.8;
        if (kier){

            x += czas * v * cos(poz);
            y -= czas * v * sin(poz) - (g * czas * czas) * 21;
            czas += 0.005;
        }

        else{
            x -= czas * v * cos(poz);
            y -= czas * v * sin(poz) - (g * czas * czas) * 21;
            czas += 0.005;
        }
        if (y > 480){
            root.getChildren().remove(this.getKula());
            obiekt.lista.remove(this);
            for (int i = 0; i < 4; i++){
                Czasteczka b = new Czasteczka(this.x, this.y, this.getColor(), random() * 2 + 2);

            }

        }

    }

    /**
     * Funkcja odpowiedzialna za wyswietlanie pocisku oraz efektow z nim zwiazanych
     */
    public void wyswietl(){

        getKula().setCenterX(x);
        getKula().setCenterY(y);
        if (this instanceof Laser){
            if (random() > 0.995){
                Czasteczka k = new Czasteczka(x, y, getColor(), random() * 3 + 1);
                k.setLive(k.getLive() - 10);
            }
            if (random() < 0.6)
                new Czasteczka(x, y, getColor(), random() * 2 + 1, 5.5, 0);

        }
        else{
            for (int i = 0; i < 2; i++){
                new Czasteczka(x,
                               y,
                               Color.color(0.3 * (getColor().getRed() * 2 + random()),
                                           0.3 * (random() + 2 * getColor().getGreen()),
                                           0.3 * (getColor().getBlue() * 2 + random())),
                               random() * 2 + 1,
                               1,
                               0.00);
            }

        }
        if (getR() > 6 && !(this instanceof Laser)){

            for (double alf = 0; alf < 360; alf += 360 / 20){
                new Czasteczka(6 * sin(alf) + x,
                               6 * cos(alf) + y,
                               Color.color((random() + getColor().getRed()) / 2,
                                           (random() + getColor().getGreen()) / 2,
                                           (random() + getColor().getBlue()) / 2),
                               random() * 4,
                               3);

            }
        }

        lec();

    }

    /**
     * funkcja odpowiedzialna za pobranie koloru
     *
     * @return kolor ksztaltu pocisku
     */
    public Color getColor(){
        return (Color)getKula().getFill();
    }

    public double getR(){
        return this.getKula().getRadius();
    }

    public void setR(int r){
        this.getKula().setRadius(r);
    }

    public Circle getKula(){
        return kula;
    }

    public Pocisk setKula(Circle kula){
        this.kula = kula;
        return this;
    }
}

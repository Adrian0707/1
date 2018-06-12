package com.company;

import javafx.scene.paint.Color;

import static com.company.Main.obiekt;
import static com.company.Main.root;
import static java.lang.Math.*;

/**
 * klasa odpowiedzialna za wszelkie kolizje w grze
 */
public class Kolizje{
    /**
     * funkcja odpowiedzialna za efekty zdezen pociskow
     */
    public void pociski_zdezenie(){
        for (int k = 0; k < obiekt.lista.size(); k++){
            Obiekty aktual = obiekt.lista.get(k);
            if (aktual instanceof Pocisk){
                Pocisk ten = (Pocisk)obiekt.lista.get(k);

                for (int l = k + 1; l < obiekt.lista.size(); l++){

                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk){

                        Pocisk ten2 = (Pocisk)obiekt.lista.get(l);
                        if ((ten.kier != ten2.kier) && (odl(ten.x, ten2.x, ten.y, ten2.y)) <= ten.getKula()
                                                                                                 .getRadius() + ten2.getKula()
                                                                                                                    .getRadius()){
                            if (ten.getR() <= 10 || ten2.getR() <= 10){
                                if (ten instanceof Laser || ten2 instanceof Laser){

                                    for (int i = 0; i < 20; i++){
                                        new Czasteczka(ten2.x,
                                                       ten2.y,
                                                       Color.color((ten2.getColor().getRed()),
                                                                   (ten2.getColor().getGreen()),
                                                                   (ten2.getColor().getBlue()),
                                                                   (ten2.getColor().getOpacity())),
                                                       random() * 3 + 1,
                                                       3,
                                                       0);
                                        new Czasteczka(ten.x,
                                                       ten.y,
                                                       Color.color((ten.getColor().getRed()),
                                                                   (ten.getColor().getGreen()),
                                                                   (ten.getColor().getBlue()),
                                                                   (ten.getColor().getOpacity())),
                                                       random() * 3 + 1,
                                                       3,
                                                       0);

                                    }
                                }
                                else{
                                    for (int i = 0; i < 90; i++){
                                        new Czasteczka(ten.x,
                                                       ten.y,
                                                       Color.color((ten.getColor().getRed() + ten2.getColor()
                                                                                                  .getRed()) * 0.5,
                                                                   (ten.getColor().getGreen() + ten2.getColor()
                                                                                                    .getGreen()) * 0.5,
                                                                   (ten.getColor().getBlue() + ten2.getColor()
                                                                                                   .getBlue()) * 0.5,
                                                                   (ten.getColor().getOpacity() + ten2.getColor()
                                                                                                      .getOpacity()) * 0.5),
                                                       random() * 2 + 2);

                                    }
                                }
                            }
                            if (ten2.getR() > 10 && !(ten2 instanceof Laser)){
                                for (int i = 0; i < 50; i++){
                                    new Czasteczka(ten2.x,
                                                   ten2.y,
                                                   Color.color((random() + ten2.getColor().getRed()) * 0.5,
                                                               (random() + ten2.getColor().getGreen()) * 0.5,
                                                               (random() + ten2.getColor().getBlue()) * 0.5,
                                                               (random() + ten2.getColor().getOpacity()) * 0.5),
                                                   random() * 3 + 3);
                                }
                            }
                            if (ten.getR() > 10 && !(ten instanceof Laser)){
                                for (int i = 0; i < 50; i++){
                                    new Czasteczka(ten.x,
                                                   ten.y,
                                                   Color.color((random() + ten.getColor().getRed()) * 0.5,
                                                               (random() + ten.getColor().getGreen()) * 0.5,
                                                               (random() + ten.getColor().getBlue()) * 0.5,
                                                               (random() + ten.getColor().getOpacity()) * 0.5),
                                                   random() * 3 + 3);
                                }
                            }
                            root.getChildren().remove(ten.getKula());
                            root.getChildren().remove(ten2.getKula());
                            obiekt.lista.remove(ten);
                            obiekt.lista.remove(ten2);

                        }

                    }
                }
            }
        }
    }

    private double odl(double x1, double x2, double y1, double y2){
        return sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    /**
     * funkcja odpowiedzialna za efekt laczenia sie dwoch pociskow w jeden gdy sa dostatecznie blisko
     */
    public void pociski_laczenie(){
        for (int k = 0; k < obiekt.lista.size(); k++){
            Obiekty aktual = obiekt.lista.get(k);
            if (aktual instanceof Pocisk && !(aktual instanceof Laser)){
                Pocisk ten = (Pocisk)obiekt.lista.get(k);

                for (int l = k + 1; l < obiekt.lista.size(); l++){

                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk && !(aktual2 instanceof Laser)){

                        Pocisk ten2 = (Pocisk)obiekt.lista.get(l);
                        if ((ten.kier == ten2.kier) && (odl(ten.x, ten2.x, ten.y, ten2.y)) < (ten.getKula()
                                                                                                 .getRadius()*2 +
                                ten2.getKula()
                                                                                                                    .getRadius())*2){
                            ten.getKula().setCenterX((ten.getKula().getCenterX() + ten2.getKula().getCenterX()) / 2);
                            ten.getKula().setCenterY((ten.getKula().getCenterY() + ten2.getKula().getCenterY()) / 2);
                            ten.setR((int)(max(ten.getKula().getRadius(),
                                               ten2.getKula().getRadius()) + sqrt(min(ten.getKula().getRadius(),
                                                                                      ten2.getKula().getRadius()))));
                            ten.getKula().setRadius(ten.getR());
                            root.getChildren().remove(ten2.getKula());
                            obiekt.lista.remove(ten2);
                            k--;
                            l--;
                        }
                    }
                }
            }
        }
    }

    /**
     * funkcja odpowieddzialna za usuwanie pociskow gdy wyjda poza ramy wyswietlanego obrazu
     */

    public void pociski_za_daleko(){
        for (int i = 0; i < obiekt.lista.size(); i++){

            if (obiekt.lista.get(i) instanceof Pocisk){
                Pocisk pocisk = (Pocisk)obiekt.lista.get(i);
                if (pocisk.x >= 1000 || pocisk.x <= 0){
                    obiekt.lista.remove(pocisk);
                    root.getChildren().remove(pocisk.getKula());
                }
            }
        }
    }

    /**
     * funkcja odpowiedzialna za usuwanie sparkow gdy wyjda poza ramy wyswietlanego obrazu
     */
    public void spark_za_daleko(){
        for (int i = 0; i < obiekt.lista.size(); i++){

            if (obiekt.lista.get(i) instanceof Czasteczka){
                Czasteczka pocisk = (Czasteczka)Czasteczka.lista.get(i);
                if (pocisk.x >= 1000 || pocisk.x <= 0 || pocisk.y > 512 || pocisk.y <= -10){

                    root.getChildren().remove(pocisk.getSpar());
                    obiekt.lista.remove(pocisk);
                }
                if (pocisk.getLive() <= 0.5){
                    root.getChildren().remove(pocisk.getSpar());
                    obiekt.lista.remove(pocisk);
                }
            }

        }
    }

    /**
     * funkcja odpowiedzialna za efekty kolizji pocisku z postacia
     *
     * @param p1 postac gracza
     */
    public void trafienie(Postac p1){
        int modx1;
        int modx2;
        int mody1;
        int mody2;
        for (int i = 0; i < obiekt.lista.size(); i++){
            Obiekty o = obiekt.lista.get(i);
            if (o instanceof Pocisk){
                Pocisk p = (Pocisk)o;
                if (p1.getKier()){
                    modx1 = 58;
                    mody1 = 45;
                    modx2 = 60;
                    mody2 = 80;
                }
                else{
                    modx1 = 42;
                    mody1 = 45;
                    modx2 = 44;
                    mody2 = 80;
                }
                if ((odl(p1.getWsk().getXt(),
                         p.x,
                         p1.getWsk().getYt(),
                         p.y) < 60) && p1.getKier() != p.kier && p1.getWsk().getStan() == 1){
                    if (p instanceof Laser){
                        p1.odzyskajMane(0.3);
                        p1.obrazTarcza(0.3);
                        p1.odzyskajZdrowie(0.15);
                    }
                    else{
                        p1.obrazTarcza((p.getR() * p.getR() * p.getR()) - 100);
                        p1.odzyskajMane(10);
                        p1.odzyskajZdrowie(5);
                    }
                    root.getChildren().remove(p.getKula());
                    obiekt.lista.remove(p);
                    for (int u = 0; u < 10; u++){
                        new Czasteczka(p.x,
                                       p.y,
                                       Color.color((p.getColor().getRed() + p1.getWsk().getColor().getRed()) * 0.5,
                                                   (p.getColor().getGreen() + p1.getWsk().getColor().getGreen()) * 0.5,
                                                   (p.getColor().getBlue() + p1.getWsk().getColor().getBlue()) * 0.5,
                                                   (p.getColor().getOpacity() + p1.getWsk()
                                                                                  .getColor()
                                                                                  .getOpacity()) * 0.5),
                                       random() + 2,
                                       3.5,
                                       0.1);

                    }
                }
                else if (((odl(p1.getX() + modx1, p.x, p1.getY() + mody1, p.y) < 30) || odl(p1.getX() + modx2,
                                                                                            p.x,
                                                                                            p1.getY() + mody2,
                                                                                            p.y) < 17) && p1.getKier() != p.kier){
                    for (int k = 0; k < 10; k++){
                        if (p instanceof Laser)
                            p1.obrazenie(0.6, p.x, p.y);
                        else{
                            p1.obrazenie(p.getKula().getRadius() * 3, p.x, p.y);

                        }
                        new Czasteczka(p.x, p.y, p.getColor(), random() * 2 + 1);
                    }
                    root.getChildren().remove(p.getKula());
                    obiekt.lista.remove(p);
                }

            }

        }

    }

    /**
     * funkcja odpowiedzialna za odbicie pocisku przez gracz
     *
     * @param p1 obiekt klasy Postac odbijajacy pocisk
     */
    public void odbiciePocisku(Postac p1){
        for (int k = 0; k < obiekt.lista.size(); k++){
            Obiekty aktual = obiekt.lista.get(k);
            if (aktual instanceof Pocisk){
                Pocisk ten = (Pocisk)obiekt.lista.get(k);

                if ((ten.kier != p1.getKier()) && (odl(ten.x,
                                                       p1.getWsk().getX(),
                                                       ten.y,
                                                       p1.getWsk().getY())) <= ten.getKula().getRadius() + p1.getWsk()
                                                                                                             .getElement()
                                                                                                             .getRadius() * 1.4){
                    for (int i = 0; i < 20; i++){
                        new Czasteczka(ten.x,
                                       ten.y,
                                       Color.color((ten.getColor().getRed()),
                                                   (ten.getColor().getGreen()),
                                                   (ten.getColor().getBlue()),
                                                   (ten.getColor().getOpacity())),
                                       random() * 3 + 1,
                                       3,
                                       0);

                    }

                    ten.setR((int)(ten.getR() + 1));

                    p1.odzyskajMane(ten.getR());
                    ten.kier = !ten.kier;
                }
            }
        }

    }

    /**
     * funkcja odpowiedzialna za wszelkie efekty zwiazane z kolizjami na ekranie
     *
     * @param gracz  postac gracza 1
     * @param gracz2 postac gracza 2
     */
    public void fizyka(Postac gracz, Postac gracz2){
        pociski_zdezenie();
        pociski_laczenie();
        pociski_za_daleko();
        spark_za_daleko();
        if (!gracz.getSmierc()){

            odbiciePocisku(gracz);
            trafienie(gracz);
        }
        if (!gracz2.getSmierc()){

            odbiciePocisku(gracz2);
            trafienie(gracz2);
        }
    }
}

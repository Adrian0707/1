package com.company;

import javafx.scene.paint.Color;

import static com.company.Klawisze.obiekt;
import static com.company.Klawisze.root;
import static java.lang.Math.*;

public class Kolizje{
    public void pociski_zdezenie(){
        for (int k = 0; k < obiekt.lista.size(); k++){
            Obiekty aktual = obiekt.lista.get(k);
            if (aktual instanceof Pocisk){
                Pocisk ten = (Pocisk)obiekt.lista.get(k);

                for (int l = k + 1; l < obiekt.lista.size(); l++){

                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk){
                        // if(!(aktual2 instanceof Laser)&&(!(ten instanceof Laser))||(aktual2 instanceof Laser)&&(ten instanceof Laser)){
                        Pocisk ten2 = (Pocisk)obiekt.lista.get(l);
                        if ((ten.kier != ten2.kier) && (odl(ten.x,
                                                            ten2.x,
                                                            ten.y,
                                                            ten2.y)) <= ten.kula.getRadius() + ten2.kula.getRadius()){
                            if (ten.getR() <= 5 || ten2.getR() <= 5){
                                if (ten instanceof Laser || ten2 instanceof Laser){

                                    for (int i = 0; i < 20; i++){
                                        new Spark(ten2.x,
                                                  ten2.y,
                                                  Color.color((ten2.color().getRed()),
                                                              (ten2.color().getGreen()),
                                                              (ten2.color().getBlue()),
                                                              (ten2.color().getOpacity())),
                                                  random() * 3 + 1,
                                                  1.4,
                                                  0);
                                        new Spark(ten.x,
                                                  ten.y,
                                                  Color.color((ten.color().getRed()),
                                                              (ten.color().getGreen()),
                                                              (ten.color().getBlue()),
                                                              (ten.color().getOpacity())),
                                                  random() * 3 + 1,
                                                  1.4,
                                                  0);

                                    }
                                }
                                else{
                                    for (int i = 0; i < 90; i++){
                                        new Spark(ten.x,
                                                  ten.y,
                                                  Color.color((ten.color().getRed() + ten2.color().getRed()) * 0.5,
                                                              (ten.color().getGreen() + ten2.color().getGreen()) * 0.5,
                                                              (ten.color().getBlue() + ten2.color().getBlue()) * 0.5,
                                                              (ten.color().getOpacity() + ten2.color()
                                                                                              .getOpacity()) * 0.5),
                                                  random() * 2 + 2);

                                        //  new Spark(ten.x,ten.y,root,obiekt, ten.color);
                                        //  new Spark(ten.x,ten.y,root,obiekt, ten2.color);

                                    }
                                }
                            }
                            if (ten2.getR() > 5){
                                for (int i = 0; i < 50; i++){
                                    new Spark(ten2.x,
                                              ten2.y,
                                              Color.color((random() + ten2.color().getRed()) * 0.5,
                                                          (random() + ten2.color().getGreen()) * 0.5,
                                                          (random() + ten2.color().getBlue()) * 0.5,
                                                          (random() + ten2.color().getOpacity()) * 0.5),
                                              random() * 3 + 3);
                                }
                            }
                            if (ten.getR() > 5){
                                for (int i = 0; i < 50; i++){
                                    new Spark(ten.x,
                                              ten.y,
                                              Color.color((random() + ten.color().getRed()) * 0.5,
                                                          (random() + ten.color().getGreen()) * 0.5,
                                                          (random() + ten.color().getBlue()) * 0.5,
                                                          (random() + ten.color().getOpacity()) * 0.5),
                                              random() * 3 + 3);
                                }
                            }
                            root.getChildren().remove(ten.kula);
                            root.getChildren().remove(ten2.kula);
                            obiekt.lista.remove(ten);
                            obiekt.lista.remove(ten2);
                            // k--;
                            // l--;
                        }
                        //}
                   /* else {
                        Pocisk ten2 = (Pocisk) obiekt.lista.get(l);
                        if ((ten.kier !=ten2.kier)&&
                                (odl(ten.x,ten2.x,ten.y,ten2.y))<=ten.kula.getRadius()+ten2.kula.getRadius()) {
                                for (int i = 0; i < 20; i++) {
                                    new Spark(ten.x, ten.y, root, obiekt, Color.color(
                                            (ten.color.getRed() + ten2.color.getRed()) * 0.5,
                                            (ten.color.getGreen() + ten2.color.getGreen()) * 0.5,
                                            (ten.color.getBlue() + ten2.color.getBlue()) * 0.5));
                                    //  new Spark(ten.x,ten.y,root,obiekt, ten.color);
                                    //  new Spark(ten.x,ten.y,root,obiekt, ten2.color);
                                }
                               //
                            if(ten2 instanceof Laser) {
                                root.getChildren().remove(ten2.kula);
                                obiekt.lista.remove(ten2);
                            }
                            else{
                                obiekt.lista.remove(ten);
                                root.getChildren().remove(ten.kula);
                                }
                                // k--;
                                // l--;

                        }
                        }*/
                    }
                }
            }
        }
    }

    private double odl(double x1, double x2, double y1, double y2){
        return sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public void pociski_łączenie(){
        for (int k = 0; k < obiekt.lista.size(); k++){
            Obiekty aktual = obiekt.lista.get(k);
            if (aktual instanceof Pocisk && !(aktual instanceof Laser)){
                Pocisk ten = (Pocisk)obiekt.lista.get(k);

                for (int l = k + 1; l < obiekt.lista.size(); l++){

                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk && !(aktual2 instanceof Laser)){

                        Pocisk ten2 = (Pocisk)obiekt.lista.get(l);
                        if ((ten.kier == ten2.kier) && (odl(ten.x,
                                                            ten2.x,
                                                            ten.y,
                                                            ten2.y)) < (ten.kula.getRadius() + ten2.kula.getRadius())){
                            ten.kula.setCenterX((ten.kula.getCenterX() + ten2.kula.getCenterX()) / 2);
                            ten.kula.setCenterY((ten.kula.getCenterY() + ten2.kula.getCenterY()) / 2);
                            ten.setR((int)(max(ten.kula.getRadius(),
                                               ten2.kula.getRadius()) + sqrt(min(ten.kula.getRadius(),
                                                                                 ten2.kula.getRadius()))));
                            ten.kula.setRadius(ten.getR());
                            root.getChildren().remove(ten2.kula);
                            obiekt.lista.remove(ten2);
                            k--;
                            l--;
                        }
                    }
                }
            }
        }
    }

    public void pociski_za_daleko(){
        for (int i = 0; i < obiekt.lista.size(); i++){

            if (obiekt.lista.get(i) instanceof Pocisk){
                Pocisk pocisk = (Pocisk)obiekt.lista.get(i);
                if (pocisk.x >= 1000 || pocisk.x <= 0){
                    obiekt.lista.remove(pocisk);
                    root.getChildren().remove(pocisk.kula);
                }
            }
        }
    }

    public void spark_za_daleko(){
        for (int i = 0; i < obiekt.lista.size(); i++){

            if (obiekt.lista.get(i) instanceof Spark){
                Spark pocisk = (Spark)Spark.lista.get(i);
                if (pocisk.x >= 1000 || pocisk.x <= 0 || pocisk.y > 512 || pocisk.y <= 0){

                    root.getChildren().remove(pocisk.spar);
                    obiekt.lista.remove(pocisk);
                }
                if (pocisk.live <= 0.5){
                    root.getChildren().remove(pocisk.spar);
                    obiekt.lista.remove(pocisk);
                }
            }

        }
    }

    public void trafienie(Postać p1){
        int modx1;
        int modx2;
        int mody1;
        int mody2;
        for (int i = 0; i < obiekt.lista.size(); i++){
            Obiekty o = obiekt.lista.get(i);
            if (o instanceof Pocisk){
                Pocisk p = (Pocisk)o;
                if (p1.isKier()){
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
                if ((odl(p1.wsk.xt, p.x, p1.wsk.yt, p.y) < 60) && p1.isKier() != p.kier && p1.wsk.stan == 1){
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
                    root.getChildren().remove(p.kula);
                    obiekt.lista.remove(p);
                    for (int u = 0; u < 10; u++){
                        new Spark(p.x,
                                  p.y,
                                  Color.color((p.color().getRed() + p1.wsk.kolorT().getRed()) * 0.5,
                                              (p.color().getGreen() + p1.wsk.kolorT().getGreen()) * 0.5,
                                              (p.color().getBlue() + p1.wsk.kolorT().getBlue()) * 0.5,
                                              (p.color().getOpacity() + p1.wsk.kolorT().getOpacity()) * 0.5),
                                  random() + 2,
                                  3.5,
                                  0.1);

                    }
                }
                else if (((odl(p1.getX() + modx1, p.x, p1.getY() + mody1, p.y) < 30) || odl(p1.getX() + modx2,
                                                                                            p.x,
                                                                                            p1.getY() + mody2,
                                                                                            p.y) < 17) && p1.isKier() != p.kier){
                    for (int k = 0; k < 10; k++){
                        if (p instanceof Laser)
                            p1.obrazenie(0.6, p.x, p.y);
                        else{
                            p1.obrazenie(p.kula.getRadius()*3, p.x, p.y);

                        }
                        new Spark(p.x, p.y, p.color(), random() * 2 + 1);
                    }
                    root.getChildren().remove(p.kula);
                    obiekt.lista.remove(p);
                }

            }

        }

    }
}

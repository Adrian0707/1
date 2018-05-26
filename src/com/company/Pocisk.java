package com.company;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

import static com.company.Klawisze.root;
import static java.lang.Math.*;


public class Pocisk extends Obiekty implements Animowane {
    double czas;

    Circle kula=new Circle();
    int nr;
    Postać nadawca,odbiorca;
    int r;
    double poz;
    int g=10;
    double v;
    Random rand = new Random();
    //Group root;
    public double x,y;
    public boolean kier;
    public void leć () {
       v=sqrt((sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x))*g)/(sin(2*poz)));
        if(kier){
            //v=sqrt(60-poz/2);
            x+=czas*v*cos(poz);
            y-=czas*v*sin(poz)-(2*g*czas*czas)*11;
            //x+=czas*v*cos(poz);
            //y-=czas*v*sin(poz/20)+(g*czas*czas/2)*400;
           // x+=3+(10-r*2);
          //  y = y + Math.sin(czas*10*(15-r*2)+4*poz)*4;//rand.nextDouble()*10; inna wercja
          //  x=10*cos(czas)+300;
          //  y=10*sin(czas);
            czas+=0.009;
            if(0==(rand.nextDouble()*10)%5 ){

            }
        }

        else{
            x-=3*r/5;
            y+=  sin(czas*15-4*poz)*4;
            czas+=0.009;}

            if (y>480){
            y=475;
            czas+=(rand.nextDouble()%1)/6;
            }

    }
    public void umrzyj(){
        nr=2;
    }
    public void wyswietl(){
       // a.drawImage(obraz[nr],x,y);
        kula.setCenterX(x);
        kula.setCenterY(y);
        leć();



    }
    public Pocisk(int a, Postać nadawca,Postać odbiorca, Color b){

       poz=(nadawca.wsk.postawa+21)/40;
        r=a;
        kier=nadawca.kier;

        x=nadawca.wsk.x;
        y=nadawca.wsk.y;
        nr=0;
        czas = 0;
        this.odbiorca=odbiorca;
        this.nadawca=nadawca;
        //this.root=root;
        kula.setRadius(r);
        kula.setFill(b);
        root.getChildren().add(kula);



    }

}

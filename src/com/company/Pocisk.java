package com.company;

import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

import static com.company.Klawisze.obiekt;
import static com.company.Klawisze.root;
import static java.lang.Math.*;

public class Pocisk extends Obiekty implements Animowane{
    double czas;

    Circle kula = new Circle();
    int nr;
    Postać nadawca, odbiorca;
    double poz;
    int g = 10;
    double v;
    Random rand = new Random();

    public double x, y;
    public boolean kier;

    public void leć(){
        //v=sqrt((sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x))*g)/(sin(2*poz)))*0.85;
        v = sqrt((sqrt((nadawca.getX() - odbiorca.getX()) * (nadawca.getX() - odbiorca.getX())) * g) / (sin(2 * poz))) * 0.8;
        if (kier){
            //v=sqrt(60-poz/2);
            x += czas * v * cos(poz);
            y -= czas * v * sin(poz) - (g * czas * czas) * 21;//-sin(poz)*0.0032*sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x));
            //x+=3*r/10;
            //y-= sin(czas*15-4*poz)*3;
            //x+=czas*v*cos(poz);
            //y-=czas*v*sin(poz/20)+(g*czas*czas/2)*400;
            // x+=3+(10-r*2);
            //  y = y + Math.sin(czas*10*(15-r*2)+4*poz)*4;//rand.nextDouble()*10; inna wercja
            //  x=10*cos(czas)+300;
            //  y=10*sin(czas);
            czas += 0.005;
        }

        else{
            x -= czas * v * cos(poz);
            y -= czas * v * sin(poz) - (g * czas * czas) * 21;//-sin(poz)*0.0022*sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x));
            czas += 0.005;
        }
        if (y > 480){
            root.getChildren().remove(this.kula);
            obiekt.lista.remove(this);
            for (int i = 0; i < 4; i++){
                Spark b = new Spark(this.x, this.y, this.color(), random() * 2 + 2);

            }

        }

    }

    public void wyswietl(){
        // a.drawImage(obraz[nr],x,y);
        kula.setCenterX(x);
        kula.setCenterY(y);
        if (this instanceof Laser){
            if (random() > 0.99){
                Spark k = new Spark(x, y, color(), random() * 3 + 1);
                k.live -= 10;
            }
        }
        else{
            for (int i = 0; i < 4; i++){
                Spark k = new Spark(x, y, color(), random() * 2 + 1, 1.2, 0.05);

                k.live -= 20;
            }
        }
        if (getR() > 5){
            //kula.setFill(Color.color(random(),random() ,random() ,random() ));
            for (double alf = 0; alf < 360; alf += 360 / 50){
                //for(int i=0;i<3;i++)
                Spark sp = new Spark(6 * sin(alf) + x,
                                     6 * cos(alf) + y,
                                     Color.color((random() + color().getRed()) / 2,
                                                 (random() + color().getGreen()) / 2,
                                                 (random() + color().getBlue()) / 2),
                                     random() * 4,
                                     3);

            }
        }

        leć();

    }

    public Pocisk(int a, Postać nadawca, Postać odbiorca, Color b){
        kier = nadawca.isKier();
        if (kier)
            poz = (nadawca.wsk.postawa + 21) / 45;
        else
            poz = (-nadawca.wsk.postawa + 21) / 45;
        setR(a);

        //color=b;
        x = nadawca.wsk.x;
        y = nadawca.wsk.y;
        nr = 0;
        czas = 0;
        this.odbiorca = odbiorca;
        this.nadawca = nadawca;
        //this.root=root;
        kula.setRadius(getR());
        kula.setFill(b);
        // kula.setBlendMode(BlendMode.RED);
        if (this instanceof Laser)
            ;
            // kula.setBlendMode(BlendMode.HARD_LIGHT);
        else
            kula.setBlendMode(BlendMode.HARD_LIGHT);
        root.getChildren().add(kula);

    }

    public Color color(){
        return (Color)kula.getFill();
    }

    public double getR(){
        return this.kula.getRadius();
    }

    public void setR(int r){
        this.kula.setRadius(r);
    }
}

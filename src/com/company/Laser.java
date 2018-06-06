package com.company;

import javafx.scene.paint.Color;

import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Laser extends Pocisk {
    public Laser(int a, Postać włas,Postać gracz, Color k) {
        super(a, włas,gracz, k);
    }
    @Override
    public void leć () {
        //v=sqrt((sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x))*g)/(sin(2*poz)))*0.85;
        v=sqrt((sqrt((nadawca.getX() - odbiorca.getX())*(nadawca.getX() - odbiorca.getX()))*g)/(sin(2*poz)))*0.8;
        if(kier){
            //v=sqrt(60-poz/2);
            //x+=czas*v*cos(poz);
            //y-=czas*v*sin(poz)-(g*czas*czas)*21;//-sin(poz)*0.0032*sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x));
            x+=3*r/6;
            y-= sin(czas*15-4*poz-10)*5;
            //x+=czas*v*cos(poz);
            //y-=czas*v*sin(poz/20)+(g*czas*czas/2)*400;
            // x+=3+(10-r*2);
            //  y = y + Math.sin(czas*10*(15-r*2)+4*poz)*4;//rand.nextDouble()*10; inna wercja
            //  x=10*cos(czas)+300;
            //  y=10*sin(czas);
            czas+=0.01;
        }

        else{
           // x-=czas*v*cos(poz);
            //y-=czas*v*sin(poz)-(g*czas*czas)*21;//-sin(poz)*0.0022*sqrt((nadawca.x-odbiorca.x)*(nadawca.x-odbiorca.x));
            //x-=3*r/6;
            //y-= sin(czas*15-4*poz-10)*10;
            x-=3*r/6;
            y-= sin(czas*15-4*poz-10)*5;
            czas+=0.01;
        }
        if (y>480){
            y=478;
            czas+=rand.nextInt()*10;
            //poz=rand.nextInt()*10;
          // x+=rand.nextInt()*10;
        }

    }
}

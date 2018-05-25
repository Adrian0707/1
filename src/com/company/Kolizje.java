package com.company;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.company.Klawisze.obiekt;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;
import static com.company.Klawisze.root;

public class Kolizje {
   public void pociski_zdezenie (){
        for (int k=0;k< obiekt.lista.size();k++)
        {
            Obiekty aktual = obiekt.lista.get(k);
            if(aktual instanceof Pocisk)
            {
                Pocisk ten = (Pocisk) obiekt.lista.get(k);

                for (int l=k+1;l< obiekt.lista.size();l++) {


                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk) {

                        Pocisk ten2 = (Pocisk) obiekt.lista.get(l);
                        if ((ten.kier !=ten2.kier)&&(odl(ten.x,ten2.x,ten.y,ten2.y))<=ten.kula.getRadius()+ten2.kula.getRadius()){
                            for(int i=0;i<20;i++)
                            {
                                Spark b=new Spark(ten.x,ten.y,root,obiekt, Color.YELLOW);
                            }
                            root.getChildren().remove(ten.kula);
                            root.getChildren().remove(ten2.kula);
                            obiekt.lista.remove(ten);
                            obiekt.lista.remove(ten2);
                           // k--;
                           // l--;
                        }
                    }
                }
            }
        }
    }
    private double odl(double x1, double x2, double y1, double y2){
        return sqrt(((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)));}
    public void pociski_łączenie (){
        for (int k=0;k< obiekt.lista.size();k++)
        {
            Obiekty aktual = obiekt.lista.get(k);
            if(aktual instanceof Pocisk&& !(aktual instanceof Laser))
            {
                Pocisk ten = (Pocisk) obiekt.lista.get(k);

                for (int l=k+1;l< obiekt.lista.size();l++) {


                    Obiekty aktual2 = obiekt.lista.get(l);
                    if (aktual2 instanceof Pocisk && !(aktual2 instanceof Laser)) {

                        Pocisk ten2 = (Pocisk) obiekt.lista.get(l);
                        if ((ten.kier ==ten2.kier)&&(odl(ten.x,ten2.x,ten.y,ten2.y))<(ten.kula.getRadius()+ten2.kula.getRadius())){
                            ten.kula.setCenterX((ten.kula.getCenterX()+ten2.kula.getCenterX())/2);
                            ten.kula.setCenterY((ten.kula.getCenterY()+ten2.kula.getCenterY())/2);
                            ten.r=(int)(max(ten.kula.getRadius(),ten2.kula.getRadius())+sqrt(min(ten.kula.getRadius(),ten2.kula.getRadius())));
                            ten.kula.setRadius(ten.r);
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
    public void pociski_za_daleko () {
        for(int i=0;i<obiekt.lista.size();i++){

            if(obiekt.lista.get(i) instanceof Pocisk){
                Pocisk pocisk =(Pocisk)obiekt.lista.get(i);
                if(pocisk.x>=1000||pocisk.x<=0){
                    obiekt.lista.remove(pocisk);
                    root.getChildren().remove(pocisk.kula);
                }
            }
        }
    }
    public void spark_za_daleko(){
        for(int i=0;i<obiekt.lista.size();i++){

            if(obiekt.lista.get(i) instanceof Spark){
                Spark pocisk =(Spark) Spark.lista.get(i);
                if(pocisk.x>=1000||pocisk.x<=0||pocisk.y>512||pocisk.y<=0){

                    pocisk.root.getChildren().remove(pocisk.spar);
                    obiekt.lista.remove(pocisk);
                }
                if(pocisk.live<=0.5){
                    pocisk.root.getChildren().remove(pocisk.spar);
                    obiekt.lista.remove(pocisk);
                }
            }


}
   }
   public void trafienie( Postać p1){
       for(int i=0;i<obiekt.lista.size();i++){
           Obiekty o = obiekt.lista.get(i);
           if(o instanceof Pocisk ){
               Pocisk p=(Pocisk)o;

               if(((odl(p1.x+60,p.x,p1.y+50,p.y)<35)||odl(p1.x+60,p.x,p1.y+90,p.y)<15)&&p1.kier!=p.kier){
                   for(int k=0;k<20;k++)
                   {
                       Spark b=new Spark(p.x,p.y,root,obiekt, Color.RED);
                   }
                   root.getChildren().remove(p.kula);
                   obiekt.lista.remove(p);
               }

           }
               else if(o  instanceof  Laser){

           }

       }

   }
}

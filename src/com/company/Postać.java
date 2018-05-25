package com.company;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.company.Klawisze.obiekt;


public class Postać extends Obiekty implements Animowane  {
    private int zdrowie = 100;
   // private int postawa = 0;
    public Wskaznik wsk;
    private  int max;
    private int min;
    public int x=0;
    public int y=0;
    Postać odbiorca;
    GraphicsContext gc;
    public boolean kier;
    public Image[] obraz=new Image[5];
    private int nr=0;
    private int atak = 10;
  //  Obiekty obiekt;

    public Image[] getObraz() {
        return obraz;
    }
    public int getZdrowie() {
        return zdrowie;
    }
    public void obrazenie(int dmg) {
        this.zdrowie -= dmg;
    }
    public void atak(Postać przeciwnik) {
        przeciwnik.obrazenie(this.atak);
    }
    public void setObraz(Image obraz, int i) {
        this.obraz[i] = obraz;
    }
    public void wyswietl(){
        if(kier){
            gc.drawImage(obraz[nr], this.x ,this.y,150,100);
            wsk.wyswietl(gc,x+70,y+50);
           }
        else{
            gc.drawImage(obraz[nr], this.x+100 ,this.y,-150,100);
            wsk.wyswietl(gc,x+30,y+50);
            }
    }
    public void pozycjaUP(){
        if (wsk.postawa<20)
            wsk.postawa+=0.6;
    }
    public void pozycjaDOWN(){
        if (wsk.postawa>-20)
                wsk.postawa-=0.6;
    }
    public void right(){
        if(x<max)
        x+=5;
        nr=1;
    }
    public void left(){
        if(x>min)
        x-=5;
        nr=1;
    }
    public void nic(){
        nr=2;
    }
    public void strzel(Color a, Postać gracz){
        this.nic();
        Pocisk poc = new Pocisk(5,this ,gracz,a);
        obiekt.lista.add(poc);
    }
    public void strzel2(Color a, Postać gracz){
        this.nic();
        Laser poc = new Laser(5, this, gracz,a);
        obiekt.lista.add(poc);
    }
    public Postać(int i,Group root,GraphicsContext gc) {
       this.gc=gc;
        if(i==1) {
            max=400;
            min=0;
            x=200;
            y=380;
            kier=true;
            Wskaznik ws = new Wskaznik(x+100,y,root,kier);
            wsk=ws;
        }
        else {
            max=900;
            min=500;
            x=700;
            y=380;
            kier=false;
            Wskaznik ws = new Wskaznik(x,y,root,kier);
            wsk=ws;
           // this.obiekt=obiekt;

                }
        obiekt.lista.add(this);


    }
}


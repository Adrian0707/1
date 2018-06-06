package com.company;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashSet;



public class Klawisze extends Application {

    static int l = 0;
    static int k = 0;
    static Scene mainScene;
    static int WIDTH = 1000;
    static int HEIGHT = 512;
    volatile static Obiekty obiekt = new Obiekty();
    volatile static Group root = new Group();
    static  Canvas canvas = new Canvas(WIDTH, HEIGHT);
    static GraphicsContext gc = canvas.getGraphicsContext2D();


    public static void main(String[] args) {
        launch(args);
    }




    static HashSet<String> currentlyActiveKeys;


    @Override
    public void start(Stage mainStage) {

        mainStage.setTitle("GRA ADRIANA");
        mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setMinHeight(HEIGHT+30);
        mainStage.setMinWidth(WIDTH);
        mainStage.setMaxHeight(HEIGHT+30);
        mainStage.setMaxWidth(WIDTH);

        root.getChildren().add(canvas);

        Image tlo = new Image("cyberpunk-street.png");
        final long startNanoTime = System.nanoTime();
        prepareActionHandlers();
        gc = canvas.getGraphicsContext2D();

        Kolizje kolizje = new Kolizje();

        Image idz = new Image("Skeleton Walk.gif");
        Image stop = new Image("Skeleton Idle.gif");



        Postać gracz = new Postać(1);
        Postać gracz2 = new Postać(2);

        gracz.setObraz(idz, 0);
        gracz.setObraz(stop, 1);

        gracz2.setObraz(idz, 0);
        gracz2.setObraz(stop, 1);


        /**
         * Main "game" loop
         */
        new AnimationTimer() {


            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / (1000000000.0 + k);

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                gc.drawImage(tlo, 0, 0, mainScene.getWidth(), mainScene.getHeight());
               /* kol1a.setCenterY(gracz.y+45);
                kol1a.setCenterX(gracz.x+58);
                kol1b.setCenterX(gracz.x+60);
                kol1b.setCenterY(gracz.y+80);
                kol2a.setCenterY(gracz2.y+45);
                kol2a.setCenterX(gracz2.x+42);
                kol2b.setCenterX(gracz2.x+44);
                kol2b.setCenterY(gracz2.y+80);*/

                t *= 0.5;
                x = 232 + 198 * Math.cos(t + 1000);
                y = 232 + 198 * Math.sin(t + 1000);


             //   gc.fillText(Integer.toString(gracz.x) + "x " + Integer.toString(gracz.y) + "y", 100, 100);
             //   gc.fillText(  Double.toString(gracz.wsk.y) + "y", 300, 100);
             //   gc.fillText(  Double.toString(gracz2.wsk.y) + "y", 300, 140);

              /*  for (Obiekty a : obiekt.lista) {
                    if (a instanceof Animowane)
                        ((Animowane) a).wyswietl(gc, t, obiekt);

                }*/
             // obiekt.oczysc();
              obiekt.wyświetlanie();

                sterowanie(gracz2,root, gracz);
                kolizje.pociski_zdezenie();
               kolizje.pociski_łączenie();
               kolizje.pociski_za_daleko();
               kolizje.spark_za_daleko();
               if(!gracz.isSmierc())
               kolizje.trafienie(gracz);
               if(!gracz2.isSmierc())
                kolizje.trafienie(gracz2);
               // Circle k= new Circle(gracz2.x+55,gracz2.y+50,40);
                //k.setFill(Color.BLUE);
                //root.getChildren().add(k);
                if(gracz.isSmierc() || gracz2.isSmierc()){
                    gc.setFill(Color.color(0.6,0.8 ,0.4 ,0.5 ));
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(2);
                    Font theFont = Font.font("Bank Gothic", FontWeight.BOLD, 48);
                    gc.setFont(theFont);
                    gc.fillText("Enter aby ponowić", WIDTH/2-200, HEIGHT/2);
                    if(currentlyActiveKeys.contains("ENTER")){
                        gracz.setSmierc(false);
                        gracz.setZdrowie(100);
                        gracz.setMana(100);
                        gracz.setX(200);
                        gracz.setY(380);
                        gracz.wsk.live=0;
                        gracz.wsk.element.setOpacity(0.4);
                        gracz2.setSmierc(false);
                        gracz2.setZdrowie(100);
                        gracz2.setMana(100);
                        gracz2.setX(700);
                        gracz2.setY(380);
                        gracz2.wsk.live=0;
                        gracz2.wsk.element.setOpacity(0.4);
                       for(int i=0; i<obiekt.lista.size();i++){
                           if(obiekt.lista.get(i) instanceof Krew){
                               Krew a= (Krew)obiekt.lista.get(i);
                          //     root.getChildren().remove(a.spar);
                             //  obiekt.lista.remove(a);
                               a.live=0;

                           }
                           else if(obiekt.lista.get(i) instanceof Spark){
                               Spark b= (Spark)obiekt.lista.get(i);

                              b.live=0;
                           }
                           else if (obiekt.lista.get(i) instanceof Pocisk){
                               Pocisk a =(Pocisk)obiekt.lista.get(i);
                               obiekt.lista.remove(a);
                               root.getChildren().remove(a.kula );
                           }
                       }


                    }
                }

            }

        }.start();

        mainStage.show();
    }

    private static void prepareActionHandlers() {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    private static String getResource(String filename) {
        return Klawisze.class.getResource(filename).toString();
    }

    private static void tickAndRender(Postać gracz) {
        // clear canvas
        //graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        if (currentlyActiveKeys.contains("LEFT")) {
            gracz.left();
        }


        if (currentlyActiveKeys.contains("RIGHT")) {
            gracz.right();
        }

        if (currentlyActiveKeys.contains("UP")) {
            // while (currentlyActiveKeys.contains("UP"));
            if(!(currentlyActiveKeys.contains("RIGHT")||currentlyActiveKeys.contains("LEFT")))
                gracz.nic();
            gracz.pozycjaDOWN();
        }
        if (currentlyActiveKeys.contains("DOWN")) {
            // while(currentlyActiveKeys.contains("DOWN"));

            if(!(currentlyActiveKeys.contains("RIGHT")||currentlyActiveKeys.contains("LEFT")))
                gracz.nic();
            gracz.pozycjaUP();
        }


    }

    private static void tickAndRender2(Postać gracz) {
        // clear canvas
        //graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        if (currentlyActiveKeys.contains("A")) {
            gracz.left();
        }


        if (currentlyActiveKeys.contains("D")) {
            gracz.right();
        }

        if (currentlyActiveKeys.contains("W")) {
            // while (currentlyActiveKeys.contains("UP"));
            if(!(currentlyActiveKeys.contains("A")||currentlyActiveKeys.contains("D")))
                gracz.nic();
            gracz.pozycjaUP();
        }
        if (currentlyActiveKeys.contains("S")) {
            // while(currentlyActiveKeys.contains("DOWN"));
            if(!(currentlyActiveKeys.contains("A")||currentlyActiveKeys.contains("D")))
                gracz.nic();
            gracz.pozycjaDOWN();
        }


    }

    private static void sterowanie(Postać gracz,Group root, Postać gracz2) {
       if(!gracz.isSmierc())
        if (!currentlyActiveKeys.isEmpty()) {
            if (currentlyActiveKeys.contains("N")&& k==1 ) {
                k = 0;
                gracz.strzel(Color.BLUE, gracz2);

            }
            if(!currentlyActiveKeys.contains("N")) k=1;
            if (currentlyActiveKeys.contains("M")) {
                gracz.strzel2( Color.color(0.5, 1, 0.5,0.5),gracz2);

            }
            if(currentlyActiveKeys.contains("B")) {
                gracz.tarczaUP();


            }
            tickAndRender(gracz);

            }
        if(!(currentlyActiveKeys.contains("B")||currentlyActiveKeys.contains("N")||
                currentlyActiveKeys.contains("M")|| currentlyActiveKeys.contains("LEFT")||
                currentlyActiveKeys.contains("RIGHT"))&&!gracz.isSmierc()){
           gracz.odzyskajMane(0.12);
            gracz.nic();
            k = 1;
        }

        if(!gracz2.isSmierc())
        if (!currentlyActiveKeys.isEmpty()) {
            if (currentlyActiveKeys.contains("R")&&l==1 ) {
                l = 0;
                gracz2.strzel(Color.RED, gracz);
            }
            if(!currentlyActiveKeys.contains("R")) l=1;
            if (currentlyActiveKeys.contains("T")) {
                gracz2.strzel2( Color.color(1,0.5, 0.5,0.5 ),gracz);

            }
            if(currentlyActiveKeys.contains("Y")) {
                gracz2.tarczaUP();

            }
                tickAndRender2(gracz2);

            }
        if(!(currentlyActiveKeys.contains("R")||currentlyActiveKeys.contains("T")||
                currentlyActiveKeys.contains("Y")|| currentlyActiveKeys.contains("A")||
                currentlyActiveKeys.contains("D"))&&!gracz2.isSmierc()){
           gracz2.odzyskajMane(0.12);
            gracz2.nic();
            l = 1;
        }

    }
}
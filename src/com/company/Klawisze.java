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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashSet;



public class Klawisze extends Application {
    static int l = 0;
    // static int m = 0;
    static int k = 0;
    static Scene mainScene;
    static GraphicsContext graphicsContext;
    static int WIDTH = 1000;
    static int HEIGHT = 512;
    static Obiekty obiekt = new Obiekty();
    static Group root = new Group();
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
        //Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);


     //   GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
        gc.setFont(theFont);


        Image tlo = new Image("cyberpunk-street.png");
        final long startNanoTime = System.nanoTime();
        prepareActionHandlers();
        graphicsContext = canvas.getGraphicsContext2D();

        Kolizje kolizje = new Kolizje();

        Image atak = new Image("Skeleton Attack.gif");
        Image idz = new Image("Skeleton Walk.gif");
        Image stop = new Image("Skeleton Idle.gif");



        Postać gracz = new Postać(1,root,gc);
        Postać gracz2 = new Postać(2,root,gc);


        gracz.setObraz(atak, 0);
        gracz.setObraz(idz, 1);
        gracz.setObraz(stop, 2);
        gracz2.setObraz(atak, 0);
        gracz2.setObraz(idz, 1);
        gracz2.setObraz(stop, 2);

        // Pocisk poc = new Pocisk(1, gracz.obraz, gracz);
        // obiekt.lista.add(poc);

        /**
         * Main "game" loop
         */
        new AnimationTimer() {


            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / (1000000000.0 + k);

                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                graphicsContext.drawImage(tlo, 0, 0, mainScene.getWidth(), mainScene.getHeight());


                t *= 0.5;
                x = 232 + 198 * Math.cos(t + 1000);
                y = 232 + 198 * Math.sin(t + 1000);


                gc.fillText(Integer.toString(gracz.x) + "x " + Integer.toString(gracz.y) + "y", 100, 100);
                gc.fillText(  Double.toString(gracz.wsk.y) + "y", 300, 100);
                gc.fillText(  Double.toString(gracz2.wsk.y) + "y", 300, 140);

              /*  for (Obiekty a : obiekt.lista) {
                    if (a instanceof Animowane)
                        ((Animowane) a).wyswietl(gc, t, obiekt);

                }*/
              obiekt.wyświetlanie();

                sterowanie(gracz,root, gracz2);
                kolizje.pociski_zdezenie();
               kolizje.pociski_łączenie();
               kolizje.pociski_za_daleko();
               kolizje.spark_za_daleko();
               kolizje.trafienie(gracz);
                kolizje.trafienie(gracz2);
               // Circle k= new Circle(gracz2.x+55,gracz2.y+50,40);
                //k.setFill(Color.BLUE);
                //root.getChildren().add(k);
               for(int i=0;i<20;i++)
                {
                   // Spark b=new Spark(100,100,root,obiekt);
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
            gracz.pozycjaUP();
        }
        if (currentlyActiveKeys.contains("DOWN")) {
            // while(currentlyActiveKeys.contains("DOWN"));

            if(!(currentlyActiveKeys.contains("RIGHT")||currentlyActiveKeys.contains("LEFT")))
                gracz.nic();
            gracz.pozycjaDOWN();
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
            gracz.pozycjaDOWN();
        }
        if (currentlyActiveKeys.contains("S")) {
            // while(currentlyActiveKeys.contains("DOWN"));
            if(!(currentlyActiveKeys.contains("A")||currentlyActiveKeys.contains("D")))
                gracz.nic();
            gracz.pozycjaUP();
        }


    }

    private static void sterowanie(Postać gracz,Group root, Postać gracz2) {
        if (!currentlyActiveKeys.isEmpty()) {
            if (currentlyActiveKeys.contains("N")&& k==1 ) {
                k = 0;
                gracz.strzel(Color.BLUE, gracz2);
            }
            if(!currentlyActiveKeys.contains("N")) k=1;
            if (currentlyActiveKeys.contains("M")) {
                gracz.strzel2( Color.ORANGE,gracz2);
            }
            tickAndRender(gracz);
            }
        else {
            gracz.nic();
            k = 1;
        }


        if (!currentlyActiveKeys.isEmpty()) {
            if (currentlyActiveKeys.contains("Q")&&l==1 ) {
                l = 0;
                gracz2.strzel(Color.RED, gracz);
            }
            if(!currentlyActiveKeys.contains("Q")) l=1;
            if (currentlyActiveKeys.contains("E")) {
                gracz2.strzel2( Color.LIGHTYELLOW,gracz);
            }
                tickAndRender2(gracz2);

            }
        else {
            gracz2.nic();
            l = 1;
        }

    }
}
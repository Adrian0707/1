package com.company;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.effect.Effect;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.util.HashSet;

/**
 * klasa glowna zawierajaca glowna petle animacji oraz obsluge klawiszy.
 */
public class Main extends Application{

    private static int l = 0;
    private static int k = 0;
    private static Scene mainScene;
    public static int WIDTH = 1000;
    public static int HEIGHT = 512;
    public volatile static Obiekty obiekt = new Obiekty();
    public volatile static Group root = new Group();
    private static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    public static GraphicsContext gc = canvas.getGraphicsContext2D();
    private static double u = 0;

    public static void main(String[] args){
        launch(args);
    }

    private static HashSet<String> currentlyActiveKeys;

    @Override
    public void start(Stage mainStage){

        mainStage.setTitle("GRA ADRIANA");
        mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.setMinHeight(HEIGHT + 30);
        mainStage.setMinWidth(WIDTH);
        mainStage.setMaxHeight(HEIGHT + 30);
        mainStage.setMaxWidth(WIDTH);

        root.getChildren().add(canvas);

        Image tlo = new Image("cyberpunk-street.png");
        final long startNanoTime = System.nanoTime();
        prepareActionHandlers();
        gc = canvas.getGraphicsContext2D();

        Kolizje kolizje = new Kolizje();

        Image idz = new Image("Skeleton Walk.gif");
        Image stop = new Image("Skeleton Idle.gif");

        Postac gracz = new Postac(1);
        Postac gracz2 = new Postac(2);

        gracz.setObraz(idz, 0);
        gracz.setObraz(stop, 1);

        gracz2.setObraz(idz, 0);
        gracz2.setObraz(stop, 1);
        Pogoda pogoda = new Pogoda();
        Rectangle swiatlo = new Rectangle(0, 0, WIDTH, HEIGHT);
        swiatlo.setFill(Color.gray(0.5));
        swiatlo.setBlendMode(BlendMode.SOFT_LIGHT);

        //swiatlo.setEffect(new javafx.scene.effect.GaussianBlur());
        swiatlo.setOpacity(0.8);
        /**
         * glowna petla animacji
         */
        new AnimationTimer(){

            public void handle(long currentNanoTime){
                double t = (currentNanoTime - startNanoTime) / (1000000000.0 + k);
                root.getChildren().remove(swiatlo );
                gc.drawImage(tlo, 0, 0, mainScene.getWidth(), mainScene.getHeight());
                obiekt.wyswietlanie();
                sterowanie(gracz2, root, gracz);
                kolizje.fizyka(gracz, gracz2);
                pogoda.wyswietl();
                obslugaReset(gracz, gracz2, t);


                root.getChildren().add(swiatlo);
            }

        }.start();

        mainStage.show();
    }

    private static void obslugaReset(Postac gracz, Postac gracz2, double t){
        if (!gracz.getSmierc() && !gracz2.getSmierc())
            u = t;
        if (gracz.getSmierc() || gracz2.getSmierc()){
            gracz.getWsk().setLive(0);
            gracz2.getWsk().setLive(0);
            if (t >= u + 6){
                gc.setFill(Color.color(0.6, 0.8, 0.4, 1 - t * 1000 % 1000 / 1000));
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);
                Font theFont = Font.font("Bank Gothic", FontWeight.BOLD, 48);
                gc.setFont(theFont);
                gc.fillText("Enter aby ponowic", WIDTH / 2 - 200, HEIGHT / 2);

                if (currentlyActiveKeys.contains("ENTER")){
                    gracz.setSmierc(false);
                    gracz.setZdrowie(100);
                    gracz.setMana(100);
                    gracz.setX(200);
                    gracz.setY(380);

                    gracz.getWsk().getElement().setOpacity(0.6);
                    gracz2.setSmierc(false);
                    gracz2.setZdrowie(100);
                    gracz2.setMana(100);
                    gracz2.setX(700);
                    gracz2.setY(380);

                    gracz2.getWsk().getElement().setOpacity(0.6);
                    for (int i = 0; i < obiekt.lista.size(); i++){
                        if (obiekt.lista.get(i) instanceof Krew){
                            Krew a = (Krew)obiekt.lista.get(i);
                            a.setLive(0);

                        }
                        else if (obiekt.lista.get(i) instanceof Czasteczka){
                            Czasteczka b = (Czasteczka)obiekt.lista.get(i);
                            b.setLive(0);
                        }
                        else if (obiekt.lista.get(i) instanceof Pocisk){
                            Pocisk a = (Pocisk)obiekt.lista.get(i);
                            root.getChildren().remove(a.getKula());
                            obiekt.lista.remove(a);

                        }
                    }

                }
            }
        }

    }

    private static void prepareActionHandlers(){
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                currentlyActiveKeys.add(event.getCode().toString());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    private static void tickAndRender(Postac gracz){
        // clear canvas
        //graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        if (currentlyActiveKeys.contains("LEFT")){
            gracz.left();
        }

        if (currentlyActiveKeys.contains("RIGHT")){
            gracz.right();
        }

        if (currentlyActiveKeys.contains("UP")){
            // while (currentlyActiveKeys.contains("UP"));
            if (!(currentlyActiveKeys.contains("RIGHT") || currentlyActiveKeys.contains("LEFT")))
                gracz.nic();
            gracz.pozycjaDOWN();
        }
        if (currentlyActiveKeys.contains("DOWN")){
            // while(currentlyActiveKeys.contains("DOWN"));

            if (!(currentlyActiveKeys.contains("RIGHT") || currentlyActiveKeys.contains("LEFT")))
                gracz.nic();
            gracz.pozycjaUP();
        }

    }

    private static void tickAndRender2(Postac gracz){
        // clear canvas
        //graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);

        if (currentlyActiveKeys.contains("A")){
            gracz.left();
        }

        if (currentlyActiveKeys.contains("D")){
            gracz.right();
        }

        if (currentlyActiveKeys.contains("W")){
            // while (currentlyActiveKeys.contains("UP"));
            if (!(currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("D")))
                gracz.nic();
            gracz.pozycjaUP();
        }
        if (currentlyActiveKeys.contains("S")){
            // while(currentlyActiveKeys.contains("DOWN"));
            if (!(currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("D")))
                gracz.nic();
            gracz.pozycjaDOWN();
        }

    }

    private static void sterowanie(Postac gracz, Group root, Postac gracz2){
        if (!gracz.getSmierc())
            if (!currentlyActiveKeys.isEmpty()){
                if (currentlyActiveKeys.contains("N") && k == 1){
                    k = 0;
                    gracz.strzel(Color.MAGENTA, gracz2);

                }
                if (!currentlyActiveKeys.contains("N"))
                    k = 1;
                if (currentlyActiveKeys.contains("M")){
                    gracz.strzel2(Color.color(0.5, 1, 0.5, 0.5), gracz2);

                }
                if (currentlyActiveKeys.contains("B")){
                    gracz.tarczaUP();

                }
                tickAndRender(gracz);

            }
        if (!(currentlyActiveKeys.contains("B") || currentlyActiveKeys.contains("N") || currentlyActiveKeys.contains("M") || currentlyActiveKeys
                .contains("LEFT") || currentlyActiveKeys.contains("RIGHT")) && !gracz.getSmierc()){
            gracz.odzyskajMane(0.3);
            gracz.nic();
            k = 1;
        }

        if (!gracz2.getSmierc())
            if (!currentlyActiveKeys.isEmpty()){
                if (currentlyActiveKeys.contains("R") && l == 1){
                    l = 0;
                    gracz2.strzel(Color.ORANGE, gracz);
                }
                if (!currentlyActiveKeys.contains("R"))
                    l = 1;
                if (currentlyActiveKeys.contains("T")){
                    gracz2.strzel2(Color.color(1, 0.5, 0.5, 0.5), gracz);

                }
                if (currentlyActiveKeys.contains("Y")){
                    gracz2.tarczaUP();

                }
                tickAndRender2(gracz2);

            }
        if (!(currentlyActiveKeys.contains("R") || currentlyActiveKeys.contains("T") || currentlyActiveKeys.contains("Y") || currentlyActiveKeys
                .contains("A") || currentlyActiveKeys.contains("D")) && !gracz2.getSmierc()){
            gracz2.odzyskajMane(0.3);
            gracz2.nic();
            l = 1;
        }

    }
}
package com.company.reszta;

import com.company.AnimatedImage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import static javafx.application.Application.launch;

public class Main extends Application{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        theStage.setTitle( "KonDzik Game" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 500, 500 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image( "earth.png" );
        Image sun   = new Image( "sun.png" );
        Image space = new Image( "space.png" );
        Image konDzik = new Image("kondzik.png");
        final long startNanoTime = System.nanoTime();
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );

        //ruchome ufo

        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++)
            imageArray[i] = new Image( "ufo_" + i + ".png" );
        ufo.frames = imageArray;
        ufo.duration = 0.100;

        new AnimationTimer()
        {int k=0;
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                k++;
                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( earth, x, y );


                t*=0.5;
                x = 232 + 198 * Math.cos(t+1000);
                y = 232 + 198 * Math.sin(t+1000);
              //  gc.drawImage( ufo, x, y );



                gc.drawImage( ufo.getFrame(t/3), (x/2)+120, y );
                gc.drawImage( sun, 196, 196 );
               // gc.fillText( Double.toString(t), 60, 50 );
            }
        }.start();

        theStage.show();
    }
}

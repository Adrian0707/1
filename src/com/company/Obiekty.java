package com.company;



import java.util.ArrayList;

public class Obiekty {


   public static ArrayList<Obiekty> lista= new ArrayList<>();

   public static  void wyświetlanie(){
      for (Obiekty a : lista) {
         if (a instanceof Animowane)
            ((Animowane) a).wyswietl();

      }
   }
}

package com.company;



import java.util.ArrayList;

public class Obiekty {
boolean usun=false;

   public static ArrayList<Obiekty> lista= new ArrayList<>();

   public static  void wyświetlanie(){
      for (int i=0;i<lista.size();i++) {
         if (lista.get(i) instanceof Animowane)
            ((Animowane) lista.get(i)).wyswietl();
      }
   }
   public static void oczysc(){
      for(int i =0; i<lista.size();i++){
         if(lista.get(i).usun){
            lista.remove(i);

         }
      }
   }
}

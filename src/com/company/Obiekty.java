package com.company;

import java.util.ArrayList;

/**
 * klasa odpowiedzialna za przechowywanie wszystkich obiektow bedadncych na ekranie w postaci listy
 */
public class Obiekty{
    /**
     * lista obiektow na ekranie
     */
    public static ArrayList<Obiekty> lista = new ArrayList<>();

    /**
     * funkcja odpowiedzialna za animowanie obiektow na ekranie
     */
    public static void wyswietlanie(){
        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i) instanceof Animowane)
                ((Animowane)lista.get(i)).wyswietl();
        }
    }

}

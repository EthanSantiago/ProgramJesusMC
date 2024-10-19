/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programjesusmc.Contadordecajas;



 public class Contador {
    private int total;
    private int meta;

    public Contador() {
        this.total = 0;
        this.meta = 0;
    }

    public void agregarPieza() {
        total++;
    }

    public void quitarPieza() {
        if (total > 0) {
            total--;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public int calcularPorcentaje() {
        if (meta == 0) {
            return 0; // Evitar división por cero
        }
        return (int) ((double) total / meta * 100);
    }

    // Método para reiniciar el contador
    public void resetear() {
        total = 0; // Reinicia el total de cajas
        meta = 0; // Opcional: reinicia la meta también
        }
 }


    


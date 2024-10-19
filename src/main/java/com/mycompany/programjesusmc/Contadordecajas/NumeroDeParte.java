/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programjesusmc.Contadordecajas;


public class NumeroDeParte extends Piezas {
    private String descripcion;

    public NumeroDeParte(String nombre, String descripcion) {
        super(nombre);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
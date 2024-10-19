/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programjesusmc.Contadordecajas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContadorDePiezasGUI extends JFrame {
    private JLabel labelTotal, labelPorcentaje, labelNotificacion; // Etiquetas
    private JTextField txtMeta, txtNumeroParte, txtLote, txtFecha; // Campos de entrada
    private JTextField txtAgregarCantidad; // Campo para ingresar cantidad manual
    private JTextArea txtHistorial; // Área de texto para el historial
    private Contador contador;
    private String horaInicio; // Hora de inicio
    private String horaFin; // Hora de finalización

    public ContadorDePiezasGUI() {
        // Configuración de la ventana
        setTitle("Contador de Cajas");
        setSize(500, 500); // Aumentar tamaño para incluir más campos
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear instancia del contador
        contador = new Contador();

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 1)); // Cambiado a 11 para incluir más campos

        // Campo para ingresar el número de parte
        txtNumeroParte = new JTextField();
        txtNumeroParte.setHorizontalAlignment(JTextField.CENTER);
        txtNumeroParte.setBorder(BorderFactory.createTitledBorder("Número de Parte"));
        panel.add(txtNumeroParte);

        // Campo para ingresar el lote
        txtLote = new JTextField();
        txtLote.setHorizontalAlignment(JTextField.CENTER);
        txtLote.setBorder(BorderFactory.createTitledBorder("Lote"));
        panel.add(txtLote);

        // Campo para ingresar la fecha
        txtFecha = new JTextField();
        txtFecha.setHorizontalAlignment(JTextField.CENTER);
        txtFecha.setBorder(BorderFactory.createTitledBorder("Fecha (DD/MM/AAAA)"));
        panel.add(txtFecha);

        // Etiqueta para mostrar el total de cajas
        labelTotal = new JLabel("Total de cajas: 0", SwingConstants.CENTER);
        panel.add(labelTotal);

        // Campo para ingresar la meta diaria
        txtMeta = new JTextField();
        txtMeta.setHorizontalAlignment(JTextField.CENTER);
        txtMeta.setBorder(BorderFactory.createTitledBorder("Meta diaria"));
        panel.add(txtMeta);

        // Etiqueta para mostrar el porcentaje alcanzado
        labelPorcentaje = new JLabel("Progreso: 0%", SwingConstants.CENTER);
        panel.add(labelPorcentaje);

        // Campo para ingresar cantidad de cajas manualmente
        txtAgregarCantidad = new JTextField();
        txtAgregarCantidad.setHorizontalAlignment(JTextField.CENTER);
        txtAgregarCantidad.setBorder(BorderFactory.createTitledBorder("Agregar cantidad"));
        panel.add(txtAgregarCantidad);

        // Nueva etiqueta para notificaciones
        labelNotificacion = new JLabel("", SwingConstants.CENTER);
        panel.add(labelNotificacion);

        // Botón para iniciar el conteo
        JButton btnIniciar = new JButton("Iniciar Conteo");
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                horaInicio = new SimpleDateFormat("HH:mm:ss").format(new Date()); // Obtener hora de inicio
                mostrarNotificacion("Conteo iniciado a las " + horaInicio);
            }
        });
        panel.add(btnIniciar);

        // Botón para agregar cajas manualmente
        JButton btnAgregarManual = new JButton("Agregar Cajas Manualmente");
        btnAgregarManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cantidad = Integer.parseInt(txtAgregarCantidad.getText());
                    if (cantidad < 0) {
                        throw new NumberFormatException(); // Lanza una excepción para números negativos
                    }
                    for (int i = 0; i < cantidad; i++) {
                        contador.agregarPieza();
                        agregarAlHistorial("Agregada 1 caja manualmente.");
                    }
                    actualizarEstado();
                    verificarMeta();
                } catch (NumberFormatException ex) {
                    mostrarNotificacion("Por favor, ingrese una cantidad válida.");
                }
            }
        });
        panel.add(btnAgregarManual);

        // Botón para agregar una caja
        JButton btnAgregar = new JButton("Agregar Caja");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador.agregarPieza();
                agregarAlHistorial("Agregada 1 caja.");
                actualizarEstado();
                verificarMeta();
            }
        });
        panel.add(btnAgregar);

        // Botón para quitar caja
        JButton btnQuitar = new JButton("Quitar Caja");
        btnQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contador.quitarPieza();
                agregarAlHistorial("Quitada 1 caja.");
                actualizarEstado();
            }
        });
        panel.add(btnQuitar);

        // Botón para limpiar resultados
        JButton btnLimpiar = new JButton("Limpiar Resultados");
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarResultados();
            }
        });
        panel.add(btnLimpiar); // Añadir el botón al panel

        // Botón para exportar resultados
        JButton btnExportar = new JButton("Exportar Resultados");
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarResultados();
            }
        });
        panel.add(btnExportar); // Añadir el botón de exportación

        // Área de texto para el historial de cambios
        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setBorder(BorderFactory.createTitledBorder("Historial"));
        panel.add(new JScrollPane(txtHistorial)); // Añadir la barra de desplazamiento

        // Añadir el panel a la ventana
        add(panel);
    }

    // Método para limpiar los resultados
    private void limpiarResultados() {
        contador.resetear(); // Método para reiniciar el contador
        labelTotal.setText("Total de cajas: 0");
        labelPorcentaje.setText("Progreso: 0%");
        txtMeta.setText("");
        txtAgregarCantidad.setText("");
        txtNumeroParte.setText(""); // Limpiar el número de parte
        txtLote.setText(""); // Limpiar el lote
        txtFecha.setText(""); // Limpiar la fecha
        txtHistorial.setText(""); // Limpiar el historial
        labelNotificacion.setText(""); // Limpiar la notificación
        horaInicio = null; // Resetear hora de inicio
        horaFin = null; // Resetear hora de finalización
    }

    // Método para actualizar el total y el porcentaje alcanzado
    private void actualizarEstado() {
        try {
            int meta = Integer.parseInt(txtMeta.getText());
            contador.setMeta(meta);
        } catch (NumberFormatException e) {
            mostrarNotificacion("Por favor, ingrese una meta válida.");
            return;
        }

        labelTotal.setText("Total de cajas: " + contador.getTotal());
        labelPorcentaje.setText("Progreso: " + contador.calcularPorcentaje() + "%");
    }

    // Método para agregar al historial
    private void agregarAlHistorial(String mensaje) {
        txtHistorial.append(mensaje + "\n");
    }

    // Método para mostrar notificaciones
    private void mostrarNotificacion(String mensaje) {
        labelNotificacion.setText(mensaje);
    }

    // Método para verificar si se ha alcanzado o superado la meta
    private void verificarMeta() {
        if (contador.calcularPorcentaje() >= 100) {
            horaFin = new SimpleDateFormat("HH:mm:ss").format(new Date()); // Obtener hora de fin
            mostrarNotificacion("¡Meta alcanzada o superada! Conteo finalizado a las " + horaFin);
        }
    }

    // Método para exportar resultados a un archivo
    private void exportarResultados() {
        // Abre un diálogo para seleccionar la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Resultados");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("Historial de Cajas:\n");
                writer.write(txtHistorial.getText());
                writer.write("\nTotal de Cajas: " + contador.getTotal() + "\n");
                writer.write("Meta Diaria: " + txtMeta.getText() + "\n");
                writer.write("Número de Parte: " + txtNumeroParte.getText() + "\n");
                writer.write("Lote: " + txtLote.getText() + "\n");
                writer.write("Fecha: " + txtFecha.getText() + "\n");
                writer.write("Hora de Inicio: " + horaInicio + "\n");
                writer.write("Hora de Finalización: " + horaFin + "\n");
                writer.write("Progreso: " + contador.calcularPorcentaje() + "%\n");
                mostrarNotificacion("Resultados exportados exitosamente a " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                mostrarNotificacion("Error al exportar resultados: " + e.getMessage());
            }
        }
    }

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ContadorDePiezasGUI().setVisible(true);
            }
        });
    }
}

   




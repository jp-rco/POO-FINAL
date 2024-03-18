package negocio;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JuanPa
 */
public class Producto {
     private String id;
    private String nombreProducto;
    private String fecha;
    private String numeroProducto;
    private String label1;
    private String label2;
    public Producto(String id, String nombreProducto, String fecha, String numeroProducto, String label1, String label2) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.fecha = fecha;
        this.numeroProducto = numeroProducto;
        this.label1 = label1;
        this.label2 = label2;
    }

    public Producto(String numeroProducto, String id, String nombreProducto, String fecha, float nuevoValor, float Label2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumeroProducto() {
        return numeroProducto;
    }

    public void setNumeroProducto(String numeroProducto) {
        this.numeroProducto = numeroProducto;
    }

    @Override
    public String toString() {
        return id + "," + nombreProducto +"," + fecha +","+ numeroProducto + "," + label1 + "," + label2;
    }
    
}

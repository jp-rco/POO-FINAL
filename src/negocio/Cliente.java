/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author Lenovo
 */
public class Cliente 
{
    private String idCliente;
    private String nombre;
    private String foto;
    private String clave;
    private char genero;
    private boolean productos[];//cuenta de ahorros, cuenta corriente, cdt, tarjeta visa, tarjeta american

    public Cliente(String idCliente, String nombre, String foto, String clave, char genero, boolean[] productos) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.foto = foto;
        this.clave = clave;
        this.genero = genero;
        this.productos = productos;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public String getClave() {
        return clave;
    }

    public char getGenero() {
        return genero;
    }

    public boolean[] getProductos() {
        return productos;
    }
    
        public void setProductos(boolean[] productos) {
        this.productos = productos;

        }
    

    @Override
    public String toString() {
        
        String produs= "";
        for (int i = 0; i < this.productos.length; i++) 
        {
            if(i!=this.productos.length-1)
            {
                produs+=this.productos[i]+",";
            }
            else {
                produs+=this.productos[i];
            }
            
        }
        return this.idCliente + "," + this.nombre + "," + this.foto + "," + this.clave + "," + this.genero +","+ produs;
        
    }
}

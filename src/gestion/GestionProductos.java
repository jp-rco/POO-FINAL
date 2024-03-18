/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;
import negocio.Cliente;
import negocio.Producto;

/**
 *
 * @author JuanPa
 */
public class GestionProductos {
    private String ruta;
    public String rutaClientes;

    public GestionProductos()
    {
        this.ruta = "./Archivos/misProductos.txt";
        this.rutaClientes = "./Archivos/misClientes.txt";
        this.verificaArchivo();
    }
    public void verificaArchivo()
    {
        try {
            File filex = new File(this.ruta);
            if (!filex.exists())
            {
                filex.createNewFile();
            }
        }
        catch (IOException ex)
        {
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Problemas con la ruta...");
            msg.showAndWait();
        }
    }
    public boolean nuevoProducto(String id, String nombre, String fecha, String num, String label1, String label2)
    {
        boolean val = true, val2;
        int numero;
        String numero2;
        if("".equals(label2))
        {
            label2 = "0";
        }
        
        Producto med = new Producto(id, nombre, num, fecha, label1, label2);
        this.guardaProduct(med);
        return val;
    }
    private void guardaProduct(Producto med)
    {
        try {
            File file = new File(this.ruta);
            FileWriter fr = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fr);
            pw.println(med);
            pw.close();
        }
        catch (IOException ex)
        {
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("no se pudo crear el producto correctamente");
            msg.showAndWait();
        }
    }
    public boolean verificaCodigo(String codigo)
    {
        boolean existe = false;
        FileReader file;
        BufferedReader br;
        String registro;

        try {
            file = new FileReader(this.ruta);
            br = new BufferedReader(file);
            while ((registro = br.readLine()) != null)
            {
                String [] tokens = registro.split(",");
                if (tokens[1].equals(codigo))
                {
                    existe = true;
                    break;
                }
            }
        }
        catch (IOException ex)
        {
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("Fallo buscando producto");
            msg.showAndWait();
        }
        return existe;
    }
        public ArrayList<Cliente> getTodos() {
        ArrayList<Cliente> clientes = new ArrayList();
        FileReader file;
        BufferedReader br;
        String registro;
        try 
        {
            file = new FileReader(rutaClientes);
            br = new BufferedReader(file);
            while ((registro = br.readLine()) != null) 
            {
                String[] campos = registro.split(",");
                    boolean productos[] = new boolean[5];
                    for(int i=5; i<=9;i++)
                    {
                        productos[i-5]= Boolean.parseBoolean(campos[i]);
                    }
                    Cliente cliente = new Cliente(campos[0], campos[1], campos[2], campos[3], campos[4].charAt(0), productos);
                    clientes.add(cliente);
                }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }
    public String Buscar(String id){
        String ide, registro;
        registro = "";
        ide = id;
        boolean existe = false;
        ArrayList<Cliente> cliente = this.getTodos();
        for(Cliente med: cliente)
        {
            if (med.getIdCliente().equals(ide))
            {
                registro = String.valueOf(med);
                existe = true;
            }
        }
        if (!existe)
        {
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("No hay cliente con esa identificacion");
            msg.showAndWait();
        }
        return registro;
    }
    public ArrayList<Producto> getTodos2(){
        FileReader file;
        BufferedReader br;
        String registro;
        Producto stud = null;
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            file = new FileReader(this.ruta);
            br = new BufferedReader(file);
            while ((registro = br.readLine()) != null)
            {
                String [] tokens = registro.split(",");
                stud =new Producto(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                productos.add(stud);
            }
        }
        catch (IOException ex)
        {
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("Fallo buscando producto");
            msg.showAndWait();
        }
        return productos;
    }
    public String BuscarProduct(String id, String producto){
        String ide, registro;
        registro = "";
        ide = id;
        boolean existe = false;
        ArrayList<Producto> productos = this.getTodos2();
        for(Producto med: productos)
        {
            if (med.getId().equals(id) && med.getNombreProducto().equals(producto))
            {
                registro = String.valueOf(med);
                existe = true;
            }
        }
        if (!existe)
        {
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("Porfavor crear el producto");
            msg.showAndWait();
        }
        return registro;
    }
    public void modificarProduct(String numeroProducto, String fecha, String label1, String label2)
    {
        ArrayList<Producto> productos = this.getTodos2();
        String  m, t, e;
        m = fecha;
        t = label1;
        e = label2;
        if("".equals(e))
        {
            e="0";
        }
        for(Producto lista: productos)
        {
            String numero = lista.getNumeroProducto();
            if (numero.equals(numeroProducto))
            {
                lista.setFecha(m);
                lista.setLabel1(t);
                lista.setLabel2(e);
                this.recargaArchivo(productos);
                Alert msg =new Alert(Alert.AlertType.INFORMATION);
                msg.setHeaderText(null);
                msg.setContentText("El producto ha sido modificado con éxito");
                msg.showAndWait(); 
                break;
            }
        }
    }
    public void recargaArchivo(ArrayList<Producto> productos)
    {
        try {
            File file = new File(this.ruta);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter pw = new PrintWriter(fr);
            for (Producto lista: productos)
                pw.println(lista);
            pw.close();
        }
        catch (IOException ex)
        {
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("no se pudo cargar el producto");
            msg.showAndWait();
        }
    }
    public void remplazaArchivo(ArrayList<Cliente> clientes) {
        try {
            File file = new File(rutaClientes);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter ps = new PrintWriter(fr);
            for (Cliente cliente : clientes) {
                ps.println(cliente);
            }
            ps.close();
        } catch (IOException ioe) {
            System.out.println("Fallando recargando productos en el archivo...!!");
            System.exit(1);
        }
    }
    public boolean deleteProduct(String numero) {
        ArrayList<Producto> productos = this.getTodos2();
        boolean val = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de confirmar la acción?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            for(Producto prod: productos)
            {
                if (prod.getNumeroProducto().equals(numero))
                {
                    productos.remove(prod);
                    Alert msg =new Alert(Alert.AlertType.INFORMATION);
                    msg.setHeaderText(null);
                    msg.setContentText("se elimino correctamente");
                    msg.showAndWait();
                    val = true;
                    break;
                }
        }
        this.recargaArchivo(productos);
        } 
        else 
        {
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("No se elimino el producto");
            msg.showAndWait();
            val = false;
        }
        return val;
    }
    
}

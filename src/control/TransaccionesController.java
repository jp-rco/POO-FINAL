/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class TransaccionesController implements Initializable {

    @FXML
    private Button btn_retiro;
    @FXML
    private Button btn_avance;
    @FXML
    private Button btn_saldo;
    @FXML
    private Button btn_pagos;
    @FXML
    private Button btn_deposito;
    @FXML
    private Button btn_compra;
    @FXML
    private Button btn_cambioClave;
    @FXML
    private Button btn_salir;
    @FXML
    private Label label_nombre;
    @FXML
    private ComboBox<?> cbx_producto;
    
     private Cliente cliente;
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private GestionProductos gestionP;
    private GestionClientes gestionC;
    private String tipo;
    private Producto producto;
    private Producto productoNuevo;
    



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gestionP = new GestionProductos();
        this.gestionC = new GestionClientes();


    }    

    @FXML
    private void do_retiro(ActionEvent event) 
    {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
         float nuevoValor;
        
        this.buscaProducto(this.tipo);
        String valor = this.showInput("Por favor, digite la cantidad en miles");
        
        boolean vacio = this.valorVacio(valor);
        
        
        if (!vacio){
            try{
            Float retiro = Float.valueOf(valor);
            if(retiro > Label1){
                this.showmessage("Transaccion rechazada, fondo insuficientes!!!", 1);
            }
            
            nuevoValor = Label1-retiro;
            this.productoNuevo = new Producto(this.producto.getNumeroProducto(),this.producto.getId(), this.producto.getNombreProducto(), this.producto.getFecha(), nuevoValor, Label2);
            
            this.traeProductos();
            Iterator<Producto> iter = this.productos.iterator();
            int pos = -1;   
            
                while(iter.hasNext()){
                    pos++;
                    if(iter.next().getNumeroProducto().equals(this.producto.getNumeroProducto())){
                    iter.remove();
                    this.productos.add(pos,productoNuevo);
                    break;
                    }
                }
                this.gestionP.recargaArchivo(this.productos);
                this.showmessage("Transaccion aprobada, retire su dinero!!!", 2);
            }catch(NumberFormatException e){
                this.showmessage("Porfavor ingrese un valor numerico", 1);
            }
        }
    }

    @FXML
    private void do_avance(ActionEvent event) 
    {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
        float nuevoValor;
        
        this.buscaProducto(this.tipo);
        String valor = this.showInput("Por favor, digite la cantidad en millones");
        boolean vacio = this.valorVacio(valor);
        
        if(!vacio){
            try{
                float retiro = Float.parseFloat(valor);
                if(retiro > Label1){
                    this.showmessage("Transaccion rechazada, cupo insuficiente!!!", 1);
                }else{
                    nuevoValor = Label1-retiro;
                    this.productoNuevo = new Producto(this.producto.getNumeroProducto(),this.producto.getId(), this.producto.getNombreProducto(), this.producto.getFecha(), nuevoValor, Label2);
                
                    this.traeProductos();
                    Iterator<Producto> iter = this.productos.iterator();
                    int pos = -1;   
                
                    while(iter.hasNext()){
                        pos++;
                        if(iter.next().getNumeroProducto().equals(this.producto.getNumeroProducto())){
                        iter.remove();
                        this.productos.add(pos,productoNuevo);
                        break;
                        }
                    }
                    this.gestionP.recargaArchivo(this.productos);
                    this.showmessage("Transaccion aprobada, retire su dinero!!!", 2);
                }
            }catch (NumberFormatException e){
                this.showmessage("Porfavor ingrese un valor numerico", 1);
            }
        }
    }

    @FXML
    private void do_saldo(ActionEvent event) {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
        String mensaje = "";
        if((this.tipo.equals("Cuenta ahorros")) || (this.tipo.equals("Cuenta corriente"))){
            this.buscaProducto(this.tipo);
            mensaje += "Producto: "+this.producto.getNumeroProducto()+"\n";
            mensaje += "Saldo actual: $"+Label1+" mil pesos";
            this.showmessage(mensaje, 2);
        }else if((this.tipo.equals("Tarjeta visa")) || (this.tipo.equals("Tarjeta American"))){
            this.buscaProducto(this.tipo);
            mensaje += "Producto: "+this.producto.getNumeroProducto()+"\n";
            mensaje += "Saldo actual: $"+Label1+" millones de pesos";
            this.showmessage(mensaje, 2);
        }
    }

    @FXML
    private void do_pagos(ActionEvent event) {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
        float nuevoValor;
        
        this.buscaProducto(this.tipo);
        String valor = this.showInput("Por favor, digite la cantidad en millones");
        boolean vacio = this.valorVacio(valor);
        
        if(!vacio){
            try{
                Float deposito = Float.valueOf(valor);
                nuevoValor = Label1+deposito;
                this.productoNuevo = new Producto(this.producto.getNumeroProducto(),this.producto.getId(), this.producto.getNombreProducto(), this.producto.getFecha(), nuevoValor, Label2);
            
                this.traeProductos();
                Iterator<Producto> iter = this.productos.iterator();
                int pos = -1;   
            
                while(iter.hasNext()){
                    pos++;
                    if(iter.next().getNumeroProducto().equals(this.producto.getNumeroProducto())){
                    iter.remove();
                    this.productos.add(pos,productoNuevo);
                    break;
                    }
                }
                this.gestionP.recargaArchivo(this.productos);
                this.showmessage("Pago realizado con exito!!!", 2);
            }catch (NumberFormatException e){
                this.showmessage("Porfavor ingrese un valor numerico", 1);
            }
        }
    }

    @FXML
    private void do_deposito(ActionEvent event) 
    {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
        float nuevoValor;
        
        this.buscaProducto(this.tipo);
        String valor = this.showInput("Por favor, digite la cantidad en miles");
        boolean vacio = this.valorVacio(valor);
        
        if(!vacio){
            try{
                float deposito = Float.parseFloat(valor);
                nuevoValor = Label1+deposito;
                this.productoNuevo = new Producto(this.producto.getNumeroProducto(),this.producto.getId(), this.producto.getNombreProducto(), this.producto.getFecha(), nuevoValor, Label2);
                
                this.traeProductos();
                Iterator<Producto> iter = this.productos.iterator();
                int pos = -1;   
            
                while(iter.hasNext()){
                    pos++;
                    if(iter.next().getNumeroProducto().equals(this.producto.getNumeroProducto())){
                    iter.remove();
                    this.productos.add(pos,productoNuevo);
                    break;
                    }
                }
                this.gestionP.recargaArchivo(this.productos);
                this.showmessage("Transaccion aprobada!!!", 2);
            }catch (NumberFormatException e){
                this.showmessage("Porfavor ingrese un valor numerico", 1);
            }
        }
    
    }

    @FXML
    private void do_compra(ActionEvent event) 
    {
        float Label1 = Float.parseFloat(this.producto.getLabel1());
        float Label2 = Float.parseFloat(this.producto.getLabel2());
              float nuevoValor;
        
        this.buscaProducto(this.tipo);
        String valor = this.showInput("Porfavor digite le valor de la compra en millones");
        boolean vacio = this.valorVacio(valor);
        
        if(!vacio){
            try{
                float retiro = Float.parseFloat(valor);
                if(retiro > Label1){
                    this.showmessage("Transaccion rechazada, cupo insuficiente!!!", 1);
                }else{
                    nuevoValor = Float.parseFloat(this.producto.getLabel1())-retiro;
                    this.productoNuevo = new Producto(this.producto.getNumeroProducto(),this.producto.getId(), this.producto.getNombreProducto(), this.producto.getFecha(), nuevoValor, Label2);
            
                    this.traeProductos();
                    Iterator<Producto> iter = this.productos.iterator();
                    int pos = -1;   
                
                    while(iter.hasNext()){
                        pos++;
                        if(iter.next().getNumeroProducto().equals(this.producto.getNumeroProducto())){
                        iter.remove();
                        this.productos.add(pos,productoNuevo);
                        break;
                        }
                    }
                    this.gestionP.recargaArchivo(this.productos);
                    this.showmessage("Compra aprobada!!", 2);
                }
            }catch (NumberFormatException e){
                this.showmessage("Porfavor ingrese un valor numerico", 1);
            }
        }
    }

    @FXML
    private void do_cambioClave(ActionEvent event) 
    {
        this.clientes = this.gestionC.getTodos();
        String nuevaClave = this.showInput("Digite la nueva clave");
        this.cliente = new Cliente(this.cliente.getIdCliente(), this.cliente.getNombre(), this.cliente.getFoto(), this.cliente.getClave(), this.cliente.getGenero(), this.cliente.getProductos());
        
        if(this.gestionC.pruebaExistenciaClave(nuevaClave)){
            this.showmessage("Esa clave ya existe, porfavor digitar otra!!!!", 1);
        }else if (!nuevaClave.isEmpty()){
            Iterator<Cliente> iter = this.clientes.iterator();
            int pos = -1;                
                while(iter.hasNext()){
                    pos++;
                    if(iter.next().getIdCliente().equals(this.cliente.getIdCliente())){
                    iter.remove();
                    this.clientes.add(pos,this.cliente);
                    break;
                    }
                }
                this.gestionC.remplazaArchivo(this.clientes);
                this.showmessage("Clave modificado con exito!!!", 2);
        }else{
            this.showmessage("Porfavor digite una clave para hacer la respectiva modificacion",1);
        }
    }

    @FXML
    private void do_salir(ActionEvent event)
    {
                try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
//            VistaTodosController ouControlador = loader.getController(); //Solo si enviamos informacion desde la primera ventana
//            ouControlador.setClientes(this.clientes); //Recibiria los parametros
                        
            Stage stage = new Stage();
            stage.setOnCloseRequest(even -> {even.consume();}); //Deshabilita la X para cerrar 
            stage.setResizable(false);
            stage.setTitle("INICIO");
            stage.initModality(Modality.WINDOW_MODAL); //PARA NO CERRAR LA VENTANA ANTERIOR
            stage.setScene(scene);
            stage.show();
            
            Stage myStage = (Stage) this.btn_salir.getScene().getWindow();
            myStage.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Fallo cambiando de ventana!!");
        }
    }

    @FXML
    private void do_producto(ActionEvent event)
    {
        this.tipoProducto();
        this.reiniciaMenu();
        
        if((this.tipo.equals("Cuenta ahorros")) || (this.tipo.equals("Cuenta corriente"))){
            this.btn_retiro.setDisable(false);
            this.btn_saldo.setDisable(false);
            this.btn_deposito.setDisable(false);
        }else if((this.tipo.equals("Tarjeta visa")) || (this.tipo.equals("Tarjeta American"))){
            this.btn_avance.setDisable(false);
            this.btn_saldo.setDisable(false);
            this.btn_pagos.setDisable(false);
            this.btn_compra.setDisable(false);
        }
    }
    
    //========================================
        public void setCliente(Cliente cliente){
        
        this.cliente = cliente;
        this.llenaCombos();
    }
    
private void llenaCombos() {
        String registro;
        ArrayList<Cliente> clientes= this.gestionP.getTodos();
        for(Cliente cli: clientes)
        {

        }
}

    
    private void traeProductos(){
        this.productos = this.gestionP.getTodos2();
    }
    
    private void buscaProducto(String tipo){
        for(Producto producto: this.productos){
            if((producto.getNumeroProducto().equals(this.cliente.getIdCliente())) && (producto.getNombreProducto().equals(tipo))){
                this.producto = producto;
            }
        }
    }
    
    private void reiniciaMenu(){
        this.btn_retiro.setDisable(true);
        this.btn_avance.setDisable(true);
        this.btn_saldo.setDisable(true);
        this.btn_pagos.setDisable(true);
        this.btn_deposito.setDisable(true);
        this.btn_compra.setDisable(true);
    }
    
  private void tipoProducto() {
    int index = this.cbx_producto.getSelectionModel().getSelectedIndex();
    String selectedProduct = (String) this.cbx_producto.getItems().get(index);
    System.out.println("Selected Product: " + selectedProduct); // Agrega esta línea para depurar

    switch (selectedProduct) {
        case "Cuenta ahorros":
            this.tipo = "Cuenta ahorros";
            break;
        case "Cuenta corriente":
            this.tipo = "Cuenta corriente";
            break;
        case "Tarjeta visa":
            this.tipo = "Tarjeta visa";
            break;
        case "Tarjeta American":
            this.tipo = "Tarjeta American";
            break;
        case "CDT":
            this.tipo = "CDT";
            break;
        default:
            break;
    }
}

    
    private boolean showmessage(String mesg, int tipo){
        Alert msg;
        boolean ok = false;
        
        if(tipo == 1){//Tipo ERROR
            msg = new Alert (Alert.AlertType.ERROR);
            msg.setTitle("ERROR");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(tipo == 2){//Tipo NOTIFICACION
            msg = new Alert (Alert.AlertType.INFORMATION);
            msg.setTitle("Listo");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(tipo == 3){//Tipo CONFIRMACION
            msg = new Alert (Alert.AlertType.CONFIRMATION);
            msg.setTitle("Peticion eliminacion");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK)
                ok = true;
        }
        return ok;
    } 
    
    private boolean valorVacio(String valor){
        boolean vacio = false;
        if (valor.isEmpty()){
            this.showmessage("Porfavor ingrese un valor!!", 1);
            vacio = true;
        }
        return vacio;   
    }
    
    private String showInput(String mensaje) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(null);
        inputDialog.setHeaderText(mensaje);
        inputDialog.setContentText("Ingrese su respuesta:");

        Optional<String> result = inputDialog.showAndWait();

        return result.orElse(null);
    }
}

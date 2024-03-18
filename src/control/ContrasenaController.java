/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ContrasenaController implements Initializable {

    @FXML
     PasswordField txt_contra;
    @FXML
    private Button btn_regresar;
    @FXML
    private Button btn_listo;
    private ArrayList<Cliente> clientes;
    private GestionClientes gestion;
    private Cliente cliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.gestion = new GestionClientes ();
    }    

    @FXML
    private void do_regresar(ActionEvent event) 
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
            
            Stage myStage = (Stage) this.btn_regresar.getScene().getWindow();
            myStage.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Fallo cambiando de ventana!!");
        }
    }

    @FXML
    private void do_listo(ActionEvent event) 
    {
                this.clientes = this.gestion.getTodos();
        String clave = this.txt_contra.getText();
        boolean existe = false;
        
        if(!clave.isEmpty()){        
            for(Cliente cliente: clientes){
                if(cliente.getClave().equals(clave)){
                    this.showmessage("Bienvenido "+cliente.getNombre(), 2);
                    existe = true;
                    this.cliente = cliente;
                    break;
                }
            }
            this.go_transacciones(existe); 
        }else{
            this.showmessage("Porfavor digite una clave!!!", 1);
            this.txt_contra.requestFocus();
        }
    }
    //=================================
    
        private void go_transacciones(boolean existe){
        
        if(existe){
            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Transacciones.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            TransaccionesController ouControlador = loader.getController(); //Solo si enviamos informacion desde la primera ventana
            ouControlador.setCliente(this.cliente); //Recibiria los parametros
                        
            Stage stage = new Stage();
            stage.setOnCloseRequest(even -> {even.consume();}); //Deshabilita la X para cerrar 
            stage.setResizable(false);
            stage.setTitle("TRANSACCIONES");
            stage.initModality(Modality.WINDOW_MODAL); //PARA NO CERRAR LA VENTANA ANTERIOR
            stage.setScene(scene);
            stage.show();
            
            Stage myStage = (Stage) this.btn_listo.getScene().getWindow();
            myStage.close();
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Fallo cambiando de ventana!!");
        }
        }else{
            this.showmessage("No se encuentra un cliente con esa clave, vuelva a digitar!!!", 1);
            this.txt_contra.clear();
            this.txt_contra.requestFocus();
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

}

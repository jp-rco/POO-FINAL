/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class PrincipalController implements Initializable {

    @FXML
    private ImageView btn_clientes;
    @FXML
    private ImageView btn_productos;
    @FXML
    private ImageView btn_trans;
    @FXML
    private ImageView btn_salida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ir_clientes(MouseEvent event)
    {
            try
            { 
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Clientes.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                 //Ventana2Controller ouControlador = loader.getController();
                //ouControlador.constructorNuevo(); //metodo que se invento el profe para inicializar y mandarle cosas
                

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("Ventana Clientes");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();


              } 
            catch (IOException ex)
            {
               Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void ir_productos(MouseEvent event) 
    {
           try
            { 
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Productos.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                ProductosController ouControlador = loader.getController();
                

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("Ventana productos");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();


              } 
            catch (IOException ex)
            {
               Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }  
    }

    @FXML
    private void ir_trans(MouseEvent event) 
    {
           try
            { 
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Contrasena.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                ContrasenaController ouControlador = loader.getController();
                

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("Ventana contraseña");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();


              } 
            catch (IOException ex)
            {
               Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }  
    }

    @FXML
    private void doSalir(MouseEvent event) 
    {
        Stage stage = (Stage) this.btn_salida.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ProductosController implements Initializable {

    @FXML
    private Button btnVerTodos;
    @FXML
    private Button btnRegresar;
    private GestionProductos gesPro;
    @FXML
    private ComboBox<String> cbxProductos;
    @FXML
    private TextField txfIdentificacion;
    @FXML
    private Button btnCrear;
    @FXML
    private DatePicker btnFecha;
    @FXML
    private TextField txfNumProd;
    @FXML
    private TextField txfLabel1;
    @FXML
    private TextField txfLabe2;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminiar;
    private String ide;
    @FXML
    private Button btn_VerClientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gesPro = new GestionProductos();
        // TODO
    }    

    @FXML
    private void irTodos(ActionEvent event) {
        try
        {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/allProductos.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                //Ventana2Controller ouControlador = loader.getController();
                //ouControlador.constructorNuevo(); //metodo que se invento el profe para inicializar y mandarle cosas

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("MANEJO DE CLIENTES");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btnVerTodos.getScene().getWindow();
                myStage.close();
              } 
            catch (IOException ex)
            {
                Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void doRegresar(ActionEvent event) {
        try
        {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/principal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                //Ventana2Controller ouControlador = loader.getController();
                //ouControlador.constructorNuevo(); //metodo que se invento el profe para inicializar y mandarle cosas

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("MANEJO DE CLIENTES");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btnRegresar.getScene().getWindow();
                myStage.close();
              } 
            catch (IOException ex)
            {
                Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void doBuscar(MouseEvent event) {
        String registro;
        registro = this.gesPro.Buscar(this.txfIdentificacion.getText());
        if(registro.length()>0)
        {
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Despliegue la lista para ver los productos de este cliente");
            msg.showAndWait();
            this.llenaComboProductos();
        }
    }
    private void llenaComboProductos(){
        String registro;
        this.cbxProductos.getItems().clear();
        ArrayList<Cliente> lista = this.gesPro.getTodos();
        for (Cliente esp:lista)
        {
            registro = String.valueOf(esp);
            String [] tokens = registro.split(",");
            if(esp.getIdCliente().equals(this.txfIdentificacion.getText()))
            {
                if("true".equals(tokens[5]))
                {
                    this.cbxProductos.getItems().add("Cuenta de ahorros");
                }
                if("true".equals(tokens[6]))
                {
                    this.cbxProductos.getItems().add("Cuenta corriente");
                }
                if("true".equals(tokens[7]))
                {
                    this.cbxProductos.getItems().add("CDT");
                }
                if("true".equals(tokens[8]))
                {
                    this.cbxProductos.getItems().add("Tarjeta Visa");
                }
                if("true".equals(tokens[9]))
                {
                    this.cbxProductos.getItems().add("Tarjeta American");  
                }
            }
        }
    }

    @FXML
    private void doSelectionProduct(ActionEvent event) {
      String registro, numero2;
      int numero;
      boolean val;
      registro = this.gesPro.BuscarProduct(this.txfIdentificacion.getText(), this.cbxProductos.getSelectionModel().getSelectedItem());
      
      this.txfNumProd.setText("");
      this.txfLabel1.setText("");
      this.txfLabe2.setText("");
      if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("Cuenta de ahorros"))
      {
          this.txfLabel1.setDisable(false);
          this.txfLabe2.setDisable(true);
          this.lbl1.setText("$Saldo(miles)");
          this.lbl2.setText("");
      }
      if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("Cuenta corriente"))
      {
         this.txfLabel1.setDisable(false);
          this.txfLabe2.setDisable(true);
          this.lbl1.setText("$Saldo(miles)");
          this.lbl2.setText("");
      }
      if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("CDT"))
      {
          this.txfLabel1.setDisable(false);
          this.txfLabe2.setDisable(false);
          this.lbl1.setText("$Inversion(millones)");
          this.lbl2.setText("Plazo(meses)");
      }
      if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("Tarjeta American"))
      {
          this.txfLabel1.setDisable(false);
          this.txfLabe2.setDisable(true);
          this.lbl1.setText("$Cupo(millones)");
          this.lbl2.setText("");
      }
      if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("Tarjeta Visa"))
      {
          this.txfLabel1.setDisable(false);
          this.txfLabe2.setDisable(true);
          this.lbl1.setText("$Cupo(millones)");
          this.lbl2.setText("");
      }
      if(registro.length()<=0)
      {
          this.btnCrear.setDisable(false);
          this.btnModificar.setDisable(true);
          this.btnEliminiar.setDisable(true);
          do
        {
            numero = (int) ((Math.random()*899999999)+100000000);
            numero2 = String.valueOf(numero);
            val = this.gesPro.verificaCodigo(numero2);
        }while(val);
        this.txfNumProd.setText(numero2);
        this.btnFecha.setValue(null);
      }
      else{
        
          String [] tokens = registro.split(",");
          LocalDate fecha = LocalDate.parse(tokens[2]);
          this.btnCrear.setDisable(true);
          this.btnModificar.setDisable(false);
          this.btnEliminiar.setDisable(false);
          this.btnFecha.setValue(fecha);
          this.txfNumProd.setText(tokens[3]);
          this.txfLabel1.setText(tokens[4]);
          if(this.cbxProductos.getSelectionModel().getSelectedItem().equals("CDT"))
          {
              this.txfLabe2.setText(tokens[5]);
          }
      }
      
    }

    @FXML
private void doCrear(ActionEvent event) {
    String lbl1, lbl2;
    String errores = "";
    boolean val;

    // Verificar si la fecha del DatePicker es nula
    LocalDate fecha = this.btnFecha.getValue();
    if (fecha == null) {
        errores += "Por favor seleccione una fecha...\n";
    } else {
        LocalDate hoy = LocalDate.now();

        // Verificar si la fecha seleccionada es antes de hoy
        if (fecha.isBefore(hoy)) {
            this.btnFecha.getEditor().clear();
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Lo siento, la fecha de la cita debe ser posterior a hoy");
            msg.showAndWait();
            return; // Salir del método si la fecha es incorrecta
        }
    }

    lbl1 = this.txfLabel1.getText();
    if (lbl1.isEmpty()) {
        errores += "Por favor escriba algo en el campo #3\n";
    }

    lbl2 = this.txfLabe2.getText();
    if (this.cbxProductos.getSelectionModel().getSelectedItem().equals("CDT") && lbl2.isEmpty()) {
        errores += "Por favor escriba algo en el campo #4\n";
    }

    if (!errores.isEmpty()) {
        // Mostrar los errores encontrados
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setHeaderText(null);
        msg.setContentText(errores);
        msg.showAndWait();
    } else {
        // Realizar el proceso de creación del producto
        val = this.gesPro.nuevoProducto(this.txfIdentificacion.getText(), this.cbxProductos.getSelectionModel().getSelectedItem(), this.txfNumProd.getText(), String.valueOf(this.btnFecha.getValue()), lbl1, lbl2);
        if (val) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Se creó correctamente el producto");
            msg.showAndWait();
            this.btnCrear.setDisable(true);
            this.btnModificar.setDisable(false);
            this.btnEliminiar.setDisable(false);
        }
    }
        
        
    }

    @FXML
    private void doModificar(ActionEvent event) {
        
        LocalDate date = this.btnFecha.getValue();
        LocalDate hoy = LocalDate.now(); 
        if(date.isBefore(hoy))
        {
            this.btnFecha.getEditor().clear();
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Lo siento la fecha de la cita tiene que ser despúes de hoy");
            msg.showAndWait();
        }
        else if("".equals(String.valueOf(this.btnFecha.getValue())))
        {
            Alert msg =new Alert(Alert.AlertType.INFORMATION);
            msg.setHeaderText(null);
            msg.setContentText("Lo siento la fecha de la cita tiene que ser despúes de hoy");
            msg.showAndWait();
        }
         else if("".equals(this.txfLabel1.getText())){
            Alert msg =new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setContentText("Porfavor escriba algo en el 3 campo");
            msg.showAndWait();
        }
        else
         {
             this.gesPro.modificarProduct(this.txfNumProd.getText(), String.valueOf(this.btnFecha.getValue()), this.txfLabel1.getText(), this.txfLabe2.getText());
         }
    }

    @FXML
    private void doEliminar(ActionEvent event) {
        String registro, seleccion;
        boolean val;
        ArrayList<Cliente> lista = this.gesPro.getTodos();
        val = this.gesPro.deleteProduct(this.txfNumProd.getText());
        if(val == true)
        {
            for (Cliente esp:lista)
            {
                registro = String.valueOf(esp);
                seleccion = this.cbxProductos.getSelectionModel().getSelectedItem();
                String [] tokens = registro.split(",");
                if(esp.getIdCliente().equals(this.txfIdentificacion.getText()))
                {
                    if(seleccion.equals("Cuenta de ahorros"))
                    {
                      tokens[5] = "false";
                      boolean [] tokenPro = new boolean[5];
                      tokenPro[0] = Boolean.parseBoolean(tokens[5]);
                      tokenPro[1] = Boolean.parseBoolean(tokens[6]);
                      tokenPro[2] = Boolean.parseBoolean(tokens[7]);
                      tokenPro[3] = Boolean.parseBoolean(tokens[8]);
                      tokenPro[4] = Boolean.parseBoolean(tokens[9]);
                      esp.setProductos(tokenPro);
                    }
                    if(seleccion.equals("Cuenta corriente"))
                    {
                        tokens[6] = "false";
                        boolean [] tokenPro = new boolean[5];
                        tokenPro[0] = Boolean.parseBoolean(tokens[5]);
                        tokenPro[1] = Boolean.parseBoolean(tokens[6]);
                        tokenPro[2] = Boolean.parseBoolean(tokens[7]);
                        tokenPro[3] = Boolean.parseBoolean(tokens[8]);
                        tokenPro[4] = Boolean.parseBoolean(tokens[9]);
                        esp.setProductos(tokenPro);
                    }
                    if(seleccion.equals("CDT"))
                    {
                        tokens[7] = "false";
                        boolean [] tokenPro = new boolean[5];
                        tokenPro[0] = Boolean.parseBoolean(tokens[5]);
                        tokenPro[1] = Boolean.parseBoolean(tokens[6]);
                        tokenPro[2] = Boolean.parseBoolean(tokens[7]);
                        tokenPro[3] = Boolean.parseBoolean(tokens[8]);
                        tokenPro[4] = Boolean.parseBoolean(tokens[9]);
                        esp.setProductos(tokenPro);
                    }
                    if(seleccion.equals("Tarjeta Visa"))
                    {
                        tokens[8] = "false";
                        boolean [] tokenPro = new boolean[5];
                        tokenPro[0] = Boolean.parseBoolean(tokens[5]);
                        tokenPro[1] = Boolean.parseBoolean(tokens[6]);
                        tokenPro[2] = Boolean.parseBoolean(tokens[7]);
                        tokenPro[3] = Boolean.parseBoolean(tokens[8]);
                        tokenPro[4] = Boolean.parseBoolean(tokens[9]);
                        esp.setProductos(tokenPro);
                    }
                    if(seleccion.equals("Tarjeta American"))
                    {
                        tokens[9] = "false";
                        boolean [] tokenPro = new boolean[5];
                        tokenPro[0] = Boolean.parseBoolean(tokens[5]);
                        tokenPro[1] = Boolean.parseBoolean(tokens[6]);
                        tokenPro[2] = Boolean.parseBoolean(tokens[7]);
                        tokenPro[3] = Boolean.parseBoolean(tokens[8]);
                        tokenPro[4] = Boolean.parseBoolean(tokens[9]);
                        esp.setProductos(tokenPro);
                    }
                }
            }
            
            this.gesPro.remplazaArchivo(lista);
            this.llenaComboProductos();
            this.btnFecha.getEditor().clear();
            this.btnModificar.setDisable(true);
            this.btnEliminiar.setDisable(true);
        }
    }

    @FXML
    private void validaNum1(KeyEvent e) {
        char c = e.getCharacter().charAt(0); //capturamos el caracter que se oprimio
        this.ide = this.txfLabel1.getText();
        
        if(e.getSource() == this.txfLabel1)//cedula
        {
            if(!((Character.isDigit(c)) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_ENTER)))
            {
                Alert msg =new Alert(Alert.AlertType.INFORMATION);
                msg.setHeaderText(null);
                msg.setContentText("Caracter no valido para este campo");
                msg.showAndWait(); 
                
                this.ide = this.ide.substring(0, this.ide.length()-1);
                this.txfLabel1.clear();
                this.txfLabel1.setText(this.ide);
            }
            this.txfLabel1.positionCaret(this.ide.length());
        }
    }

    @FXML
    private void validaNum2(KeyEvent e) {
        char c = e.getCharacter().charAt(0); //capturamos el caracter que se oprimio
        this.ide = this.txfLabe2.getText();
        
        if(e.getSource() == this.txfLabe2)//cedula
        {
            if(!((Character.isDigit(c)) || (c == java.awt.event.KeyEvent.VK_BACK_SPACE) || (c == java.awt.event.KeyEvent.VK_ENTER)))
            {
                Alert msg =new Alert(Alert.AlertType.INFORMATION);
                msg.setHeaderText(null);
                msg.setContentText("Caracter no valido para este campo");
                msg.showAndWait(); 
                
                this.ide = this.ide.substring(0, this.ide.length()-1);
                this.txfLabe2.clear();
                this.txfLabe2.setText(this.ide);
            }
            this.txfLabe2.positionCaret(this.ide.length());
        }
    }

    @FXML
    private void doVerClientes(ActionEvent event) 
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
}

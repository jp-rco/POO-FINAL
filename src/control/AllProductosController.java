/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AllProductosController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML
    private TableView<Producto> tblAllProd;
    @FXML
    private ComboBox<String> cbxIdeProd;
    @FXML
    private ComboBox<String> cbxNombreProd;
     private Button btn_regresar;
    
    private ArrayList<Producto> productos;
    
    private ObservableList<Producto> obsProductos;
    
    private ObservableList<Producto> filtrados;
    @FXML
    private TableColumn<?, ?> colIdProd;
    @FXML
    private TableColumn<?, ?> colIdCli;
    @FXML
    private TableColumn<?, ?> colNomProd;
    @FXML
    private TableColumn<?, ?> colApert;
    @FXML
    private TableColumn<?, ?> colDis;
    private GestionProductos gesPro;
    private String ide;
    @FXML
    private TextField txfLet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gesPro = new GestionProductos();
        this.obsProductos=FXCollections.observableArrayList();
        this.filtrados=FXCollections.observableArrayList();
        this.llenaComboProd();
        this.llenaComboId();
        this.modelaTabla();
        this.getProductos();
        // TODO
    }
    private void getProductos()
    {
        this.obsProductos.clear();
        this.productos = this.gesPro.getTodos2();
        
        for(Producto pro: productos)
        {
            this.obsProductos.add(pro);
        }
        this.tblAllProd.setItems(this.obsProductos);
    }
    private void llenaComboId()
    {
        this.cbxIdeProd.getItems().add("Todos");
        String registro;
        ArrayList<Cliente> clientes= this.gesPro.getTodos();
        for(Cliente cli: clientes)
        {
            this.cbxIdeProd.getItems().add(cli.getIdCliente());
        }
    }
    private void llenaComboProd()
    {
        this.cbxNombreProd.getItems().add("Todos");
        this.cbxNombreProd.getItems().add("Cuenta de ahorros");
        this.cbxNombreProd.getItems().add("Cuenta corriente");
        this.cbxNombreProd.getItems().add("CDT");
        this.cbxNombreProd.getItems().add("Tarjeta Visa");
        this.cbxNombreProd.getItems().add("Tarjeta American");
    }
    private void modelaTabla()
    {
        this.colIdCli.setCellValueFactory(new PropertyValueFactory("id"));
        this.colIdProd.setCellValueFactory(new PropertyValueFactory("numeroProducto"));
        this.colApert.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.colNomProd.setCellValueFactory(new PropertyValueFactory("nombreProducto"));
        this.colDis.setCellValueFactory(new PropertyValueFactory("label1"));
    }

    @FXML
    private void doRegresar(ActionEvent event) {
        try
        {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Productos.fxml"));
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
    private void doFiltroIdCliente(ActionEvent event) {
        this.filtrados.clear();
        String index, registro;
        
        index = this.cbxIdeProd.getSelectionModel().getSelectedItem();
        
        if(index.equals("Todos"))
        {
            this.tblAllProd.setItems(this.obsProductos);
        }
        else
        {
            ArrayList<Producto> productos = this.gesPro.getTodos2();
        
            for(Producto prod: productos)
            {
                registro = String.valueOf(prod);
                String [] tokens = registro.split(",");
                
                if(tokens[0].equals(index))
                {
                    this.filtrados.add(prod);
                }
            }
            this.tblAllProd.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFiltroProducto(ActionEvent event) {
        this.filtrados.clear();
        String index, registro;
        
        index = this.cbxNombreProd.getSelectionModel().getSelectedItem();
        
        if(index.equals("Todos"))
        {
            this.tblAllProd.setItems(this.obsProductos);
        }
        else
        {
            ArrayList<Producto> productos = this.gesPro.getTodos2();
        
            for(Producto prod: productos)
            {
                registro = String.valueOf(prod);
                String [] tokens = registro.split(",");
                
                if(tokens[1].equals(index))
                {
                    this.filtrados.add(prod);
                }
            }
            this.tblAllProd.setItems(this.filtrados);
        }
    }

    @FXML
    private void doRestaurarProd(MouseEvent event) {
        this.cbxNombreProd.getSelectionModel().selectFirst();
    }

    @FXML
    private void doRestaurarCli(MouseEvent event) {
        this.cbxIdeProd.getSelectionModel().selectFirst();
    }

    @FXML
    private void doFiltroText(ActionEvent event) {
    }

    @FXML
    private void buscar(KeyEvent e) {
        this.filtrados.clear();
        String index, registro;
        
        char c = e.getCharacter().charAt(0); //capturamos el caracter que se oprimio
        this.ide = this.txfLet.getText();
        ArrayList<Producto> productos = this.gesPro.getTodos2();
      
            for(Producto prod: productos)
            {
                registro = String.valueOf(prod);
                String [] tokens = registro.split(",");
                
                if(tokens[3].contains(ide) == false)
                {
                } else {
                    this.filtrados.add(prod);
            }
            }
        this.tblAllProd.setItems(this.filtrados);
    }
    
        public void setProductos(ArrayList<Producto>productos)
    {
        this.productos=productos;
        this.obsProductos=FXCollections.observableArrayList(this.productos);
        this.tblAllProd.setItems(this.obsProductos);
    }
}

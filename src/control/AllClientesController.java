/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class AllClientesController implements Initializable {

    @FXML
    private ComboBox<String> cbx_genero;
    @FXML
    private ComboBox<String> cbx_producto;
    @FXML
    private TextField txt_buscatitulo;
    @FXML
    private TableView<Cliente> tbl_listado;
    @FXML
    private TableColumn<?, ?> col_isbn;
    @FXML
    private TableColumn<?, ?> col_nombre;
    @FXML
    private TableColumn<?, ?> col_genero;
    @FXML
    private Button btn_regresar;
    @FXML
    private ImageView img_foto;
    
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> obsClientes;
    private ObservableList<Cliente> filtrados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.obsClientes=FXCollections.observableArrayList();
        this.filtrados=FXCollections.observableArrayList();
        this.llenaCombos();
        this.modelaTabla();
    }    

    @FXML
    private void doFiltroGenero(ActionEvent event) 
    {
        int index;
        
        this.filtrados.clear();
        index = this.cbx_genero.getSelectionModel().getSelectedIndex();
        
        if(index ==0)
        {
            this.tbl_listado.setItems(this.obsClientes);
        }
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    if(cliente.getGenero()==this.cbx_genero.getSelectionModel().getSelectedItem().charAt(0))
                    {
                        this.filtrados.add(cliente);
                    }
                }
            }
            this.tbl_listado.setItems(this.filtrados);
        }
    }

    @FXML
    private void doFiltroProducto(ActionEvent event) 
    {
        int index;
        
        this.filtrados.clear();
        index = this.cbx_producto.getSelectionModel().getSelectedIndex();
        
        if(index ==0)
        {
            this.tbl_listado.setItems(this.obsClientes);
        }
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    boolean products []=cliente.getProductos();
                    if(products[index-1]==true)
                    {
                        this.filtrados.add(cliente);
                    }
                }
            }
            this.tbl_listado.setItems(this.filtrados);
        }
    }

    @FXML
    private void doBuscaTitulo(KeyEvent event) 
    {
        String filtroName;
        filtroName = this.txt_buscatitulo.getText();
        
        if(filtroName.isEmpty())
        {
            this.tbl_listado.setItems(this.obsClientes);
        }
        else{
            this.filtrados.clear();
            for(Cliente p:this.obsClientes)
            {
                if((p.getNombre()).toLowerCase().contains(filtroName.toLowerCase()))
                {
                    this.filtrados.add(p);
                }
            }
            this.tbl_listado.setItems(this.filtrados);
        }
    }

    @FXML
    private void doPoneFoto(MouseEvent event) 
    {
        String carre;
        
        Cliente perso = this.tbl_listado.getSelectionModel().getSelectedItem();
        if(perso != null)
        {
            File imgFile = new File("././images/"+perso.getFoto());
            String url = imgFile.toURI().toString();
            Image image = new Image(url, true);
            this.img_foto.setImage(image);
        }
    }

    @FXML
    private void doRegresar(ActionEvent event) 
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
                stage.setTitle("MANEJO DE CLIENTES");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btn_regresar.getScene().getWindow();
                myStage.close();
              } 
            catch (IOException ex)
            {
                Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //=======================
    
    public void setClientes(ArrayList<Cliente>clientes)
    {
        this.clientes=clientes;
        this.obsClientes=FXCollections.observableArrayList(this.clientes);
        this.tbl_listado.setItems(this.obsClientes);
        
    }
    
    private void llenaCombos()
    {
        this.cbx_genero.getItems().add("Todos");
        this.cbx_genero.getItems().add("Masculino");
        this.cbx_genero.getItems().add("Femenino");
        this.cbx_genero.getItems().add("Otro");
        
        this.cbx_producto.getItems().add("Todos");
        this.cbx_producto.getItems().add("Cta. Ahorros");
        this.cbx_producto.getItems().add("Cta. Corriente");
        this.cbx_producto.getItems().add("CDT");
        this.cbx_producto.getItems().add("Tarj. Visa");
        this.cbx_producto.getItems().add("Tarj. American");
    }
    
    private void modelaTabla()
    {
        this.col_isbn.setCellValueFactory(new PropertyValueFactory("idCliente"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_genero.setCellValueFactory(new PropertyValueFactory("genero"));
    }
}

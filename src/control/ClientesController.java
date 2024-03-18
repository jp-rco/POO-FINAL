/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ClientesController implements Initializable {

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_clave;
    @FXML
    private RadioButton rbt_hombre;
    @FXML
    private RadioButton rbt_mujer;
    @FXML
    private RadioButton rbt_otro;
    @FXML
    private CheckBox chk_ahorros;
    @FXML
    private CheckBox chk_visa;
    @FXML
    private CheckBox chk_american;
    @FXML
    private CheckBox chk_corriente;
    @FXML
    private CheckBox chk_cdt;
    @FXML
    private Button btn_regresar;
    @FXML
    private ImageView img_foto;
    @FXML
    private Button btn_buscafoto;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Button btn_savenuevo;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_vertodos;
    @FXML
    private Button btn_patras;
    @FXML
    private Button btn_palante;
    @FXML
    private Button btn_buscar;
    
    //VARIABLES DE CLASE
    private GestionClientes gesCli;
    private GestionProductos gesProd;
    private ArrayList<Cliente> clientes;
    private int pos;
    private String laFoto;
    private String idActual;
    private String rutaImages;
    @FXML
    private Button btn_verproductos;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gesProd = new GestionProductos();
        this.gesCli = new GestionClientes();
        this.pos=0;
        this.rutaImages="././images/";
        this.laFoto="sinrostro.jpg";
        
        ToggleGroup tg = new ToggleGroup();
        this.rbt_hombre.setToggleGroup(tg);
        this.rbt_mujer.setToggleGroup(tg);
        this.rbt_otro.setToggleGroup(tg);
        this.rbt_otro.setSelected(true);
        
        this.traeClientes();
    }    

    @FXML
    private void doRegresar(ActionEvent event) 
    {
        
                try
        {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene (root);

                //Ventana2Controller ouControlador = loader.getController();
                //ouControlador.constructorNuevo(); //metodo que se invento el profe para inicializar y mandarle cosas

                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("PRINCIPAL");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btn_regresar.getScene().getWindow();
                myStage.close();
              } 
            catch (IOException ex)
            {
                Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void doBuscaFoto(ActionEvent event) 
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("BUSCAR FOTO");
        fc.setInitialDirectory(new File(this.rutaImages));
        try
        {
            File imgFile = fc.showOpenDialog(new Stage());
            if(imgFile.canExecute())
            {
                this.laFoto=imgFile.getName();
                
                //muestra la imagen
                if(this.laFoto!=null)
                {
                    Image image = new Image("file:"+imgFile.getAbsolutePath());
                    this.img_foto.setImage(image); //la muestra
                }
            }
        }
        catch (Exception ex){}
    }

    @FXML
    private void doLimpiar(ActionEvent event) 
    {
        this.txt_id.clear();
        this.txt_nombre.clear();
        this.txt_clave.clear();
        this.rbt_hombre.setSelected(false);
        this.rbt_mujer.setSelected(false);
        this.rbt_otro.setSelected(true);
        this.limpiaChecks();
        this.laFoto="sinrostro.jpg";
        this.poneFoto();
        this.txt_id.requestFocus();
        this.idActual="new";
    }

    @FXML
    private void doGuardaNuevo(ActionEvent event) 
    {
        String id, name, clave;
        char gender = '*';
        boolean products[]={false,false,false,false,false};
        String errores="";
        int conta = 0;
        boolean existe;
        
        id = this.txt_id.getText();
        if(id.isEmpty())
            errores+="El campo Id esta vacio...\n";
        name = this.txt_nombre.getText();
        if(name.isEmpty())
            errores+="El campo Nombre esta vacio...\n";
        clave = this.txt_clave.getText();
        if(clave.isEmpty())
            errores+="El campo Clave esta vacio...\n";
        
        if(this.rbt_hombre.isSelected())
            gender = 'M';
        if(this.rbt_mujer.isSelected())
            gender = 'F';
        if(this.rbt_otro.isSelected())
            gender = 'O';
        
        if(this.chk_ahorros.isSelected())//ahorros
           products[0]=true;
        if(this.chk_corriente.isSelected())//corriente
           products[1]=true;
        if(this.chk_cdt.isSelected())//cdt
           products[2]=true;
        if(this.chk_visa.isSelected())//visa
           products[3]=true;
        if(this.chk_american.isSelected())//ahorros
           products[4]=true;
        
        //verificar si selecciono almenos un producto
        for (int i = 0; i < products.length; i++) 
        {
            if(products[i]==true)
                conta ++;
        }
        
        if(conta==0)
            errores+="Debe elegir al menos un producto...\n"; 
        if(errores.length()==0)
        {
            Cliente cliente = new Cliente(id, name, this.laFoto, clave, gender, products);
            if(this.idActual.equals("new"))//creando nuevo
            {
                if(this.gesCli.pruebaExistenciaClave(clave))
                    this.showMessages(1, "Esa clave ya existe");
                if(this.gesCli.pruebaExistencia(id))
                    this.showMessages(1, "Ese ID ya existe...!!");
                else
                {
                    if(this.gesCli.guardaCliente(cliente)==true)
                    {
                        this.showMessages(2, "El cliente ha sido creado con exito...!!");
                        this.traeClientes();
                    }
                    else
                        this.showMessages(1, "Fallo creando el Cliente...revise!!");
                }
            }
            else //Modificacion 
            {
                Iterator<Cliente> iter = this.clientes.iterator();
                int pos =-1;
                while(iter.hasNext())
                {
                    pos++;
                    if(iter.next().getIdCliente().equals(this.idActual))
                    {
                        iter.remove();
                        this.clientes.add(pos,cliente);
                        break;
                    }
                }
                this.gesCli.remplazaArchivo(this.clientes);
                this.showMessages(2,"El cliente ha sido modificado..!!");
                this.traeClientes();
            }
        }
        else
        {
            this.showMessages(1, errores);
        }
    }

    @FXML
    private void doEliminar(ActionEvent event) 
    {
        boolean seguro;
        
        seguro = this.showMessages(3, "Confirma la operacion?");
        if(seguro)
        {
            Iterator<Cliente> iter = this.clientes.iterator();
            while(iter.hasNext()){
                if(iter.next().getIdCliente().equals(this.idActual))
                {
                    iter.remove();
                    break;
                }
            }
            this.gesCli.remplazaArchivo(this.clientes);
            this.showMessages(2, "El cliente ha sido removido..!!");
            this.traeClientes();
        }
    }
    

    @FXML
    private void doVerTodos(ActionEvent event) 
    {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/AllClientes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            //Si necesito mandarle cosas a la otra ventana le programo un metodo constructor () a la segunda ventana y por ahi le mando
            AllClientesController controlVentana2 = loader.getController();
            controlVentana2.setClientes(this.clientes);
            
                Stage stage = new Stage ();
                stage.setOnCloseRequest(even -> {even.consume();});             //deshabilita la x de cerrar
                stage.setResizable(false);                                                       //no permite redimensionar la ventana
                stage.setTitle("LISTADO DE CLIENTES");
                stage.initModality(Modality.WINDOW_MODAL);                        //no se cierra la otra ventana
                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btn_regresar.getScene().getWindow();
                myStage.close();
        }
               
            catch (Exception ex)
            {
               Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void doPatras(ActionEvent event) 
    {
        if(this.pos>0)
        {
            this.pos--;
            Cliente anterior = this.clientes.get(this.pos);
            this.poneCliente(anterior);
            this.idActual=anterior.getIdCliente();
        }
    }

    @FXML
    private void doPalante(ActionEvent event) 
    {
        if(this.pos<this.clientes.size()-1)
        {
            this.pos++;
            Cliente siguiente = this.clientes.get(this.pos);
            this.poneCliente(siguiente);
            this.idActual=siguiente.getIdCliente();
        }
    }

    @FXML
    private void doBuscar(ActionEvent event) 
    {
        String id;
        int posi=-1;
        boolean existe = false;
        
        id = this.txt_id.getText();
        if(id.isEmpty())
            this.showMessages(1, "Debe digitar el ID del cliente a buscar..!!");
        else
        {
            for (Cliente cliente:this.clientes)
            {
                posi++;
                if(cliente.getIdCliente().equals(id))
                {
                     this.poneCliente(cliente);
                     this.idActual=cliente.getIdCliente();
                     this.pos=posi;
                     existe=true;
                     break;
                }
            }
            if(!existe)
            {
                this.showMessages(1, "No existe cliente con ese ID..!!");
            }
       }
    }
        
        
    
    //========================
    
    private void traeClientes()
    {
        this.pos=0;
        
        this.clientes=this.gesCli.getTodos();
        if(!this.clientes.isEmpty())
        {
            Cliente primero = this.clientes.get(this.pos);
            this.poneCliente(primero);
            this.idActual = primero.getIdCliente();
        }
        else{
            this.idActual="new";
        }
    }
    
    private void poneCliente(Cliente cliente)
    {
        boolean productos [];
        this.limpiaChecks();
        
        this.txt_id.setText(cliente.getIdCliente());
        this.txt_nombre.setText(cliente.getNombre());
        this.txt_clave.setText(cliente.getClave());
        
        if(cliente.getGenero()=='M')
            this.rbt_hombre.setSelected(true);
        if(cliente.getGenero()=='F')
            this.rbt_mujer.setSelected(true);
        if(cliente.getGenero()=='O')
            this.rbt_otro.setSelected(true);
       
        productos = cliente.getProductos();
        if(productos[0]==true)//ahorros
            this.chk_ahorros.setSelected(true);
        if(productos[1]==true)//corriente
            this.chk_corriente.setSelected(true);
        if(productos[2]==true)//cdt
            this.chk_cdt.setSelected(true);
        if(productos[3]==true)//visa
            this.chk_visa.setSelected(true);
        if(productos[4]==true)//american
            this.chk_american.setSelected(true);
        
        this.laFoto=cliente.getFoto();
        this.poneFoto();        
    }
    
    private void limpiaChecks()
    {
        this.chk_ahorros.setSelected(false);
        this.chk_corriente.setSelected(false);
        this.chk_cdt.setSelected(false);
        this.chk_visa.setSelected(false);
        this.chk_american.setSelected(false);
    }
    
    private void poneFoto()
    {
        File imgFile = new File(this.rutaImages+this.laFoto);
        String url = imgFile.toURI().toString();
        Image image = new Image(url,true);
        this.img_foto.setImage(image);
    }
    
         private boolean showMessages(int caso, String mesg)
    {
        Alert msg;
        boolean ok = false;
        
        if(caso ==1)//Error
        {
            msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("ERROR");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        
        if(caso==2)//Notificacion
        {
            msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("LISTO");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        
        if(caso==3)//Confirmacion
        {
            msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Peticion Eliminacion");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK)
            {
                 ok = true;
            }
            
    }
        return ok;
}
      public boolean guardaCliente(Cliente cliente)
      {
        String rutaClientes = gesCli.rutaClientes;
          boolean ok = false;
          try
          {
              File file = new File(rutaClientes);
              FileWriter fr = new FileWriter(file, true);
              PrintWriter ps = new PrintWriter(fr);
                    ps.println(cliente);
              ps.close();
              ok = true;
          }
          catch(IOException ioe)
          {
              System.out.println("Fallo guardando cliente en el archivo..!!");
          }
          return ok;
      }

    @FXML
    private void doverproductos(ActionEvent event) 
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
}

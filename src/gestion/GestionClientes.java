/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

/**
 *
 * @author Lenovo
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import negocio.Cliente;

/**
 * @author JP
 */
public class GestionClientes {
    public String rutaClientes;

    // Métodos
    public GestionClientes() {
        this.rutaClientes = "./Archivos/misClientes.txt";
        this.verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File filex = new File(this.rutaClientes);
            if (!filex.exists()) {
                filex.createNewFile(); // Lo crea
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Fallo Buscando Archivo...!");
            // ex.printStackTrace();
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*

     //De servicio
    public void crearCliente() {
        String id = null, nombre, clave;
        float saldo;
        boolean existeId;

        // Pedimos información de la cliente
        do {
            id = JOptionPane.showInputDialog("Digite el id del cliente: ");
            existeId = this.existeCliente(id);
            if (existeId) {
                JOptionPane.showMessageDialog(null, "Ya existe una cliente con esta id: ");
            }
        } while (existeId);
        nombre = JOptionPane.showInputDialog("Digite el nombre").toUpperCase();
        clave = String.valueOf((int) (1000 + Math.random() * 9000));
        saldo = Float.parseFloat(JOptionPane.showInputDialog("Digite saldo inicial"));

        // Creamos el objeto tipo cliente
        Cliente cliente = new Cliente(clave, id, nombre, saldo);

        // Guardamos el objeto en el contenedor
        this.guardaCliente(cliente);
        JOptionPane.showMessageDialog(null, "Operación exitosa..!!");
    }
*/

    public boolean guardaCliente(Cliente cliente) {

        boolean ok = false;
        try {
            File file = new File(rutaClientes);
            FileWriter fr = new FileWriter(file, true);
            PrintWriter ps = new PrintWriter(fr);
            ps.println(cliente);
            ps.close();
            ok = true;
        }
        catch (IOException ioe) 
        {
            System.out.println("Falla guardando cliente en el archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }

    public void verClientes() {
        ArrayList<Cliente> clientes = this.getTodos();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        System.out.println("========================");
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

    public boolean pruebaExistencia(String id) {
        boolean existe = false;
        FileReader file;
        String registro;
        try {
            file = new FileReader(rutaClientes);
            Scanner lector = new Scanner(file);
            while (lector.hasNextLine()) {
                registro = lector.nextLine();
                String[] campos = registro.split(",");
                if (campos[0].equals(id)) {
                    existe = true;
                    break;
                }
            }
        } catch (FileNotFoundException ioe) {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
        public boolean pruebaExistenciaClave(String clave) {
        boolean existe = false;
        FileReader file;
        String registro;
        try {
            file = new FileReader(rutaClientes);
            Scanner lector = new Scanner(file);
            while (lector.hasNextLine()) {
                registro = lector.nextLine();
                String[] campos = registro.split(",");
                if (campos[4].equals(clave)) {
                    existe = true;
                    break;
                }
            }
        } catch (FileNotFoundException ioe) {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
        
        /*

    public void verCliente() {
        String id;
        Cliente cliente;
        id = JOptionPane.showInputDialog("Digite el id del cliente: ");
        cliente = this.getTodos(id);
        if (cliente != null) {
            System.out.println(cliente);
            System.out.println("========================");
        } else {
            JOptionPane.showMessageDialog(null, "No existe una cliente con ese ID..!!");
        }
    }*/

    public Cliente buscarCliente(String id) {

        Cliente cliente = null;
        FileReader file;
        BufferedReader br;
        String registro;
        try {
            file = new FileReader(rutaClientes);
            br = new BufferedReader(file);
            while ((registro = br.readLine()) != null) 
            {
                String[] campos = registro.split(",");
                if (campos[0].equals(id)) 
                {
                    boolean productos[] = new boolean[5];
                    for(int i=5; i<=9;i++)
                    {
                        productos[i-5]= Boolean.parseBoolean(campos[i]);
                    }
                    cliente = new Cliente(campos[0], campos[1], campos[2], campos[3], campos[4].charAt(0), productos);
                    break;
                }
            }
              } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente;
    }

    public boolean hayClientes() {
        boolean hay = false;
        FileReader file;
        try {
            file = new FileReader(rutaClientes);
            Scanner lector = new Scanner(file);
            while (lector.hasNextLine()) {
                hay = true;
                break;
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Fallo buscando archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hay;
    }

    private boolean existeCliente(String id) {
        boolean existe = false;
        FileReader file;
        String registro;
        try {
            file = new FileReader(rutaClientes);
            Scanner lector = new Scanner(file);
            while (lector.hasNextLine()) {
                registro = lector.nextLine();
                String[] campos = registro.split(",");
                if (campos[1].equals(id)) {
                    existe = true;
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Problemas leyendo archivo..!!");
            // Logger.getLogger(GestionClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    /*

    public void modifyCliente() {
        String id, nuevoDato;
        boolean existe = false, existeId;
        ArrayList<Cliente> clientes = this.getTodos();

        id = JOptionPane.showInputDialog("Digite el id de la cliente a modificar: ");
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(id)) {
                do {
                    nuevoDato = JOptionPane.showInputDialog("Digite el nuevo id del cliente: ");
                    existeId = this.existeCliente(nuevoDato);
                    if (existeId) {
                        JOptionPane.showMessageDialog(null, "Ya existe una cliente con esta id: ");
                    }
                } while (existeId);
                cliente.setIdCliente(nuevoDato);
                this.remplazaArchivo(clientes);

                JOptionPane.showMessageDialog(null, "Operación exitosa");
                existe = true;
                break;
            }
        }
        if (!existe) {
            JOptionPane.showMessageDialog(null, "Esa cliente no existe");
        }
    }

    public void modifyClave() {
        String id, nuevoDato;
        boolean existe = false;
        ArrayList<Cliente> clientes = this.getTodos();

        id = JOptionPane.showInputDialog("Digite el id de la cliente a modificar: ");
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(id)) {
                nuevoDato = String.valueOf((int) (1000 + Math.random() * 9000));
                cliente.setClave(nuevoDato)
                this.remplazaArchivo(clientes);

                JOptionPane.showMessageDialog(null, "Operación exitosa");
                existe = true;
                break;
            }
        }
        if (!existe) {
            JOptionPane.showMessageDialog(null, "Esa cliente no existe");
        }
    }

    public void modifyNombre() {
        String id, nuevoDato;
        boolean existe = false;
        ArrayList<Cliente> clientes = this.getTodos();

        id = JOptionPane.showInputDialog("Digite el id de la cliente a modificar: ").toUpperCase();
        for (Cliente cliente : clientes) {``
            if (cliente.getIdCliente().equals(id)) {
                nuevoDato = JOptionPane.showInputDialog("Digite el nuevo nombre: ");
                cliente.setNombre(nuevoDato);
                this.remplazaArchivo(clientes);

                JOptionPane.showMessageDialog(null, "Operación exitosa");
                existe = true;
                break;
            }
        }
        if (!existe) {
            JOptionPane.showMessageDialog(null, "Esa cliente no existe");
        }
    }*/

    public void deleteCliente(String id) {
        ArrayList<Cliente> clientes = this.getTodos();
        
        for (Cliente cliente : clientes) 
        {
            if (cliente.getIdCliente().equals(id)) {
                clientes.remove(cliente);
                break;
            }
        }
        this.remplazaArchivo(clientes);
        JOptionPane.showMessageDialog(null, "Eliminacion exitosa..!!");
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
            System.out.println("Fallando recargando clientes en el archivo...!!");
            System.exit(1);
        }
    }

    /*
    public void hacerOperacion() {
        String clave;
        int tipo;
        boolean existe = false;
        float monto;

        ArrayList<Cliente> clientes = this.getTodos();

        clave = JOptionPane.showInputDialog("Digite la clave");
        for (Cliente cliente : clientes) {
            if (cliente.getClave().equals(clave)) {
                do {
                    tipo = Integer.parseInt(JOptionPane.showInputDialog(null, "Bienvenid@ " + cliente.getNombre() + "\nQué desea hacer? \n1. Depósito | \n2. Retiro | \n3. Consulta de saldo"));
                    if (tipo != 1 && tipo != 2 && tipo != 3) {
                        JOptionPane.showMessageDialog(null, "Opción NO válida, Digite 1 o 2 o 3");
                    }
                } while (tipo != 1 && tipo != 2 && tipo != 3);
                if (tipo == 1) {
                    monto = Float.parseFloat(JOptionPane.showInputDialog("Cuánto va a depositar?: "));
                    cliente.setSaldo(cliente.getSaldo() + monto);
                    JOptionPane.showMessageDialog(null, "Su saldo ha sido actualizado");
                }
                if (tipo == 2) {
                    monto = Float.parseFloat(JOptionPane.showInputDialog("Cuánto va a retirar?: "));
                    if (monto > cliente.getSaldo()) {
                        JOptionPane.showMessageDialog(null, "Sus fondos son insuficientes");
                    } else {
                        cliente.setSaldo(cliente.getSaldo() - monto);
                        JOptionPane.showMessageDialog(null, "Su saldo ha sido actualizado");
                    }
                }
                if (tipo == 3) {
                    JOptionPane.showMessageDialog(null, "Su saldo es: " + cliente.getSaldo());
                }

                this.remplazaArchivo(clientes);
                existe = true;
                break;
            }
        }

        if (!existe) {
            JOptionPane.showMessageDialog(null, "Esa cliente no existe");
        }
    }
    */
}

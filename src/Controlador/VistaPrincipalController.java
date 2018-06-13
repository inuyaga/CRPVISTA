package Controlador;

import Modelo.Tablainfo;
import cma.carmelo.jasperviewerfx.JasperFX;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static jdk.nashorn.internal.objects.NativeString.trim;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import Modelo.conexion;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Sistemas2
 */
public class VistaPrincipalController implements Initializable {

    @FXML
    private Button btngenerar;
    @FXML
    private TextField txtvendedor;
    @FXML
    private TextField txtruta;
    @FXML
    private CheckBox CHKconruta;
    @FXML
    private TableView<Tablainfo> TABLAVENTAS;
    @FXML
    private MenuItem CLICDERECHO;
    @FXML
    private TableColumn<?, ?> COLNUMEROCLIENTE;
    @FXML
    private TableColumn<?, ?> COLCLIENTE;
    @FXML
    private TableColumn<?, ?> COLVENTA;
    @FXML
    private TableColumn<?, ?> COLRUTA;
    @FXML
    private TableColumn<?, ?> COLCLAVE;
    private ObservableList<Tablainfo> USUARIO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }

    @FXML
    private void Generar_Reporte(ActionEvent event)
    {
        String Ven = trim(txtvendedor.getText());
        String rut = trim(txtruta.getText());

        if (!Ven.isEmpty()) {
            if(rut.isEmpty())
            {
                try {
                    Connection cnx = null;
                    conexion conexbd = new conexion();
                    conexbd.crearConexion();
                    cnx = conexbd.getConexion();
                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("P_VENDEDOR", Ven);
                    ParametrosJasperReport.put("P_RUTA", rut);
                    ParametrosJasperReport.put("P_SEMANA", "semana");
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ReporteVendedoSinRuta.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    LlenarTabla(Ven,rut);
                    jpFX.show();
                } catch (NumberFormatException | JRException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("I have a great message for you!"+e);
                    alert.showAndWait();
                }
            }else{
                try {
                    Connection cnx = null;
                    conexion conexbd = new conexion();
                    conexbd.crearConexion();
                    cnx = conexbd.getConexion();
                    HashMap<String, Object> ParametrosJasperReport = new HashMap<>();
                    ParametrosJasperReport.put("P_VENDEDOR", Ven);
                    ParametrosJasperReport.put("P_RUTA", rut);
                    ParametrosJasperReport.put("P_SEMANA", "semana");
                    InputStream fis2 = null;
                    fis2 = this.getClass().getResourceAsStream("/Reporte/ReporteVendedor.jasper");
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(fis2);
                    JasperPrint print = JasperFillManager.fillReport(reporte, ParametrosJasperReport, cnx);
                    JasperFX jpFX = new JasperFX(print);
                    LlenarTabla(Ven,rut);
                    jpFX.show();
                } catch (NumberFormatException | JRException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Look, an Information Dialog");
                    alert.setContentText("I have a great message for you!"+e);
                    alert.showAndWait();
                }
            }
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Campo Vacío");
            alert.setHeaderText("El campo es obligatorio");
            alert.setContentText("Debe escribir la clave de vendedor");
            alert.showAndWait();
        }
    }

    @FXML
    private void ClicSobreCHK(ActionEvent event)
    {
        if(CHKconruta.isSelected())
        {
            txtruta.setDisable(false);
        }else{
            txtruta.setDisable(true);
            txtruta.setText("");
        }
    }

    @FXML
    private void GUARDARCOMENTARIO(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Enviar el mensaje");
        alert.setHeaderText("Escribe la observación para esta venta en especifico");
        alert.setContentText("Escribe en el siguiente recuadro");
        
        TextArea textArea = new TextArea();
        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);
        alert.getDialogPane().setContent(content);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            PreparedStatement ps = null;
            Connection cnx = null;
            conexion conexbd = new conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            String sql = "INSERT INTO Ruta_Comentarios"
                + "(Comentarios_Venta,Comentarios_Comentario) "
                + "VALUES(?,?)";
            
            int indez=TABLAVENTAS.getSelectionModel().getSelectedIndex();
            if(indez >= 0)
            {
                String Venta=TABLAVENTAS.getItems().get(indez).getVENTA();
                try {
                    ps = cnx.prepareStatement(sql);
                    ps.setString(1, trim(Venta));
                    ps.setString(2, trim(textArea.getText()));
                    ps.executeUpdate();
                } catch (SQLException e) {
                    Alert alert2 = new Alert(AlertType.WARNING);
                    alert2.setTitle("Advertencia");
                    alert2.setHeaderText("No se pudo guardar la información");
                    alert2.setContentText("Al parecer no se encontró el servidor "+e.toString());
                    alert2.showAndWait();
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (SQLException e) {
                        Alert alert2 = new Alert(AlertType.WARNING);
                        alert2.setTitle("Advertencia");
                        alert2.setHeaderText("No se pudo cerrar conexiones");
                        alert2.setContentText("Error: "+e.toString());
                        alert2.showAndWait();
                    }
                }
            }else
            {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Selección no válida");
                alert3.setHeaderText(null);
                alert3.setContentText("Debe seleccionar la fila de la venta donde quiera agregar la observación");
                alert3.showAndWait();
            }
        }
    }
    
    private void LlenarTabla(String Vendedor, String ruta)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection cnx = null;
        String sql="SELECT * FROM Corte_Rutas";
        conexion conexbd = new conexion();
        conexbd.crearConexion();
        cnx = conexbd.getConexion();
        USUARIO=FXCollections.observableArrayList();
        try 
        {
            if(ruta.isEmpty())
            {
                sql = "SELECT * FROM Corte_Rutas where DetCort_ClaveVendedor = '"+Vendedor+"'";
            }else{
                sql = "SELECT * FROM Corte_Rutas where DetCort_ClaveVendedor = '"+Vendedor+"' AND DetCort_Ruta='"+ruta+"'";
            }
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                USUARIO.add(new Tablainfo(rs.getInt("DetCort_NumeroCliente"),rs.getString("DetCort_NombreCliente"),rs.getString("DetCort_Venta"),rs.getString("DetCort_Ruta"),rs.getString("DetCort_ClaveVendedor")));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Problemas de conexión");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Problemas de conexión");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        COLNUMEROCLIENTE.setCellValueFactory(new PropertyValueFactory<>("NUMEROCLIENTE"));
        COLCLIENTE.setCellValueFactory(new PropertyValueFactory<>("NOMBRECLIENTE"));
        COLVENTA.setCellValueFactory(new PropertyValueFactory<>("VENTA"));
        COLRUTA.setCellValueFactory(new PropertyValueFactory<>("RUTA"));
        COLCLAVE.setCellValueFactory(new PropertyValueFactory<>("CLAVE"));
        TABLAVENTAS.setItems(USUARIO);
    }

    @FXML
    private void AGREGARVENTA(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Enviar Solicitud para agregar");
        alert.setHeaderText("Escribe la observación para esta venta sea AGREGADA al corte");
        alert.setContentText("Escribe en el siguiente recuadro");
        
        TextArea textArea = new TextArea();
        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);
        alert.getDialogPane().setContent(content);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            PreparedStatement ps = null;
            Connection cnx = null;
            conexion conexbd = new conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            String sql = "INSERT INTO Solicitudes"
                + "(Solicitud_Venta,Solicitud_Ruta,Solicitud_Mensaje,Solicitud_Clave_Vendedor) "
                + "VALUES(?,?,?,?)";
            
            int indez=TABLAVENTAS.getSelectionModel().getSelectedIndex();
            if(indez >= 0)
            {
                String Venta=TABLAVENTAS.getItems().get(indez).getVENTA();
                String Ruta=TABLAVENTAS.getItems().get(indez).getRUTA();
                try {
                    ps = cnx.prepareStatement(sql);
                    ps.setString(1, trim(Venta));
                    ps.setString(2, trim(Ruta));
                    ps.setString(3, trim(textArea.getText()));
                    ps.setString(4, trim(txtvendedor.getText()));
                    ps.executeUpdate();
                    Alert enviarAgregar = new Alert(AlertType.INFORMATION);
                    enviarAgregar.setTitle("Solicitud enviada");
                    enviarAgregar.setHeaderText(null);
                    enviarAgregar.setContentText("La solicitud fue enviada, quedará en espera de ser autorizada");
                    enviarAgregar.showAndWait();
                } catch (SQLException e) {
                    Alert alert2 = new Alert(AlertType.WARNING);
                    alert2.setTitle("Advertencia");
                    alert2.setHeaderText("No se pudo guardar la información");
                    alert2.setContentText("Al parecer no se encontró el servidor "+e.toString());
                    alert2.showAndWait();
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (SQLException e) {
                        Alert alert2 = new Alert(AlertType.WARNING);
                        alert2.setTitle("Advertencia");
                        alert2.setHeaderText("No se pudo cerrar conexiones");
                        alert2.setContentText("Error: "+e.toString());
                        alert2.showAndWait();
                    }
                }
            }else
            {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Selección no válida");
                alert3.setHeaderText(null);
                alert3.setContentText("Debe seleccionar la fila de la venta donde quiera agregar la observación");
                alert3.showAndWait();
            }
        }
    }

    @FXML
    private void ELIMINARVENTA(ActionEvent event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Enviar solicitud para eliminar");
        alert.setHeaderText("Escribe la observación para esta venta sea ELIMINADA del corte");
        alert.setContentText("Escribe en el siguiente recuadro");
        
        TextArea textArea = new TextArea();
        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(textArea, 0, 0);
        alert.getDialogPane().setContent(content);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            PreparedStatement ps = null;
            Connection cnx = null;
            conexion conexbd = new conexion();
            conexbd.crearConexion();
            cnx = conexbd.getConexion();
            String sql = "INSERT INTO Solicitudes"
                + "(Solicitud_Venta,Solicitud_Ruta,Solicitud_Mensaje,Solicitud_Clave_Vendedor,Solicitud_Tipo) "
                + "VALUES(?,?,?,?,?)";
            
            int indez=TABLAVENTAS.getSelectionModel().getSelectedIndex();
            if(indez >= 0)
            {
                String Venta=TABLAVENTAS.getItems().get(indez).getVENTA();
                String Ruta=TABLAVENTAS.getItems().get(indez).getRUTA();
                try {
                    ps = cnx.prepareStatement(sql);
                    ps.setString(1, trim(Venta));
                    ps.setString(2, trim(Ruta));
                    ps.setString(3, trim(textArea.getText()));
                    ps.setString(4, trim(txtvendedor.getText()));
                    ps.setInt(5, 0);
                    ps.executeUpdate();
                    
                    Alert enviarEliminacion = new Alert(AlertType.INFORMATION);
                    enviarEliminacion.setTitle("Solicitud enviada");
                    enviarEliminacion.setHeaderText(null);
                    enviarEliminacion.setContentText("La solicitud fue enviada, quedará en espera de ser autorizada");
                    enviarEliminacion.showAndWait();
                } catch (SQLException e) {
                    Alert alert2 = new Alert(AlertType.WARNING);
                    alert2.setTitle("Advertencia");
                    alert2.setHeaderText("No se pudo guardar la información");
                    alert2.setContentText("Al parecer no se encontró el servidor "+e.toString());
                    alert2.showAndWait();
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        if (cnx != null) {
                            cnx.close();
                        }
                    } catch (SQLException e) {
                        Alert alert2 = new Alert(AlertType.WARNING);
                        alert2.setTitle("Advertencia");
                        alert2.setHeaderText("No se pudo cerrar conexiones");
                        alert2.setContentText("Error: "+e.toString());
                        alert2.showAndWait();
                    }
                }
            }else
            {
                Alert alert3 = new Alert(AlertType.INFORMATION);
                alert3.setTitle("Selección no válida");
                alert3.setHeaderText(null);
                alert3.setContentText("Debe seleccionar la fila de la venta donde quiera agregar la observación");
                alert3.showAndWait();
            }
        }
    }

    @FXML
    private void AccionSalir(MouseEvent event) {
        final Node source = (Node) event.getSource();
        final Stage nodo = (Stage) source.getScene().getWindow();
        nodo.close();
    }
}

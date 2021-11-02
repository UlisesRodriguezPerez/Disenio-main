package Vista.Controladores;

import Controlador.DragWindow;
import FileManager.ProcesadorSerializable;
import Modelo.Arma;
import Modelo.EnumPrototypes;
import Modelo.FactoryPattern.PrototypeFactory;
import Modelo.Personaje;
import Modelo.PrototypePattern.IPrototype;
import Vista.ControllerComun;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerPersonaje implements Initializable, DragWindow,ILoadImages {

    private final ControllerComun comun = ControllerComun.getInstance();

    @FXML
    private Pane contenedor;

    @FXML
    private Text modificarLabel;

    @FXML
    private Text crearLabel;

    @FXML
    private Button modificarButt;

    @FXML
    private Button agregarBut;

    @FXML
    private ImageView salir2;

    @FXML
    private ImageView salir;

    @FXML
    private TextField vidaTF;

    @FXML
    private TextField nombreTF;

    @FXML
    private TextField ataqueTF;

    @FXML
    private TextField camposTF;

    @FXML
    private TextField costoTF;

    @FXML
    private TextField nivelAparTF;

    @FXML
    private TextField nivelPersonajeTF;

    @FXML
    private ComboBox<String> armasPersonaje;

    @FXML
    public void agregarNiveles(ActionEvent event) throws IOException {
        if (!nivelPersonajeTF.getText().isEmpty()){
            comun.abrirVentana("FXMLS/escogerNiveles.fxml",this);
        }else
            System.out.println("Escriba un nivel");
    }

    @FXML
    public void cerrar(MouseEvent event) throws IOException {
        comun.cerrar(event, true);
        comun.abrirVentana("FXMLS/principal.fxml");
    }

    @FXML
    public void agregarNuevo(ActionEvent event){
        if (!nombreTF.getText().isEmpty()){
            setNombre();
            if (!ataqueTF.getText().isEmpty())
                setAtaque();
            if (!vidaTF.getText().isEmpty())
                setVida();
            if (!camposTF.getText().isEmpty())
                setCampos();
            if (!nivelAparTF.getText().isEmpty())
                setNivel();
            if (!costoTF.getText().isEmpty())
                setCosto();
            comun.getControlador().buildCurrentPersonaje();
        }else{
            System.out.println("Tiene que tener nombre");
        }
    }

    @FXML
    public void modificar(ActionEvent event){
        if (!ataqueTF.getText().isEmpty())
            setAtaque();
        if (!vidaTF.getText().isEmpty())
            setVida();
        if (!camposTF.getText().isEmpty())
            setCampos();
        if (!nivelAparTF.getText().isEmpty())
            setNivel();
        if (!costoTF.getText().isEmpty())
            setCosto();
        Personaje personaje = comun.getControlador().buildCurrentPersonaje();
        System.out.println( personaje.toString());
    }

    @Override
    public void onDraggedScene(Pane panelFather) {
        DragWindow.super.onDraggedScene(panelFather);
    }

    public void cerrar2(MouseEvent event) throws IOException {
        comun.cerrar(event, true);
    }

    public void setNombre(){ comun.getControlador().setNameBuilderPersonaje(nombreTF.getText());}

    public void setNivel(){ comun.getControlador().setNivelCurrentPersonaje(Integer.parseInt(nivelAparTF.getText()));}

    public void setCampos(){ comun.getControlador().setCamposCurrentPersonaje(Integer.parseInt(camposTF.getText())); }

    public void setAtaque(){ comun.getControlador().setAtaqueCurrentPersonaje(Integer.parseInt(ataqueTF.getText()));}

    public void setVida() { comun.getControlador().setVidaCurrentPersonaje(Integer.parseInt(vidaTF.getText()));}

    public void setCosto(){
        comun.getControlador().setCamposCostoPersonaje(Float.parseFloat(costoTF.getText()));
    }

    public void addArma(String armaNombre){
        Arma arma = (Arma) PrototypeFactory.getItem(armaNombre, 1, EnumPrototypes.ARMAS).get(0);// cambiar
        comun.getControlador().agregarArmaCurrentPersonaje(arma);
    }

    @FXML
    public void agregarArma(MouseEvent event){
        if (!armasPersonaje.getSelectionModel().getSelectedItem().isEmpty())
            addArma(armasPersonaje.getSelectionModel().getSelectedItem());
    }

    @Override
    public void loadImages(String accion, ArrayList<String> images) {
        comun.getControlador().addAparienciaBuilderPersonaje(Integer.parseInt(nivelPersonajeTF.getText()),accion,images);
    }

    @Override
    public EnumPrototypes getType() {
        return EnumPrototypes.PERSONAJES;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        if (!comun.getRutaDirectorio().isEmpty()) {
            HashMap<EnumPrototypes, HashMap<String, IPrototype>> loadedHash = ProcesadorSerializable.fileReader(comun.getRutaDirectorio());
            if (!loadedHash.isEmpty())
                PrototypeFactory.setPrototipos(loadedHash);
        }
        this.onDraggedScene(contenedor);
        ObservableList<String> armas = FXCollections.observableArrayList();
        ArrayList<String> nombres = PrototypeFactory.getAllKeys(EnumPrototypes.ARMAS);
        armas.addAll(nombres);
        armasPersonaje.setItems(armas);
        if (comun.isModificado()){
            comun.getControlador().createFromPersonajeExistente(comun.getNombreElemento());
            nombreTF.setDisable(true);
            salir2.setVisible(true);
            salir.setVisible(false);
            crearLabel.setVisible(false);
            modificarLabel.setVisible(true);
            agregarBut.setVisible(false);
            modificarButt.setVisible(true);
        }else{
            comun.getControlador().addBuilderPersonaje();
            nombreTF.setDisable(false);
            salir2.setVisible(false);
            salir.setVisible(true);
            crearLabel.setVisible(true);
            modificarLabel.setVisible(false);
            agregarBut.setVisible(true);
            modificarButt.setVisible(false);
        }
    }
}

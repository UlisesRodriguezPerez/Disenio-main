package Vista;

import Controlador.CreadorDePersonajes;
import Modelo.Apariencia.ManagerApariencia;
import Modelo.Arma;
import Modelo.BuilderPattern.IBuilder;
import Modelo.EnumPrototypes;
import Modelo.FactoryPattern.PrototypeFactory;
import Modelo.Personaje;
import Modelo.PrototypePattern.IPrototype;
import Modelo.WeaponManager;
import Vista.Controladores.ControllerNiveles;
import Vista.Controladores.ILoadImages;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ControllerComun {
    /**
     *
     *
     *
     *
     */
    private CreadorDePersonajes controladorLogico;
    private boolean modificado;
    private boolean armas;
    private static ControllerComun controllerComun ;
    private String rutaDirectorio;
    private String nombreElemento;
    private boolean esArma;

    public ControllerComun(){}

    public static ControllerComun getInstance(){
        if(controllerComun == null){
            controllerComun = new ControllerComun();
            controllerComun.controladorLogico = new CreadorDePersonajes();
        }
        return controllerComun;
    }

    public boolean isModificado() {
        return modificado;
    }

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

    public boolean isArmas() {
        return armas;
    }

    public void setArmas(boolean armas) {
        this.armas = armas;
    }

    public String getRutaDirectorio() {
        return rutaDirectorio;
    }

    public void setRutaDirectorio(String rutaDirectorio) {
        this.rutaDirectorio = rutaDirectorio;
    }

    public String getNombreElemento() {
        return nombreElemento;
    }

    public void setNombreElemento(String nombreElemento) {
        this.nombreElemento = nombreElemento;
    }

    public void cerrar(MouseEvent event, Boolean atras){
        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();
        stageActual.close();
        if (!atras)
            System.exit(0);
    }

    public void abrirVentana(String fxmlName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void abrirVentana(String fxmlName, ILoadImages type) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));;
        Parent root = fxmlLoader.load();
        ControllerNiveles pantallaNivel = fxmlLoader.getController();
        pantallaNivel.setPantalla(type);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }


    public CreadorDePersonajes getControlador(){
        return this.controladorLogico;
    }

    public void agregarControlador(CreadorDePersonajes controladorLogico){
        this.controladorLogico = controladorLogico;
    }

    public ArrayList<String> getListaAcciones(EnumPrototypes type){
        switch (type){
            case ARMAS:
                Arma arma = (Arma) controladorLogico.getCurrentBuilding();
                return arma.getApariencia().getAllActions();

            case PERSONAJES:
                Personaje personaje = (Personaje) controladorLogico.getCurrentBuilding();
                return personaje.getApariencia().getAllActions();
        }
        return null;
    }


}

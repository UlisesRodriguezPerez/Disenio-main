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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class PersonajesCreados implements Initializable, DragWindow {

    private final ControllerComun comun = ControllerComun.getInstance();
    private int nivel = 0;
    private Personaje personajeP;
    private Arma armaP;
    private Set<Integer> listaNivelesPersonajes;
    private Set<Integer> listaNivelesArma;
    private ArrayList<IPrototype> personajes;
    private ArrayList<IPrototype> armasA;

    @FXML
    private Text armasL;

    @FXML
    private Pane wait;

    @FXML
    private Pane armas;

    @FXML
    private Pane contenedor;

    @FXML
    private ComboBox<String> nombresPersonajes;

    @FXML
    private Text creadosL;

    @FXML
    private Pane detalles;

    @FXML
    private TextArea clones;

    @FXML
    private TextArea datosArmas;

    @FXML
    private TextField cantClonesTF;

    @FXML
    private TextField cantidadClonesATF;

    @FXML
    private ImageView imagenPersonaje;

    @FXML
    private ImageView imagenArma;

    @FXML
    private ImageView imagenArma2;

    @FXML
    private Text nivelLabel;

    @FXML
    private Text imganesPersonaje;

    @FXML
    public void verDetalles(ActionEvent event) throws FileNotFoundException {
        nivel = 0;
        if (listaNivelesPersonajes != null) {
            listaNivelesPersonajes.clear();
        }if (listaNivelesArma != null){
            listaNivelesArma.clear();
        }
        wait.setVisible(false);
        cargarPrimeravez();
        cargar();
    }

    @FXML
    public void siguiente(ActionEvent event) throws FileNotFoundException {
        try {
            nivel ++;
            String nivels = "Imágenes nivel " + listaNivelesPersonajes.toArray()[nivel];
            imganesPersonaje.setText(nivels);
            cargarImagenesPersonaje();
        }catch (NullPointerException | ArrayIndexOutOfBoundsException e){
            nivel = -1;
            System.out.println("No hay más imágemes");
        }
    }

    @FXML
    public void siguienteArmas(ActionEvent event){
        try {
            nivel ++;
            String nivels = "Imágenes nivel " + listaNivelesArma.toArray()[nivel];
            nivelLabel.setText(nivels);
            cargarImagenesArma();
        }catch (NullPointerException | ArrayIndexOutOfBoundsException | FileNotFoundException e){
            nivel = -1;
            System.out.println("No hay más imágenes para mostrar");
        }

    }

    public void cargarPrimeravez(){
        if (!comun.isArmas()) {
            personajes = PrototypeFactory.getItem(nombresPersonajes.getSelectionModel().getSelectedItem(), 1, EnumPrototypes.PERSONAJES);
            personajeP = (Personaje) personajes.get(0);
            listaNivelesPersonajes = personajeP.getApariencia().getImagenes().keySet();
            listaNivelesArma = personajeP.getArmas().getArmaActual().getApariencia().getImagenes().keySet();
        }else{
            armasA = PrototypeFactory.getItem(nombresPersonajes.getSelectionModel().getSelectedItem(), 1, EnumPrototypes.ARMAS);
            armaP = (Arma) armasA.get(0);
            listaNivelesArma = armaP.getApariencia().getImagenes().keySet();
        }
    }

    public void cargar() throws FileNotFoundException {
        if (!comun.isArmas()){
            StringBuilder mostrar = new StringBuilder("PERSONAJE\n");
            int i = 0;
            if (!cantClonesTF.getText().isEmpty()){
                int cantClones = Integer.parseInt(cantClonesTF.getText());
                personajes = PrototypeFactory.getItem(nombresPersonajes.getSelectionModel().getSelectedItem(), cantClones, EnumPrototypes.PERSONAJES);
            }
            for (IPrototype per : personajes){
                if (personajes.get(0) != per)
                    mostrar.append("\n*****************************************************\nCLON "+ i + "\n");
                i++;
                Personaje personaje = (Personaje) per;
                mostrar.append("\tNombre\t\t\t");
                mostrar = mostrar.append(personaje.getNombre());
                mostrar.append("\n\tAtaque\t\t\t");
                mostrar.append(personaje.getataque());
                mostrar.append("\n\tCampos\t\t\t");
                mostrar.append(personaje.getCampos());
                mostrar.append("\n\tNivel aparición\t");
                mostrar.append(personaje.getnivelAparicion());
                mostrar.append("\n\tVida\t\t\t");
                mostrar.append(personaje.getVida());
                mostrar.append("\n\tCosto\t\t\t");
                mostrar.append(personaje.getCosto());
            }
            cargarImagenesPersonaje();
            clones.setText(String.valueOf(mostrar));
            detalles.setVisible(true);
            armas.setVisible(false);
        }else{
            StringBuilder mostrar = new StringBuilder("ARMA\n");
            int i = 0;

            if (!cantidadClonesATF.getText().isEmpty()) {
                int cantClones = Integer.parseInt(cantidadClonesATF.getText());
                armasA = PrototypeFactory.getItem(nombresPersonajes.getSelectionModel().getSelectedItem(), cantClones, EnumPrototypes.ARMAS);
            }
            for (IPrototype arm : armasA){
                if (armasA.get(0) != arm)
                    mostrar.append("\n*****************************************************\nCLON "+ i + "\n");
                i++;
                Arma arm1 = (Arma) arm;
                mostrar.append("\tNombre\t\t\t\t");
                mostrar = mostrar.append(arm1.getNombre());
                mostrar.append("\n\tDaño\t\t\t\t");
                mostrar.append(arm1.getDano());
                mostrar.append("\n\tAlcance\t\t\t\t");
                mostrar.append(arm1.getAlcance());
                mostrar.append("\n\tRango de explosión\t");
                mostrar.append(arm1.getRangoExplosion());
            }
            cargarImagenesArma();
            datosArmas.setText(String.valueOf(mostrar));
            detalles.setVisible(false);
            armas.setVisible(true);
        }
    }

    public void cargarImagenesPersonaje() throws FileNotFoundException {
        String path = personajeP.getApariencia().getImagenes().get(listaNivelesPersonajes.toArray()[nivel]).getDefault().get(0);
        InputStream personaje = new FileInputStream(path);
        Image personajeImagen = new Image(personaje);
        imagenPersonaje.setImage(personajeImagen);
        try{
            String pathArma = personajeP.getArmas().getArmaActual().getApariencia().getImagenes().get(listaNivelesArma.toArray()[nivel]).getDefault().get(0);
            InputStream arma = new FileInputStream(pathArma);
            Image armaImagen = new Image(arma);
            imagenArma.setImage(armaImagen);
        }catch (NullPointerException | ArrayIndexOutOfBoundsException e){
            System.out.println("No hay arma");
        }

    }

    public void cargarImagenesArma() throws FileNotFoundException {
        String path = armaP.getApariencia().getImagenes().get(listaNivelesArma.toArray()[nivel]).getDefault().get(0);
        InputStream stream = new FileInputStream(path);
        Image image = new Image(stream);
        imagenArma2.setImage(image);
    }

    @FXML
    public void siguienteArma(ActionEvent event) throws FileNotFoundException {
        personajeP.getArmas().nextArma();
        cargarImagenesPersonaje();
    }

    @FXML
    public void cargarClones(KeyEvent key) throws FileNotFoundException {
        if (key.getCode().equals(KeyCode.ENTER))
            cargar();
    }

    @FXML
    public void salir(MouseEvent event) throws IOException {
        comun.cerrar(event, true);
        comun.abrirVentana("FXMLS/principal.fxml");
    }

    @FXML
    public void modificar(ActionEvent event) throws IOException {
        if (!nombresPersonajes.getSelectionModel().getSelectedItem().isEmpty())
            comun.setNombreElemento(nombresPersonajes.getSelectionModel().getSelectedItem());

        if (!comun.isArmas()) {
            comun.setModificado(true);
            comun.abrirVentana("FXMLS/ventanaPersonaje.fxml");
            detalles.setVisible(true);
            wait.setVisible(false);
            armas.setVisible(false);
        }
        else{
            comun.setArmas(true);
            comun.abrirVentana("FXMLS/crearArma.fxml");
            detalles.setVisible(false);
            wait.setVisible(false);
            armas.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String nivels = "Imágenes nivel " + nivel;
        nivelLabel.setText(nivels);
        imganesPersonaje.setText(nivels);
        PrototypeFactory.setPrototipos(ProcesadorSerializable.fileReader(comun.getRutaDirectorio()));
        detalles.setVisible(false);
        wait.setVisible(true);
        armas.setVisible(false);
        this.onDraggedScene(contenedor);
        try {
            ObservableList<String> personajes = FXCollections.observableArrayList();
            ArrayList<String> nombres;
            if (comun.isArmas()) {
                nombres = PrototypeFactory.getAllKeys(EnumPrototypes.ARMAS);
                armasL.setVisible(true);
                creadosL.setVisible(false);
            }else{
                nombres = PrototypeFactory.getAllKeys(EnumPrototypes.PERSONAJES);
                armasL.setVisible(false);
                creadosL.setVisible(true);
            }
            personajes.addAll(nombres);
            nombresPersonajes.setItems(personajes);
        }
        catch (NullPointerException e){
            System.out.println("No hay personajes");
        }

    }
}

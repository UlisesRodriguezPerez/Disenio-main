package Controlador;

import Modelo.Apariencia.LvlImages;
import Modelo.Apariencia.ManagerApariencia;
import Modelo.Arma;
import Modelo.BuilderPattern.IBuilder;
import Modelo.EnumPrototypes;
import Modelo.FactoryPattern.PrototypeFactory;
import Modelo.Personaje;
import Modelo.PrototypePattern.IPrototype;
import Modelo.WeaponManager;

import java.util.ArrayList;

public class CreadorDePersonajes {


    IBuilder currentBuilding;



    ////////////////////////////////////////////////////Metodos de clonacion////////////////////////////////////////////////////


    public ArrayList<Personaje> getClonesPersonajes(int clones,String nombre){
        ArrayList<Personaje> listaDeClones = new ArrayList<>();
        for(IPrototype prototype: PrototypeFactory.getItem(nombre,clones,EnumPrototypes.PERSONAJES)){
            listaDeClones.add((Personaje) prototype);
        }
        return listaDeClones;
    }

    public ArrayList<Arma> getClonesArmas(int clones,String nombre) {
        ArrayList<Arma> listaDeClones = new ArrayList<>();
        for (IPrototype prototype : PrototypeFactory.getItem(nombre, clones, EnumPrototypes.ARMAS)) {
            listaDeClones.add((Arma) prototype);
        }
        return listaDeClones;

    }

    //TODO:Validacion tonta para usar el resto de metodos
    //Usar en interfaz para controlar que entra al Prototype. Que pasa con un build de nombre vacio?
    //Obligar al usuario a meter algunos datos como nombre al menos?
    public boolean isBuilding(){
        return currentBuilding!= null;
    }

    ////////////////////////////////////////////////////Metodos para reusar personajes////////////////////////////////////////////////////

    //El metodo se crea porque el contralador solo maneje una referencia a builder y asi no haya que agregar nada mas y todo funcione como una sola cosa.
    //Estos metodos se pueden usar tanto para modificar como para la creacion de un nuevo personaje a base de otro
    //Aprovechando el builder para modificar lo que ya esxiste pero con una nueva referencia.
    //Cuando se guarda en el hash, el id se encarga de sobreescribir o de guardar un nuevo personaje
    public void createFromPersonajeExistente(String personaje){
        //A partir de este ya se puede usar el builder como si fuerea desde cero.
        Personaje protoPersonaje = (Personaje)PrototypeFactory.getItem(personaje,1,EnumPrototypes.PERSONAJES).get(0);
        currentBuilding = protoPersonaje.getBuildable();
    }
    public void createFromArmaExistente(String arma){
        Arma protoArma = (Arma)PrototypeFactory.getItem(arma,1,EnumPrototypes.ARMAS).get(0);
        currentBuilding = protoArma.getBuildable();
    }

    ////////////////////////////////////////////////////Metodos de BuilderPersonaje////////////////////////////////////////////////////

    public void addBuilderPersonaje() {
        this.currentBuilding = new Personaje.BuilderPersonaje();
    }

    public void setNameBuilderPersonaje(String nombre){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setNombre(nombre);
    }

    public void setNivelCurrentPersonaje(int nivel){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setnivelAparicion(nivel);
    }

    public void setCamposCurrentPersonaje(int campos){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setCampos(campos);
    }

    public void setCamposCostoPersonaje(float costo){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setCosto(costo);
    }

    public void setAtaqueCurrentPersonaje(int golpes){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setCantGolpesRecibos(golpes);
    }

    public void setVidaCurrentPersonaje(int vida){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.setVida(vida);
    }

    public void agregarArmaCurrentPersonaje(Arma arma){
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.addArma(arma);
    }

    public void addAparienciaBuilderPersonaje(int nivel,String accion,ArrayList<String> imagenes){
        LvlImages imagenPorAccion = new LvlImages(accion,imagenes);
        Personaje.BuilderPersonaje nowBuilding = (Personaje.BuilderPersonaje) currentBuilding;
        this.currentBuilding = nowBuilding.addApariencia(nivel,imagenPorAccion);
        System.out.println("Logico "+imagenes.get(0));
    }




    //TODO:Implementar en GUI
    public Personaje buildCurrentPersonaje(){
        Personaje nuevoPersonaje = (Personaje)currentBuilding.build();
        PrototypeFactory.addItem(nuevoPersonaje.getNombre(),nuevoPersonaje,EnumPrototypes.PERSONAJES);
        return nuevoPersonaje;
    }

    ////////////////////////////////////////////////////Metodos de BuilderArma//////////////////////////////////////////////////


    //TODO:Implementar en GUI
    public void addBuilderArma() {
        this.currentBuilding = new Arma.BuilderArma();
    }

    public void setNameBuilderArma(String nombre){
        Arma.BuilderArma nowBuilding = (Arma.BuilderArma) currentBuilding;
        this.currentBuilding = nowBuilding.setNombre(nombre);
    }

    public void setAlcanceBuilderArma(int alcance){
        Arma.BuilderArma nowBuilding = (Arma.BuilderArma) currentBuilding;
        this.currentBuilding = nowBuilding.setAlcance(alcance);
    }

    public void setDanoBuilderArma(int dano){
        Arma.BuilderArma nowBuilding = (Arma.BuilderArma) currentBuilding;
        this.currentBuilding = nowBuilding.setDano(dano);
    }

    public void setRangoBuilderArma(int rangoExplosion){
        Arma.BuilderArma nowBuilding = (Arma.BuilderArma) currentBuilding;
        this.currentBuilding = nowBuilding.setRangoExplosion(rangoExplosion);
    }

    public void addAparienciaArma(int nivel,String accion,ArrayList<String> currentImages)
    {
        Arma.BuilderArma nowBuilding = (Arma.BuilderArma) currentBuilding;
        this.currentBuilding = nowBuilding.addApariencia(nivel,accion,currentImages);
    }

    public Arma buildCurrentArma(){
        Arma arma = (Arma)this.currentBuilding.build();
        PrototypeFactory.addItem(arma.getNombre(),arma, EnumPrototypes.ARMAS);
        return arma;
    }

    public IBuilder getCurrentBuilding(){
        return this.currentBuilding;
    }

}

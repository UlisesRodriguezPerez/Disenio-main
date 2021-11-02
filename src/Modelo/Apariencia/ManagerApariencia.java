package Modelo.Apariencia;

import Modelo.PrototypePattern.IPrototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerApariencia implements IPrototype<ManagerApariencia> {

    private static final long serialVersionUID = 6642325130412386279L;

    HashMap<Integer,LvlImages>  aparienciasDisponibles;

    public ManagerApariencia(){
        this.aparienciasDisponibles = new HashMap<>();
    }

    public ManagerApariencia(HashMap<Integer,LvlImages> aparienciasDisponibles){
        this.aparienciasDisponibles = aparienciasDisponibles;
    }

    @Override
    public ManagerApariencia clone() {
        HashMap<Integer,LvlImages> copiaApariencias = new HashMap<>();
        for(Map.Entry<Integer,LvlImages> apariencia : aparienciasDisponibles.entrySet()) {
            Integer key = apariencia.getKey();
            LvlImages value = new LvlImages();
            copiaApariencias.put(key, value);
        }
        return new ManagerApariencia(copiaApariencias);
    }

    @Override
    public ManagerApariencia deepClone() {
        HashMap<Integer,LvlImages> copiaApariencias = new HashMap<>();
        for(Map.Entry<Integer,LvlImages> apariencia : aparienciasDisponibles.entrySet()) {
            Integer key = apariencia.getKey();
            LvlImages value = apariencia.getValue().deepClone();
            copiaApariencias.put(key, value);
        }
        return new ManagerApariencia(copiaApariencias);
    }

    public void addApariencia(int nivel,LvlImages imagenes){
        this.aparienciasDisponibles.put(nivel, imagenes);
    }

    //Agrega un nuevo LvlImages
    public void addApariencia(int nivel,String nombre,ArrayList<String> urls){
        LvlImages images = this.aparienciasDisponibles.get(nivel);
        if(!(images == null))
        {images.addApariencia(nombre, urls);}
        else
        {this.aparienciasDisponibles.put(nivel,new LvlImages(nombre,urls));}
    }

    //Retorna un LvlImages(Diferentes set de imagenes por accion) por nivel.
    public LvlImages getImagenesPorNivel(int nivel){
        return this.aparienciasDisponibles.get(nivel);
    }

    //Retorna una lista de imagenes por nombre
    public ArrayList<String> getImagenPorNivelNombre(int nivel,String nombre){
        return getImagenesPorNivel(nivel).getAparienciasPorNivel(nombre);
    }

    //Retornara todo el hash de imagenes disponibles. Se podira hacer que solo retorne imagenes
    public HashMap<Integer, LvlImages> getImagenes(){
        return aparienciasDisponibles;
    }

    public ArrayList<String> getAllActions(){
        ArrayList<String> listaAcciones = new ArrayList<>();
        for (LvlImages imagenesPorAccion:aparienciasDisponibles.values()) {
            for(String accion:imagenesPorAccion.getAparienciasPorNivel().keySet()){
                listaAcciones.add(accion);
            }
        }
        return listaAcciones;
    }

    @Override
    public String toString() {
        return "ManagerApariencia" +
                "aparienciasDisponibles=" + aparienciasDisponibles;
    }
}

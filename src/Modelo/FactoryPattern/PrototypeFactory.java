package Modelo.FactoryPattern;

import Modelo.EnumPrototypes;
import Modelo.PrototypePattern.IPrototype;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class PrototypeFactory {



    public static HashMap<EnumPrototypes, HashMap<String, IPrototype>> prototipos = new HashMap<>();

    public static void setPrototipos(HashMap<EnumPrototypes, HashMap<String, IPrototype>> proto){
        prototipos = proto;
    }

    public static void initiliazeFactory() {
        for(EnumPrototypes element : EnumPrototypes.values()){
            prototipos.put(element, new HashMap<>());
        }
    }

    public static HashMap<EnumPrototypes, HashMap<String, IPrototype>> getPrototipos() {
        return prototipos;
    }

    public static ArrayList<IPrototype> getItem(String nombrePrototipo, int cant, EnumPrototypes tipoHash) {
        ArrayList<IPrototype> peticiones = new ArrayList<>();
        for (int i = 0; i < cant; i++) {
            peticiones.add(((IPrototype) prototipos.get(tipoHash).get(nombrePrototipo).deepClone()));
        }
        return peticiones;
    }


    public static void addItem(String nombre, IPrototype item, EnumPrototypes tipoHash){
        prototipos.get(tipoHash).put(nombre,item);
    }

    public static ArrayList<IPrototype>getAll(EnumPrototypes tipoHash){
        ArrayList<IPrototype> listaItems = new ArrayList<>();

        Set<String> llaves = prototipos.get(tipoHash).keySet();

        for(String llave : llaves) {
            listaItems.add(((IPrototype)prototipos.get(tipoHash).get(llave).deepClone()));
        }
        return listaItems;
    }

    public static ArrayList<String> getAllKeys(EnumPrototypes tipoHash){
        ArrayList<String> keys = new ArrayList<>();

        Set<String> llaves = prototipos.get(tipoHash).keySet();

        for(String llave : llaves) {
            keys.add(llave);
        }
        return keys;
    }

    public static HashMap<EnumPrototypes, HashMap<String, IPrototype>> getHash(){
        return prototipos;
    }

}

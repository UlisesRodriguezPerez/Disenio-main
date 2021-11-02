package FileManager;

import Modelo.EnumPrototypes;
import Modelo.Personaje;
import Modelo.PrototypePattern.IPrototype;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcesadorSerializable {

    public ProcesadorSerializable() {}

    public static void fileWriter(HashMap<EnumPrototypes, HashMap<String, IPrototype>> prototipos, String path) {

        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(prototipos);
            out.close();
            fileOut.close();
            System.out.println("Database saved successfully at: " + path);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap fileReader(String path) {

        HashMap<EnumPrototypes, HashMap<String, IPrototype>> prototipos = new HashMap<>();
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            prototipos = (HashMap<EnumPrototypes, HashMap<String, IPrototype>>) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded DataBase successfully from: " + path);
        }catch (FileNotFoundException e){
            System.out.println("No hay archivo");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("File not found");
            c.printStackTrace();
        }
        return prototipos;
    }

}
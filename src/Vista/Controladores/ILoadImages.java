package Vista.Controladores;

import Modelo.EnumPrototypes;

import java.util.ArrayList;

public interface ILoadImages {
    public void loadImages(String accion,ArrayList<String> images);
    public EnumPrototypes getType();
}

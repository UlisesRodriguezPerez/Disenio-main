package Modelo.PrototypePattern;

import java.io.Serializable;

public interface IPrototype<T> extends Serializable {
    /**
    *
    *
    *
    */

    T clone();
    T deepClone();
}
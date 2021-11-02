package Modelo;

import Modelo.Apariencia.LvlImages;
import Modelo.Apariencia.ManagerApariencia;
import Modelo.BuilderPattern.IBuilder;
import Modelo.PrototypePattern.IPrototype;

import java.util.ArrayList;

public class Personaje implements IPrototype<Personaje> {

    private static final long serialVersionUID = 2439140702371300606L;

    private String nombre;
    private ManagerApariencia apariencia;
    private int vida;
    private int ataque;
    private int nivel;
    private int campos;
    private int nivelAparicion;
    private float costo;
    private WeaponManager armas;
    boolean alive;
    int posX;
    int posY;


    public Personaje(String nombre, ManagerApariencia apariencia, int vida, int ataque, int nivel, int campos, int nivelAparicion, float costo,WeaponManager armas) {
        this.nombre = nombre;
        this.apariencia = apariencia;
        this.vida = vida;
        this.ataque = ataque;
        this.nivel = nivel;
        this.campos = campos;
        this.nivelAparicion = nivelAparicion;
        this.costo = costo;
        this.armas = armas;
        this.alive = true;
        this.posX = 0;
        this.posY = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ManagerApariencia getApariencia() {
        return apariencia;
    }

    public void setApariencia(ManagerApariencia apariencia) {
        this.apariencia = apariencia;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getataque() {
        return ataque;
    }

    public void setataque(int ataque) {
        this.ataque = ataque;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getCampos() {
        return campos;
    }

    public void setCampos(int campos) {
        this.campos = campos;
    }

    public int getnivelAparicion() {
        return nivelAparicion;
    }

    public void setnivelAparicion(int nivelAparicion) {
        this.nivelAparicion = nivelAparicion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public WeaponManager getArmas() {
        return armas;
    }

    public void setArmas(WeaponManager armas) {
        this.armas = armas;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public int[] getPosition(){
        int[] position= new int[2];
        position[0] = this.posX;
        position[1] = this.posY;
        return position;
    }

    public void setPosition(int x,int y){
        this.posX = x;
        this.posY = y;
    }

    public void setPosX(int x){
        this.posX = x;
    }

    public void setPosY(int y){
        this.posY = y;
    }

    ///Metodos Dummy

    public void atacar(ArrayList<Personaje> objetivos,ArrayList<Arma> armas){
        if(armas != null){
            for (Arma arma:armas){
                for (Personaje objetivo:objetivos ){
                    objetivo.danar(arma.getDano());
                }
            }
        }
        else{
            for(Personaje objetivo:objetivos){
                objetivo.danar(ataque);
            }
        }
    }

    public void moverse(int x,int y){
        setPosition(x,y);
    }

    public void morir(){
        setAlive(false);
        this.vida = 0;
    }

    public void levelUp(){
        this.nivel ++;
    }

    public void danar(int dano){
        this.vida -= dano;
        if (vida<=0){
            morir();
        }
    }

    ///BUILDER
    public static class BuilderPersonaje implements IBuilder<Personaje> {
        private String nombre;
        private ManagerApariencia apariencia = new ManagerApariencia();
        private int vida;
        private int ataque;
        private int nivel;
        private int campos;
        private int nivelAparicion;
        private float costo;
        private WeaponManager armas = new WeaponManager();

        public BuilderPersonaje(){

        }

        //Duda al profe de que si estoy compartiendo referencias en los tipos nativos.
        public BuilderPersonaje(Personaje personaje) {
            this.nombre = personaje.nombre;
            this.apariencia = personaje.apariencia.deepClone();
            this.vida = personaje.vida;
            this.ataque = personaje.ataque;
            this.nivel = personaje.nivel;
            this.campos = personaje.campos;
            this.nivelAparicion = personaje.nivelAparicion;
            this.costo = personaje.costo;
            this.armas = personaje.armas.deepClone();
        }

        public BuilderPersonaje setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public BuilderPersonaje setApariencia(ManagerApariencia apariencia) { //revisar
            this.apariencia = apariencia;
            return this;
        }

        public BuilderPersonaje setVida(int vida) {
            this.vida = vida;
            return this;
        }

        public BuilderPersonaje setnivelAparicion(int nivelAparicion){
            this.nivelAparicion = nivelAparicion;
            return this;
        }

        public BuilderPersonaje setCantGolpesRecibos(int ataque) {
            this.ataque = ataque;
            return this;
        }

        public BuilderPersonaje setNivel(int nivel) {
            this.nivel = nivel;
            return this;
        }

        public BuilderPersonaje setCampos(int campos) {
            this.campos = campos;
            return this;
        }

        public BuilderPersonaje addApariencia(int nivel,String nombre, ArrayList<String> imagenes) {
            this.apariencia.addApariencia(nivel,nombre,imagenes);
            return this;
        }

        public BuilderPersonaje addApariencia(int nivel, LvlImages imagenes) {
            this.apariencia.addApariencia(nivel,imagenes);
            return this;
        }

        public BuilderPersonaje setCosto(float costo) {
            this.costo = costo;
            return this;
        }

        public BuilderPersonaje addArma(Arma arma){
            this.armas.addArma(arma);
            return this;
        }

        public BuilderPersonaje setArmas(WeaponManager armas){
            this.armas = armas;
            return this;
        }

        @Override
        public Personaje build() {
            return new Personaje(nombre, apariencia, vida, ataque, nivel, campos, nivelAparicion, costo,armas);
        }
    }

    @Override
    public Personaje clone() {
        return new Personaje(nombre, new ManagerApariencia(), vida, ataque, nivel, campos, nivelAparicion, costo,armas.clone());
    }

    @Override
    public Personaje deepClone() {
        return new Personaje(nombre, this.apariencia.deepClone(), vida, ataque, nivel, campos, nivelAparicion, costo,armas.deepClone());
    }

    @Override
    public String toString() {
        return "Personaje:" +
                "Nombre:'" + nombre  + '\n' +
                ", Apariencia:" + apariencia + '\n' +
                ", Vida:" + vida + '\n' +
                ", Ataque:" + ataque + '\n' +
                ", Nivel:" + nivel + '\n' +
                ", Campos:" + campos + '\n' +
                ", NivelAparicion:" + nivelAparicion + '\n' +
                ", Costo:" + costo
                +", Armas: " + armas;
    }

    public BuilderPersonaje getBuildable(){
        return new BuilderPersonaje(this);
    }

}

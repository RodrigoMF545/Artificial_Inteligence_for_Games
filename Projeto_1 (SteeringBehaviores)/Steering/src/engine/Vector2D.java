package engine;

import java.util.Vector;

import engine.Car;
import engine.Game;
import engine.GameObject;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D (double x, double y){
        this.x = x;
        this.y = y;
    }

    //subtração do vector.this - vector.other
    public Vector2D subtração(Vector2D other){
        Vector2D n = new Vector2D(x, y);

        n.x = this.x - other.x;
        n.y = this.y - other.y;

        return n;
    }

    //adição do vector.this + vector.other
    public Vector2D adicao (Vector2D other){
        Vector2D n = new Vector2D(x, y);

        n.x = this.x + other.x;
        n.y = this.y + other.y;

        return n;
    }

    //retornar umnovo valor que é(this * factor (alpha))
    public Vector2D times (double alpha){
        Vector2D n = new Vector2D(x, y);

        n.x = alpha * this.x;
        n.y = alpha * this.y;

        return n;
    }

    //calcular o produto interno entre vetores
    public double inner_product (Vector2D other){
        return (this.x * other.x) + (this.y * other.y);   
    }

    //calculando a norma euclediana de 2 vetores (magnitude)
    public double norma (){
        return Math.sqrt(this.inner_product(this));
    }

    //calcula a distancia euclediana entre Vector.this and Vector.other
    public double distanceTo(Vector2D other){
        return this.subtração(other).norma();
    }

    //retornar um vetor unitario
    public Vector2D direction(){
        if(this.norma() == 0) throw new RuntimeException("Zero-vector has no derection");
        return this.times(1.0/this.norma());
        
    }

    public Vector2D normalize () {

        Vector2D n = new Vector2D(x, y);   
        
        n.x = this.x/this.norma();
        n.y = this.y/this.norma();

        return n;
    
    }

    public static Vector2D Normalize(Vector2D v) {
		Vector2D n = new Vector2D(v.x, v.y);

		n.x = v.x / v.norma();
		n.y = v.y / v.norma();
		
		return n;
	}


}

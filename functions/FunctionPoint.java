package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double ox){
        this.x = ox;
    }

    public void setY(double oy){
        this.y = oy;
    }
    
    public FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }

    public FunctionPoint(double ox, double oy){
        this.x = ox;
        this.y = oy;
    }

    public FunctionPoint(FunctionPoint point){
        this.x=point.getX();
        this.y=point.getY();
    }
}

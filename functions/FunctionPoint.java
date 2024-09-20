package functions;

public class FunctionPoint{
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

    @Override
    public String toString(){
        return ('(' + getX() + "; " + getY() + ')');
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (obj==null || this.getClass()!=obj.getClass()){
            return false;
        }
        // https://www.geeksforgeeks.org/double-compare-method-in-java-with-examples/
        FunctionPoint other = (FunctionPoint) obj;
        return Double.compare(this.x, other.x)==0 && Double.compare(this.y, other.y)==0;
    }

    @Override
    public int hashCode(){
        // Получаю битовое представление координаты x
        long bitsX = Double.doubleToLongBits(x);
        // Извлекаю младшие 32 бита
        int xLow = (int) bitsX;
        // Извлекаю старшие 32 бита
        int xHigh = (int) (bitsX >>> 32);

        // Получаю битовое представление координаты y
        long bitsY = Double.doubleToLongBits(y);
        // Извлекаю младшие 32 бита
        int yLow = (int) bitsY;
        // Извлекаю старшие 32 бита
        int yHigh = (int) (bitsY >>> 32);

        // Выполняю побитовое исключающее ИЛИ всех int-значений
        return xLow ^ xHigh ^ yLow ^ yHigh;
    }

    @Override
    public FunctionPoint clone() {
        return new FunctionPoint(this.x, this.y);
    }
}

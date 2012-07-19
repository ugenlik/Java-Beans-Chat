package serDeser.util;

@SuppressWarnings("serial")
public class Mesh extends MyBean {
    private Integer x_coordinate;
    private Integer y_coordinate;
    private Double value;
       
    public Mesh()
    {
    }
       
    public Mesh(int x, int y, double value)
    {
    	x_coordinate = new Integer(x);
    	y_coordinate = new Integer(y);
    	this.value = new Double(value);
    }
    
    public String toString()
    {
    	StringBuilder sb = new StringBuilder();
    	sb.append("X coordinate: " + x_coordinate + "\n");
    	sb.append("Y coordinate: " + y_coordinate + "\n");
    	sb.append("Value: " + value + "\n");
    	return sb.toString();
    }

	public void setX_coordinate(Integer x_coordinate) {
		this.x_coordinate = x_coordinate;
	}

	public Integer getX_coordinate() {
		return x_coordinate;
	}

	public void setY_coordinate(Integer y_coordinate) {
		this.y_coordinate = y_coordinate;
	}

	public Integer getY_coordinate() {
		return y_coordinate;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}
}

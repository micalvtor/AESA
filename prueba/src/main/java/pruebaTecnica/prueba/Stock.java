package pruebaTecnica.prueba;

public class Stock {
	private int sizeId;
    private int quantity;
    
	public Stock(int sizeId, int quantity) {
		super();
		this.sizeId = sizeId;
		this.quantity = quantity;
	}
	
	public int getSizeId() {
		return sizeId;
	}
	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}

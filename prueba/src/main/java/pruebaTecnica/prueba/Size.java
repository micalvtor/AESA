package pruebaTecnica.prueba;

public class Size {
	private int id;
    private int productId;
    private boolean backSoon;
    private boolean special;
	public Size(int id, int productId, boolean backSoon, boolean special) {
		super();
		this.id = id;
		this.productId = productId;
		this.backSoon = backSoon;
		this.special = special;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public boolean isBackSoon() {
		return backSoon;
	}
	public void setBackSoon(boolean backSoon) {
		this.backSoon = backSoon;
	}
	public boolean isSpecial() {
		return special;
	}
	public void setSpecial(boolean special) {
		this.special = special;
	}
}

package defaultPackage;

public abstract class Book {
	private String name;
	private String autor;
	private int releaseYear;
	private boolean accessibility;
	
	public Book(String name, String autor, int releaseYear, boolean accessibility) {
		super();
		this.name = name;
		this.autor = autor;
		this.releaseYear = releaseYear;
		this.accessibility = accessibility;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public boolean getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(boolean accessibility) {
		this.accessibility = accessibility;
	}
	

}

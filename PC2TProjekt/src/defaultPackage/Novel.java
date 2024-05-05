package defaultPackage;

public class Novel extends Book {
	private String genre;
	public Novel(String name, String autor, int releaseYear, boolean accessibility, String genre) {
		super(name, autor, releaseYear, accessibility);
		this.genre=genre;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	

}

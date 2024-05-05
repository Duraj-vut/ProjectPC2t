package defaultPackage;

public class TextBook extends Book {
	private int recommendedYear;
	public TextBook(String name, String autor, int releaseYear, boolean accessibility,int recommendedYear) {
		super(name, autor, releaseYear, accessibility);
		this.recommendedYear=recommendedYear;
	}
	public int getRecommendedYear() {
		return recommendedYear;
	}
	public void setRecommendedYear(int recommendedYear) {
		this.recommendedYear = recommendedYear;
	}
	

}

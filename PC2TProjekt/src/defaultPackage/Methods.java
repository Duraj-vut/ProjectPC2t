package defaultPackage;

import java.util.ArrayList;
import java.util.Scanner;

final class Methods {
	static Scanner sc = new Scanner(System.in);
	public Methods() {};
	public static String duplicity(ArrayList<Book> database ) {
		boolean duplicity=false;
    	String name;
		do {
			duplicity=false;
			System.out.print("Zadajte nazov knihy: ");
			name = sc.nextLine();
			for (Book book : database) {
				if(book.getName().equals(name)) {
					duplicity=true;
					System.out.println("Kniha s tymto menom uz existuje");								
				}
			}
		}while(duplicity==true);
		return name;
	}
	public static String searchName(ArrayList<Book> database ) {
		boolean found=false;
    	String name;
		do {
			System.out.print("Zadajte nazov knihy: ");
			name = sc.nextLine();
			for (Book book : database) {
				if(book.getName().equals(name)) {
					found=true;												
				}
			}
			if(found==false) {
				System.out.println("Kniha s tymto menom nebola najdena");
			}
		}while(found==false);
		return name;
	}
	
}

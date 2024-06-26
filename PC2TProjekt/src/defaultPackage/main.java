package defaultPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.sql.Statement;

public class main {
    public static void main(String[] args) {
    	
		String url = "jdbc:mysql://127.0.0.1:3306/Books";
		String username = "root";
		String password = "13234";
		String selectQuery = "SELECT * from book";
		Scanner sc = new Scanner(System.in);
		ArrayList<Book> database = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {
			while (resultSet.next()) {
				String name = resultSet.getString("bookName");
				String autor = resultSet.getString("author");
				int releaseYear = resultSet.getInt("releaseYear");
				boolean accessibility = resultSet.getBoolean("accessibility");
				String bookClass = resultSet.getString("class");
				String genre = resultSet.getString("genre");
				int year = resultSet.getInt("recYear");
				if (bookClass.equals("NOVEL")) {					
					Novel book = new Novel(name, autor, releaseYear, accessibility, genre);
					database.add(book);
				} else if(bookClass.equals("TEXTBOOK")) {
					
					TextBook book = new TextBook(name, autor, releaseYear, accessibility, year);
					database.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("K databaze sa nepodarilo pripojit");
			e.printStackTrace();
		}

        while (true) {
            System.out.print("    MENU\n1.)Pridanie knihy\n2.)Uprava knihy\n3.)Zmazanie knihy\n4.)Zmenit stav knihy pozicana/vratena\n5.)Vypis knih\n6.)Vyhladanie knihy\n7.)Vypis vsetkych knih podla autora\n8.)Vypis podla zanru\n9.)Vypis vsetkych pozicanych knih\n10.)Ulozenie informacii o vybranej knihe do suboru\n11.)Nacitanie informacii o knihe\n\n12.)Ukoncit program\nvasa volba:");
            try {
                int option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        try {
                        	System.out.println("Pridanie knihy");
                        	String name=Methods.duplicity(database);
                            System.out.print("Zadajte meno autora: ");
                            String autor = sc.nextLine();
                            System.out.print("Zadajte rok vydania: ");
                            int year = sc.nextInt();sc.nextLine();
                            System.out.print("Zadajte typ knihy roman->1/ucebnica->0: ");
                            int type = sc.nextInt();sc.nextLine();
                            System.out.print("Zadajte pristupnost pristupna->1/nepristupna->0: ");
                            int accessibility = sc.nextInt();sc.nextLine();
                            boolean accessibilityB;
                            if(accessibility==1) {
                            	accessibilityB=true;
                            }
                            else if(accessibility==0) {
                            	accessibilityB=false;
                            }
                            else {throw new InputMismatchException("Chyba, nespravny vstup"); }
                            if(type==1) {
                            	System.out.print("Zadajte zaner:");
                            	String genre = sc.nextLine();
                            	database.add(new Novel(name,autor,year,accessibilityB,genre));
                            }
                            else if(type==0) {
                            System.out.print("Zadajte odporucany vek:");
                        	int recYear = sc.nextInt();sc.nextLine();
                        	database.add(new TextBook(name,autor,year,accessibilityB,recYear));
                            }
                            else {throw new InputMismatchException("Chyba, nespravny vstup");}
                        }
                        catch(InputMismatchException e) {
                        	System.out.println("Chyba, nespravny vstup");
                        	sc.nextLine();
                        }
                        catch(Exception e) {
                        	System.out.println("Chyba:"+e);
                        	sc.nextLine();
                        }
                        break;
                    case 2:
                        System.out.println("Uprava knihy");
                        String name = Methods.searchName(database);
                        Novel changeNovel=null;
                        TextBook changeTextBook=null;
                        for (int i=0;i<database.size();i++) {   
                        	if (database.get(i).getName().equals(name) && database.get(i)instanceof Novel) {
                        		changeNovel = (Novel) database.get(i);
                        		System.out.println("Nazov knihy: "+changeNovel.getName()+"\n1.Nazov autora: "+changeNovel.getAutor()+"\n2.Rok vydania: "+changeNovel.getReleaseYear()+"\n3.Dostupnost: "+changeNovel.getAccessibility()+"\n4.Zaner: "+changeNovel.getGenre()+"\n");
                        	}
                        	else if (database.get(i).getName().equals(name) && database.get(i)instanceof TextBook) {
                        		changeTextBook = (TextBook) database.get(i);
                        		System.out.println("Nazov knihy: "+changeTextBook.getName()+"\n1.Nazov autora: "+changeTextBook.getAutor()+"\n2.Rok vydania: "+changeTextBook.getReleaseYear()+"\n3.Dostupnost: "+changeTextBook.getAccessibility()+"\n4.Odporucany vek: "+changeTextBook.getRecommendedYear()+"\n");
                        	}
                        }
                        System.out.print("Zadajte, ktory parameter chcete zmenit(zadajte cislo z vypisu):");
                        try {
                        	int param = sc.nextInt();sc.nextLine();
                        	if(changeNovel!=null) {
                        		switch(param) {
                            	case 1:
                            		System.out.print("Novy nazov autora: ");                            		
                            		changeNovel.setAutor(sc.nextLine());
                            		break;
                            	case 2:
                            		System.out.print("Novy rok vydania: ");
                            		changeNovel.setReleaseYear(sc.nextInt());sc.nextLine();
                            		break;
                            	case 3:
                            		System.out.print("Nova dostupnost, pristupna->1/nepristupna->0: ");
                            		int changeAccessibility = sc.nextInt();sc.nextLine();
                                    boolean changeAccessibilityB;
                                    if(changeAccessibility==1) {
                                    	changeAccessibilityB=true;
                                    }
                                    else{
                                    	changeAccessibilityB=false;
                                    }
                            		changeNovel.setAccessibility(changeAccessibilityB);
                            		break;
                            	case 4:                          		
                            		System.out.print("Novy zaner: ");
                            		changeNovel.setGenre(sc.nextLine());
                            		break;
                            	}                      		
                        	}
                        	else {
                        		switch(param) {
                            	case 1:
                            		System.out.print("Novy nazov autora: ");                            		
                            		changeTextBook.setAutor(sc.nextLine());
                            		break;
                            	case 2:
                            		System.out.print("Novy rok vydania: ");
                            		changeTextBook.setReleaseYear(sc.nextInt());sc.nextLine();
                            		break;
                            	case 3:
                            		System.out.print("Nova dostupnost, pristupna->1/nepristupna->0: ");
                            		int changeAccessibility = sc.nextInt();sc.nextLine();
                                    boolean changeAccessibilityB;
                                    if(changeAccessibility==1) {
                                    	changeAccessibilityB=true;
                                    }
                                    else{
                                    	changeAccessibilityB=false;
                                    }
                                    changeTextBook.setAccessibility(changeAccessibilityB);
                            		break;
                            	case 4:                          		
                            		System.out.print("Novy zaner: ");
                            		changeTextBook.setRecommendedYear(sc.nextInt());sc.nextLine();
                            		break;
                            	}
                        	}
                        }
                        catch(InputMismatchException e) {
                        	System.out.println("\nNastala chyba, musite zadat cislo");
                            sc.nextLine();
                        }
                        break;
                    case 3:
                    	boolean found = false;
                    	try {
	                        System.out.println("Zmazanie knihy");
	                        System.out.print("Zadajte nazov knihy: ");                            		
	                		String delBook = sc.nextLine();
	                		for (int i=0;i<database.size();i++) {
	                			if(database.get(i).getName().equals(delBook)) {
	                				database.remove(i);
	                				found = true;
	    	                		System.out.println("Kniha "+delBook+" bola vymazana");
	                			}                		
	                		}
	                		if (!found) {
	                	        throw new NoSuchElementException("Nastala chyba, dana kniha neexistuje");
	                	    }
                    	}
                    	catch(NoSuchElementException e) {
                    		System.out.println(e);
                    	}
                        break;
                    case 4:
                        System.out.println("Zmenit stav knihy pozicana/vratena");
                        String accName = Methods.searchName(database);
                        try {
	                        for (int i=0;i<database.size();i++) {   
	                        	if (database.get(i).getName().equals(accName)) {
	                        		Book changeBook = database.get(i);
	                        		System.out.print("Nova dostupnost, pristupna->1/nepristupna->0: ");
	                        		int changeAccessibility = sc.nextInt();sc.nextLine();
	                                boolean changeAccessibilityB;
	                                if(changeAccessibility==1) {
	                                	changeAccessibilityB=true;
	                                }
	                                else{
	                                	changeAccessibilityB=false;
	                                }
	                                changeBook.setAccessibility(changeAccessibilityB);
	                        	}
	                        }                      
                        }
                        catch(InputMismatchException e) {System.out.println("Chyba, nespravny vstup");}
                        break;
					case 5:
						System.out.println("\nVypis knih\n");
						if (database.isEmpty() == true) {
							System.out.println("Niesu ulozene ziadne zanamy");
						} else {
							ArrayList<Book> sortedList = new ArrayList<>(database);
							Collections.sort(sortedList, (book1, book2) -> book1.getName().compareTo(book2.getName()));
							for (int i = 0; i < sortedList.size(); i++) {
								if (sortedList.get(i) instanceof Novel) {
									Novel novel = (Novel) sortedList.get(i);
									System.out.println(
											"Nazov knihy: " + novel.getName() + "\nNazov autora: " + novel.getAutor()
													+ "\nRok vydania: " + novel.getReleaseYear() + "\nDostupnost: "
													+ novel.getAccessibility() + "\nZaner: " + novel.getGenre() + "\n");
								} else if (sortedList.get(i) instanceof TextBook) {
									TextBook textBook = (TextBook) sortedList.get(i);
									System.out.println("Nazov knihy: " + textBook.getName() + "\nNazov autora: "
											+ textBook.getAutor() + "\nRok vydania: " + textBook.getReleaseYear()
											+ "\nDostupnost: " + textBook.getAccessibility() + "\nOdporucany vek: "
											+ textBook.getRecommendedYear() + "\n");
								}
							}
						}
                        break;
                    case 6:
                        System.out.println("Vyhladanie knihy");
                        System.out.print("Zadajte nazov knihy:");
                        String searchName = sc.nextLine();
                        boolean searchFound=false;
                        for (int i=0;i<database.size();i++) {   
                        	if (database.get(i).getName().equals(searchName) && database.get(i)instanceof Novel) {
                        		Novel searchNovel = (Novel) database.get(i);
                        		System.out.println("Nazov knihy: "+searchNovel.getName()+"\n1.Nazov autora: "+searchNovel.getAutor()+"\n2.Rok vydania: "+searchNovel.getReleaseYear()+"\n3.Dostupnost: "+searchNovel.getAccessibility()+"\n4.Zaner: "+searchNovel.getGenre()+"\n");
                        	searchFound =true;
                        	}
                        	else if (database.get(i).getName().equals(searchName) && database.get(i)instanceof TextBook) {
                        		TextBook searchTextBook = (TextBook) database.get(i);
                        		System.out.println("Nazov knihy: "+searchTextBook.getName()+"\n1.Nazov autora: "+searchTextBook.getAutor()+"\n2.Rok vydania: "+searchTextBook.getReleaseYear()+"\n3.Dostupnost: "+searchTextBook.getAccessibility()+"\n4.Odporucany vek: "+searchTextBook.getRecommendedYear()+"\n");
                        	searchFound = true;
                        	}
                        }
                        if(!searchFound) {System.out.println("Nastala chyba, dana kniha neexistuje");}
                        break;
                    case 7:
                        System.out.println("Vypis vsetkych knih podla autora");
                        System.out.print("Zadajte meno autora: "); 
                        String searchAutor = sc.nextLine();
                        ArrayList<Book> chronoList = new ArrayList<>(database);
                        Collections.sort(chronoList, Comparator.comparingInt(Book::getReleaseYear));
                        boolean searchAutFound = false;                        
                        for (int i=0;i<chronoList.size();i++) {   
                        	if (chronoList.get(i).getAutor().equals(searchAutor) && chronoList.get(i)instanceof Novel) {
                        		Novel searchNovel = (Novel) chronoList.get(i);
                        		System.out.println("Nazov knihy: "+searchNovel.getName()+"\n1.Nazov autora: "+searchNovel.getAutor()+"\n2.Rok vydania: "+searchNovel.getReleaseYear()+"\n3.Dostupnost: "+searchNovel.getAccessibility()+"\n4.Zaner: "+searchNovel.getGenre()+"\n");
                        	searchAutFound =true;
                        	}
                        	else if (chronoList.get(i).getAutor().equals(searchAutor) && chronoList.get(i)instanceof TextBook) {
                        		TextBook searchTextBook = (TextBook) chronoList.get(i);
                        		System.out.println("Nazov knihy: "+searchTextBook.getName()+"\n1.Nazov autora: "+searchTextBook.getAutor()+"\n2.Rok vydania: "+searchTextBook.getReleaseYear()+"\n3.Dostupnost: "+searchTextBook.getAccessibility()+"\n4.Odporucany vek: "+searchTextBook.getRecommendedYear()+"\n");
                        	searchAutFound = true;
                        	}
                        }
                        if (!searchAutFound) {
                        	System.out.println("Zadanemu kriteriu nevyovuju ziadne polozky");
                        }                       
                        break;
                    case 8:
                        System.out.println("Vypis podla zanru");
                        System.out.print("Zadajte zaner: "); 
                        String searchGenre = sc.nextLine();
                        boolean searchGenFound = false;                        
                        for (int i=0;i<database.size();i++) {  
                        	if(database.get(i)instanceof Novel) {
                        		Novel genreNovel=(Novel)(database.get(i));                         	
                            	if (genreNovel.getGenre().equals(searchGenre)) {
                                	System.out.println("Nazov knihy: "+genreNovel.getName()+"\n1.Nazov autora: "+genreNovel.getAutor()+"\n2.Rok vydania: "+genreNovel.getReleaseYear()+"\n3.Dostupnost: "+genreNovel.getAccessibility()+"\n4.Zaner: "+genreNovel.getGenre()+"\n");
                                searchGenFound =true;
                                }  
                        	}                      	
                        }
                        if (!searchGenFound) {
                        	System.out.println("Zadanemu kriteriu nevyovuju ziadne polozky");
                        }                
                        break;
                    case 9:
                        System.out.println("Vypis vsetkych pozicanych knih");
                        boolean accessFound= false;
                        for (int i=0;i<database.size();i++) {  
                        		if(database.get(i).getAccessibility()==false) {
                        			if(database.get(i)instanceof Novel) {
                        				Novel accessNovel = (Novel)database.get(i);
                        				System.out.println("Nazov knihy: "+accessNovel.getName()+"\n1.Nazov autora: "+accessNovel.getAutor()+"\n2.Rok vydania: "+accessNovel.getReleaseYear()+"\n3.Dostupnost: "+accessNovel.getAccessibility()+"\n4.Zaner: "+accessNovel.getGenre()+"\n");
                        				accessFound=true;
                        			}
                        			else {
                        				TextBook accessTextBook = (TextBook)database.get(i);
                        				System.out.println("Nazov knihy: "+accessTextBook.getName()+"\n1.Nazov autora: "+accessTextBook.getAutor()+"\n2.Rok vydania: "+accessTextBook.getReleaseYear()+"\n3.Dostupnost: "+accessTextBook.getAccessibility()+"\n4.Odporucany vek: "+accessTextBook.getRecommendedYear()+"\n");
                        				accessFound=true;
                        			}
                        		}
                        	}  
                        if(!accessFound) {
                        	System.out.println("Ziadna kniha nebola pozicana");
                        }
                        break;
                    case 10:
                        System.out.println("Ulozenie informacie");
                        String saveName = Methods.searchName(database);
                		FileWriter fWriter = null;
                		BufferedWriter bWriter = null; 
                        for (int i=0;i<database.size();i++) {   
                        	if (database.get(i).getName().equals(saveName) && database.get(i)instanceof Novel) {
                        		Novel saveNovel = (Novel) database.get(i);
                        		File file = new File(saveName+".txt");                  		
                        		try {
                        			fWriter = new FileWriter(file);
                        			bWriter = new BufferedWriter(fWriter);
                        			bWriter.write(String.format(saveNovel.getName()+"*"+saveNovel.getAutor()+"*"+saveNovel.getReleaseYear()+"*"+saveNovel.getAccessibility()+"*"+saveNovel.getGenre()+"*NOVEL\n"));		
                        			bWriter.newLine();
                        			bWriter.close();
                        			fWriter.close();
                        			System.out.println("Kniha bola uspesne ulozena");
                        		}
                        		catch(IOException e){
                        			System.out.println("Nastala chyba");
                        		}
                        	}
                        	else if (database.get(i).getName().equals(saveName) && database.get(i)instanceof TextBook) {
                        		TextBook saveTextBook = (TextBook) database.get(i);                     
                        		File file = new File(saveName+".txt");                  		
                        		try {
                        			fWriter = new FileWriter(file);
                        			bWriter = new BufferedWriter(fWriter);
                        			bWriter.write(String.format(saveTextBook.getName()+"*"+saveTextBook.getAutor()+"*"+saveTextBook.getReleaseYear()+"*"+saveTextBook.getAccessibility()+"*"+saveTextBook.getRecommendedYear()+"*TEXTBOOK\n"));		
                        			bWriter.newLine();
                        			bWriter.close();
                        			fWriter.close();
                        			System.out.println("Kniha bola uspesne ulozena");
                        		}
                        		catch(IOException e){
                        			System.out.println("Nastala chyba");
                        		}
                        	}
                        }
                        break;
					case 11:
						System.out.println("Nacitanie informacii o knihe");
						String fileName = Methods.duplicity(database) + ".txt";
						try {
							BufferedReader reader = new BufferedReader(new FileReader(fileName));
							String[] parts = reader.readLine().split("\\*");
							if (parts[5] == "TEXTBOOK") {
								database.add(new TextBook(parts[0], parts[1], Integer.parseInt(parts[2]),
										Boolean.parseBoolean(parts[3]), Integer.parseInt(parts[4])));
							} else {
								database.add(new Novel(parts[0], parts[1], Integer.parseInt(parts[2]),
										Boolean.parseBoolean(parts[3]), parts[4]));
							}
							System.out.println("Zaznam bol pridany");
						} catch (FileNotFoundException e) {
							System.out.println(e);
						} catch (Exception e) {
							System.out.println(e);
						}

						break;
                    case 12:
                    	try (Connection connection = DriverManager.getConnection(url, username, password);
                    		    Statement statement = connection.createStatement()) {
                    		    String deleteQuery = "DELETE FROM book";
                    		    int affectedRows = statement.executeUpdate(deleteQuery);

                    		} catch (SQLException e) {
                    		    System.err.println("Chyba: " + e.getMessage());
                    		}
                    	 try (Connection connection = DriverManager.getConnection(url, username, password)) {
                    		 
                             for (Book book : database) {
                            	 String insertQuery ="INSERT INTO book (bookName, author, releaseYear, accessibility, genre, recYear, class) VALUES (?, ?, ?, ?, ?, ?, ?)";

                                 try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                                     preparedStatement.setString(1, book.getName());
                                     preparedStatement.setString(2, book.getAutor());
                                     preparedStatement.setInt(3, book.getReleaseYear());
                                     preparedStatement.setBoolean(4, book.getAccessibility());

                                     if (book instanceof Novel) {
                                         preparedStatement.setString(5, ((Novel) book).getGenre());
                                         preparedStatement.setString(6, null);
                                         preparedStatement.setString(7, "NOVEL");
                                     }
                                     else if(book instanceof TextBook) {
                                    	 preparedStatement.setInt(6, ((TextBook) book).getRecommendedYear());
                                    	 preparedStatement.setString(5, null);
                                    	 preparedStatement.setString(7, "TEXTBOOK");
                                     }

                                     int rowsAffected = preparedStatement.executeUpdate();
                                     if (rowsAffected == 0) {
                                    	 System.out.println("Nastala chyba");
                                     } 
                                 }
                             }
                         } catch (SQLException e) {
                             System.err.println("Error: " + e.getMessage());
                         }
                        System.exit(0);
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("\nNastala chyba, musite zadat cislo");
                sc.nextLine();
            }
        }
    }
}
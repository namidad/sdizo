package sdizo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

class Value {
	int value; // przechowywanie danych
	Value next;

	// tworzymy obiekt odpowiadajacy pojedynczemu elementowi listy
	Value(int x, Value Next) {
		value = x;
		next = Next;
	}
}

class List {
	Value first; // pobieranie informacji o pierwszym elemencie listy

	List() {
		first = null;
	}

	Value add(int x, Value v) {
		Value objekt = null; // tworzymy obiekt klasy Value ( nowa pozycja na
								// liscie)

		if (first == null) // sprawdzamy czy mamy pierwszy element
		{
			objekt = new Value(x, null); // nie ma pierwszego elementu, nasz
											// obiekt staje sie tym elementem
			first = objekt;

		} else {
			objekt = new Value(x, v.next); // do pola next poprzedniego obiektu(
											// poprzednia pozycja na liscie,
											// podanej jako argument)
			v.next = objekt; // przypisujemy referencje do naszego nowo
								// utworzonego elementu
		}

		return objekt;
	}

	void searchValue(int x) {
		Value f = first; // pierwszy element
		try {
			while (f != null && f.value !=  x) {
				f = f.next; // przechodzimy po calej liscie do momentu
							// znalezienia szukanej wartosci
			}
		} catch (NullPointerException e) {

		}
		if(f.value==x){
		System.out.println("Istnieje element rowny " + f.value);
		}else System.out.println("Nie ma elementu rownego" + x);


	}

	void delete(int x) {
		Value f = first; // poczatakowy element listy
		Value prev = null;// poprzedzajacy element ( puste bo first nie ma
							// porpzednika)
		int i = 0;
		while (f != null && i != x) {
			prev = f;
			f = f.next;
			i++;
		}

		if (f != null)
			if (prev == null)
				first = f.next;
			else
				prev.next = f.next;

	}

	void wyswietl() {
		Value f = first;

		while (f != null) {
			System.out.print(f.value + " -> "); // wypisujemy listê
			f = f.next;
		}

	}

	Value createList(int x) {
		long start = System.nanoTime();
		int losowaLiczba = 0;
		Random rand = new Random();
		Value value = null;

		for (int i = 0; i < x; i++) {
			losowaLiczba = rand.nextInt(49999);
			value = add(losowaLiczba, value); // dopisujemy do listy elementy
		}

		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas tworzenia listy wynosi: " + czas);

		return value;

	}

}

class Heap {

	int[] heap;
	int liczbaElementow, check = 0;

	public int getLiczbaElementow() {
		return liczbaElementow;
	}

	public void setLiczbaElementow(int liczbaElementow) {
		this.liczbaElementow = liczbaElementow;
	}

	void stworzKopiec(int x) {
		long start = System.nanoTime();
		liczbaElementow = x;
		int losowaLiczba = 0;
		Random rand = new Random();
		heap = new int[x];

		for (int i = 0; i < x; i++) {
			losowaLiczba = rand.nextInt(49999);
			heap[i] = losowaLiczba;
		}
		budujKopiec();
		check = 1;
		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas tworzenia kopca wynosi: " + czas);
	}

	void usunElement() {

	}

	void budujKopiec() {

		int rodzic, lewyPotomek, prawyPotomek, test;
		int bufor = 0;
		if (check == 1) {
			for (int i = heap.length - 1; i > 0; i--) {
				rodzic = (i - 1) / 2;
				lewyPotomek = 2 * rodzic + 1;
				prawyPotomek = 2 * rodzic + 2;
				if (prawyPotomek < heap.length && heap[lewyPotomek] > heap[prawyPotomek]) {
					if (heap[lewyPotomek] > heap[rodzic]) {
						bufor = heap[lewyPotomek];
						heap[lewyPotomek] = heap[rodzic];
						heap[rodzic] = bufor;

						i = 2 * rodzic + 1;
						test = 2 * i + 1;
						if (test <= heap.length) {
							i = 2 * i + 3;
						}

					}
				} else {
					if (prawyPotomek < heap.length && heap[prawyPotomek] > heap[rodzic]) {
						bufor = heap[prawyPotomek];
						heap[prawyPotomek] = heap[rodzic];
						heap[rodzic] = bufor;

						i = 2 * rodzic + 1;
						test = 2 * i + 1;
						if (test <= heap.length) {
							i = 2 * i + 3;
						}
					}
				}
				if (prawyPotomek == heap.length) {
					if (heap[lewyPotomek] > heap[rodzic]) {
						bufor = heap[lewyPotomek];
						heap[lewyPotomek] = heap[rodzic];
						heap[rodzic] = bufor;
						i = 2 * rodzic + 1;
						test = 2 * i + 1;
						if (test <= heap.length) {
							i = 2 * i + 3;
						}

					}

					i--;
				}

			}
		} else
			System.out.println("Brak kopca");

	}

	void wyswietlKopiec() {

		int i, f = 0;
		System.out.println(heap[0]);
		i = 1;
		int d = 2 * i;
		while (f < heap.length) {
			for (int o = i; o <= d; o++) {
				if (o <= heap.length - 1) {

					System.out.print(heap[o] + " ");
				} else {
					f = heap.length;
					o = d + 1;
				}
			}
			i = d + 1;
			d = 2 * i;
			System.out.println();

		}
	}

	void dodajElement() {
		long start = System.nanoTime();
		Random rand = new Random();
		int liczba = rand.nextInt(49999);
		if (check == 1) {
			if (heap.length != 0) {
				int[] res = new int[heap.length + 1];
				for (int i = 0; i < res.length - 1; i++) {
					res[i] = heap[i];
				}
				res[heap.length] = liczba;
				this.heap = res;
				budujKopiec();
				System.out.println("dodany element to " + liczba);

			} else {
				int[] res = new int[1];
				res[0] = liczba;
				this.heap = res;
			}

		} else {

			check = 1;
			int[] res = new int[1];
			res[0] = liczba;
			this.heap = res;

		}

		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas dodawania elementu w kopcu wynosi : " + czas);

	}

	void znajdzElement(int x){
		int y=0;
		if(check == 1){
		if (heap.length > 0) {
			for (int i = 0; i < heap.length; i++) {
				if (heap[i] == x) {
					System.out.println("W kopcu istnieje taki element !");
					i = heap.length;
					y = 1;
				}
			}
			if (y == 0) {
				System.out.println("Nie ma takiego elementu w kopcu");
			}
		} else
			System.out.println("Brak kopcay");
	} else
		System.out.println("Brak kopca");
	}
}

class Array {

	int[] tablica;
	int check = 0;

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	void stworzTablice(int x) {
		long start = System.nanoTime();
		if (check == 0) {
			int losowaLiczba = 0;
			Random rand = new Random();
			tablica = new int[x];

			for (int i = 0; i < x; i++) {
				losowaLiczba = rand.nextInt(49999);
				tablica[i] = losowaLiczba;
			}
			check = 1;
			long stop = System.nanoTime();
			long czas = stop - start;
			System.out.println("Czas tworzenia tablicy wynosi: " + czas);

		} else
			System.out.println("Tablica jest juz stworzona");
	}

	void wyswietlTablice() {
		if (check == 1) {
			for (int i = 0; i < tablica.length; i++) {
				System.out.println(tablica[i]);
			}
		} else
			System.out.println("Stworz najpierw tablice !");
	}

	void dodajElementLosowy(int x) {
		long start = System.nanoTime();
		if (check == 1) {
			Random rand = new Random();
			if (tablica.length != 0) {
				int[] res = new int[tablica.length + 1];
				int liczba = rand.nextInt(res.length);
				for (int i = 0; i < liczba; i++) {
					res[i] = tablica[i];
				}
				res[liczba] = x;

				for (int i = liczba + 1; i < res.length; i++) {
					res[i] = tablica[i - 1];
				}
				this.tablica = res;
			} else {
				int[] res = new int[1];
				res[0] = x;
				this.tablica = res;
			}
		} else
			System.out.println("Stworz najpierw tablice !");
		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas dodawania elementu na losowa pozycje wynosi : " + czas);
	}

	void usunElementLosowy() {
		long start = System.nanoTime();
		if (check == 1) {
			Random rand = new Random();
			if (tablica.length != 0) {
				int[] res = new int[tablica.length - 1];
				int liczba = rand.nextInt(res.length);
				for (int i = 0; i < liczba; i++) {
					res[i] = tablica[i];
				}

				for (int i = liczba; i < res.length; i++) {
					res[i] = tablica[i + 1];
				}
				this.tablica = res;
			} else {
				System.out.println("TABLICA JEST PUSTA !");
			}
		} else
			System.out.println("Stworz najpierw tablice !");
		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas usuwania elementu z losowej pozycji wynosi : " + czas);
	}

	void dodajElementNaPoczatek(int x) {
		long start = System.nanoTime();
		if (check == 1) {
			if (tablica.length != 0) {
				int[] res = new int[tablica.length + 1];
				res[0] = x;
				for (int i = 1; i < res.length; i++) {
					res[i] = tablica[i - 1];
				}
				this.tablica = res;
			} else {
				int[] res = new int[1];
				res[0] = x;
				this.tablica = res;
			}
		} else
			System.out.println("Stworz najpierw tablice !");
		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas dodawania elementu na pierwsza pozycje wynosi : " + czas);

	}

	void usunElementPoczatkowy() {
		long start = System.nanoTime();
		if (check == 1) {
			if (tablica.length != 0) {
				int[] res = new int[tablica.length - 1];
				for (int i = 1; i <= res.length; i++) {
					res[i - 1] = tablica[i];
				}
				this.tablica = res;
			} else {
				System.out.println("Tablica by³a ju¿ pusta");
			}
		} else
			System.out.println("Stworz najpierw tablice !");

		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas usuwania elementu z poczatkowej pozycji wynosi : " + czas);

	}

	void usunElemenKoncowy() {
		long start = System.nanoTime();

		if (check == 1) {
			if (tablica.length != 0) {
				int[] res = new int[tablica.length - 1];

				for (int i = 0; i < res.length; i++) {
					res[i] = tablica[i - 1];

				}
				this.tablica = res;
			} else {
				System.out.println("Tablica byla juz pusta");
			}
		} else
			System.out.println("Stworz najpierw tablice !");

		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas usuwania elementu z ostatniej pozycji wynosi : " + czas);
	}

	void dodajElementKoncowy(int x) {
		long start = System.nanoTime();

		if (check == 1) {
			if (tablica.length != 0) {
				int[] res = new int[tablica.length + 1];

				for (int i = 0; i < res.length; i++) {
					res[i] = tablica[i - 1];

				}
				res[res.length] = x;
				this.tablica = res;
			} else {
				int[] res = new int[1];
				res[0] = x;
				this.tablica = res;
			}
		} else
			System.out.println("Stworz najpierw tablice !");

		long stop = System.nanoTime();
		long czas = stop - start;
		System.out.println("Czas dodawania elementu na ostatnia pozycje wynosi : " + czas);
	}

	void znajdzElement(int x) {
		int y = 0;
		if (check == 1) {
			if (tablica.length > 0) {
				for (int i = 0; i < tablica.length; i++) {
					if (tablica[i] == x) {
						System.out.println("W tabeli istnieje taki element !");
						i = tablica.length;
						y = 1;
					}
				}
				if (y == 0) {
					System.out.println("Nie ma takiego elementu w tablicy");
				}
			} else
				System.out.println("Brak tablicy");
		} else
			System.out.println("Brak tablicy");
	}
}

public class sdz {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Array tablica = new Array();
		List lista = new List();
		Value value = null;
		Heap kopiec = new Heap();
		@SuppressWarnings("resource")
		Scanner odczyt = new Scanner(System.in);
		StringTokenizer token;
		int i=0;

	/*	File plik = new File("sdizowczytywanie.txt");
		Scanner odczyt2 = new Scanner(plik);
		while(odczyt2.hasNextLine()){
			token = new StringTokenizer(odczyt2.nextLine()," ");
			System.out.println("Token = " + token.nextToken());
			int tab[]=new int[Integer.parseInt(token.nextToken())];
			while(token.hasMoreElements()){
				
				tab[i]=Integer.parseInt(token.nextToken());
			i++;
			}
			
		}
		
		
		
		*/
		
		
		
		
		
		int numer, numer1, numer3;
		do {
			do {
				System.out.println("Menu programu");
				System.out.println("0. Wyjscie");
				System.out.println("1. Tablica");
				System.out.println("2. Kopiec");
				System.out.println("3. Lista");

				System.out.print("Podaj numer: ");
				numer3 = odczyt.nextInt();
			} while (numer3 > 3 && numer3 < 1);

			switch (numer3) {
			case 0:
				break;
			case 1:
				do {
					System.out.println("Menu podprogramu - lista");
					System.out.println("0. Cofnij");
					System.out.println("1. Stworz tablice");
					System.out.println("2. Dodaj element w dowolnym miejscu");
					System.out.println("3. Dodaj element na poczatek");
					System.out.println("4. Dodaj element na koniec");
					System.out.println("5. Usun dowolny element");
					System.out.println("6. Usun pierwszy element");
					System.out.println("7. Usun ostatni element");
					System.out.println("8. Znajdz element");

					do {
						System.out.print("Podaj numer: ");
						numer1 = odczyt.nextInt();
					} while (numer1 > 8 && numer1 < 1);

					switch (numer1) {
					case 0:
						numer1 = 0;
						break;
					case 1:
						do {
							System.out.print("Podaj wielkosc tablicy do stworzenia");
							numer = odczyt.nextInt();
						} while (numer < 1);
						tablica.stworzTablice(numer);
						System.out.println();

						break;
					case 2:

						do {
							System.out
									.print("Podaj wartosc elementu, ktory zostanie dodany w dowolnym miejscu tablicy");
							numer = odczyt.nextInt();
						} while (numer < 1);

						tablica.dodajElementLosowy(numer);
						System.out.println();

						break;
					case 3:
						do {
							System.out.print("Podaj wartosc elementu, ktory zostanie dodany na poczatek tablicy");
							numer = odczyt.nextInt();

						} while (numer < 1);

						tablica.dodajElementNaPoczatek(numer);
						System.out.println();

						break;
					case 4:
						do {
							System.out.print("Podaj wartosc elementu, ktory zostanie dodany na koniec tablicy ");
							numer = odczyt.nextInt();
						} while (numer < 1);

						tablica.dodajElementKoncowy(numer);
						System.out.println();

						break;
					case 5:

						tablica.usunElementLosowy();
						System.out.println();

						break;

					case 6:
						tablica.usunElementPoczatkowy();
						System.out.println();

						break;
					case 7:

						tablica.usunElemenKoncowy();
						System.out.println();

						break;
					case 8:
						do {
							System.out.print("Podaj wartosc elementu, ktory ma zostac znaleziony ");
							numer = odczyt.nextInt();

						} while (numer < 0);

						tablica.znajdzElement(numer);

						break;
					default:
						System.out.println("Try again");
					}

				} while (numer1 != 0);
				break;

			case 2:
				do {
					System.out.println("Menu podprogramu - kopiec");
					System.out.println("0. Cofnij");
					System.out.println("1. Stworz kopiec");
					System.out.println("2. Buduj kopiec");
					System.out.println("3. Dodaj element");
					System.out.println("4. Usun element");
					System.out.println("5. Wyswietl kopiec");
					System.out.println("6. Znajdz element w kopcu");

					System.out.println();
					do {
						System.out.print("Podaj numer: ");
						numer1 = odczyt.nextInt();
					} while (numer1 > 5 && numer1 < 0);

					switch (numer1) {
					case 0:
						numer1 = 0;
						break;
					case 1:

						do {
							System.out.print("Podaj wielkosc kopca ");
							numer = odczyt.nextInt();
						} while (numer < 1);
						kopiec.stworzKopiec(numer);
						System.out.println();

						break;
					case 2:
						kopiec.budujKopiec();
						System.out.println();

						break;
					case 3:
						kopiec.dodajElement();
						System.out.println();

						break;
					case 4:
						kopiec.usunElement();
						System.out.println();

						break;
					case 5:
						kopiec.wyswietlKopiec();
						System.out.println();

						break;
						
					case 6:
						do {
						System.out.print("Podaj wartosc elementu kopca ");
						numer = odczyt.nextInt();
					} while (numer < 0);
						kopiec.znajdzElement(numer);
						break;
					default:
						System.out.println("Try again");

					}
				} while (numer1 != 0);
				break;
			case 3:
				do {
					System.out.println("Menu podprogramu - lista");
					System.out.println("0. Cofnij");
					System.out.println("1. Stworz listê");
					System.out.println("2. Usun element");
					System.out.println("3. Dodaj element");
					System.out.println("4. Wyswietl listê");
					System.out.println("5. Znajdz wartosc");
					System.out.println();
					do {
						System.out.print("Podaj numer: ");
						numer1 = odczyt.nextInt();
					} while (numer1 > 5 && numer1 < 0);

					switch (numer1) {
					case 0:
						numer1 = 0;
						break;
					case 1:
						do {
							System.out.print("Podaj ilosc elementow ");
							numer = odczyt.nextInt();

						} while (numer < 1);
						value = lista.createList(numer);
						break;
					case 2:
						do {
							System.out.print("Podaj indeks elementu ");
							numer = odczyt.nextInt();

						} while (numer < 0);
						lista.delete(numer);
						break;
					case 3:

						lista.add(1, value);
						break;
					case 4:
						lista.wyswietl();
						break;
					case 5:
						do {
							System.out.print("Podaj wartosc elementu ");
							numer = odczyt.nextInt();

						} while (numer < 1);
						lista.searchValue(numer);
						break;
					default:
						System.out.println("Try again");

					}

				} while (numer1 != 0);

				break;

			default:
				System.out.println("Try again");
			}

		} while (numer3 != 0);

		System.out.println(" <- <- <- <- koniec -> -> -> ->");

	}
}

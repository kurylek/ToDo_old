import java.io.*;
import java.util.Scanner;

public class Main {

    static int tasksCount= 0;
    static String[] taskName= new String[100];
    static String[] taskDescription= new String[100];
    static boolean[] done= new boolean[100];

    public static void menu(){
        System.out.println("0. Wyjdz.");
        System.out.println("1. Wyswietl zadania do zrobienia.");
        System.out.println("2. Dodaj zadanie.");
        System.out.println("3. Usun zadania.");
        System.out.println("4. Edytuj zadanie.");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner= new Scanner(System.in);
        int option;

        do{
            menu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    System.out.println("Zegnaj!");
                    break;
                case 1:
                    System.out.println("//lista//");
                    break;
                case 2:
                    System.out.println("//dodawaie//");
                    break;
                case 3:
                    System.out.println("//usun//");
                    break;
                case 4:
                    System.out.println("//edytuj//");
                    break;
                default:
                    System.out.println("Wybrano zla opcje!");
                    break;

            }

        }while(option!=0);
    }
}
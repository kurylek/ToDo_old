import java.io.*;
import java.util.Scanner;

public class Main {

    static int tasksCount= 0;
    static String[] taskName= new String[100];
    static String[] taskDescription= new String[100];
    static boolean[] taskDone= new boolean[100];
    static String currentTaskList= "default.txt"; //zmienna przechowująca nazwę listy zadan

    public static void menu(){
        System.out.println("\n");
        System.out.println("0. Wyjdz.");
        System.out.println("1. Wyswietl zadania do zrobienia.");
        System.out.println("2. Dodaj zadanie.");
        System.out.println("3. Usun zadania.");
        System.out.println("4. Edytuj zadanie.");
        System.out.println("5. Zapisz liste.");
        System.out.println("6. Opcje.");
    }

    public static void listTasks(){
        System.out.println("\n");
        if(tasksCount==0){
            System.out.println("Brak zadan na liscie!");
            return;
        }

        for(int i=0;i<tasksCount;i++){
            System.out.print((i+1)+". "+taskName[i]);
            if(taskDone[i]){
                System.out.print(" | Wykonano |");
            }else{
                System.out.print(" | W trakcie |");
            }
            if(taskDescription[i].length()!=0){
                System.out.println(" - "+taskDescription[i]);
            }else{
                System.out.println();
            }
        }
    }

    public static void addTask(String newTaskName, String newTaskDescription){
        taskName[tasksCount]= newTaskName;
        taskDescription[tasksCount]= newTaskDescription;
        taskDone[tasksCount]= false;
        tasksCount++;

        System.out.println("Dodano nowe zadanie!");
    }

    public static void removeTask(int taskRemoveId){
        for(int i=taskRemoveId; i<tasksCount-1;i++){
            taskName[i]= taskName[i+1];
            taskDescription[i]= taskDescription[i+1];
            taskDone[i]= taskDone[i+1];
        }
        tasksCount--;

        System.out.println("Usunieto zadanie!");
    }

    public static void editTaskMenu(){
        System.out.println("0. Zakancz edycje zadania.");
        System.out.println("1. Edytuj nazwe zadania.");
        System.out.println("2. Edytuj opis zadania.");
        System.out.println("3. Edytuj stan zadania.");
    }

    public static void editTask(int taskEditId){
        int option;
        Scanner scanner= new Scanner(System.in);
        System.out.println("\n");
        do{
            editTaskMenu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    System.out.println("Koniec edycji zadania");
                    break;
                case 1:
                    System.out.println("Podaj nowa nazwe zadania: ");
                    String newTaskName= scanner.next();
                    taskName[taskEditId]= newTaskName;
                    break;
                case 2:
                    System.out.println("Podaj nowy opis zadania: ");
                    String newTaskDescription= scanner.next();
                    taskDescription[taskEditId]= newTaskDescription;
                    break;
                case 3:
                    System.out.println("Podaj nowy stan zadania (true/false): ");
                    boolean newTaskDone= scanner.nextBoolean();
                    taskDone[taskEditId]= newTaskDone;
                    break;
                default:
                    break;
            }
        }while(option!=0);
    }

    public static void loadTasks() throws  IOException{

        File taskFile = new File(currentTaskList);
        boolean taskFileExists = taskFile.exists();

        if(taskFileExists){
            FileReader fileReader= new FileReader(currentTaskList);
            BufferedReader reader= new BufferedReader(fileReader);

            tasksCount= Integer.parseInt(reader.readLine());
            for(int i=0;i<tasksCount;i++){
                taskName[i]= reader.readLine();
                taskDescription[i]= reader.readLine();
                taskDone[i]= Boolean.parseBoolean(reader.readLine());
            }

            reader.close();

            System.out.println("Wczytano "+tasksCount+" zadan.");
        }else{
            System.out.println("Nie wczytano zadan.");
        }
    }

    public static void saveTasks() throws IOException{
        PrintWriter printWriter= new PrintWriter(currentTaskList);

        printWriter.println(tasksCount);
        for(int i=0;i<tasksCount;i++){
            printWriter.println(taskName[i]);
            printWriter.println(taskDescription[i]);
            printWriter.println(taskDone[i]);
        }

        printWriter.close();

        System.out.println("Zapisano "+tasksCount+" zadan.");
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner= new Scanner(System.in);
        int option;
        boolean taskExist;

        loadTasks();

        do{
            menu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    System.out.println("Zegnaj!");
                    break;
                case 1:
                    listTasks();
                    break;
                case 2:
                    boolean hasCapacityForNewTask= tasksCount<taskName.length;
                    if(hasCapacityForNewTask){
                        System.out.println("Podaj nazwe nowego zadania:");
                        String newTaskName= scanner.next();
                        newTaskName+= scanner.nextLine();

                        System.out.println("Podaj opis zadania:");
                        String newTaskDescription= scanner.nextLine();

                        addTask(newTaskName, newTaskDescription);
                    }else{
                        System.out.println("Nie ma miejsca na nowe zadanie!");
                    }
                    break;
                case 3:
                    System.out.println("Podaj numer zadania do usuniecia: ");
                    int taskRemoveId= scanner.nextInt()-1;
                    taskExist= taskRemoveId<tasksCount;
                    if(taskRemoveId<0)taskExist=false;

                    if(taskExist){
                        removeTask(taskRemoveId);
                    }else{
                        System.out.println("Zadanie o takim numerze nie istnieje!");
                    }
                    break;
                case 4:
                    System.out.println("Podaj numer zadania do edycji: ");
                    int taskEditId= scanner.nextInt()-1;
                    taskExist= taskEditId<tasksCount;
                    if(taskEditId<0)taskExist=false;

                    if(taskExist){
                        editTask(taskEditId);
                    }else{
                        System.out.println("Zadanie o takim numerze nie istnieje!");
                    }
                    break;
                case 5:
                    saveTasks();
                    break;
                case 6:
                    System.out.println("//opcje//");
                    break;
                default:
                    System.out.println("Wybrano zla opcje!");
                    break;

            }

        }while(option!=0);
    }
}
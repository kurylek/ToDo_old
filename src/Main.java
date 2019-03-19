import java.io.*;
import java.util.Scanner;

public class Main {

    static int tasksCount= 0;
    static String[] taskName= new String[100];
    static String[] taskDescription= new String[100];
    static boolean[] taskDone= new boolean[100];
    static String currentTaskList= "default.txt"; //zmienna przechowująca nazwę listy zadan
    static boolean nextRun= false;

    public static void menu(){
        System.out.println("\n");
        System.out.println("0. Wyjdz");
        System.out.println("1. Wyswietl zadania do zrobienia");
        System.out.println("2. Dodaj zadanie");
        System.out.println("3. Usun zadania");
        System.out.println("4. Edytuj zadanie");
        System.out.println("5. Opcje");
    }

    public static void listTasks(){
        clearTerminal();
        if(tasksCount==0){
            System.out.println("Brak zadan na liscie!");
            return;
        }

        for(int i=0;i<tasksCount;i++){
            listOneTask(i);
        }
        System.out.println();
    }

    public static void listOneTask(int taskToList){
        System.out.print((taskToList+1)+". "+taskName[taskToList]);
        if(taskDone[taskToList]){
            System.out.print(" | Wykonano |");
        }else{
            System.out.print(" | W trakcie |");
        }
        if(taskDescription[taskToList].length()!=0){
            System.out.println(" - "+taskDescription[taskToList]);
        }else{
            System.out.println();
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
        System.out.println("\n0. Zakancz edycje zadania");
        System.out.println("1. Edytuj nazwe zadania");
        System.out.println("2. Edytuj opis zadania");
        System.out.println("3. Edytuj stan zadania");
    }

    public static void editTask(int taskEditId){
        int option;
        String editTmpString;
        Scanner scanner= new Scanner(System.in);
        System.out.println("\n");
        do{
            clearTerminal();
            listOneTask(taskEditId);
            editTaskMenu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    clearTerminal();
                    System.out.println("Koniec edycji zadania");
                    break;
                case 1:
                    clearTerminal();
                    System.out.println("Podaj nowa nazwe zadania: ");
                    editTmpString= scanner.next();
                    editTmpString+= scanner.nextLine();
                    taskName[taskEditId]= editTmpString;
                    break;
                case 2:
                    clearTerminal();
                    System.out.println("Podaj nowy opis zadania: ");
                    editTmpString= scanner.next();
                    editTmpString+= scanner.nextLine();
                    taskDescription[taskEditId]= editTmpString;
                    break;
                case 3:
                    clearTerminal();
                    System.out.println("Podaj nowy stan zadania (true/false): ");
                    boolean newTaskDone= scanner.nextBoolean();
                    taskDone[taskEditId]= newTaskDone;
                    break;
                default:
                    break;
            }
        }while(option!=0);
    }

    public static void clearTasks(){
        tasksCount= 0;
        taskName= new String[100];
        taskDescription= new String[100];
        taskDone= new boolean[100];
    }

    public static void loadTasks() throws  IOException{
        clearTasks();
        File taskFile = new File(currentTaskList);
        boolean taskFileExists = taskFile.exists();

        if(taskFileExists){
            FileReader fileReader= new FileReader(currentTaskList);
            BufferedReader reader= new BufferedReader(fileReader);
            String tmp= reader.readLine();
            if(tmp == null || tmp.equals("0")){
                if(nextRun){
                    System.out.println("W liscie nie ma zadan!");
                }
            }else{
                tasksCount = Integer.parseInt(tmp);
                for (int i = 0; i < tasksCount; i++) {
                    taskName[i] = reader.readLine();
                    taskDescription[i] = reader.readLine();
                    taskDone[i] = Boolean.parseBoolean(reader.readLine());
                }

                reader.close();

                System.out.println("Wczytano " + tasksCount + " zadan.");
            }
        }else{
            if(nextRun){
                System.out.println("Plik z zadaniami nie istnieje!");
            }
        }
        nextRun= true;
    }

    public static void saveTasks() throws IOException{
        PrintWriter printWriter= new PrintWriter(currentTaskList);

        if(tasksCount==0){
        }else{
            printWriter.println(tasksCount);

            for(int i=0;i<tasksCount;i++){
                printWriter.println(taskName[i]);
                printWriter.println(taskDescription[i]);
                printWriter.println(taskDone[i]);
            }
        }

        printWriter.close();

        System.out.println("Zapisano "+tasksCount+" zadan.");
    }

    public static void optionsMenu(){
        System.out.println("\n0. Powrot");
        System.out.println("1. Zapisz liste");
        System.out.println("2. Zmien liste");
    }

    public static void options() throws IOException {
        int option;
        Scanner scanner= new Scanner(System.in);

        do{
            optionsMenu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    clearTerminal();
                    break;
                case 1:
                    clearTerminal();
                    saveTasks();
                    break;
                case 2:
                    clearTerminal();
                    System.out.println("Podaj nazwe pliku z lista (bez '.txt')");
                    currentTaskList= scanner.next();
                    currentTaskList+=".txt";
                    clearTerminal();
                    loadTasks();
                    break;
                default:
                    clearTerminal();
                    System.out.println("Wybrano zla opcje!");
                    break;
            }
        }while(option!=0);
    }

    public static void clearTerminal(){
        for(int i=0;i<30;i++){
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner= new Scanner(System.in);
        int option;
        boolean taskExist;

        clearTerminal();
        loadTasks();

        do{
            menu();
            option= scanner.nextInt();

            switch(option){
                case 0:
                    clearTerminal();
                    System.out.println("Zegnaj!");
                    break;
                case 1:
                    listTasks();
                    break;
                case 2:
                    boolean hasCapacityForNewTask= tasksCount<taskName.length;
                    if(hasCapacityForNewTask){
                        clearTerminal();
                        System.out.println("Podaj nazwe nowego zadania:");
                        String newTaskName= scanner.next();
                        newTaskName+= scanner.nextLine();

                        System.out.println("\nPodaj opis zadania:");
                        String newTaskDescription= scanner.nextLine();


                        clearTerminal();
                        addTask(newTaskName, newTaskDescription);
                    }else{
                        clearTerminal();
                        System.out.println("Nie ma miejsca na nowe zadanie!");
                    }
                    break;
                case 3:
                    clearTerminal();
                    listTasks();
                    System.out.println("Podaj numer zadania do usuniecia: ");
                    int taskRemoveId= scanner.nextInt()-1;
                    taskExist= taskRemoveId<tasksCount;
                    if(taskRemoveId<0)taskExist=false;

                    clearTerminal();
                    if(taskExist){
                        removeTask(taskRemoveId);
                    }else{
                        System.out.println("Zadanie o takim numerze nie istnieje!");
                    }
                    break;
                case 4:
                    clearTerminal();
                    listTasks();
                    System.out.println("Podaj numer zadania do edycji: ");
                    int taskEditId= scanner.nextInt()-1;
                    taskExist= taskEditId<tasksCount;
                    if(taskEditId<0)taskExist=false;

                    if(taskExist){
                        clearTerminal();
                        editTask(taskEditId);
                    }else{
                        clearTerminal();
                        System.out.println("Zadanie o takim numerze nie istnieje!");
                    }
                    break;
                case 5:
                    clearTerminal();
                    options();
                    break;
                default:
                    clearTerminal();
                    System.out.println("Wybrano zla opcje!");
                    break;

            }

        }while(option!=0);
    }
}
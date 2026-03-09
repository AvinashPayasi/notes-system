package com.securenotes.cli;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInteraction {
    private Scanner scanner=new Scanner(System.in);
    private Console console=System.console();
    private final ClientService clientService;

    public UserInteraction(ClientService clientService){
        this.clientService=clientService;
    }

    public void authentication(){
            System.out.println("1. LogIn");
            System.out.println("2. Register");
            int choice = choose(2);
            switch (choice) {
                case 1:
                    logIn();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    break;
        }
    }

    private int choose(int i){
        System.out.println("0. Exit");
        while(true) {
            System.out.print("Enter choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice > i || choice<0) {
                    System.out.println("Enter number between 0-" + i);
                    continue;
                }
                return choice;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("Enter digits only");
                scanner.nextLine();
            }
        }
    }

    public void logIn(){
        System.out.print("Enter Username/Email: ");
        String username=scanner.nextLine();
        System.out.print("Enter Password: ");
        char[] password=console.readPassword();
    }

    public void register(){
        System.out.print("Enter Name: ");
        String name=scanner.nextLine();
        System.out.print("Enter Username: ");
        String username=scanner.nextLine();
        System.out.print("Enter Email: ");
        String email=scanner.nextLine();
        System.out.print("Enter Password: ");
        char[] password1=console.readPassword();
        System.out.print("Confirm Password: ");
        char[] password2= console.readPassword();
    }

    public void home(){
        while(true) {
            System.out.println("1. Get All Notes");
            System.out.println("2. Get Note By ID");
            System.out.println("3. Add Note");
            System.out.println("4. Add Bulk Notes");
            System.out.println("5. Edit Notes");
            int choice = choose(5);

            switch (choice) {
                case 1 -> clientService.getAllNotes();
                case 2 -> noteById();
                case 3 -> addNote();
                case 0 -> {
                    return;
                }
            }
        }
    }

    private void addNote() {
        System.out.print("Title: ");
        String title=scanner.nextLine();
        System.out.print("Note: ");
        String note=scanner.nextLine();
        clientService.addNote(new NoteRequestDTO(title, note));

    }

    public void noteById(){
        System.out.print("Enter Note Id: ");
        int noteId=scanner.nextInt();
        scanner.nextLine();
        clientService.getNoteById(noteId);
    }
}
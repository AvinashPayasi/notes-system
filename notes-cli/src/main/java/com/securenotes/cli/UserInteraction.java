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

    public void startCli(){
        while(true) {
            System.out.print("\u001B[32m" + "secure-notes-> " + "\u001B[0m");

            String command = scanner.nextLine();
            if (command.isBlank()) {
                continue;
            }
            String[] params = command.trim().split("\\s+");
            handleCommand(params);
        }
    }

    public void handleCommand(String[] params){
        String command= params[0];

        switch(command){

            case "notes" -> clientService.getAllNotes();

            case "note" -> {
                if(params.length<2){
                    System.out.println("Usage: Note <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.getNoteById(id);
                }
            }

            case "add" -> addNote();

            case "delete" -> {
                if(params.length<2){
                    System.out.println("Usage: delete <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.deleteNote(id);
                }
            }

            case "recover" -> {
                if(params.length<2){
                    System.out.println("Usage: recover <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.recoverNote(id);
                }
            }

            case "purge" -> {
                if(params.length<2){
                    System.out.println("Usage: purge <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.deletePermanently(id);
                }
            }

            case "trash" -> {
                if (params.length < 2) {
                    clientService.getStateBasedNotes("trash");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.getTrashNoteById(id);
                }
            }

            case "pinned" -> {
                if(params.length<2){
                    clientService.getStateBasedNotes("pinned");
                    return;
                }
                /*int id=handleId(params[1]);
                if(id!=-1){
                    clientService.getPinnedNoteById(id);
                }*/
            }

            case "archived" -> {
                if(params.length<2){
                    clientService.getStateBasedNotes("archived");
                    return;
                }
            }

            case "pin" -> {
                if(params.length<2){
                    System.out.println("Usage: pin <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.pinNote(id);
                }

            }

            case "unpin" -> {
                if(params.length<2){
                    System.out.println("Usage: unpin <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.unpinNote(id);
                }
            }

            case "archive" -> {
                if(params.length<2){
                    System.out.println("Usage: archive <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.archiveNote(id);
                }
            }

            case "unarchive" -> {
                if(params.length<2){
                    System.out.println("Usage: unarchive <id>");
                    return;
                }
                int id=handleId(params[1]);
                if(id!=-1){
                    clientService.unarchiveNote(id);
                }
            }

            case "quit" -> {
                System.exit(0);
            }

            default -> System.out.println("Not a valid command");
        }
    }

    private int handleId(String id){
        try {
            int id1 = Integer.parseInt(id);
            return id1;
        }catch(NumberFormatException numberFormatException){
            System.out.println("Id can only be integer");
            return -1;
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

    private void addNote() {
        System.out.print("Title: ");
        String title=scanner.nextLine();
        StringBuilder note=new StringBuilder();
        System.out.print("Note (type END on new line to finish) : ");
        while(true){
            String line=scanner.nextLine();

            if(line.equals("END")){
                break;
            }

            note.append(line).append("\n");


        }

        clientService.addNote(new NoteRequestDTO(title, note.toString()));
    }
}
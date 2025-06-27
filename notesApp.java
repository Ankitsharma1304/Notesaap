import java.io.*;
import java.util.Scanner;
import java.nio.file.*;

public class notesApp {
    static final String NOTES_DIR = "notes";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File notesFolder = new File(NOTES_DIR);
        if (!notesFolder.exists()) notesFolder.mkdir();

        while (true) {
            System.out.println("\n📒 Notes App Menu:");
            System.out.println("1. Create Note");
            System.out.println("2. Read Note");
            System.out.println("3. Append to Note");
            System.out.println("4. Delete Note");
            System.out.println("5. List All Notes");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();  // consume newline

            switch (choice) {
                case 1 -> createNote(sc);
                case 2 -> readNote(sc);
                case 3 -> appendNote(sc);
                case 4 -> deleteNote(sc);
                case 5 -> listNotes();
                case 6 -> {
                    System.out.println("Exiting Notes App.");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void createNote(Scanner sc) {
        System.out.print("Enter note name: ");
        String name = sc.nextLine();
        File file = new File(NOTES_DIR + "/" + name + ".txt");

        if (file.exists()) {
            System.out.println("Note already exists!");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            System.out.println("Enter note content (end with a blank line):");
            String line;
            while (!(line = sc.nextLine()).isEmpty()) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Note created successfully!");
        } catch (IOException e) {
            System.out.println("Error writing note: " + e.getMessage());
        }
    }

    static void readNote(Scanner sc) {
        System.out.print("Enter note name: ");
        String name = sc.nextLine();
        File file = new File(NOTES_DIR + "/" + name + ".txt");

        if (!file.exists()) {
            System.out.println("Note not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\n--- Note Content ---");
            String line;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            System.out.println("Error reading note: " + e.getMessage());
        }
    }

    static void appendNote(Scanner sc) {
        System.out.print("Enter note name: ");
        String name = sc.nextLine();
        File file = new File(NOTES_DIR + "/" + name + ".txt");

        if (!file.exists()) {
            System.out.println("Note not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            System.out.println("Enter content to append (end with a blank line):");
            String line;
            while (!(line = sc.nextLine()).isEmpty()) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Content appended successfully!");
        } catch (IOException e) {
            System.out.println("Error appending note: " + e.getMessage());
        }
    }

    static void deleteNote(Scanner sc) {
        System.out.print("Enter note name to delete: ");
        String name = sc.nextLine();
        File file = new File(NOTES_DIR + "/" + name + ".txt");

        if (file.delete())
            System.out.println("Note deleted successfully.");
        else
            System.out.println("Could not delete note. Maybe it doesn't exist?");
    }

    static void listNotes() {
        File folder = new File(NOTES_DIR);
        String[] files = folder.list((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No notes found.");
            return;
        }

        System.out.println("\n📂 Notes:");
        for (String filename : files)
            System.out.println("- " + filename.replace(".txt", ""));
    }
}

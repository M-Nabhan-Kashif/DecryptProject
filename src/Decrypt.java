import java.util.Scanner;

/**
 * CS312 Assignment 9.
 *
 * On my honor, Mohammad N Kashif, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address: mohammadnkashif@utexas.edu
 *  UTEID: mnk665
 *  TA name: Yundi Li
 *  Number of slip days used on this assignment: 1
 *
 * Program to decrypt a message that has been
 * encrypted with a substitution cipher.
 * We assume only characters with ASCII codes
 * from 32 to 126 inclusive have been encrypted.
 */

public class Decrypt {
    // 3 Constants created for start, end, and length of ASCII values
    public static final int ASCII_START = 32;
    public static final int ASCII_END = 126;
    public static final int ASCII_LENGTH = 128;

    public static void main(String[] arg) {

        Scanner keyboard = new Scanner(System.in);
        String fileName = getFileName(keyboard);
        String encryptedText = DecryptUtilities.convertFileToString(fileName);
        String alteredText = new String(encryptedText);
        // Frequency array stores character frequencies received from getCharFrequency
        int[] frequency = getCharFrequency(alteredText);
        // currentKey array stores current key, initialized using decrypt utilities
        char[] currentKey = DecryptUtilities.getDecryptionKey(frequency);

        intro(encryptedText, alteredText, frequency, currentKey);

        // Boolean used to track if user is satisfied with the key and decrypted text or more change
        // is needed. askUser method modifies the solved boolean in each loop iteration.
        Boolean solved = false;
        while(solved == false) {
            solved = askUser(keyboard);
            if (solved == false) {
                char[] decryptChars = getChars(keyboard);
                currentKey = updateKey(decryptChars, currentKey);
                alteredText = alterText(encryptedText, currentKey);
                System.out.println("\nThe current version of the decrypted text is: \n");
                System.out.println(alteredText);
            }
            else {
                displayFinalResult(alteredText, currentKey);
            }
        }
        keyboard.close();
    }

    // Intro analysis including original text, character frequency, current key, and decryption
    // based on original key, is printed to user.
    private static void intro(String encryptedText, String alteredText, int[] frequency, char[] currentKey) {
        System.out.println("The encrypted text is:");
        System.out.println(encryptedText);
        printFrequency(frequency);
        printKey(currentKey);
        alteredText = alterText(encryptedText, currentKey);
        System.out.println("\nThe current version of the decrypted text is: \n");
        System.out.println(alteredText);
    }

    // User is asked if they want to make another/a change to the key. Boolean returned.
    private static Boolean askUser (Scanner kb) {
        Boolean solved = true;
        System.out.println("Do you want to make a change to the key?");
        System.out.print("Enter 'Y' or 'y' to make change: ");
        if (kb.next().toUpperCase().charAt(0) == 'Y') solved = false;
        return solved;
    }

    // User choice for decrypt character replacement read in and returned via 2 character array
    private static char[] getChars (Scanner kb) {
        char[] chars = new char[2];
        System.out.print("Enter the decrypt character you want to change: ");
        chars[0] = kb.next().charAt(0);
        System.out.print("Enter what the character " + chars[0] + " should decrypt to instead: ");
        chars[1] = kb.next().charAt(0);
        System.out.println(chars[0] + "'s will now decrypt to " + chars[1] +
                "'s and vice versa.");
        return chars;
    }

    // Array created for character frequencies and frequency analysis performed in for loop
    private static int[] getCharFrequency(String altered) {
        int[] frequencies = new int[ASCII_LENGTH];
        for (int i = 0; i < altered.length(); i++) {
            frequencies[altered.charAt(i)]++;
        }
        return frequencies;
    }

    // Method loops through frequency array and prints frequencies to user
    private static void printFrequency(int[] frequency) {
        System.out.println("Frequencies of characters.");
        System.out.println("Character - Frequency");
        for (int i = ASCII_START; i <= ASCII_END; i++) {
            System.out.println((char)i + " - " + frequency[i]);
        }
    }

    // Loops through key and prints each decrypt character along with the related encrypt character.
    private static void printKey(char[] currentKey) {
        System.out.println("\nThe current version of the key for ASCII characters 32 to 126 is: ");
        for (int i = ASCII_START; i <= ASCII_END; i++) {
            System.out.println("Encrypt character: " + (char)i + ", decrypt character: "
                    + currentKey[i]);
        }
    }

    // This method searches for the characters user wants to swap and makes the change using
    // a for loop
    private static char[] updateKey(char[] decryptChars, char[] currentKey) {
        int index = 0;
        char[] key = currentKey;
        for (int i = ASCII_START; i <= ASCII_END; i++) {
            if (key[i] == decryptChars[0]) key[i] = decryptChars[1];
            else if (key[i] == decryptChars[1]) key[i] = decryptChars[0];
        }
        return key;
    }

    // This method uses the current key to create a new decrypted version of the text.
    // A for loop is used to loop through the original text and match characters with the key.
    private static String alterText(String original, char[] currentKey) {
        String altered = "";
        for (int i = 0; i < original.length(); i++) {
            altered += (currentKey[original.charAt(i)]);
        }
        return altered;
    }

    // Method prints final key and final version of text when user is done changing the key.
    private static void displayFinalResult(String alteredText, char[] currentKey) {
        printKey(currentKey);
        System.out.println("\nThe final version of the decrypted text is: \n");
        System.out.println(alteredText);
    }

    // Get the name of file to use. For this assignment, no error
    // checking is required.
    public static String getFileName(Scanner kbScanner) {
        System.out.print("Enter the name of the encrypted file: ");
        String fileName = kbScanner.nextLine().trim();
        System.out.println();
        return fileName;
    }
}
package no.hvl.dat107.util;

import java.time.LocalDate;
import java.util.Scanner;

public class BrukerInputUtil {

    // Fjern unødvendige DAO-referanser og static scanner
    // Bruk scanner-parameter i stedet

    public static int lesHeltall(String melding, Scanner scanner) {
        System.out.print(melding);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ugyldig tall. Prøv igjen: ");
            }
        }
    }

    public static double lesDesimaltall(String melding, Scanner scanner) {
        System.out.print(melding);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ugyldig tall. Prøv igjen: ");
            }
        }
    }

    public static LocalDate lesDato(String melding, Scanner scanner) {
        System.out.print(melding);
        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) {
                    throw new IllegalArgumentException();
                }
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.print("Ugyldig datoformat. Bruk YYYY-MM-DD: ");
            }
        }
    }

    public static String lesStreng(String melding, Scanner scanner) {
        System.out.print(melding);
        return scanner.nextLine();
    }
}
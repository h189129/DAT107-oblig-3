package no.hvl.dat107.usecases;

import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.entitet.Prosjekt;
import no.hvl.dat107.util.BrukerInputUtil;
import java.util.Scanner;

public class ProsjektUseCases {
    private final Scanner scanner;
    private final ProsjektDAO prosjektDAO;

    public ProsjektUseCases(Scanner scanner, ProsjektDAO prosjektDAO) {
        this.scanner = scanner;
        this.prosjektDAO = prosjektDAO;
    }

    // Legger til et nytt prosjekt
    public void leggTilNyttProsjekt() {

        System.out.println("\nLegg til nytt prosjekt: ");
        String navn = BrukerInputUtil.lesStreng("Navn på prosjektet: ", scanner);
        String beskrivelse = BrukerInputUtil.lesStreng("Beskrivelse av prosjektet: ", scanner);

        Prosjekt nyttProsjekt = new Prosjekt(navn, beskrivelse);
        prosjektDAO.leggTilProsjekt(nyttProsjekt);
        System.out.println("Nytt prosjekt er lagt til!");
    }

    // Finn prosjekt med id
    public void sokEtterProsjektMedId(int id) {
        id = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID: ", scanner);
        Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(id);
        if (prosjekt != null) {
            prosjekt.skrivUtProsjekt();
        } else {
            System.out.println("Ingen ansatt funnet med ID " + id);
        }
    }


}

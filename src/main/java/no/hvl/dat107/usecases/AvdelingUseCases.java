package no.hvl.dat107.usecases;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.entitet.Avdeling;
import no.hvl.dat107.entitet.Ansatt;
import no.hvl.dat107.util.BrukerInputUtil;
import java.util.List;
import java.util.Scanner;

public class AvdelingUseCases {
    private final AvdelingDAO avdelingDAO;
    private final AnsattDAO ansattDAO;
    private final Scanner scanner;

    public AvdelingUseCases(AvdelingDAO avdelingDAO, AnsattDAO ansattDAO, Scanner scanner) {
        this.avdelingDAO = avdelingDAO;
        this.ansattDAO = ansattDAO;
        this.scanner = scanner;
    }

    // Vis avdeling etter ID (ingen endringer)
    public void sokEtterAvdelingId() {
        int id = BrukerInputUtil.lesHeltall("Skriv inn avdeling-ID: ", scanner);
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);
        if (avdeling == null) {
            System.out.println("Ingen avdeling funnet med ID " + id);
            return;
        }
        System.out.println("\n--- Avdeling ---");
        System.out.println("ID: " + avdeling.getId());
        System.out.println("Navn: " + avdeling.getNavn());
        System.out.println("Sjef: " + avdeling.getSjef().getFornavn() + " " + avdeling.getSjef().getEtternavn());
        System.out.println("Antall ansatte: " + avdeling.getAnsatte().size());
    }

    // Case 10: Vis alle avdelinger
    public void visAlleAvdelinger() {
        List<Avdeling> avdelinger = avdelingDAO.hentAlleAvdelinger();
        if (avdelinger.isEmpty()) {
            System.out.println("Ingen avdelinger registrert.");
        } else {
            System.out.println("\n--- Alle avdelinger ---");
            avdelinger.forEach(avdeling ->
                    System.out.println("ID: " + avdeling.getId() +
                            ", Navn: " + avdeling.getNavn() +
                            ", Sjef: " + avdeling.getSjef().getFornavn() +
                            ", Antall ansatte: " + avdeling.getAnsatte().size())
            );
        }
    }

    // Case 11: Legg til ny avdeling
    public void leggTilNyAvdeling() {
        System.out.println("\nLegg til ny avdeling:");
        String navn = BrukerInputUtil.lesStreng("Navn: ", scanner);
        int sjefId = BrukerInputUtil.lesHeltall("Sjef-ID: ", scanner);

        try {
            avdelingDAO.leggTilNyAvdelingMedSjef(navn, sjefId);
            System.out.println("Ny avdeling lagt til!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // Case 12: Vis ansatte på avdeling
    public void visAnsattePaaAvdeling() {
        int avdelingId = BrukerInputUtil.lesHeltall("Skriv inn avdeling-ID: ", scanner);
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdelingId);
        if (avdeling == null) {
            System.out.println("Ingen avdeling funnet med ID " + avdelingId);
            return;
        }
        System.out.println("\n--- Ansatte i " + avdeling.getNavn() + " ---");
        avdeling.getAnsatte().forEach(ansatt -> {
            String sjefStatus = (ansatt.getId() == avdeling.getSjef().getId()) ? "[SJEF] " : "";
            System.out.println(sjefStatus + ansatt.getFornavn() + " " + ansatt.getEtternavn());
        });
    }

    // Flytt sjef til annen avdeling
    public void flyttSjefTilAnnenAvdeling() {
        int gammelAvdelingId = BrukerInputUtil.lesHeltall("Gammel avdeling-ID: ", scanner);
        int nyAvdelingId = BrukerInputUtil.lesHeltall("Ny avdeling-ID: ", scanner);
        int nySjefId = BrukerInputUtil.lesHeltall("Ny sjef-ID: ", scanner);

        // Validering av forretningsregler
        Avdeling gammelAvdeling = avdelingDAO.finnAvdelingMedId(gammelAvdelingId);
        Avdeling nyAvdeling = avdelingDAO.finnAvdelingMedId(nyAvdelingId);
        Ansatt nySjef = ansattDAO.finnAnsattMedId(nySjefId);

        if (gammelAvdeling == null || nyAvdeling == null || nySjef == null) {
            System.out.println("Ugyldig ID(er)!");
            return;
        }
        if (!gammelAvdeling.getAnsatte().contains(nySjef)) {
            System.out.println(nySjef.getFornavn() + " jobber ikke i " + gammelAvdeling.getNavn() + "!");
            return;
        }
        if (avdelingDAO.erSjefIAnnenAvdeling(nySjefId, gammelAvdelingId)) {
            System.out.println(nySjef.getFornavn() + " er allerede sjef i en annen avdeling!");
            return;
        }

        // Utfør flytting
        avdelingDAO.flyttSjefTilNyAvdeling(gammelAvdelingId, nyAvdelingId, nySjefId);
        System.out.println("Sjef flyttet!");
    }
}
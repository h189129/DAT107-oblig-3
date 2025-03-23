package no.hvl.dat107.usecases;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.entitet.Ansatt;
import no.hvl.dat107.entitet.Avdeling;
import no.hvl.dat107.util.BrukerInputUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AnsattUseCases {
    private final AnsattDAO ansattDAO;
    private final AvdelingDAO avdelingDAO;
    private final Scanner scanner;

    public AnsattUseCases(AnsattDAO ansattDAO, AvdelingDAO avdelingDAO, Scanner scanner) {
        this.ansattDAO = ansattDAO;
        this.avdelingDAO = avdelingDAO;
        this.scanner = scanner;
    }

    // Case 1: Vis alle ansatte
    public void visAlleAnsatte() {
        List<Ansatt> ansatte = ansattDAO.hentAlleAnsatte();
        if (ansatte.isEmpty()) {
            System.out.println("Ingen ansatte registrert.");
        } else {
            ansatte.forEach(Ansatt::skrivUt);
        }
    }

    // Case 4: Legg til ny ansatt
    public void leggTilNyAnsatt() {
        System.out.println("\nLegg til ny ansatt:");
        String brukernavn = BrukerInputUtil.lesStreng("Brukernavn: ", scanner);

        // Valider unikt brukernavn
        if (ansattDAO.finnAnsattMedBrukernavn(brukernavn) != null) {
            System.out.println("Brukernavnet er allerede i bruk!");
            return;
        }

        String fornavn = BrukerInputUtil.lesStreng("Fornavn: ", scanner);
        String etternavn = BrukerInputUtil.lesStreng("Etternavn: ", scanner);
        LocalDate ansettelsesdato = BrukerInputUtil.lesDato("Ansettelsesdato (YYYY-MM-DD): ", scanner);
        String stilling = BrukerInputUtil.lesStreng("Stilling: ", scanner);
        double lonn = BrukerInputUtil.lesDesimaltall("Månedslønn: ", scanner);
        int avdelingId = BrukerInputUtil.lesHeltall("Avdeling-ID: ", scanner);

        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(avdelingId);
        if (avdeling == null) {
            System.out.println("Ugyldig avdeling-ID!");
            return;
        }

        Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, ansettelsesdato, stilling, lonn, avdeling);
        ansattDAO.leggTilNyAnsatt(nyAnsatt);
        System.out.println("Ansatt lagt til!");
    }

    // Case 7: Oppdater avdeling for ansatt
    public void oppdaterAvdelingForAnsatt() {
        int ansattId = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedId(ansattId);

        if (ansatt == null) {
            System.out.println("Ansatt ikke funnet!");
            return;
        }

        int nyAvdelingId = BrukerInputUtil.lesHeltall("Ny avdeling-ID: ", scanner);
        Avdeling nyAvdeling = avdelingDAO.finnAvdelingMedId(nyAvdelingId);

        if (nyAvdeling == null) {
            System.out.println("Avdeling ikke funnet!");
            return;
        }

        // Sjekk om ansatt er sjef i gammel avdeling
        Avdeling gammelAvdeling = ansatt.getAvdeling();
        if (gammelAvdeling.getSjef().getId() == ansattId) {
            System.out.println("Kan ikke flytte sjefen til avdelingen!");
            return;
        }

        ansattDAO.oppdaterAvdeling(ansattId, nyAvdeling);
        System.out.println("Avdeling oppdatert!");
    }

    // Case 5: Fjern ansatt
    public void fjernAnsatt() {
        int id = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID å slette: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);

        if (ansatt == null) {
            System.out.println("Ansatt ikke funnet!");
            return;
        }

        // Sjekk om ansatt er sjef i noen avdeling
        if (avdelingDAO.erSjefIAnnenAvdeling(ansatt.getId(), -1)) {
            System.out.println("Kan ikke slette en sjef!");
            return;
        }

        ansattDAO.fjernAnsatt(id);
        System.out.println("Ansatt slettet!");
    }

    // Case 2: Søk etter ansatt med ID
    public void sokEtterId() {
        int id = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
        if (ansatt != null) {
            ansatt.skrivUt();
        } else {
            System.out.println("Ingen ansatt funnet med ID " + id);
        }
    }

    // Case 3: Søk etter ansatt med brukernavn
    public void sokEtterBrukernavn() {
        String brukernavn = BrukerInputUtil.lesStreng("Skriv inn brukernavn: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedBrukernavn(brukernavn);
        if (ansatt != null) {
            ansatt.skrivUt();
        } else {
            System.out.println("Ingen ansatt funnet med brukernavn " + brukernavn);
        }
    }

    // Case 6: Oppdater stilling og lønn
    public void oppdaterStillingOgLonn() {
        int id = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);

        if (ansatt == null) {
            System.out.println("Ingen ansatt funnet med ID " + id);
            return;
        }

        System.out.println("\nNåværende informasjon:");
        ansatt.skrivUt();

        String nyStilling = BrukerInputUtil.lesStreng("Ny stilling: ", scanner);
        double nyLonn = BrukerInputUtil.lesDesimaltall("Ny månedslønn: ", scanner);

        ansattDAO.oppdaterStillingOgLonn(id, nyStilling, nyLonn);
        System.out.println("\nOppdatert informasjon:");
        ansattDAO.finnAnsattMedId(id).skrivUt();
    }

    // Case 8: Oppdater kun lønn
    public void oppdaterLonn() {
        int id = BrukerInputUtil.lesHeltall("Skriv inn ansatt-ID: ", scanner);
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);

        if (ansatt == null) {
            System.out.println("Ingen ansatt funnet med ID " + id);
            return;
        }

        System.out.println("\nNåværende lønn: " + ansatt.getMaanedslonn());
        double nyLonn = BrukerInputUtil.lesDesimaltall("Ny månedslønn: ", scanner);

        ansattDAO.oppdaterLonn(id, nyLonn);
        System.out.println("\nLønn oppdatert til: " + nyLonn);
        ansattDAO.finnAnsattMedId(id).skrivUt();
    }
}
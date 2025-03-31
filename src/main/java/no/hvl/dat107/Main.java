package no.hvl.dat107;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.AvdelingDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.dao.ProsjektdeltagelseDAO;
import no.hvl.dat107.meny.Meny;
import no.hvl.dat107.usecases.AnsattUseCases;
import no.hvl.dat107.usecases.AvdelingUseCases;
import no.hvl.dat107.usecases.ProsjektUseCases;
import no.hvl.dat107.usecases.ProsjektdeltagelseUseCases;
import no.hvl.dat107.util.BrukerInputUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");
        AnsattDAO ansattDAO = new AnsattDAO(emf);
        AvdelingDAO avdelingDAO = new AvdelingDAO(emf);
        ProsjektDAO prosjektDAO = new ProsjektDAO(emf);
        ProsjektdeltagelseDAO prosjektdeltagelseDAO = new ProsjektdeltagelseDAO(emf);
        Scanner scanner = new Scanner(System.in);

        AnsattUseCases ansattCases = new AnsattUseCases(ansattDAO, avdelingDAO, scanner);
        AvdelingUseCases avdelingCases = new AvdelingUseCases(avdelingDAO, ansattDAO, scanner);
        ProsjektUseCases prosjektCases = new ProsjektUseCases(scanner, prosjektDAO);
        ProsjektdeltagelseUseCases prosjektdeltagelseCases = new ProsjektdeltagelseUseCases(prosjektdeltagelseDAO, prosjektDAO, ansattDAO, scanner);

        boolean kjor = true;
        while (kjor) {
            Meny.skrivMeny();
            int valg = BrukerInputUtil.lesHeltall("Velg et alternativ: ", scanner);

            switch (valg) {
                case 1 -> ansattCases.visAlleAnsatte();
                case 2 -> ansattCases.sokEtterId();
                case 3 -> ansattCases.sokEtterBrukernavn();
                case 4 -> ansattCases.leggTilNyAnsatt();
                case 5 -> ansattCases.fjernAnsatt();
                case 6 -> ansattCases.oppdaterStilling();
                case 7 -> ansattCases.oppdaterAvdelingForAnsatt();
                case 8 -> ansattCases.oppdaterLonn();
                case 9 -> avdelingCases.sokEtterAvdelingId();
                case 10 -> avdelingCases.visAlleAvdelinger();
                case 11 -> avdelingCases.leggTilNyAvdeling();
                case 12 -> avdelingCases.visAnsattePaaAvdeling();
                case 13 -> avdelingCases.slettAvdeling();
                case 14 -> prosjektCases.leggTilNyttProsjekt();
                case 15 -> prosjektCases.sokEtterProsjektMedId();
                case 16 -> prosjektdeltagelseCases.registrerAnsattPaaProsjekt();
                case 17 -> prosjektdeltagelseCases.oppdaterTimerForAnsattPaaProsjekt();
                case 18 -> kjor = false;
                default -> System.out.println("Ugyldig valg, prøv igjen.");
            }
        }

        scanner.close();
        emf.close();
    }
}
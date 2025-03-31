package no.hvl.dat107.usecases;

import no.hvl.dat107.dao.AnsattDAO;
import no.hvl.dat107.dao.ProsjektDAO;
import no.hvl.dat107.dao.ProsjektdeltagelseDAO;
import no.hvl.dat107.entitet.Ansatt;
import no.hvl.dat107.entitet.Prosjekt;
import no.hvl.dat107.entitet.Prosjektdeltagelse;
import no.hvl.dat107.util.BrukerInputUtil;

import java.util.Scanner;

public class ProsjektdeltagelseUseCases {
    private final ProsjektdeltagelseDAO prosjektdeltagelseDAO;
    private final ProsjektDAO prosjektDAO;
    private final AnsattDAO ansattDAO;
    private final Scanner scanner;

    public ProsjektdeltagelseUseCases(ProsjektdeltagelseDAO prosjektdeltagelseDAO, ProsjektDAO prosjektDAO, AnsattDAO ansattDAO, Scanner scanner) {
        this.prosjektdeltagelseDAO = prosjektdeltagelseDAO;
        this.prosjektDAO = prosjektDAO;
        this.ansattDAO = ansattDAO;
        this.scanner = scanner;
    }

    // Case 16: Registrer ansatt på prosjekt
    public void registrerAnsattPaaProsjekt() {
        System.out.println("Registrer ansatt på prosjekt: ");
        int ansattId = BrukerInputUtil.lesHeltall("Ansatt-ID: ", scanner);
        int prosjektId = BrukerInputUtil.lesHeltall("Prosjekt-ID: ", scanner);
        String rolle = BrukerInputUtil.lesStreng("Rolle for prosjektet: ", scanner);

        Ansatt ansatt = ansattDAO.finnAnsattMedId(ansattId);
        if (ansatt == null) {
            System.out.println("Ansatt-ID eksisterer ikke.");
            return;
        }

        Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(prosjektId);
        if (prosjekt == null) {
            System.out.println("Prosjekt-ID eksisterer ikke.");
            return;
        }

        Prosjektdeltagelse nyDeltager = new Prosjektdeltagelse(ansatt, prosjekt, rolle);
        prosjektdeltagelseDAO.registrerDeltagelse(nyDeltager);
        System.out.println("Ansatt lagt til!");

    }

    // Case 17: Oppdater timer for ansatt på prosjekt
    public void oppdaterTimerForAnsattPaaProsjekt() {
        System.out.println("Registrer timer for ansatt på prosjekt: ");
        int ansattId = BrukerInputUtil.lesHeltall("Ansatt-ID: ", scanner);
        int prosjektId = BrukerInputUtil.lesHeltall("Prosjekt-ID: ", scanner);
        int antTimer = BrukerInputUtil.lesHeltall("Antall timer: ", scanner);

        Ansatt ansatt = ansattDAO.finnAnsattMedId(ansattId);
        if (ansatt == null) {
            System.out.println("Ansatt-ID eksisterer ikke.");
            return;
        }

        Prosjekt prosjekt = prosjektDAO.finnProsjektMedId(prosjektId);
        if (prosjekt == null) {
            System.out.println("Prosjekt-ID eksisterer ikke.");
            return;
        }

        prosjektdeltagelseDAO.oppdaterTimer(ansattId, prosjektId, antTimer);
        System.out.println("Timer er oppdatert.");
    }
}

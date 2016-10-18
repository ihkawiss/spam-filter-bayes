package ch.fhnw.dist.spamfilter;

import ch.fhnw.dist.spamfiler.filter.MailFilter;
import ch.fhnw.dist.spamfilter.model.Mail;
import ch.fhnw.dist.spamfilter.util.MailAnalyser;
import ch.fhnw.dist.spamfilter.util.MailReader;
import ch.fhnw.dist.spamfilter.util.MailStatistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class Main {

    // formatting output a little bit
    public final static String line   = "-----------------------------------------------------------";
    public final static String tab  = "  ";

    public static void main(String[] args) {

        greetUser();

        learnMails();

        calibrateMails();

        analyseTestMails();

        analyseMail();
    }

    // introduce application to user
    private static void greetUser(){
        System.out.println(line);
        System.out.println(tab + "SPAM FILTER NACH BAYES THEOREM");
        System.out.println();
        System.out.println(tab + "FHNW, Diskrete Stochastik HS16");
        System.out.println(tab + "Team: Kevin Kirn, Hoang Tran");
        System.out.println(line);
    }

    // learn mails upon start
    private static void learnMails(){
        System.out.println();
        System.out.println("(2.a) Anlernen von Mails wird durchgeführt (ham-anlern.zip, spam-anlern.zip)");
        System.out.println();

        // read mails
        MailReader mailReader = new MailReader();
        mailReader.readMails();

        // Analyse mails
        MailAnalyser.getInstance().analyse(MailReader.learnMails);

        // print out analysed count
        MailStatistic statistic = MailStatistic.getInstance();
        System.out.println(tab + tab + "HAM Mails analysiert: " + statistic.getHamMailsProcessed());
        System.out.println(tab + tab + "SPAM Mails analysiert: " + statistic.getSpamMailsProcessed());
        System.out.println();
    }

    // ask user which mail to analyse
    private static void analyseMail() {
        System.out.println();
        System.out.println("(2.e) Analyse einer Mail durch den Benutzer.");
        System.out.println();
        System.out.println("Geben Sie den Dateinamen (aus ham-test.zip oder spam-test.zip) der zu analysierenden Mail ein:");

        // read file name
        Scanner s = new Scanner(System.in);
        String fileName = "";

        // wait for valid input
        while (fileName == null || fileName.isEmpty())
            fileName = s.next();

        // find mail within test mails
        final String finalFileName = fileName;
        List<Mail> mailsFound = Mail.testMails.stream()
                                            .filter(m -> m.getFileName()
                                            .equals(finalFileName))
                                            .collect(Collectors.toList());

        // test if mail was found
        if (mailsFound.isEmpty()) {
            System.out.println();
            System.out.println("!!! EINGEGEBENE MAIL KONNTE NICHT GEFUNDEN WERDEN !!!");
            System.out.println(line);
            System.out.println();
            analyseMail(); // no Mails was found, retry!
        } else {

            // get first found mail
            Mail mailToAnalyse = mailsFound.get(0);

            // fire mail filter on mail
            double result = MailFilter.getInstance().filter(mailToAnalyse);

            System.out.println();
            System.out.println(Main.tab + "Berechnete SPAM-Wahrscheinlichkeit beträgt: " + result + "%");
            System.out.println();
            System.out.println(Main.tab + "FOLGLICHES ERGEBNIS: " + Main.tab + (result < 52d ? "HAM" : "SPAM"));
            System.out.println();

            System.out.println(Main.line);
            System.out.println();
            System.out.println("Ordnen Sie dieses Mail gleich ein?");
            System.out.println(Main.tab + Main.tab + "(1) JA " + Main.tab + Main.tab + "(2) NEIN");
            System.out.println();

            // read answer


        }

    }

    private static void calibrateMails(){
        System.out.println(line);
        System.out.println();
        System.out.println("(2.b / 2.c) Kalibrierung wird durchgeführt (ham-kalibrierung.zip, spam-kalibrierung.zip)");
        System.out.println();

        // Calibrate / analyse mails
        MailAnalyser.getInstance().analyse(MailReader.calibrateMails);

        // print out analysed count
        MailStatistic statistic = MailStatistic.getInstance();
        System.out.println(tab + tab + "Total HAM Mails analysiert: " + statistic.getHamMailsProcessed());
        System.out.println(tab + tab + "Total SPAM Mails analysiert: " + statistic.getSpamMailsProcessed());
        System.out.println();

        // print median for spam and ham mails to find current edge
        List<Mail> allMails = new ArrayList<>();
        allMails.addAll(MailReader.calibrateMails);
        allMails.addAll(MailReader.learnMails);

        System.out.println(tab + tab +"BESTIMMUNG SCHWELLENWERT");
        System.out.println();

        // count spamms
        List<Mail> spams = allMails.stream().filter(m -> m.isSpam()).collect(Collectors.toList());
        double medianSpam = MailAnalyser.getInstance().calculateProbabilitiesOfList(spams);
        System.out.println(tab + tab + "Median berechnete Wahrscheinlichkeiten bei Spam Mails: " + (int)medianSpam + "%");

        // count hams
        List<Mail> hams = allMails.stream().filter(m -> !m.isSpam()).collect(Collectors.toList());
        double medianHam = MailAnalyser.getInstance().calculateProbabilitiesOfList(hams);
        System.out.println(tab + tab + "Median berechnete Wahrscheinlichkeiten bei Ham Mails: " + (int)medianHam + "%");
        System.out.println();
        System.out.println(tab + tab + "SPAM wenn Wahrscheinlichkeit > 51%");
        System.out.println();

        System.out.println(line);

    }

    private static void analyseTestMails(){
        System.out.println();
        System.out.println("(2.d) Berechnung Erkennungsrate in ham-test.zip und spam-test.zip");
        System.out.println();

        double successRate = MailAnalyser.getInstance().calculateSuccessRate(MailReader.testMails);
        System.out.println(tab + tab + "Erfolgsrate bei " + MailReader.testMails.size() + " analysierten Mails beträgt: " + successRate + "%");
        System.out.println();
        System.out.println(line);
    }

}

package ch.fhnw.dist.spamfilter;

import ch.fhnw.dist.spamfiler.filter.MailFilter;
import ch.fhnw.dist.spamfilter.model.Mail;
import ch.fhnw.dist.spamfilter.util.MailReader;
import ch.fhnw.dist.spamfilter.util.MailStatistic;

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

        printBayes();
        greetUser();

        learnMails();

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
        System.out.println("(1) Anlernen von Mails wird durchgeführt...");
        System.out.println();

        // read mails
        MailReader mailReader = new MailReader();
        mailReader.readMails();

        // print out analysed count
        MailStatistic statistic = MailStatistic.getInstance();
        System.out.println(tab + tab + "HAM Mails analysiert: " + statistic.getHamMailsProcessed());
        System.out.println(tab + tab + "SPAM Mails analysiert: " + statistic.getSpamMailsProcessed());
        System.out.println(line);
        System.out.println();
    }

    // ask user which mail to analyse
    private static void analyseMail(){
        System.out.println("Geben Sie den Dateinamen (aus ham-test.zip oder spam-test.zip) der zu analysierenden Mail ein:");

        // read file name
        Scanner s = new Scanner(System.in);
        String fileName = "";

        // wait for valid input
        while(fileName == null || fileName.isEmpty())
            fileName = s.next();

        // find mail within test mails
        final String finalFileName = fileName;
        List<Mail> mailsFound = Mail.testMails.stream()
                                            .filter(m->m.getFileName()
                                            .equals(finalFileName))
                                            .collect(Collectors.toList());

        // test if mail was found
        if(mailsFound.isEmpty()){
            System.out.println();
            System.out.println("!!! EINGEGEBENE MAIL KONNTE NICHT GEFUNDEN WERDEN !!!");
            System.out.println(line);
            System.out.println();
            analyseMail(); // no Mails was found, retry!
        } else {

            // get first found mail
            Mail mailToAnalyse = mailsFound.get(0);

            // fire mail filter on mail
            MailFilter.getInstance().filter(mailToAnalyse);

            // retry
            analyseMail();
        }

    }

    private static void printBayes(){
        System.out.println(line);
        System.out.println("Ungefähr so sah Herr Bayes aus:");
        System.out.println("\n" +
                "               `/hddhdhhhyo.                       \n" +
                "             +dmdhhysshhysyho-                    \n" +
                "            +ds+y+ys+shhhyohyo-                   \n" +
                "           -dyysoosso+:--:/osdm:                  \n" +
                "           /d.    ``     .:-osmo                  \n" +
                "           -+           .-::+ymo                  \n" +
                "            .`        `---:-/ymy                  \n" +
                "            :yys/-`./syss+:.-dyo.                 \n" +
                "            `+o/o+. oo//so+.`hys/                 \n" +
                "             .` ..  sh- ..`/+hoo:                 \n" +
                "             .`    `h+-  .::ohyys-                \n" +
                "             `:. .-:yys: -..+ym+dy+               \n" +
                "            -++.-+::/oo+...:oh/ohso+`             \n" +
                "         .smNmh```./o+o:.-:/y/odhyyyy-            \n" +
                "       -sdmmmmmh/.  .`.-o+oosmdhdyoooo+/:.`       \n" +
                "     -ymdhyyhmmyoosoossys++-yNdyys:+/++/::/+-`    \n" +
                "   `sdhhyosyyo-..```:..`-:/:+mdsy+:+/:/-:-:oo/.   \n" +
                "  .yhyy+shsh+:-`   /:.    .:odh+s-o/+:::o::++oo:  \n" +
                " .sshs/yo+hs--.   -y .   .--yhyo-/s+o:-:o+:-+o:.  \n" +
                "`oyyyoo/ydm/-..`  h/.-.` .--yyoo:h+/+-..://:---`  \n" +
                "`+/ssh+ydmy:--..`:m:--..``..hso:so:y-...:::::..   \n" +
                "`.-/y-shds---::- +m/--.----`y+/oo/y+..`.:-:-`     \n" +
                "  .o:.hshs/-----`od::-..-..`//+s//o-.```-..`      \n" +
                "   ..-o+/o:-/+osshs+++:/:/+:::-/::...``   `       \n" +
                "     -:..---..::-:--.`....-.``` ``.```           ");
        System.out.println();
    }

}

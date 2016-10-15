package ch.fhnw.dist.spamfilter;

import ch.fhnw.dist.spamfilter.util.MailReader;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class Main {

    public static void main(String[] args) {
        MailReader mailReader = new MailReader();
        mailReader.readMails();
    }

}

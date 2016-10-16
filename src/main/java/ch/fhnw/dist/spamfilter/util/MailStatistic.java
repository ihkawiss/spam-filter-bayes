package ch.fhnw.dist.spamfilter.util;

import ch.fhnw.dist.spamfilter.model.Mail;

import java.util.HashMap;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailStatistic {

    private MailStatistic(){}

    private static class MailStatisticHolder {
        private static final MailStatistic INSTANCE = new MailStatistic();
    }

    public static MailStatistic getInstance() {
        return MailStatisticHolder.INSTANCE;
    }

    private HashMap<String, Float[]> wordMap;

    // ------------------------ TESTING REMOVE ME ------------------------

    private Mail testMail;

    public Mail getTestMail() {
        return testMail;
    }

    public void setTestMail(Mail testMail) {
        this.testMail = testMail;
    }

    // ------------------------      TEST END     ------------------------


    public HashMap<String, Float[]> getWordMap() {
        return wordMap;
    }

    public void setWordMap(HashMap<String, Float[]> wordMap) {
        this.wordMap = wordMap;
    }

}

package ch.fhnw.dist.spamfilter.util;

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

    private HashMap<String, Integer> hamWords;
    private HashMap<String, Integer> spamWords;

    public HashMap<String, Integer> getHamWords() {
        return hamWords;
    }

    public void setHamWords(HashMap<String, Integer> hamWords) {
        this.hamWords = hamWords;
    }

    public HashMap<String, Integer> getSpamWords() {
        return spamWords;
    }

    public void setSpamWords(HashMap<String, Integer> spamWords) {
        this.spamWords = spamWords;
    }

}

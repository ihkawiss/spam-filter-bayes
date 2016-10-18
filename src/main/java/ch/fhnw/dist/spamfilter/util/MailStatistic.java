package ch.fhnw.dist.spamfilter.util;

import ch.fhnw.dist.spamfilter.model.Mail;

import java.util.HashMap;
import java.util.List;

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

    private HashMap<String, Float[]> wordMap = new HashMap<>();

    private int spamMailsProcessed;
    private int hamMailsProcessed;

    public Integer getSpamMailsProcessed() {
        return spamMailsProcessed;
    }

    public void setSpamMailsProcessed(Integer spamMailsProcessed) {
        this.spamMailsProcessed = spamMailsProcessed;
    }

    public Integer getHamMailsProcessed() {
        return hamMailsProcessed;
    }

    public void setHamMailsProcessed(Integer hamMailsProcessed) {
        this.hamMailsProcessed = hamMailsProcessed;
    }

    public void incrementSpamMailsProcessed(){
        this.spamMailsProcessed++;
    }

    public void incrementHamMailsProcessed(){
        this.hamMailsProcessed++;
    }

    // ------------------------ TESTING REMOVE ME ------------------------

    private List<Mail> testMails;

    public List<Mail> getTestMail() {
        return testMails;
    }

    public void setTestMail(List<Mail> testMail) {
        this.testMails = testMail;
    }

    // ------------------------      TEST END     ------------------------


    public HashMap<String, Float[]> getWordMap() {
        return wordMap;
    }

    public void setWordMap(HashMap<String, Float[]> wordMap) {
        this.wordMap = wordMap;
    }

}

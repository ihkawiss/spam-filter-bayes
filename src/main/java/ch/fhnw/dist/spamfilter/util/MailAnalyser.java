package ch.fhnw.dist.spamfilter.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ch.fhnw.dist.spamfilter.model.Mail;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailAnalyser {

    private final String SPLITTER = " ";

    private MailAnalyser(){}

    private static class MailAnalyserHolder {
        private static final MailAnalyser INSTANCE = new MailAnalyser();
    }

    public static MailAnalyser getInstance() {
        return MailAnalyserHolder.INSTANCE;
    }

    /**
     * Analyse words within mail and store statistics
     * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
     * @param mails List of mails to analyse
     */
    public void analyse(List<Mail> mails){

        if(mails == null || mails.size() < 1)
            throw new IllegalArgumentException("Mail list can't be null or empty.");

        MailStatistic statistic = MailStatistic.getInstance();

        // word count holder
        HashMap<String, Integer> spamWords = new HashMap<String, Integer>();
        HashMap<String, Integer> hamWords = new HashMap<String, Integer>();

        // iterate through all spam mails and count words
        mails.stream().filter(m -> m.isSpam()).forEach(m -> {
            countWordsToList(spamWords, m);
            statistic.setSpamWords(spamWords);
        });

        // iterate through all spam mails and count words
        mails.stream().filter(m -> !m.isSpam()).forEach(m -> {
            countWordsToList(hamWords, m);
            statistic.setHamWords(hamWords);
        });

    }

    /**
     * Count words in Mail and store result into map
     * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
     * @param words HashMap to store word count
     * @param m Mail reference to count on
     */
    public void countWordsToList(HashMap<String, Integer> words, Mail m){
        for(String word : m.getContent().split(SPLITTER)){
            words.put(word, words.get(word) == null ? 1 : words.get(word) + 1);
        }
    }

}

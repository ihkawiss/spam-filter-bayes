package ch.fhnw.dist.spamfilter.util;

import java.util.HashMap;
import java.util.List;

import ch.fhnw.dist.spamfiler.filter.MailFilter;
import ch.fhnw.dist.spamfilter.model.Mail;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailAnalyser {

    public static final String SEPARATOR = "[\\s\\n]";
    public static final int SPAM_COUNT_INDEX = 0;
    public static final int HAM_COUNT_INDEX = 1;

    private MailAnalyser() {
    }

    private static class MailAnalyserHolder {
        private static final MailAnalyser INSTANCE = new MailAnalyser();
    }

    public static MailAnalyser getInstance() {
        return MailAnalyserHolder.INSTANCE;
    }

    /**
     * Analyse words within mail and store statistics
     *
     * @param mails List of mails to analyse
     * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
     */
    public void analyse(List<Mail> mails) {

        if (mails == null || mails.size() < 1)
            throw new IllegalArgumentException("Mail list can't be null or empty.");

        MailStatistic statistic = MailStatistic.getInstance();

        // word count holder
        HashMap<String, Float[]> words = statistic.getWordMap();

        // iterate through all spam mails and count words
        mails.stream().filter(m -> m.isSpam()).forEach(m -> {
            countWordsToList(words, m);
            statistic.setWordMap(words);
            statistic.incrementSpamMailsProcessed();
        });

        // iterate through all spam mails and count words
        mails.stream().filter(m -> !m.isSpam()).forEach(m -> {
            countWordsToList(words, m);
            statistic.setWordMap(words);
            statistic.incrementHamMailsProcessed();
        });

    }

    public double calculateProbabilitiesOfList(List<Mail> mails){

        double sum = 0;

        for(Mail m : mails)
            sum += MailFilter.getInstance().filter(m);

        return sum / mails.size();
    }

    public double calculateSuccessRate(List<Mail> mails){

        int fails = 0;

        for(Mail m: mails){
            double propability = MailFilter.getInstance().filter(m);

            if(propability > 52d && !m.isSpam())
                fails++; // mail is no spam
        }

        return (mails.size() - fails) / (mails.size() / 100);
    }


    /**
     * Count words in Mail and store result into map
     *
     * @param words HashMap to store word count
     * @param m     Mail reference to count on
     * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
     */
    public void countWordsToList(HashMap<String, Float[]> words, Mail m) {
        for (String word : m.getContent().split(SEPARATOR)) {

            if (words.containsKey(word)) {
                Float[] counts = words.get(word);

                if (m.isSpam())
                    counts[SPAM_COUNT_INDEX]++;
                else
                    counts[HAM_COUNT_INDEX]++;

            } else {
                Float[] counts;

                if (m.isSpam())
                    counts = new Float[]{1f, 0.001f};
                else
                    counts = new Float[]{0.001f, 1f};

                words.put(word, counts);
            }

        }
    }

}

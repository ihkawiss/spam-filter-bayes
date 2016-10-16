package ch.fhnw.dist.spamfiler.filter;

import ch.fhnw.dist.spamfilter.model.Mail;
import ch.fhnw.dist.spamfilter.util.MailAnalyser;
import ch.fhnw.dist.spamfilter.util.MailReader;
import ch.fhnw.dist.spamfilter.util.MailStatistic;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailFilter {

    private MailFilter(){}

    private static class MailFilterHolder {
        private static final MailFilter INSTANCE = new MailFilter();
    }

    public static MailFilter getInstance() {
        return MailFilterHolder.INSTANCE;
    }

    /**
     *
     * @param m Mail to filter
     * @return true if is probably spam
     */
    public boolean filter(Mail m){

        MailStatistic statistic = MailStatistic.getInstance();

        HashMap<String, Float> mostSignificantWords = getMostSignificantWords(m);

        double spamValue = 1;
        double hamValue = 1;

        for(HashMap.Entry<String, Float> entry : mostSignificantWords.entrySet()){

            // get word from statistics
            Float[] counts = statistic.getWordMap().get(entry.getKey());

            // calculate spam value
            spamValue *= counts[MailAnalyser.SPAM_COUNT_INDEX] / statistic.getSpamMailsProcessed();

            // calculate ham value
            hamValue *= counts[MailAnalyser.HAM_COUNT_INDEX] / statistic.getHamMailsProcessed();

        }

        double result = spamValue / (spamValue + hamValue);

        System.out.println("Spam Wahrscheinlichkeit beträgt " + result * 100 + "% ---> wirklich spam: " + m.isSpam());

        return false;
    }

    /**
     * TODO: write something
     * @param m
     * @return
     */
    private HashMap<String, Float> getMostSignificantWords(Mail m){

        HashMap<String, Float> mostSignificantWords = new HashMap<>();

        MailStatistic statistic = MailStatistic.getInstance();

        // get all words from mail
        String[] words = m.getContent().split(MailAnalyser.SEPERATOR);

        // find 10 most significant based on statistics
        for(String word : words){

            // check if word was already rated
            if(statistic.getWordMap().containsKey(word)){
                Float[] counts = statistic.getWordMap().get(word);

                // sum of spam & ham eq significantly value
                float sum = counts[0];

                if(mostSignificantWords.size() < 10) {
                    mostSignificantWords.put(word, sum);
                }
                else{

                    // find smallest entry which is smaller than the word we want to add
                    HashMap.Entry<String, Float> min = null;
                    for (HashMap.Entry<String, Float> entry : mostSignificantWords.entrySet()) {
                        if (entry.getValue() < sum && (min == null || min.getValue() > entry.getValue())) {
                            min = entry;
                        }
                    }

                    // if a entry < word was found, replace it
                    if(min != null){
                        // remove least significantly entry from list
                        mostSignificantWords.remove(min.getKey());

                        // insert current word into list
                        mostSignificantWords.put(word, sum);
                    }

                }
            }

        }

        return mostSignificantWords;
    }
}

package ch.fhnw.dist.spamfilter.util;

import java.util.HashMap;
import java.util.List;

import ch.fhnw.dist.spamfilter.model.Mail;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailAnalyser {

    public static final String SEPERATOR = "[\\s\\n]";
    private final int SPAM_COUNT_INDEX = 0;
    private final int HAM_COUNT_INDEX = 1;

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

        // @FIXME: remove this piece of shit
        statistic.setTestMail(mails.get(12));

        // word count holder
        HashMap<String, Float[]> words = new HashMap<>();

        // iterate through all spam mails and count words
        mails.stream().filter(m -> m.isSpam()).forEach(m -> {
            countWordsToList(words, m);
            statistic.setWordMap(words);
        });

        // iterate through all spam mails and count words
        mails.stream().filter(m -> !m.isSpam()).forEach(m -> {
            countWordsToList(words, m);
            statistic.setWordMap(words);
        });


        // DEBUG, @FIXME: remove if no longer needed
        /*for(HashMap.Entry<String, Float[]> entry : statistic.getWordMap().entrySet()) {
            String key = entry.getKey();
            Float[] value = entry.getValue();

            if(value[0] > 10f && value[1] < 10f)
                System.out.println(key + ": {" + value[0] + " , " + value[1] + "}");
        }*/

    }

    /**
     * Count words in Mail and store result into map
     * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
     * @param words HashMap to store word count
     * @param m Mail reference to count on
     */
    public void countWordsToList(HashMap<String, Float[]> words, Mail m){
        for(String word : m.getContent().split(SEPERATOR)){

            if(words.containsKey(word)){
                Float[] counts = words.get(word);

                if(m.isSpam())
                    counts[SPAM_COUNT_INDEX]++;
                else
                    counts[HAM_COUNT_INDEX]++;

            } else {
                Float[] counts;

                if(m.isSpam())
                    counts = new Float[]{1f, 0.001f};
                else
                    counts = new Float[]{0.001f, 1f};

                words.put(word, counts);
            }

        }
    }

}

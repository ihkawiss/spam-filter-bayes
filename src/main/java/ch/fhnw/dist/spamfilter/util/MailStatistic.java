package ch.fhnw.dist.spamfilter.util;

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

    // manage mail statistics here...
}

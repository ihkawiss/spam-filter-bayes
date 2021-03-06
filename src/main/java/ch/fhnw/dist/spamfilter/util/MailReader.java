package ch.fhnw.dist.spamfilter.util;

import ch.fhnw.dist.spamfilter.model.Mail;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 15.10.2016
 *
 * @author Kevin Kirn <kevin.kirn@students.fhnw.ch>
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class MailReader {

    public static List<Mail> learnMails = null;
    public static List<Mail> testMails = null;
    public static List<Mail> calibrateMails = null;

    // filenames
    private static final String HAM_ANLERN_FILENAME = "ham-anlern.zip";
    private static final String SPAM_ANLERN_FILENAME = "spam-anlern.zip";
    private static final String HAM_TEST_FILENAME = "ham-test.zip";
    private static final String SPAM_TEST_FILENAME = "spam-test.zip";
    private static final String HAM_CALIBRATE_FILENAME = "ham-kallibrierung.zip";
    private static final String SPAM_CALIBRATE_FILENAME = "spam-kallibrierung.zip";

    public void readMails() {
        // delete temp dir for clean environment
        FileUtils.deleteTempDir();

        // create empty temp dir
        File tempDir = new File(FileUtils.getTempPath());
        tempDir.mkdir();

        learnMails = new ArrayList<>();
        testMails = new ArrayList<>();
        calibrateMails = new ArrayList<>();

        // ham anlern mails
        ZipFile hamAnlernFile = FileUtils.getZipFileFromResources(HAM_ANLERN_FILENAME);
        List<Mail> hamAnlernMails = readMailsFromZip(hamAnlernFile, false);
        learnMails.addAll(hamAnlernMails);

        // delete temp dir to prevent reloading ham mails
        FileUtils.deleteTempDir();

        // spam anlern mails
        ZipFile spamAnlernFile = FileUtils.getZipFileFromResources(SPAM_ANLERN_FILENAME);
        List<Mail> spamAnlernMails = readMailsFromZip(spamAnlernFile, true);
        learnMails.addAll(spamAnlernMails);

        // delete temp dir to prevent reloading ham mails
        FileUtils.deleteTempDir();

        // ham test
        ZipFile hamTestFile = FileUtils.getZipFileFromResources(HAM_TEST_FILENAME);
        testMails.addAll(readMailsFromZip(hamTestFile, false));

        // delete temp dir to prevent reloading ham mails
        FileUtils.deleteTempDir();

        // spam test
        ZipFile spamTestFile = FileUtils.getZipFileFromResources(SPAM_TEST_FILENAME);
        testMails.addAll(readMailsFromZip(spamTestFile, true));

        // delete temp dir to prevent reloading ham mails
        FileUtils.deleteTempDir();

        // ham calibrate
        ZipFile hamCalibrateFile = FileUtils.getZipFileFromResources(HAM_CALIBRATE_FILENAME);
        calibrateMails.addAll(readMailsFromZip(hamCalibrateFile, false));

        // delete temp dir to prevent reloading ham mails
        FileUtils.deleteTempDir();

        // spam calibrate
        ZipFile spamCalibrateFile = FileUtils.getZipFileFromResources(SPAM_CALIBRATE_FILENAME);
        calibrateMails.addAll(readMailsFromZip(spamCalibrateFile, true));

        // save all test mails
        Mail.testMails.addAll(testMails);

        // delete temp dir for clean environment
        FileUtils.deleteTempDir();
    }

    private List<Mail> readMailsFromZip(ZipFile zipFile, boolean isSpam) {
        String tempDirPath = FileUtils.getTempPath();
        try {
            zipFile.extractAll(tempDirPath);
            File tempDir = new File(tempDirPath);
            return readMailsFromDirectory(tempDir, isSpam);
        } catch (ZipException e) {
            System.err.println("Could not extract zip: " + tempDirPath);
        }
        return new ArrayList<>();
    }

    private List<Mail> readMailsFromDirectory(File dir, boolean isSpam) {
        List<Mail> mails = new ArrayList<>();

        File[] mailFiles = dir.listFiles();
        if (mailFiles != null && mailFiles.length > 0) {
            for (File mailFile : mailFiles) {
                String fileName = mailFile.getName();
                String content = FileUtils.getFileContent(mailFile);
                Mail mail = new Mail(fileName, isSpam, content);
                mails.add(mail);
            }
        }

        return mails;
    }
}

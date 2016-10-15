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
    private static final String HAM_ANLERN_FILENAME = "ham-anlern.zip";
    private static final String SPAM_ANLERN_FILENAME = "spam-anlern.zip";
    //private static final String TEMP_PATH = Paths.get(".").toAbsolutePath().normalize().toString() + "/spam-filter-bayer-temp";

    public void readMails() {
        // delete temp dir for clean environment
        FileUtils.deleteTempDir();

        List<Mail> mails = new ArrayList<>();

        // ham anlern mails
        ZipFile hamAnlernFile = FileUtils.getZipFileFromResources(HAM_ANLERN_FILENAME);
        List<Mail> hamAnlernMails = readMailsFromZip(hamAnlernFile, false);
        mails.addAll(hamAnlernMails);

        // delete temp dir to prevent realoding ham mails
        FileUtils.deleteTempDir();

        // spam anlern mails
        ZipFile spamAnlernFile = FileUtils.getZipFileFromResources(SPAM_ANLERN_FILENAME);
        List<Mail> spamAnlernMails = readMailsFromZip(spamAnlernFile, true);
        mails.addAll(spamAnlernMails);


    }

    private List<Mail> readMailsFromZip(ZipFile zipFile, boolean isSpam) {
        String tempDirPath = FileUtils.TEMP_PATH;
        try {
            zipFile.extractAll(tempDirPath);
            File tempDir = new File(tempDirPath);
            return readMailsFromDirectory(tempDir, false);
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

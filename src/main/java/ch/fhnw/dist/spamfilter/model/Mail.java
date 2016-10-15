package ch.fhnw.dist.spamfilter.model;

/**
 * @author Hoang
 */
public class Mail {
    private String fileName;
    private boolean isSpam;
    private String content;

    public Mail(String fileName, boolean isSpam, String content) {
        this.fileName = fileName;
        this.isSpam = isSpam;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

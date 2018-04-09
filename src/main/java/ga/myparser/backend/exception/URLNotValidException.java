package ga.myparser.backend.exception;

public class URLNotValidException extends RuntimeException {
    private String url;

    public URLNotValidException(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

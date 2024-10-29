package app.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ApiResponse<T> {
    @JsonProperty("response_code")
    private int responseCode;
    
    @JsonProperty("results")
    private List<T> results;

    
    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}

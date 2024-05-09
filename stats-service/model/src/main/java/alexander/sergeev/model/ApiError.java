package alexander.sergeev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private String message;

    private String reason;

    private String status;

    private String timestamp;

    private StackTraceElement[] errors;

}

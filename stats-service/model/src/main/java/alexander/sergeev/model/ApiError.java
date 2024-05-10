package alexander.sergeev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private String message;

    private String reason;

    private String status;

    private String timestamp;

    private StackTraceElement[] errors;

}

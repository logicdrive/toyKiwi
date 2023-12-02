package toykiwi.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class NotifyUploadedVideoCommand {

    private String uploadedUrl;
}

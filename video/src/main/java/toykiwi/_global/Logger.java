package toykiwi._global;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class Logger {
    public void debug() {
        String currentDate = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date());
        System.out.println(currentDate);
    }
}
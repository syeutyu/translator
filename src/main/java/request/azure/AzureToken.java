package request.azure;

import java.time.LocalDateTime;

/**
 * Created by jojoldu@gmail.com on 2017. 5. 3.
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */

public class AzureToken {

    private LocalDateTime createTime;
    private String token;

    private static final long INTERVAL_TIME = 10;

    public boolean isExpired(LocalDateTime currentTime){
        return currentTime.isAfter(createTime.plusMinutes(INTERVAL_TIME));
    }

    public AzureToken(LocalDateTime createTime, String token) {
        this.createTime = createTime;
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

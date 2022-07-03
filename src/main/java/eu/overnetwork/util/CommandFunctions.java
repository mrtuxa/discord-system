package eu.overnetwork.util;

import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.VideoSnippet;
import org.testng.internal.collections.Pair;

import java.io.File;

public class CommandFunctions {
    public static Pair<String, Integer> gayRate() {
        String gayness;
        int rate = randomRate();
        if (rate<20)
            gayness="https://raw.githubusercontent.com/over-network/discord-system/main/src/main/resources/worlds-leading-anxiety-expert-found-curing-people-2-8748-1448032226-1_dblbig.jpg";
        else if (20<rate && rate<50)
            gayness="https://raw.githubusercontent.com/over-network/discord-system/main/src/main/resources/maxresdefault.jpg";
        else if (50<rate && rate<80)
            gayness="https://raw.githubusercontent.com/over-network/discord-system/main/src/main/resources/drew-pisarra-book.jpg";
        else
            gayness="https://raw.githubusercontent.com/over-network/discord-system/main/src/main/resources/artworks-000655332292-x1ui3u-t500x500.jpg";
        return new Pair<>(gayness, rate);
    }

    public static int randomRate() {
        return (int)(Math.random()*100+1);
    }
}

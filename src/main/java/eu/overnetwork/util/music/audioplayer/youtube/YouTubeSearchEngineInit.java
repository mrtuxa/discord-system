package eu.overnetwork.util.music.audioplayer.youtube;

import com.google.api.client.googleapis.apache.v2.GoogleApacheHttpTransport;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class YouTubeSearchEngineInit {
    final private static GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException A general type safety enforcing exception
     * @throws IOException an input output exception
     */
    public static YouTube getService() throws GeneralSecurityException, IOException {
        final ApacheHttpTransport httpTransport = GoogleApacheHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName("OverNetworkYoutubeSearchEngine")
                .build();
    }
}

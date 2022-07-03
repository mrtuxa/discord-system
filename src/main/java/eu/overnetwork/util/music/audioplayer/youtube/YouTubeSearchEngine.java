package eu.overnetwork.util.music.audioplayer.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YouTubeSearchEngine {
    // youtube service api instance
    public YouTube youtube;
    public Dotenv dotenv = Dotenv.load();

    // constructor that initializes the search engine api service
    public YouTubeSearchEngine() {
        try {
            this.youtube = YouTubeSearchEngineInit.getService();
        } catch (GeneralSecurityException | IOException exception) {
            System.out.println("Error trying to initialize Youtube Search Engine object!");
            this.youtube = null;
        }
    }

    /**
     * Method that returns the best YouTube search result based on a search string
     *
     * @param searchString a user defined search string
     * @return the best YouTube search result
     */
    public SearchResult getBestSearchResult(String searchString) {
        YouTube.Search.List searchRequest;
        SearchListResponse searchResponse = null;

        try {
            searchRequest = this.youtube.search().list(List.of("snippet"));
            searchResponse = searchRequest.setMaxResults(25L)
                    .setQ(searchString)
                    .setKey(dotenv.get("YoutubeApiKey"))
                    .execute();
        } catch (IOException exception) {
            System.out.println("Error trying to search Youtube!");
        }

        assert searchResponse != null;
        return searchResponse.getItems().get(0);
    }

     /**
     * Method that gets a YouTube video snippet based on its video ID
     * @param videoId the YouTube video's ID
     * @return a video snippet of the YouTube video
     */

     public final VideoSnippet getVideoSnippedById(String videoId) {
         YouTube.Videos.List request;
         VideoListResponse response = null;
         try {
             request = this.youtube.videos().list(List.of("snippet"));
             response = request.setMaxResults(1L)
                     .setId(List.of(videoId))
                     .setKey(dotenv.get("YoutubeApiKey"))
                     .execute();
         } catch (IOException exception) {
             System.out.println("Error trying to retrieve Youtube API VideoListResponse!");
         }
         assert response != null;
         Video result = response.getItems().get(0);
         return result.getSnippet();
     }
}

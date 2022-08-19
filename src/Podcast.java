public class Podcast extends Track{
    String podcastId;
    String episodeTitle;
    String podcastName;
    String podcastGuest;
    String podcastDuration;
    String publishDate;

    public String getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(String podcastId) {
        this.podcastId = podcastId;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public String getPodcastGuest() {
        return podcastGuest;
    }

    public void setPodcastGuest(String podcastGuest) {
        this.podcastGuest = podcastGuest;
    }

    public String getPodcastDuration() {
        return podcastDuration;
    }

    public void setPodcastDuration(String podcastDuration) {
        this.podcastDuration = podcastDuration;
    }

    public Podcast(String podcastId, String episodeTitle, String podcastName, String podcastGuest, String podcastDuration, String publishDate) {
        super(podcastId, episodeTitle, podcastName, podcastDuration);
        this.podcastId = podcastId;
        this.episodeTitle = episodeTitle;
        this.podcastName = podcastName;
        this.podcastGuest = podcastGuest;
        this.podcastDuration = podcastDuration;
        this.publishDate = publishDate;
    }
}

import org.testng.annotations.Test;
import java.text.ParseException;


import static org.junit.Assert.*;

public class JukeboxTest {

    @Test
    public void createAUser() {
        Jukebox jukebox = new Jukebox();
        assertEquals("User Already Exists.", jukebox.createAUser(new User("test", 23, "testpassword")));
    }

    @Test
    public void validateAUser() {
        Jukebox jukebox = new Jukebox();
        assertTrue(jukebox.validateAUser("sherlock11", "sher"));
    }

    @Test
    public void allSongs() {
        Jukebox jukebox = new Jukebox();
        assertEquals(20, jukebox.allSongs().size());
    }

    @Test
    public void categorizeSongsByArtist() {
        Jukebox jukebox = new Jukebox();
        assertEquals(3, jukebox.categorizeSongsByArtist("2Pac").size());
    }

    @Test
    public void categorizeSongsByGenre() {
        Jukebox jukebox = new Jukebox();
        assertEquals(3, jukebox.categorizeSongsByGenre("Rock").size());
    }

    @Test
    public void categorizeSongsByAlbum() {
        Jukebox jukebox = new Jukebox();
        assertEquals(2, jukebox.categorizeSongsByAlbum("Still Here").size());
    }

    @Test
    public void searchByName() {
        Jukebox jukebox = new Jukebox();
        assertEquals(2, jukebox.searchByName("The").size());
    }

    @Test
    public void allPodcast() {
        Jukebox jukebox = new Jukebox();
        assertEquals(5, jukebox.allPodcast().size());
    }

    @Test
    public void searchByGuest() {
        Jukebox jukebox = new Jukebox();
        assertEquals(1, jukebox.searchByGuest("Will Smith").size());
    }

    @Test
    public void searchPodcastByPublishDate(){
        Jukebox jukebox = new Jukebox();
        assertEquals(1, jukebox.searchPodcastByPublishDate("2019-10-01").size());
    }

    @Test
    public void createPlaylist() {
        Jukebox jukebox = new Jukebox();
        assertTrue(jukebox.createPlaylist("Test Playlist", "test"));
    }

    @Test
    public void insertSongToPlaylist() {
        Jukebox jukebox = new Jukebox();
        assertTrue(jukebox.insertSongToPlaylist("S00001", "Test Playlist"));
    }

    @Test
    public void insertSongAlbumToPlaylist() {
        Jukebox jukebox = new Jukebox();
        assertTrue(jukebox.insertSongAlbumToPlaylist("Me Against The World", "Test Playlist"));
    }

    @Test
    public void insertPodcastToPlaylist() {
        Jukebox jukebox = new Jukebox();

        assertTrue(jukebox.insertPodcastToPlaylist("P00005", "Test Playlist"));
    }

    @Test
    public void displayPlaylistNamesForUser() {
        Jukebox jukebox = new Jukebox();
        assertEquals(3, jukebox.displayPlaylistNamesForUser("john"));
    }

    @Test
    public void tracksInPlaylist() {
        Jukebox jukebox = new Jukebox();

        assertEquals(4, jukebox.tracksInPlaylist("Monday Blues").size());
    }
}
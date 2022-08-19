import java.util.ArrayList;
import java.util.Scanner;

public class JukeboxMain {
    public static void main(String[] args) {
        Jukebox jukebox = new Jukebox();

        ArrayList<Song> allSongsList = jukebox.allSongs();
        ArrayList<Podcast> allPodcastList = jukebox.allPodcast();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Jukebox!");

        System.out.println("\n");

        System.out.println("Enter 1 to Create an Account.");
        System.out.println("Enter 2 to Log In.");
        System.out.println("Enter your choice:");
        int choice = scanner.nextInt();

        switch (choice)
        {
            case 1:
                System.out.println("Enter your age:");
                int userAge = scanner.nextInt();

                System.out.println("Enter username:");
                String userName = scanner.next();

                System.out.println("Enter Password:");
                String password = scanner.next();

                User user = new User(userName,userAge, password);
                String createUser = jukebox.createAUser(user);
                System.out.println(createUser);

            case 2:
                System.out.println("Enter your username:");
                String validUserName = scanner.next();

                System.out.println("Enter your Password:");
                String validPassword = scanner.next();

                System.out.println("\n");

                if(jukebox.validateAUser(validUserName, validPassword))
                {
                    System.out.println("\n");
                    System.out.println("---------------------------------------------------------------------------Songs--------------------------------------------------------------------");
                    jukebox.displaySongs(allSongsList);

                    System.out.println("\n");

                    System.out.println("-------------------------------------------------------------Podcasts-----------------------------------------------------------------------");
                    jukebox.displayPodcast(allPodcastList);

                    System.out.println("\n");

                    System.out.println("Enter 1 to Categorize Songs by Artist");
                    System.out.println("Enter 2 to Categorize Songs by Genre");
                    System.out.println("Enter 3 to Categorize Songs by Album");
                    System.out.println("Enter 4 to search song by its Name");

                    System.out.println("\n");

                    System.out.println("Enter 5 to search Podcast by its Celebrity or Guest");
                    System.out.println("Enter 6 to find Podcasts Published on specific Date");

                    System.out.println("\n");

                    System.out.println("Enter 7 to create a new Playlist");
                    System.out.println("Enter 8 to add Songs to Existing Playlist");
                    System.out.println("Enter 9 to add Album to Existing Playlist");
                    System.out.println("Enter 10 to add Podcast to the Existing Playlist");
                    System.out.println("Enter 11 to see your Playlists and it's content");
                    System.out.println("Enter 12 to play songs from the Playlist");
                    System.out.println("Enter 13 to play particular song or podcast");

                    System.out.println("Enter your Choice:");

                    int categoryChoice = scanner.nextInt();

                    switch (categoryChoice)
                    {
                        case 1:
                            System.out.println("Enter the name of Artist:");
                            scanner.nextLine();
                            String artist = scanner.nextLine();

                            ArrayList<Song> songsByArtist = jukebox.categorizeSongsByArtist(artist);
                            jukebox.displaySongs(songsByArtist);
                            break;


                        case 2:
                            System.out.println("Enter the genre:");
                            scanner.nextLine();
                            String genre = scanner.nextLine();

                            ArrayList<Song> songsByGenre = jukebox.categorizeSongsByGenre(genre);
                            jukebox.displaySongs(songsByGenre);
                            break;


                        case 3:
                            System.out.println("Enter the Name of Album:");
                            scanner.nextLine();
                            String albumName = scanner.nextLine();

                            ArrayList<Song> songsByAlbum = jukebox.categorizeSongsByAlbum(albumName);
                            jukebox.displaySongs(songsByAlbum);
                            break;


                        case 4:
                            System.out.println("Enter song name:");
                            scanner.nextLine();
                            String songName = scanner.nextLine();

                            ArrayList<Song> songsBySongName = jukebox.searchByName(songName);
                            jukebox.displaySongs(songsBySongName);
                            break;


                        case 5:
                            System.out.println("Enter name of Celebrity:");
                            scanner.nextLine();
                            String celebrityName = scanner.nextLine();

                            ArrayList<Podcast> podcastByCelebrity = jukebox.searchByGuest(celebrityName);
                            jukebox.displayPodcast(podcastByCelebrity);
                            break;


                        case 6:
                            System.out.println("Enter a Date in (yyyy-mm-dd):");
                            scanner.nextLine();
                            String publishDate = scanner.nextLine();

                            ArrayList<Podcast> podcastByPublishDate = jukebox.searchPodcastByPublishDate(publishDate);
                            jukebox.displayPodcast(podcastByPublishDate);
                            break;


                        case 7:
                            System.out.println("Enter name of a playlist: ");
                            scanner.nextLine();
                            String playlistName = scanner.nextLine();
                            boolean playlistCreated = jukebox.createPlaylist(playlistName, validUserName);

                            String playlistStatus = playlistCreated ? "Playlist " + playlistName + "Created Successfully" : "Unable to create a Playlist";
                            System.out.println(playlistStatus);
                            break;

                        case 8:
                            System.out.println("Enter the name of a Playlist:");
                            scanner.nextLine();
                            String existingPlaylistName = scanner.nextLine();

                            String playlistResponse = "";
                            while (!playlistResponse.equals("Q")) {

                                System.out.println("Enter A: To Add Songs       Q: To Stop Adding Songs");
                                playlistResponse = scanner.next();
                                playlistResponse =playlistResponse.toUpperCase();

                                switch (playlistResponse){
                                    case "A":
                                        System.out.println("Enter song Id of a song to add into playlist");
                                        String songId = scanner.next();

                                        boolean songInserted = jukebox.insertSongToPlaylist(songId, existingPlaylistName);
                                        String songInsertStatus = songInserted ? "Song added to the playlist" : "Unable to add song";
                                        System.out.println(songInsertStatus);
                                        break;

                                    case "Q":
                                        break;

                                    default:
                                        System.out.println("Invalid Input");
                                        break;
                                }
                            }
                            break;

                        case 9:
                            System.out.println("Enter album Name to add to playlist");
                            scanner.nextLine();
                            String albumNameToAdd = scanner.nextLine();

                            System.out.println("Enter name of a Playlist");
                            String playlistExisting = scanner.nextLine();

                            boolean albumInserted = jukebox.insertSongAlbumToPlaylist(albumNameToAdd, playlistExisting);
                            String albumStatus = albumInserted ? "Album added to the playlist." : "Unable to add album.";
                            System.out.println(albumStatus);
                            break;

                        case 10:
                            System.out.println("Enter Podcast ID to add to the Playlist");
                            String podcastId = scanner.next();

                            System.out.println("Enter the name of a Playlist");
                            scanner.nextLine();
                            String playlistNameExisting = scanner.nextLine();

                            boolean podcastAdded = jukebox.insertPodcastToPlaylist(podcastId, playlistNameExisting);
                            String podcastAddStatus = podcastAdded ? "Podcast to the the playlist. " : "Unable to add podcast";
                            System.out.println(podcastAddStatus);
                            break;

                        case 11:
                            System.out.println("\n");
                            System.out.println("Your Playlists");
                            int playlistCount = jukebox.displayPlaylistNamesForUser(validUserName);
                            System.out.println("You have total " + playlistCount + " Playlist");

                            System.out.println("\n");

                            System.out.println("Enter the name of Playlist to see it's content: ");
                            scanner.nextLine();
                            String playlistToDisplay = scanner.nextLine();

                            ArrayList<Track> playlist = jukebox.tracksInPlaylist(playlistToDisplay);
                            jukebox.displayPlaylist(playlist);

                        case 12:
                            System.out.println("\n");
                            System.out.println("Enter the name of Playlist to play:");
                            String playlistToPlay = scanner.nextLine();

                            jukebox.playSongsFromPlaylist(playlistToPlay);
                            break;

                        case 13:
                            System.out.println("\n");
                            System.out.println("Enter Song ID or Podcast ID");
                            String trackId = scanner.next();

                            jukebox.playParticularSongOrPodcast(trackId);
                            break;

                        default:
                            System.out.println("Enter 1, 2 or 3 only.");
                            break;
                    }
                }
                else
                {
                    System.out.println("Invalid username and password");
                }
                break;

            default:
                System.out.println("Enter 1 or 2 only.");
                break;
        }
    }
}

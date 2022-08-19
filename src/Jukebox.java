import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Jukebox {

    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Jukebox(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "password");
            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String createAUser(User user){
        String userCreated = "User Already Exists.";

        try {
            resultSet = statement.executeQuery("select userId from Users order by userId desc limit 1");

            int userCount = 0;
            if(resultSet.next())
                userCount = Integer.parseInt(resultSet.getString(1).substring(1));

            preparedStatement = connection.prepareStatement("insert into Users values (?, ?, ?, ?)");

            preparedStatement.setString(1, "U".concat(String.format("%03d", userCount + 1)));
            preparedStatement.setString(2, user.userName);
            preparedStatement.setInt(3, user.userAge);
            preparedStatement.setString(4, user.password);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                userCreated = "Account Created Successfully.";
            }
            else{
                userCreated = "Unable to Create a Account";
            }

        } catch (SQLException e) {
            System.out.println("Username is taken please choose another username.");
        }

        return userCreated;
    }

    public boolean validateAUser(String userName, String password)
    {
        boolean flag = false;
        try {
            resultSet = statement.executeQuery("select count(*) from Users where username ='" + userName + "' and userpassword = '" + password + "'");

            if(resultSet.next())
            {
                if(resultSet.getInt(1) == 1)
                {
                    System.out.println("Welcome, " + userName + "!");
                    flag = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public ArrayList<Song> allSongs()
    {
        ArrayList<Song> allSongsList = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("select songId, songName, genre, artistName, albumName, songDuration from Song sg join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId order by songId");


            while (resultSet.next()){
                allSongsList.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSongsList;
    }

    public ArrayList<Song> categorizeSongsByArtist(String artistName)
    {
        ArrayList<Song> categorizedByArtist = new ArrayList<Song>();
        ArrayList<Song> songList = new ArrayList<Song>();

        try {
            resultSet = statement.executeQuery("select songId, songName, genre, artistName, albumName, songDuration from Song sg join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId order by songId");

            while (resultSet.next()) {
                songList.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        songList.stream().filter(song -> song.songArtist.contains(artistName)).forEach(song -> categorizedByArtist.add(song));

        return categorizedByArtist;
    }

    public ArrayList<Song> categorizeSongsByGenre(String genre)
    {
        ArrayList<Song> categorizedByGenre = new ArrayList<Song>();
        ArrayList<Song> songList = new ArrayList<Song>();

        try {
            resultSet = statement.executeQuery("select songId, songName, genre, artistName, albumName, songDuration from Song sg join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId order by songId");


            while (resultSet.next()) {
                songList.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        songList.stream().filter(song -> song.songGenre.contains(genre)).forEach(song -> categorizedByGenre.add(song));

        return categorizedByGenre;
    }

    public ArrayList<Song> categorizeSongsByAlbum(String album)
    {
        ArrayList<Song> categorizedByAlbum = new ArrayList<Song>();

        ArrayList<Song> songList = new ArrayList<Song>();

        try {
            resultSet = statement.executeQuery("select songId, songName, genre, artistName, albumName, songDuration from Song sg join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId order by songId");


            while (resultSet.next()) {
                songList.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        songList.stream().filter(song -> song.albumName.contains(album)).forEach(song -> categorizedByAlbum.add(song));

        return categorizedByAlbum;
    }

    public ArrayList<Song> searchByName(String songNameToSearch)
    {
        ArrayList<Song> searchResult = new ArrayList<Song>();

        ArrayList<Song> songList = new ArrayList<Song>();

        try {
            resultSet = statement.executeQuery("select songId, songName, genre, artistName, albumName, songDuration from Song sg join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId order by songId");

            while (resultSet.next()) {
                songList.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        songList.stream().filter(song -> song.songName.contains(songNameToSearch)).forEach(song -> searchResult.add(song));

        return searchResult;
    }

    public void displaySongs(ArrayList<Song> songList)
    {
        System.out.printf("%-20s%-30s%-25s%-25s%-25s%-25s\n", "song Id", "Song Name", "Genre", "Artist", "Duration", "Album");
        songList.forEach(song -> System.out.printf("%-20s%-30s%-25s%-25s%-25s%-25s\n", song.songId, song.songName, song.songGenre, song.songArtist, song.songDuration, song.albumName));
    }

    public ArrayList<Podcast> allPodcast()
    {
        ArrayList<Podcast> allPodcastList = new ArrayList<Podcast>();

        try {
            resultSet = statement.executeQuery("select podcastId, episodeTitle, podcastName, podcastGuest, podcastDuration, publishDate from Podcast pc join PodcastName pn on pc.podcastNameId = pn.podcastNameId");

            while (resultSet.next()){
                allPodcastList.add(new Podcast(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allPodcastList;
    }

    public ArrayList<Podcast> searchByGuest(String guestName)
    {
        ArrayList<Podcast> searchResultForGuest = new ArrayList<Podcast>();

        ArrayList<Podcast> allPodcast = new ArrayList<Podcast>();

        try {
            resultSet = statement.executeQuery("select podcastId, episodeTitle, podcastName, podcastGuest, podcastDuration, publishDate from Podcast pc join PodcastName pn on pc.podcastNameId = pn.podcastNameId");

            while (resultSet.next()){
                allPodcast.add(new Podcast(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        allPodcast.stream().filter(podcast -> podcast.podcastGuest.equalsIgnoreCase(guestName)).forEach(podcast -> searchResultForGuest.add(podcast));

        return searchResultForGuest;
    }

    public ArrayList<Podcast> searchPodcastByPublishDate(String publishDate)
    {
        ArrayList<Podcast> searchResultByDate = new ArrayList<Podcast>();

        ArrayList<Podcast> allPodcast = new ArrayList<Podcast>();

        try {
            resultSet = statement.executeQuery("select podcastId, episodeTitle, podcastName, podcastGuest, podcastDuration, publishDate from Podcast pc join PodcastName pn on pc.podcastNameId = pn.podcastNameId");

            while (resultSet.next()){
                allPodcast.add(new Podcast(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        allPodcast.stream().filter(podcast -> podcast.publishDate.equals(publishDate)).forEach(podcast -> searchResultByDate.add(podcast));

        return searchResultByDate;
    }

    public void displayPodcast(ArrayList<Podcast> podcastList)
    {
        System.out.printf("%-20s%-40s%-25s%-20s%-20s%-20s\n", "Podcast Id", "Episode Title", "Podcast Name", "Podcast Guest", "Duration", "Published Date");
        podcastList.forEach(podcast -> System.out.printf("%-20s%-40s%-25s%-20s%-20s%-20s\n", podcast.podcastId, podcast.episodeTitle, podcast.podcastName, podcast.podcastGuest, podcast.podcastDuration, podcast.publishDate));
    }

    public boolean createPlaylist(String playlistName, String userName)
    {
        boolean creationStatus = false;

        try{
            resultSet = statement.executeQuery("select userId from Users where userName = '" + userName + "'");

            String userId = "";
            if(resultSet.next())
                userId = resultSet.getString(1);

            resultSet = statement.executeQuery("select playlistId from Playlist order by playlistId desc limit 1");

            int playlistCount = 0;
            if(resultSet.next())
                playlistCount = Integer.parseInt(resultSet.getString(1).substring(2));

            preparedStatement = connection.prepareStatement("insert into Playlist values (?, ?, ?)");

            preparedStatement.setString(1, "PL".concat(String.format("%03d", playlistCount + 1)));
            preparedStatement.setString(2, playlistName);
            preparedStatement.setString(3, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0)
                creationStatus = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creationStatus;
    }

    public boolean insertSongToPlaylist(String songId, String playlistName)
    {
        boolean insertStatus = false;

        try {
            resultSet = statement.executeQuery("select playlistId from Playlist where playlistName = '" + playlistName + "'");
            String playlistId = "";

            if(resultSet.next()){
                playlistId = resultSet.getString(1);
            }

            resultSet = statement.executeQuery("select songPlaylistId from SongPlaylist order by songPlaylistId desc limit 1");

            int songPlaylistCount = 0;
            if(resultSet.next())
                songPlaylistCount = Integer.parseInt(resultSet.getString(1).substring(3));

            preparedStatement = connection.prepareStatement("insert into SongPlaylist values (?, ?, ?)");

            preparedStatement.setString(1, "SPL".concat(String.format("%03d", songPlaylistCount + 1)));
            preparedStatement.setString(2, songId);
            preparedStatement.setString(3, playlistId);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                insertStatus = true;
            }

        } catch (SQLException e) {
            System.out.println("Incorrect Song ID");
        }

        return insertStatus;
    }

    public boolean insertSongAlbumToPlaylist(String albumName, String playlistName)
    {
        boolean albumInsertStatus = false;

        ArrayList<Song> songsByAlbumList = categorizeSongsByAlbum(albumName);

        try {
            resultSet = statement.executeQuery("select playlistId from Playlist where playlistName = '" + playlistName + "'");
            String playlistId = "";

            if(resultSet.next()){
                playlistId = resultSet.getString(1);
            }

            int rowsAffected = 0;

            for(Song song: songsByAlbumList){

                resultSet = statement.executeQuery("select songPlaylistId from SongPlaylist order by songPlaylistId desc limit 1");

                int songPlaylistCount = 0;
                if(resultSet.next())
                    songPlaylistCount = Integer.parseInt(resultSet.getString(1).substring(3));

                preparedStatement = connection.prepareStatement("insert into SongPlaylist values (?, ?, ?)");

                preparedStatement.setString(1, "SPL".concat(String.format("%03d", songPlaylistCount + 1)));
                preparedStatement.setString(2, song.songId);
                preparedStatement.setString(3, playlistId);

                rowsAffected += preparedStatement.executeUpdate();
            }

            if(rowsAffected > 0) {

                albumInsertStatus = true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return albumInsertStatus;
    }

    public boolean insertPodcastToPlaylist(String podcastId, String playlistName)
    {
        boolean podcastInsertStatus = false;

        try
        {
            resultSet = statement.executeQuery("select playlistId from Playlist where playlistName = '" + playlistName + "'");
            String playlistId = "";

            if(resultSet.next()){
                playlistId = resultSet.getString(1);
            }

            resultSet = statement.executeQuery("select podcastPlaylistId from PodcastPlaylist order by podcastPlaylistId desc limit 1");

            int podcastPlaylistCount = 0;
            if(resultSet.next())
                podcastPlaylistCount = Integer.parseInt(resultSet.getString(1).substring(3));

            preparedStatement = connection.prepareStatement("insert into podcastPlaylist values (?, ?, ?)");

            preparedStatement.setString(1, "PPL".concat(String.format("%03d", podcastPlaylistCount + 1)));
            preparedStatement.setString(2, podcastId);
            preparedStatement.setString(3, playlistId);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0){
                podcastInsertStatus = true;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return podcastInsertStatus;
    }

    public int displayPlaylistNamesForUser(String userName)
    {
        int playlistCount = 0;

        try{
            resultSet = statement.executeQuery("select userId from Users where userName = '" + userName + "'");

            String userId = "";
            if(resultSet.next())
                userId = resultSet.getString(1);

            resultSet = statement.executeQuery("select playlistName from Playlist where userId = '" + userId + "'");

            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
                playlistCount++;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlistCount;

    }

    public ArrayList<Track> tracksInPlaylist(String playlistName)
    {
        ArrayList<Track> tracksInPlaylist = new ArrayList<Track>();


        try {
            resultSet = statement.executeQuery("select spl.songId, songName, genre, artistName, albumName, songDuration from playlist pl join songplaylist spl on pl.playlistId = spl.playlistId join Song sg on sg.songId = spl.songId join Genre g on sg.genreId = g.genreId join Artist ar on sg.artistId = ar.artistId join Album al on sg.albumId = al.albumId where playlistName = '" + playlistName +"'");

            while (resultSet.next()) {
                tracksInPlaylist.add(new Song(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }


            System.out.println();

            resultSet = statement.executeQuery("select ppl.podcastId, episodeTitle, podcastName, podcastGuest, podcastDuration, publishDate from playlist pl join podcastplaylist ppl on pl.playlistId = ppl.playlistId join Podcast pc on ppl.podcastId = pc.podcastId join PodcastName pn on pc.podcastNameId = pn.podcastNameId where playlistName = '" + playlistName +"'");

            while (resultSet.next()){
                tracksInPlaylist.add(new Podcast(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6) ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tracksInPlaylist;
    }

    public void displayPlaylist(ArrayList<Track> tracksList)
    {
        System.out.printf("%-40s%-30s%-20s\n", "Track Name", "Collection Name", "Track Duration");

        tracksList.forEach(track -> System.out.printf("%-40s%-30s%-20s\n",track.trackName, track.trackCollectionName, track.trackDuration));
    }

    public void playSongsFromPlaylist(String playlistName)
    {
        ArrayList<Track> tracksList = tracksInPlaylist(playlistName);

        AudioInputStream audioInputStream;
        Clip clip;

        try {
            ListIterator<Track> listIterator = tracksList.listIterator();

            String previousStatus = "";
            String nextStatus = "";

            Track startTrack = listIterator.next();

            audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + startTrack.trackId + ".wav").getAbsoluteFile());
            System.out.println("Currently Playing: " + startTrack.trackName);
            System.out.println("Album or Podcast Name: " + startTrack.trackCollectionName);
            System.out.println("Duration: " + startTrack.trackDuration);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            Scanner scanner = new Scanner(System.in);
            clip.start();

            boolean value = true;

            while (value)
            {
                System.out.println("Enter your Choice: 1. Play  2. Pause    3.Loop  4.Jump  5. Restart   6. Previous Song   7. Next Song    8. Shuffle Playlist     9. Quit");

                int optionChoice = scanner.nextInt();

                switch (optionChoice)
                {
                    case 1:
                        clip.start();
                        break;

                    case 2:
                        clip.stop();
                        break;

                    case 3:
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        System.out.println("You have looped to the current song.");
                        break;


                    case 4:
                        clip.stop();

                        System.out.println("Current Song Position: " + clip.getMicrosecondPosition());
                        System.out.println("Song End Position: " + clip.getMicrosecondLength());
                        System.out.println("Enter position on which you have to jump:");

                        long jumpPosition = scanner.nextLong();
                        clip.setMicrosecondPosition(jumpPosition);

                        clip.start();
                        break;

                    case 5:
                        clip.stop();
                        clip.setMicrosecondPosition(0);
                        clip.start();
                        break;

                    case 6:
                        clip.close();

                        if (listIterator.hasPrevious()) {
                            Track previousTrack;

                            if (previousStatus.equals("Previous")) {
                                listIterator.previous();

                                previousTrack = listIterator.previous();

                                audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + previousTrack.trackId + ".wav").getAbsoluteFile());

                                System.out.println("Currently Playing:" + previousTrack.trackName);
                                System.out.println("Album or Podcast Name: " + previousTrack.trackCollectionName);
                                System.out.println("Duration: " + previousTrack.trackDuration);

                                previousStatus = "";
                            }
                            else {
                                previousTrack = listIterator.previous();

                                audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + previousTrack.trackId + ".wav").getAbsoluteFile());

                                System.out.println("Currently Playing:" + previousTrack.trackName);
                                System.out.println("Album or Podcast Name: " + previousTrack.trackCollectionName);
                                System.out.println("Duration: " + previousTrack.trackDuration);
                            }

                        }
                        else{
                            audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + tracksList.get(tracksList.size() - 1).trackId + ".wav").getAbsoluteFile());

                            System.out.println("Currently Playing: " + tracksList.get(tracksList.size() - 1).trackName);
                            System.out.println("Album or Podcast Name: " + tracksList.get(tracksList.size() - 1).trackCollectionName);
                            System.out.println("Duration: " + tracksList.get(tracksList.size() - 1).trackDuration);

                            while (listIterator.hasNext())
                                listIterator.next();

                            previousStatus = "Previous";
                        }

                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();

                        nextStatus = "Next";

                        break;


                    case 7:
                        clip.close();

                        if (listIterator.hasNext()) {
                            Track nextTrack;

                            if(nextStatus.equals("Next")){
                                listIterator.next();

                                nextTrack = listIterator.next();

                                audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + nextTrack.trackId + ".wav").getAbsoluteFile());

                                System.out.println("Currently Playing: " + nextTrack.trackName);
                                System.out.println("Album or Podcast Name: " + nextTrack.trackCollectionName);
                                System.out.println("Duration: " + nextTrack.trackDuration);

                                nextStatus = "";

                            }
                            else {
                                nextTrack = listIterator.next();

                                audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + nextTrack.trackId + ".wav").getAbsoluteFile());

                                System.out.println("Current Playing: " + nextTrack.trackName);
                                System.out.println("Album or Podcast Name: " + nextTrack.trackCollectionName);
                                System.out.println("Duration: " + nextTrack.trackDuration);
                            }

                        }
                        else {
                            audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + tracksList.get(0).trackId + ".wav").getAbsoluteFile());

                            System.out.println("Current Playing: " + tracksList.get(0).trackName);
                            System.out.println("Album or Podcast Name: " + tracksList.get(0).trackCollectionName);
                            System.out.println("Duration: " + tracksList.get(0).trackDuration);

                            while (listIterator.hasPrevious())
                                listIterator.previous();

                            nextStatus = "Next";
                        }

                        clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();

                        previousStatus = "Previous";
                        break;

                    case 8:
                        Collections.shuffle(tracksList);
                        System.out.println("Playlist Shuffled");
                        break;

                    case 9:
                        value = false;
                        System.out.println("Thank you for using Jukebox.");
                        break;

                    default:
                        System.out.println("Enter 1 to 9 only.");
                        break;
                }
            }

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playParticularSongOrPodcast(String trackID)
    {

        AudioInputStream audioInputStream;
        Clip clip;

        try {

            audioInputStream = AudioSystem.getAudioInputStream(new File("Songs/" + trackID + ".wav").getAbsoluteFile());

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            Scanner scanner = new Scanner(System.in);
            clip.start();

            boolean value = true;

            while (value)
            {
                System.out.println("Enter your Choice: 1. Play  2. Pause    3.Loop  4.Jump  5. Restart     6. Quit");

                int optionChoice = scanner.nextInt();

                switch (optionChoice)
                {
                    case 1:
                        clip.start();
                        break;

                    case 2:
                        clip.stop();
                        break;

                    case 3:
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        System.out.println("You have looped to the current song.");
                        break;


                    case 4:
                        clip.stop();

                        System.out.println("Current Song Position: " + clip.getMicrosecondPosition());
                        System.out.println("Song End Position: " + clip.getMicrosecondLength());
                        System.out.println("Enter position on which you have to jump:");

                        long jumpPosition = scanner.nextLong();
                        clip.setMicrosecondPosition(jumpPosition);

                        clip.start();
                        break;

                    case 5:
                        clip.stop();
                        clip.setMicrosecondPosition(0);
                        clip.start();
                        break;

                    case 6:
                        value = false;
                        System.out.println("Thank you for using Jukebox.");
                        break;

                    default:
                        System.out.println("Enter 1 to 9 only.");
                        break;
                }
            }

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
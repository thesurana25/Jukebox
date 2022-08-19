public class Song extends Track{
    String songId;
    String songName;
    String songGenre;
    String songArtist;
    String songDuration;
    String albumName;


    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Song(String songId, String songName, String songGenre, String songArtist,String albumName, String songDuration) {
        super(songId, songName, albumName, songDuration);
        this.songId = songId;
        this.songName = songName;
        this.songGenre = songGenre;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
        this.albumName = albumName;
    }
}

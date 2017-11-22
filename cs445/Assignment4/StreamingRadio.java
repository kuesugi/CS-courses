package cs445.a4;

/**
 * This abstract data type represents the backend for a streaming radio service.
 * It stores the songs, stations, and users in the system, as well as the
 * ratings that users assign to songs.
 */
public interface StreamingRadio {

    /**
     * The abstract methods below are declared as void methods with no
     * parameters. You need to expand each declaration to specify a return type
     * and parameters, as necessary. You also need to include a detailed comment
     * for each abstract method describing its effect, its return value, any
     * corner cases that the client may need to consider, any exceptions the
     * method may throw (including a description of the circumstances under
     * which this will happen), and so on. You should include enough details
     * that a client could use this data structure without ever being surprised
     * or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new song to the system. This method successfully adds a song to 
     * the system only when the song does not exist in the system and is not 
     * null. Since the system cannot contain two identical songs, if the song 
     * already exists in th system, this method will throw an 
     * IllegalArgumentException; if the song is null, this method will throw a
     * NullPointerException. If the song is successfully added to the system,
     * return true, if not, return false.
     *
     * @param theSong song that is to be added to the system
     * @return true if the song is added to the system; false otherwise
     * @throws IllegalArgumentException if the song already exists 
     * in the system
     * @throws NullPointerException if the song is null
     */
    public boolean addSong(Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Removes an existing song from the system. This method can remove the 
     * song only when the system contains the song and the song is not null.
     * If the song does not exist in the system, this method will throw an
     * IllegalArgumentException since no such a song can be removed; if the 
     * song is null, this method will throw a NullPointerException. If the 
     * song is successfully removed from the system, return true, if not, 
     * return false.
     *
     * @param theSong song that is to be removed from the system
     * @return true if the song is removed from the system; false otherwise
     * @throws IllegalArgumentException if the song does not exist
     * in the system
     * @throws NullPointerException if the song if null
     */
    public boolean removeSong(Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Adds an existing song to the playlist for an existing radio station. 
     * This method can add a song to the playlist for a radio station only if 
     * both the song and the station are valid (not null and exist in the system)
     * and the song does not exist in the playlist for the radio station. 
     * If the song already exists in the playlist for the station, this method
     * will throw an IllegalArgumentException; if either the song or the station
     * does not exist in the system, this method will throw an 
     * IllegalArgumentException; if either the song or the station is null, 
     * this method will throw a NullPointerException. If the song is added to
     * the playlist for the station, return true, if not, return false.
     *
     * @param theStation station that the song is to be added to
     * @param theSong song that is to be added to the playlist for the station
     * @return true if the song is added to the playlist for the station; false
     * otherwise
     * @throws IllegalArgumentException if the song already exists
     * in the playlist for the station and if either the song or the station
     * does not exist in the system
     * @throws NullPointerException if either the song or the station is null
     */
    public boolean addToStation(Station theStation, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Removes a song from the playlist for a radio station. This method can
     * remove a song from the playlist for a radio station only when both 
     * the song and the station are valid (not null and exist in the system) 
     * and the station contains the song. If the song does not exist in the
     * station, this method will throw an IllegalArgumentException; if either
     * the song or the station does not exist in the system, this method will
     * throw an IllegalArgumentException; if either the song or the station 
     * is null, this method will throw a NullPointerException. If the song
     * is removed from the playlist for the station, return true, if not,
     * return false.
     *
     * @param theStation station that the song is to be removed from
     * its playlist
     * @param theSong song that is to be removed from the the playlist for
     * the station
     * @return true if the song removed from the playlist for the station; 
     * false otherwise
     * @throws IllegalArgumentException if the song does not exist in the
     * playlist for the station and if either the song or the station
     * does not exist in the system
     * @throws NullPointerException if either the song or the station is null
     */
    public boolean removeFromStation(Station theStation, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Sets a user's rating for a song, as a number of stars from 1 to 5. 
     * If the user's rating is a double type value from 1 to 5 and both the song 
     * and the user are valid (not null and exist in the system), this method can 
     * set the rating. If the user has already rated for the song, the new rating 
     * will replace the old one even they are equal. If either the song or the 
     * user is null, this method will throw an NullPointerException; if the 
     * rating value is not a double type value from 1 to 5 (eg. 6.0), this method 
     * will throw an IllegalArgumentException; if either the song or the user does 
     * not exist in the system, this method will throw an IllegalArgumentException.
     *
     * @param theUser user who is rating the song
     * @param theSong song that is to be rated by the user
     * @param theRate rate that the user is giving for the song (a double type value
     * from 1 to 5, eg. 4.6, 3.0)
     * @throws IllegalArgumentException if theRate is not a double type value from 
     * 1 to 5 and if either the song or the user does not exist in the system
     * @throws NullPointerException either the song or the user is null
     */
    public void rateSong(User theUser, Song theSong, double theRate)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Clears a user's rating on a song. If this user has rated this song and
     * the rating has not already been cleared, then the rating is cleared and
     * the state will appear as if the rating was never made. If there is no
     * such rating on record (either because this user has not rated this song,
     * or because the rating has already been cleared), then this method will
     * throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theSong song from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a
     * rating on record for the song
     * @throws NullPointerException if either the user or the song is null
     */
    public void clearRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Predicts the rating a user will assign to a song that they have not yet
     * rated, as a number of stars from 1 to 5. This method can return a double
     * type value from 1 to 5 (as rating value) only when both the song and the 
     * user are valid (not null and exist in the system) and the song is not yet 
     * rated by the user. This method will throw an IllegalArgumentException when
     * the song is already rated by the user; if either the song or the user 
     * is null, this method will throw a NullPointerException; if either the 
     * song or the user does not exist in the system, this method will throw an 
     * IllegalArgumentException; if there has no sufficient data of the user
     * to predict the rating, this method will throw an IllegalArgumentException.
     * 
     * @param theUser user whose rating to a song is to be predicted
     * @param theSong song whose rating is to be predicted
     * @return the predicted rating for the song with a double type value from
     * 1 to 5
     * @throws IllegalArgumentException if the song is already rated by the user,
     * if either the song or the user does not exist in the system, and if there
     * has no sufficient data of the user to predict the rating (eg. a new user)
     * @throws NullPointerException either the song or the user is null
     */
    public double predictRating(User theUser, Song theSong)
    throws IllegalArgumentException, NullPointerException;

    /**
     * Suggests a song for a user that they are predicted to like. This method can
     * return a song to the user only when the user is in the system, the user
     * is not null, and there has a song which the user is predicted to like. 
     * Only return the first qualified song, no need to find all potential suggetions. 
     * If there has no song which the user is predicted to like, then return null.
     * If the user is null, this method will throw a NullPointerException; if the 
     * user is not in the system, this method will throw an IllegalArgumentException.
     * 
     * @param theUser user whom is suggested a song that the user is predicted to like
     * @return the suggested song that the user is predicted to like if there has at 
     * least one; if not, return null
     * @throws IllegalArgumentException if the user does not exist in the system
     * @throws NullPointerException if the user is null
     */
    public Song suggestSong(User theUser)
    throws IllegalArgumentException, NullPointerException;

}


import java.util.ArrayList;
import java.sql.*;
import java.util.Collections;

public class Assignment2 {

	/* A connection to the database */
	private Connection connection;

	/**
	 * Empty constructor. There is no need to modify this. 
	 */
	public Assignment2() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Failed to find the JDBC driver");
		}
	}

	/**
	* Establishes a connection to be used for this session, assigning it to
	* the instance variable 'connection'.
	*
	* @param  url       the url to the database
	* @param  username  the username to connect to the database
	* @param  password  the password to connect to the database
	* @return           true if the connection is successful, false otherwise
	*/
	public boolean connectDB(String url, String username, String password) {
		try {
			this.connection = DriverManager.getConnection(url, username, password);
			return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception. <Message>: " + se.getMessage());
			return false;
		}
	}

	/**
	* Closes the database connection.
	*
	* @return true if the closing was successful, false otherwise
	*/
	public boolean disconnectDB() {
		try {
			this.connection.close();
		return true;
		} catch (SQLException se) {
			System.err.println("SQL Exception. <Message>: " + se.getMessage());
			return false;
		}
	}

	/**
	 * Returns a sorted list of the names of all musicians and bands 
	 * who released at least one album in a given genre. 
	 *
	 * Returns an empty list if no such genre exists or no artist matches.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param genre  the genre to find artists for
	 * @return       a sorted list of artist names
	 */
	public ArrayList<String> findArtistsInGenre(String genre) {

		ArrayList<String> Result = new ArrayList<String>();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			stmt.execute("SET search_path TO artistdb");
		
		String query  = "SELECT DISTINCT A.name"
				+ "FROM Artist A, Album L, Genre G"
				+ "WHERE A.artist_id=L.artist_id AND L.genre_id=G.genre_id AND G.genre='"+genre+"'"
				+ "ORDER BY A.name;";
		
			rs = stmt.executeQuery(query);
		
			while (rs.next()){
				Result.add(rs.getString("A.name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result;
		
	}

	/**
	 * Returns a sorted list of the names of all collaborators
	 * (either as a main artist or guest) for a given artist.  
	 *
	 * Returns an empty list if no such artist exists or the artist 
	 * has no collaborators.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist  the name of the artist to find collaborators for
	 * @return        a sorted list of artist names
	 */
	public ArrayList<String> findCollaborators(String artist) {
		
		ArrayList<String> Result = new ArrayList<String>();
		Statement stmt=null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			stmt.execute("SET search_path TO artistdb");
			
			String query  = "CREATE VIEW Given AS"
					+ "SELECT artist_id "
					+ "FROM Artist "
					+ "WHERE name = '" + artist + "';"
					+ "(SELECT name FROM Artist A, Collaboration C, Given G WHERE A.artist_id=C.artist2 and artist1=G.artist_id)"
					+ "UNION"
					+ "(SELECT name FROM Artist A, Collaboration C, Given G WHERE A.artist_id=C.artist1 and artist2=G.artist_id)"
					+ "ORDER BY name;"
					+ "DROP VIEW Given;";
			
	        rs = stmt.executeQuery(query);
			
			while (rs.next()){
				Result.add(rs.getString("name"));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result;
		
	}


	/**
	 * Returns a sorted list of the names of all songwriters
	 * who wrote songs for a given artist (the given artist is excluded).  
	 *
	 * Returns an empty list if no such artist exists or the artist 
	 * has no other songwriters other than themself.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist  the name of the artist to find the songwriters for
	 * @return        a sorted list of songwriter names
	 */
	public ArrayList<String> findSongwriters(String artist) {
		ArrayList<String> Result = new ArrayList<String>();
		Statement stmt=null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			stmt.execute("SET search_path TO artistdb");
			
			String query = "SELECT A1.name"
					+ "FROM Artist A, Album L, BelongsToAlbum B, Song S, Artist A1"
					+ "WHERE A.name='"+artist+"' AND A.artist_id=L.artist_id AND L.album_id=B.album_id "
					+ "AND B.song_id=S.song_id AND S.songwriter_id<>A.artist_id  AND A1.artist_id=S.songwriter_id"
					+"ORDER BY name;";
	
			rs = stmt.executeQuery(query);
			
			while (rs.next()){
				Result.add(rs.getString("name"));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result;			
	}

	/**
	 * Returns a sorted list of the names of all acquaintances
	 * for a given pair of artists.  
	 *
	 * Returns an empty list if either of the artists does not exist, 
	 * or they have no acquaintances.
	 *
	 * NOTE:
	 *    Use Collections.sort() to sort the names in ascending
	 *    alphabetical order.
	 *
	 * @param artist1  the name of the first artist to find acquaintances for
	 * @param artist2  the name of the second artist to find acquaintances for
	 * @return         a sorted list of artist names
	 */
	public ArrayList<String> findAcquaintances(String artist1, String artist2) {
		ArrayList<String> colla1 = new ArrayList<String>();
		ArrayList<String> colla2 = new ArrayList<String>();
		ArrayList<String> sw1 = new ArrayList<String>();
		ArrayList<String> sw2 = new ArrayList<String>();
		
		colla1.addAll(findCollaborators(artist1));
		colla2.addAll(findCollaborators(artist2));
		sw1.addAll(findSongwriters(artist1));
		sw2.addAll(findSongwriters(artist2));

		colla1.retainAll(colla2);
		sw1.retainAll(sw2);
		colla1.addAll(sw1);
		Collections.sort(colla1);
		
		return colla1;	
		
	}
	
	
	public static void main(String[] args) {
		
		Assignment2 a2 = new Assignment2();
		
		/* TODO: Change the database name and username to your own here. */
		a2.connectDB("jdbc:postgresql://localhost:5432/csc343h-bogdan",
		             "bogdan",
		             "");
		
                System.err.println("\n----- ArtistsInGenre -----");
                ArrayList<String> res = a2.findArtistsInGenre("Rock");
                for (String s : res) {
                  System.err.println(s);
                }

		System.err.println("\n----- Collaborators -----");
		res = a2.findCollaborators("Michael Jackson");
		for (String s : res) {
		  System.err.println(s);
		}
		
		System.err.println("\n----- Songwriters -----");
	        res = a2.findSongwriters("Justin Bieber");
		for (String s : res) {
		  System.err.println(s);
		}
		
		System.err.println("\n----- Acquaintances -----");
		res = a2.findAcquaintances("Jaden Smith", "Miley Cyrus");
		for (String s : res) {
		  System.err.println(s);
		}

		
		a2.disconnectDB();
	}
}


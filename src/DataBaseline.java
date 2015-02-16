import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by: film42 on: 10/10/14.
 */
public class DataBaseline implements IData {

  private Map< Category, Map< Character, Long >> collection;
  private Map< Category, Long > distribution;
  private Map< Category, Long > occurrence;


  public DataBaseline() {
    this.collection = new HashMap<>();
    this.distribution = new HashMap<>();
    this.occurrence = new HashMap<>();
  }

  @Override
  public void parseString( String filePath ) throws FileNotFoundException {
    // Get the Scanner
    Scanner reader = new Scanner(new File(filePath) );
    reader.useDelimiter("\\t+|\\n");

    while(reader.hasNext()) {

      String type = reader.next();
      String word = reader.next();

      Category category = Category.valueOf( type.toUpperCase() );

      // Keep track of occurrences
      if( occurrence.containsKey(category) ) {
        long count = occurrence.get(category);
        occurrence.put(category, count + 1L);
      } else {
        occurrence.put(category, 0L);
      }


      // Process each character
      for( char c : word.toCharArray() ) {

        // Check for null
        if( !this.collection.containsKey(category) ) {
          this.collection.put( category, new HashMap<Character, Long>() );
        }

        // Increment at category
        if( this.collection.get( category ).containsKey(c) ) {
          long counter = this.collection.get( category ).get( c );
          this.collection.get( category ).put( c, counter + 1L);
        } else {
          this.collection.get( category ).put( c, 1L );
        }

        // Increment at distribution level
        if( this.distribution.containsKey( category ) ) {
          long dist_counter = this.distribution.get( category );
          this.distribution.put( category, dist_counter + 1L );
        } else {
          this.distribution.put( category, 1L );
        }
      }
    }
  }

  @Override
  public long dataSize() {
    long total = 0;

    for( long value : occurrence.values() ) {
      total += value;
    }

    return total;
  }

  @Override
  public double probabilityOfCategory(Category category) {
    return occurrence.get(category) / (double)dataSize();
  }

  @Override
  public long charGivenCategory(char c, Category category) {

    if( collection.get(category).containsKey(c) ) {
      return collection.get(category).get(c);
    }

    // Ignore any value that does not exist
    return 1;
  }

  @Override
  public long denominatorForCategory(Category category) {
    return distribution.get( category );
  }

  @Override
  public long categoryCount( Category category ) {

    long total = 0;

    for( long value : collection.get(category).values() ) {
      total += value;
    }

    return total;
  }

  @Override
  public long distributionCount() {

    long total = 0;

    for( Category category : collection.keySet() ) {
      total += categoryCount( category );
    }

    return total;
  }

}

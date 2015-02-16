import java.io.FileNotFoundException;

/**
 * Created by: film42 on: 10/10/14.
 */
public interface IData {

  public void parseString( String filePath ) throws FileNotFoundException;

  long dataSize();

  // number of time category occurred out of all test data
  double probabilityOfCategory(Category category);

  public long charGivenCategory(char c, Category category);

  public long denominatorForCategory(Category category);

  public long categoryCount( Category category );

  public long distributionCount();

}
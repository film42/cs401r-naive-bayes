import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by: film42 on: 10/8/14.
 */
public class BaselineClassifier implements IClassifier {

  ConfusionMatrix matrix;
  IData data;

  public BaselineClassifier(IData data, ConfusionMatrix matrix) {
    this.data = data;
    this.matrix = matrix;
  }

  @Override
  public double classifyWord(Category category, String word) {
    return 0;
  }

  public Category mostPopularCategory() {
    Category mostPopularCategory = null;
    long mostPopularCategoryValue = 0;

    for( Category category : Category.values() ) {

      long count = data.categoryCount(category);

      if( count > mostPopularCategoryValue ) {
        mostPopularCategory = category;
        mostPopularCategoryValue = count;
      }
    }

    return  mostPopularCategory;
  }

  @Override
  public void classifyFile(String filePath) throws FileNotFoundException {

    // Let's assume the category should be the max category
    Category popularCategory = mostPopularCategory();

    Scanner reader = new Scanner(new File(filePath) );
    reader.useDelimiter("\\t+|\\n");

    while( reader.hasNext() ) {
      String token = reader.next();
      String word = reader.next();

      Category category = Category.valueOf(token.toUpperCase());

      // Now add the results to our confusion matrix
      matrix.incrementAt( popularCategory, category );
    }

  }

}

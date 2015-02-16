import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by: film42 on: 10/8/14.
 */
public class BayesClassifier implements IClassifier {

  ConfusionMatrix matrix;
  IData data;

  public BayesClassifier(IData data, ConfusionMatrix matrix) {
    this.data = data;
    this.matrix = matrix;
  }

  @Override
  public double classifyWord(Category category, String word) {

    double denominator = data.denominatorForCategory(category);

    double accumulator = 1D;

    // The marginal: The P(Category) first.
    accumulator *= data.probabilityOfCategory( category );

    // The conditional probabilities
    for( char c : word.toCharArray() ) {
      // Get numerator
      long numerator = data.charGivenCategory(c, category);

      if( numerator == 0 ) continue;

      // Multiply the known char probability
      accumulator *= ( numerator / denominator );
    }

    return accumulator;
  }

  @Override
  public void classifyFile(String filePath) throws FileNotFoundException {

    Scanner reader = new Scanner(new File(filePath) );
    reader.useDelimiter("\\t+|\\n");

    while( reader.hasNext() ) {
      String token = reader.next();
      String word = reader.next();

      Category category = Category.valueOf(token.toUpperCase());

      // Set our base variables for comparison
      Category highestCategory = category;
      double highestProbability = classifyWord(category, word );

      // Run word against each category
      for( Category candidateCategory : Category.values() ) {

        double probability = classifyWord(candidateCategory, word);

        if( probability > highestProbability ) {
          highestCategory = candidateCategory;
          highestProbability = probability;
        }

      }

      // Now add the results to our confusion matrix
      matrix.incrementAt( highestCategory, category );
    }

  }

}

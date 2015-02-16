import java.io.FileNotFoundException;

/**
 * Created by: film42 on: 10/8/14.
 */
public class NaiveBayes {

  public static void main( String[] args ) throws FileNotFoundException {

    //////////////
    // BASELINE //
    //////////////

    System.out.println("BASELINE CLASSIFIER");

    DataBaseline baselineTrainingData = new DataBaseline();
    baselineTrainingData.parseString("data/pnp-train.txt");

    ConfusionMatrix baselineMatrix = new ConfusionMatrix( 5 );

    IClassifier baseline = new BaselineClassifier( baselineTrainingData, baselineMatrix );

    baseline.classifyFile("data/pnp-test.txt");

    System.out.println( baselineMatrix.toString() );

    System.out.println("- - - - - - - - - - - - - - - - - - - - ");



    /////////////////
    // NAIVE BAYES //
    /////////////////

    System.out.println("NAIVE BAYES CLASSIFIER");

    // Parse Training Data and Evaluate Training Data
    DataZeroMarkov trainingData = new DataZeroMarkov();
    trainingData.parseString("data/pnp-train.txt");

    // Evaluate Real Data (using Confusion Matrix)
    ConfusionMatrix testMatrix = new ConfusionMatrix( 5 );
    ConfusionMatrix validateMatrix = new ConfusionMatrix( 5 );


    // TEST
    IClassifier tester = new BayesClassifier( trainingData, testMatrix );
    tester.classifyFile("data/pnp-test.txt");


    // VALIDATE
    IClassifier validator = new BayesClassifier( trainingData, validateMatrix );
    validator.classifyFile("data/pnp-validate.txt");

    // Print Results
    System.out.println(testMatrix.toString());

    System.out.println("- - - - - - - - - - - - - - - - - - - - ");

    System.out.println(validateMatrix.toString());

    ///////////////////////
    // TEST DISTRIBUTION //
    ///////////////////////

    double total = 0;
    for( Category c : Category.values() ) {
      total += trainingData.probabilityOfCategory( c );
    }

    System.out.println( total );
  }

}

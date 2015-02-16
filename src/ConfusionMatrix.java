/**
 * Created by: film42 on: 10/8/14.
 */
public class ConfusionMatrix {

  private int[][] matrix;

  public ConfusionMatrix( int size ) {
    matrix = new int[size][size];

    // Zero out the matrix just for fun
    for( int i = 0; i < matrix.length; ++i ) {
      for( int hyp = 0; hyp < matrix.length; ++hyp ) {
        matrix[i][hyp] = 0;
      }
    }
  }

  public void incrementAt( Category ref, Category hyp ) {

    ++matrix[ref.ordinal()][hyp.ordinal()];
  }

  public double getAccuracy() {

    // Sum of main col
    // -----------
    // Sum of matrix

    long total = 0;
    long diagonal = 0;

    // Zero out the matrix just for fun
    for( int ref = 0; ref < matrix.length; ++ref ) {
      for( int hyp = 0; hyp < matrix.length; ++hyp ) {

        // Add to total
        total += matrix[ref][hyp];

        // Add diagonals
        if( ref == hyp ) {
          diagonal += matrix[ref][hyp];
        }

      }
    }

    // Avoid divide by zeros
    if( total == 0 ) {
      return 0;
    }

    return diagonal / (double)total;
  }

  public String toString() {

    StringBuilder sb = new StringBuilder();

    // Print the matrix
    for( int ref = 0; ref < matrix.length; ++ref ) {

      sb.append( Category.values()[ ref ].toString() );
      sb.append( "\t\t" );

      for( int hyp = 0; hyp < matrix.length; ++hyp ) {

        sb.append( matrix[ref][hyp] );
        sb.append( "\t" );

      }

      // New line
      sb.append('\n');
    }

    sb.append('\n');
    sb.append("Accuracy: ").append( getAccuracy() * 100).append("%");

    return sb.toString();

  }

}

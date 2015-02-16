import java.io.FileNotFoundException;

/**
 * Created by: film42 on: 10/10/14.
 */
public interface IClassifier {
  double classifyWord(Category category, String word);

  void classifyFile(String filePath) throws FileNotFoundException;
}

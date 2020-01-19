package filesearch

import java.io.File
import java.nio.charset.CodingErrorAction
import java.util.StringTokenizer

import filesearch.model._

import scala.io.{Codec, Source}

trait FileTokenizer {
  def tokenize(file: File, del: List[Char]): Terms
}

object FileTokenizer {

  /**
   * Default FileTokenizer that uses java StringTokenizer to split whole file on terms.
   * All actions are streaming because getLines method returns BufferedLineIterator.
   * After zipWithIndex order is preserved, so we can rely on it to give tokens the order in which they appear in file.
   *
   * Todo: Stemming algorithm
   *  Link below is implementation of Porter Stemming algorithm,
   *  while it is quite old it still can improve how index is accessed and stored.
   *  I'm not going to use it here but this is obvious improvement that can be done here.
   *  https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/process/Stemmer.java
   */
  trait Live extends FileTokenizer {
    implicit val codec: Codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    override def tokenize(file: File, delims: List[Char]): Terms = {
      Source
        .fromFile(file)
        .getLines()
        .flatMap(new StringTokenizer(_, delims.mkString).tokens())
        .zipWithIndex
        .map {
          case (token, position) => token -> FileTerm(file, position)
        }
        .foldLeft(Map.empty[String, List[FileTerm]]) {
          case (tokenMap, (token, term)) => tokenMap + (token -> (term :: tokenMap.get(token).toList.flatten))
        }
    }
  }

}

package filesearch

import java.io.File

package object model {
  type Terms = Map[String, List[FileTerm]]

  case class FileTerm(file: File, position: Int)

  case class FoundWord(file: File, word: String, position: Int)

  case class FileScore(file: File, score: Double)

  case class Index(terms: Terms)

  sealed trait ReadFileError

  case object MissingPathArg extends ReadFileError

  case class NotDirectory(error: String) extends ReadFileError

  case class FileNotFound(t: Throwable) extends ReadFileError

}

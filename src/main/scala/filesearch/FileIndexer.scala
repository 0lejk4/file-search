package filesearch

import java.io.File

import filesearch.model.{Index, Terms}

import scala.annotation.tailrec

trait FileIndexer {
  def index(file: File, delims: List[Char]): Index
}

object FileIndexer {

  /**
   * FileIndexer uses FileTokenizer to recursively
   * parse files in input directory into Index
   * with given delimiters
   */
  trait Live extends FileIndexer { self: FileTokenizer =>
    override def index(file: File, delims: List[Char]): Index = {
      @tailrec
      def go(files: List[File], terms: Terms): Terms = files match {
        case Nil => terms
        case head :: tail if head.isDirectory =>
          go(Option(head.listFiles).map(_.toList ::: tail).getOrElse(tail), terms)
        case head :: tail if head.isFile =>
          go(tail, tokenize(head, delims).merge(terms).view.mapValues(_.flatten).toMap)
      }

      Index(go(List(file), Map.empty))
    }
  }

}

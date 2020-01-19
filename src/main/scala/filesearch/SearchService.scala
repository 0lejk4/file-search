package filesearch

import filesearch.model.{FileScore, FoundWord, Index}

trait SearchService {
  def search(index: Index, words: List[String]): List[FileScore]
}

object SearchService {

  /**
   * SearchService uses ScoreService, parsed reverse index and input words
   * to find 10 best fitting Files.
   */
  trait Live extends SearchService { self: ScoreService =>
    override def search(index: Index, words: List[String]): List[FileScore] = {
      words
        .flatMap(word => index.terms.get(word).map(word -> _))
        .flatMap { case (word, terms) => terms.map(f => FoundWord(f.file, word, f.position)) }
        .groupBy(_.file)
        .view
        .map { case (file, found) => FileScore(file, score(words, found)) }
        .toList
        .sortBy(_.score)(Ordering[Double].reverse)
        .take(10)
    }
  }

}

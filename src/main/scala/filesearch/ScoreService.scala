package filesearch

import filesearch.model.{FoundWord, Terms}

trait ScoreService {
  def score(words: List[String], found: List[FoundWord]): Double
}


object ScoreService {

  /**
   * ScoringService just calculates score from the words searched and found in file words
   *
   * Todo: the order of word in file is available,
   *  so scoring algorithm can be adjusted ti use it, too
   */
  trait Live extends ScoreService {
    override def score(words: List[String], found: List[FoundWord]): Double = {
      val foundWordsCount = found.distinctBy(_.word).size.toDouble
      val searchedWordsCount = words.size.toDouble

      //could be just else cause it will actually be same, but for the sake of keeping this logic they are separated
      if (foundWordsCount == searchedWordsCount) 100
      else foundWordsCount / searchedWordsCount * 100.0
    }
  }

}
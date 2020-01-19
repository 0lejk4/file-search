package filesearch

import filesearch.model.{FileScore, Index}

import scala.reflect.io.File

class SearchServiceTest extends FunTest {
  val component: SearchService = new SearchService.Live with ScoreService.Live {}

  test("search index - OK") {
    val file1 = File.makeTemp().jfile
    val file2 = File.makeTemp().jfile

    val terms = Map(
      "scala" -> fileTerms(file1, 7, 4, 3, 0),
      "julia" -> (fileTerms(file2, 0) ++ fileTerms(file1, 10))
    )
    val index = Index(terms)

    val expectedFileScores = List(
      FileScore(file1, 100.0),
      FileScore(file2, 50.0)
    )

    component.search(index, List("scala", "julia")) shouldBe expectedFileScores
  }
}

package filesearch

import filesearch.model.FoundWord
import org.scalactic.{Equality, TolerantNumerics}

import scala.reflect.io.File

class ScoreServiceTest extends FunTest {
  val component: ScoreService = new ScoreService.Live {}
  val file: File = File.makeTemp()
  implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.01)

  test("gives 100% if all word found") {
    val words = List("scala", "java")
    val found = List(FoundWord(file.jfile, "scala", 0), FoundWord(file.jfile, "java", 1))
    component.score(words, found) shouldBe 100.0
  }


  test("calculates score depending on found word count") {
    val words = List("scala", "java", "kotlin")
    val found = List(FoundWord(file.jfile, "scala", 0))
    component.score(words, found) shouldEqual 33.33
  }
}

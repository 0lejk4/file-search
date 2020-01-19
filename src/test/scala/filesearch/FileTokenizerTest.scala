package filesearch

import scala.reflect.io.File

class FileTokenizerTest extends FunTest {
  val component: FileTokenizer = new FileTokenizer.Live {}

  test("parse file - OK") {
    val delimiters = List(',', ' ', '|', '/', '-', '_', '\t', '\n', '\r', '\f')
    val file = File.makeTemp()
    val jfile = file.jfile
    file.writeAll(
      """
        |scala, java - java scala|scala/kotlin
        |groovy scala java javascript
        |""".stripMargin
    )
    val tokenMap = component.tokenize(jfile, delimiters)
    //Bad practise to depend on order here, but I want to save time here :D
    val expected = Map(
      "scala" -> fileTerms(jfile, 7, 4, 3, 0),
      "java" -> fileTerms(jfile, 8, 2, 1),
      "kotlin" -> fileTerms(jfile, 5),
      "groovy" -> fileTerms(jfile, 6),
      "javascript" -> fileTerms(jfile, 9)
    )

    tokenMap should contain theSameElementsAs expected
  }
}

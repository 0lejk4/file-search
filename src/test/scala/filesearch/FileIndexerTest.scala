package filesearch

import java.io.{File => JFile}
import java.nio.file.Files

import filesearch.model.{Index, Terms}

import scala.reflect.io.File
import scala.util.Random

class FileIndexerTest extends FunTest {
  val component: FileIndexer = new FileIndexer.Live with FileTokenizer {
    override def tokenize(file: JFile, del: List[Char]): Terms = {
      Map("test" -> fileTerms(file, 0))
    }
  }

  test("index files recursively") {
    val rootDir = Files.createTempDirectory(Random.nextString(6)).toFile
    val nestedDir = Files.createTempDirectory(rootDir.toPath, Random.nextString(6)).toFile

    val file1 = File.makeTemp(dir = rootDir)
    val file2 = File.makeTemp(dir = nestedDir)

    val expectedIndex = Index(
      Map(
        "test" -> (fileTerms(file2.jfile, 0) ++ fileTerms(file1.jfile, 0))
      )
    )
    component.index(rootDir, List.empty) shouldBe expectedIndex
  }
}

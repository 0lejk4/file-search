package filesearch

import java.io.File

import filesearch.model._

import scala.annotation.tailrec
import scala.util.Try
import scala.io.StdIn.readLine

object App extends scala.App {
  Program.readFile(args).fold(println, file => Program.iterate(Program.index(file)))
}

object Program
  extends ScoreService.Live
    with FileTokenizer.Live
    with FileIndexer.Live
    with SearchService.Live {
  val DefaultDelimiters = List(' ', '\t', '\n', '\r', '\f', ',', '.', ';', ':', '|', '_', '-', '(', ')')

  def index(file: File): Index = index(file, DefaultDelimiters)

  @tailrec
  def iterate(index: Index): Unit = {
    print(s"search> ")
    //Assume that input is separated with spaces
    val words = readLine().split(" ").toList
    val leaderboard = search(index, words)
    //Could be moved to separate trait like "ProgramView"
    if (leaderboard.nonEmpty) leaderboard.foreach(println)
    else println("No match found")
    iterate(index)
  }

  //Read input directory file
  def readFile(args: Array[String]): Either[ReadFileError, File] = {
    for {
      path <- args.headOption.toRight(MissingPathArg)
      file <- Try(new java.io.File(path))
        .fold(
          throwable => Left(FileNotFound(throwable)),
          file =>
            if (file.isDirectory) Right(file)
            else Left(NotDirectory(s"Path [ $path ] is not a directory")))
    } yield file
  }
}

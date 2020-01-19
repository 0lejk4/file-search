package filesearch

import java.io.File

import filesearch.model.FileTerm
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar
import org.scalatest.{GivenWhenThen, Informing, OptionValues}

abstract class FunTest
  extends AnyFunSuite
    with Informing
    with Matchers
    with Eventually
    with ScalaFutures
    with GivenWhenThen
    with SpanSugar
    with OptionValues {
  def fileTerms(file: File, positions: Int*): List[FileTerm] = positions.map(FileTerm(file, _)).toList
}

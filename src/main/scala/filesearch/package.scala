import java.util.StringTokenizer

import scala.annotation.tailrec

package object filesearch {

  //Ops class to merge two map
  implicit class MapMergeOps[K, V](map: Map[K, V]) {
    def merge(other: Map[K, V]): Map[K, List[V]] = {
      (map.keySet ++ other.keySet).map(i => i -> (map.get(i).toList ::: other.get(i).toList)).toMap
    }
  }

  //Ops class to get all tokens from string tokenizer
  implicit class StringTokenizerOps(st: StringTokenizer) {
    def tokens(): List[String] = {
      @tailrec
      def go(found: List[String]): List[String] = {
        if (st.hasMoreTokens) go(found :+ st.nextToken())
        else found
      }

      go(List.empty)
    }
  }

}

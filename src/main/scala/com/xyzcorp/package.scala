package com

package object xyzcorp {
    implicit val tuple2List: ((Int, String)) => List[String] = (t: (Int, String)) => List.fill(t._1)(t._2)
}

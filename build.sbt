
lazy val root = Project(id = "file-search", base = file("."))
  .settings(
    name := "FileSearch",
    version := "0.1",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.1.0" % Test)
  )
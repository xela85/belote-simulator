name := "BeloteSimulator"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.beachape" %% "enumeratum" % "1.5.13",
  "com.typesafe.akka" %% "akka-actor" % "2.5.12",
  "org.specs2" %% "specs2-core" % "4.2.0" % "test",
  "org.specs2" %% "specs2-scalacheck" % "4.2.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
)
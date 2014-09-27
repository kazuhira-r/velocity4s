name := "velocity4s"

version := "0.0.1-SNAPSHOT"

val primaryScalaVersion = "2.11.2"

val secondaryScalaVersion = "2.10.4"

scalaVersion := primaryScalaVersion

organization := "com.github.kazuhira-r"

crossScalaVersions := Seq(primaryScalaVersion, secondaryScalaVersion)

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

incOptions := incOptions.value.withNameHashing(true)

libraryDependencies ++= Seq(
  "org.apache.velocity" % "velocity" % "1.7",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

name := "velocity4s"

version := "0.0.1-SNAPSHOT"

val primaryScalaVersion = "2.11.2"

val secondaryScalaVersion = "2.10.4"

scalaVersion := primaryScalaVersion

startYear := Some(2014)

organization := "com.github.kazuhira-r"

description := "Apache Velocity Wrapper Library for Scala"

crossScalaVersions := Seq(primaryScalaVersion, secondaryScalaVersion)

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

incOptions := incOptions.value.withNameHashing(true)

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "org.apache.velocity" % "velocity" % "1.7",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

import sbt._
import sbt.Keys._

object BuildSettings {
  val appOrganization = "com.github.kazuhira-r"
  val appVersion = "0.0.1-SNAPSHOT"

  val appPrimaryScalaVersion = "2.11.2"
  val appSecondaryScalaVersion = "2.10.4"

  val appScalacOptions = Seq("-Xlint", "-deprecation", "-unchecked", "-feature")
  val appJavacOptions = Seq("-source", "1.7", "-target", "1.7")

  val appDescription = "Apache Velocity Wrapper Library for Scala"

  val appSettings = Seq(
    organization := appOrganization,
    version := appVersion,
    scalaVersion := appPrimaryScalaVersion,
    crossScalaVersions := Seq(appPrimaryScalaVersion, appSecondaryScalaVersion),
    scalacOptions ++= appScalacOptions,
    javacOptions ++= appJavacOptions,
    startYear := Some(2014),
    incOptions := incOptions.value.withNameHashing(true)
  )
}

object Dependencies {
  val compileLibraries = Seq(
    "org.apache.velocity" % "velocity" % "1.7"
  )

  val testLibraries = Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )
}

object Velocity4s extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val root =
    Project("velocity4s-parent",
            file("."),
            settings = appSettings)
              .aggregate(velocity4s, examples)

  lazy val velocity4s =
    Project("velocity4s",
            file("velocity4s"),
            settings = appSettings
                      ++ Seq(libraryDependencies ++= compileLibraries ++ testLibraries))

  lazy val examples =
    Project("examples",
            file("examples"),
            settings = appSettings).dependsOn(velocity4s)
}

import sbt._
import sbt.Keys._

import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import scalariform.formatter.preferences._

object BuildSettings {
  val appOrganization = "com.github.kazuhira-r"
  val appVersion = "0.0.1-SNAPSHOT"

  val appPrimaryScalaVersion = "2.11.3"
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

  val appDependencyGraphSettings = net.virtualvoid.sbt.graph.Plugin.graphSettings

  val appScalariformSettings =
    SbtScalariform.scalariformSettings ++
      Seq(ScalariformKeys.preferences := ScalariformKeys.preferences.value
        .setPreference(AlignParameters, true)
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(DoubleIndentClassDeclaration, true)
        .setPreference(PreserveSpaceBeforeArguments, true)
      )
}

object Dependencies {
  val compileLibraries = Seq(
    "org.apache.velocity" % "velocity" % "1.7"
  )

  val testLibraries = Seq(
    "org.scalatest" %% "scalatest" % "2.2.2" % "test",
    "org.mockito" % "mockito-core" % "1.10.8" % "test"
  )
}

object Velocity4s extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val root =
    Project("root",
            file("."),
            settings = appSettings
    ).aggregate(velocity4s, slf4j, jbossLogging)

  lazy val velocity4s =
    Project("velocity4s",
            file("velocity4s"),
            settings = appSettings
              ++ Seq(libraryDependencies ++= compileLibraries ++ testLibraries)
              ++ appScalariformSettings
              ++ appDependencyGraphSettings
    )

  lazy val slf4j =
    Project("slf4j",
            file("slf4j"),
            settings = appSettings
                      ++ Seq(libraryDependencies ++= compileLibraries ++ Seq(
                        "org.slf4j" % "slf4j-api" % "1.7.7"
                      ) ++ testLibraries)
              ++ appScalariformSettings
              ++ appDependencyGraphSettings
    ).dependsOn(velocity4s)

  lazy val jbossLogging =
    Project("jboss-logging",
            file("jboss-logging"),
            settings = appSettings
                      ++ Seq(libraryDependencies ++= compileLibraries ++ Seq(
                        "org.jboss.logging" % "jboss-logging" % "3.1.4.GA"
                      ) ++ testLibraries)
              ++ appScalariformSettings
              ++ appDependencyGraphSettings
    ).dependsOn(velocity4s)

  lazy val examples =
    Project("examples",
            file("examples"),
            settings = appSettings
              ++ appScalariformSettings
              ++ appDependencyGraphSettings
    ).dependsOn(velocity4s)
}

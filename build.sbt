organization := "com.github.kammoh"

version := "3.1-SNAPSHOT"

name := "chisel-tutorial"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:reflectiveCalls")

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the default development versions, not the "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.1-SNAPSHOT",
  "chisel-iotesters" -> "1.2-SNAPSHOT")

libraryDependencies ++= Seq("chisel3", "chisel-iotesters").map {
  dep: String => "com.github.kammoh" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep))
}

resolvers ++= Seq(
  Resolver.defaultLocal,
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// Recommendations from http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

// Running tests in parallel on Jenkins currently fails.
parallelExecution in Test := false

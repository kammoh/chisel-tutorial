organization := "edu.berkeley.cs"

version := "3.0-SNAPSHOT"

name := "chisel-tutorial"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-language:reflectiveCalls")

resolvers += Resolver.url("kamyar-maven", url("https://nexus.kamyar.xyz/repository/maven-public/"))

resolvers += Resolver.url("kamyar-ivy", url("https://nexus.kamyar.xyz/repository/ivy-public/"))(Resolver.ivyStylePatterns)

// Provide a managed dependency on X if -DXVersion="" is supplied on the command line.
// The following are the default development versions, not the "release" versions.
val defaultVersions = Map(
  "chisel3" -> "3.0-SNAPSHOT",
  "chisel-iotesters" -> "1.1-SNAPSHOT"
  )


libraryDependencies ++= Seq("chisel3","chisel-iotesters").map {
  dep: String => "edu.berkeley.cs" %% dep % sys.props.getOrElse(dep + "Version", defaultVersions(dep)) }

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

// Recommendations from http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

// Disable parallel execution when running te
//  Running tests in parallel on Jenkins currently fails.
parallelExecution in Test := false

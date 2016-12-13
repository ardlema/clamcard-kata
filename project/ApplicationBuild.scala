import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  Resolver.sonatypeRepo("releases")

  import org.scalastyle.sbt.ScalastylePlugin.{Settings => scalastyleSettings}
  import scoverage.ScoverageSbtPlugin.{buildSettings => scoverageSettings}

  val customScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xfuture",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen")

  val customJavaInRuntimeOptions = Seq(
    "-Xmx512m"
  )

  val customJavaInTestOptions = Seq(
    "-Xmx512m"
  )

  val customResolvers = Seq(
    Classpaths.sbtPluginReleases,
    Classpaths.typesafeReleases,
    "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/"
  )

  val customLibraryDependencies = Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
  ).map(_.exclude(
    "org.slf4j", "slf4j-log4j12"
  ))

  lazy val main = Project(
    id = "clamcard-kata",
    base = file("."),
    settings = Seq(
      version := "1.0",
      scalaVersion := "2.11.5",
      javaOptions in Runtime ++= customJavaInRuntimeOptions,
      javaOptions in Test ++= customJavaInTestOptions,
      scalacOptions ++= customScalacOptions,
      resolvers ++= customResolvers,
      libraryDependencies ++= customLibraryDependencies,
      parallelExecution in Test := false,
      fork in Test := true
    ) ++ scalastyleSettings
  )
}
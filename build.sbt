import PlayCrossCompilation._
import play.core.PlayVersion
import sbt.Keys._
import sbt._

val libName = "play-partials"

val compileDependencies =  dependencies(
  shared = Seq(
    filters,
    "com.google.guava"   %  "guava"              % "19.0",
    // force dependencies due to security flaws found in jackson-databind < 2.9.x using XRay
    "com.fasterxml.jackson.core"     % "jackson-core"            % "2.9.7",
    "com.fasterxml.jackson.core"     % "jackson-databind"        % "2.9.7",
    "com.fasterxml.jackson.core"     % "jackson-annotations"     % "2.9.7",
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8"   % "2.9.7",
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.7",
    // force dependencies due to security flaws found in xercesImpl 2.11.0
    "xerces" % "xercesImpl" % "2.12.0"
  ),
  play25 = Seq(
    "com.typesafe.play" %% "play" %  "2.5.19",
    "uk.gov.hmrc" %% "http-verbs" % "9.1.0-play-25"
  ),
  play26 = Seq(
    "com.typesafe.play" %% "play" % "2.6.20",
    "uk.gov.hmrc" %% "http-verbs" % "9.1.0-play-26"
  )
)

val testDependencies = dependencies(
  shared = Seq(
    "org.scalatest"          %% "scalatest"          % "3.0.5"       % Test,
    "org.pegdown"            % "pegdown"             % "1.6.0"       % Test,
    "org.mockito"            %  "mockito-all"        % "1.9.5"       % Test
  ),
  play25 = Seq(
    "com.typesafe.play"      %% "play-test"          %  "2.5.19"     % Test,
    "com.typesafe.play"      %% "play-specs2"        %  "2.5.19"     % "test"
  ),
  play26 = Seq(
    "com.typesafe.play"      %% "play-test"          % "2.6.20"      % Test,
    "com.typesafe.play"      %% "play-specs2"        % "2.6.20"      % "test"
  )
)

lazy val playPartials = Project(libName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(
    majorVersion := 6,
    makePublicallyAvailableOnBintray := true,
    scalaVersion := "2.11.7",
    libraryDependencies ++= compileDependencies ++ testDependencies,
    crossScalaVersions := Seq("2.11.7"),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases"),
      "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    playCrossCompilationSettings
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)

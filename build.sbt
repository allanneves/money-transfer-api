name := """money-transfer-api"""
organization := "com.allan"
version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.7"
val lombokVersion = "1.18.2"
val h2DatabaseVersion = "1.4.192"
val monetaVersion = "1.1"

libraryDependencies ++= Seq(
  guice,
  "org.projectlombok" % "lombok" % lombokVersion % "provided",
  "com.h2database" % "h2" % h2DatabaseVersion,
  "org.javamoney" % "moneta" % monetaVersion
)
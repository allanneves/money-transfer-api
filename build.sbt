name := """amount-transfer-api"""
organization := "com.allan"
version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.7"
val lombokVersion = "1.18.2"
val h2DatabaseVersion = "1.4.192"
val monetaVersion = "1.1"
val assertJVersion = "3.11.1"
val jooqVersion = "3.11.5"
val jooqMetaVersion = "3.11.5"
val jooqCodeGenVersion = "3.11.5"

libraryDependencies ++= Seq(
  guice,
  "org.projectlombok" % "lombok" % lombokVersion % "provided",

  "com.h2database" % "h2" % h2DatabaseVersion,
  "org.javamoney" % "moneta" % monetaVersion,
  "org.jooq" % "jooq" % jooqVersion,
  "org.jooq" % "jooq-meta" % jooqMetaVersion,
  "org.jooq" % "jooq-codegen" % jooqCodeGenVersion,

  "org.assertj" % "assertj-core" % assertJVersion % "test"
)
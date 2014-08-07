import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._
import sbtbuildinfo.Plugin._

object Build extends sbt.Build {
  lazy val buildVersion = "1.0.0"

  lazy val root = Project(id = "affectr-java-2", base = file("."))
    .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
    .settings(sbtassembly.Plugin.assemblySettings: _*)
    .settings(sbtbuildinfo.Plugin.buildInfoSettings: _*)
    .settings(
    version := buildVersion,
    organization := "io.theysay",
    scalaVersion := "2.10.2",
    resolvers += Resolver.typesafeRepo("releases"),
    resolvers += Resolver.typesafeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.6",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-Xlog-reflective-calls"
    ),
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.0.12",
      "com.mashape.unirest" % "unirest-java" % "1.3.8",
      "net.sf.jopt-simple" % "jopt-simple" % "4.6",
      "org.apache.commons" % "commons-lang3" % "3.1"
    )
  )
}
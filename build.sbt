import AssemblyKeys._

assemblySettings

name := "template-scala-parallel-vanilla"

organization := "org.apache.predictionio"

libraryDependencies ++= Seq(
  "org.apache.predictionio" %% "apache-predictionio-core" % "0.10.0-incubating" % "provided",
  "org.apache.spark"        %% "spark-core"               % "1.6.3" % "provided",
  "org.apache.spark"        %% "spark-mllib"              % "1.6.3" % "provided",
  "org.scalatest"           %% "scalatest"                % "2.2.1" % "test",
  "org.scalikejdbc"         %% "scalikejdbc"              % "2.3.5" % "test",
  "com.h2database"          %  "h2"                       % "1.4.193" % "test",
  "ch.qos.logback"          %  "logback-classic"          % "1.1.7" % "test")

// SparkContext is shared between all tests via SharedSparkContext
parallelExecution in Test := false
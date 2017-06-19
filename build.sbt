name := "P2PClient"

version := "1.0"

scalaVersion := "2.12.2"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.102-R11",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.3",
  "com.jfoenix" % "jfoenix" % "1.4.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.0.pr3"
)
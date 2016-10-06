import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import play.sbt.PlayScala

import scalariform.formatter.preferences._


name := """Sangraha-drishti"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SbtScalariform, DockerPlugin)
  .settings(SbtScalariform.defaultScalariformSettings ++ Seq(
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(FormatXml, false)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(DanglingCloseParenthesis, Force)
  ))

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
   "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "angularjs" % "1.5.8",
  "org.webjars" % "requirejs" % "2.3.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

//compile <<= (compile in Compile).dependsOn((scalastyle in Compile).toTask(""))

fork in run := true
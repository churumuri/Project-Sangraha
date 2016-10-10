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
  "org.webjars" % "angularjs" % "1.5.8",
  "org.webjars" % "jquery" % "2.2.0",
  "org.webjars" % "requirejs" % "2.3.2",
  "org.webjars" % "smart-table" % "2.1.3-1",
  "org.webjars" % "d3js" % "3.5.17",
  "org.webjars" % "nvd3" % "1.8.2",
  "org.webjars" % "angular-nvd3" % "1.0.5",
  "org.webjars" % "bootstrap" % "3.3.5" exclude("org.webjars", "jquery"),
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

//compile <<= (compile in Compile).dependsOn((scalastyle in Compile).toTask(""))

fork in run := true
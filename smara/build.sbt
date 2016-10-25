name := """smara"""

version := "0.0.1"

lazy val root = (project in file("."))
	.settings(sharedSettings)
	.settings(slick := slickCodeGenTask.value )
	.settings(sourceGenerators in Compile += slickCodeGenTask.taskValue)
	.dependsOn(codegen)
    .enablePlugins(PlayScala)

//scalaVersion := "2.11.7"

/** codegen project containing the customized code generator */
lazy val codegen = project
    .settings(sharedSettings)
    .settings(libraryDependencies ++= List (
        "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
        "org.slf4j" % "slf4j-nop" % "1.7.10"
        )
    )

// shared sbt config between main project and codegen project
lazy val sharedSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation"),
  libraryDependencies ++= List(
    "com.typesafe.slick" %% "slick" % "3.1.1",
    "mysql" % "mysql-connector-java" % "5.1.+"
  )
)


lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (baseDirectory, sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (bd, dir, cp, r, s) =>
  import java.io.File
  val dbName = "smara"
  val userName = "root"
  val password = "G0od0ldPa55w0rd"
  val url = s"jdbc:mysql://smaraDb:3306/$dbName?autoReconnect=true&useSSL=false" // adapt as necessary to your system
  val jdbcDriver = "com.mysql.jdbc.Driver"         // replace if not MySQL
  val slickDriver = "slick.driver.MySQLDriver"     // replace if not MySQL
  val targetPackageName = "models.persistence" // package name to give it
  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, targetPackageName, userName, password), s.log))
  val fname = outputDir + "/models/persistence/Tables.scala"
  Seq(file(fname))
}


libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1",
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test

)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


fork in run := true

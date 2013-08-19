import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "geebooks"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "com.typesafe.play" %% "play-slick" % "0.4.0", 
    "postgresql" % "postgresql" % "8.4-702.jdbc4"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}

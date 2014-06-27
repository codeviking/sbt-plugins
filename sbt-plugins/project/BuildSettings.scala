import sbt._
import Keys._

import com.typesafe.sbt.SbtScalariform.scalariformSettings
import com.typesafe.sbt.SbtScalariform._

import bintray.{ Keys => BintrayKeys }
import bintray.{ Plugin => BintrayPlugin }

import scalariform.formatter.preferences._
import sbtrelease.ReleasePlugin._

object BuildSettings {

  lazy val basicSettings = releaseSettings ++ scalariformSettings ++ Seq(
    isSnapshot := version.value.trim.endsWith("SNAPSHOT"),
    organization := "org.allenai.plugins",
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      //"-target:jvm-1.7",
      "-language:_",
      "-Xlog-reflective-calls"),
    scalaVersion := "2.10.4",
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
      .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true))

  lazy val sbtPluginSettings = basicSettings ++ Format.settings ++ publishToBintraySettings ++ Seq(
    sbtPlugin := true
  )

  lazy val publishToBintraySettings = BintrayPlugin.bintrayPublishSettings ++ Seq(
    publishMavenStyle := false,
    BintrayKeys.repository in BintrayKeys.bintray := "sbt-plugins",
    BintrayKeys.bintrayOrganization in BintrayKeys.bintray := Some("allenai"),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))
  )

  lazy val noPublishing = Seq(
    publish := (),
    publishLocal := (),
    // required until these tickets are closed https://github.com/sbt/sbt-pgp/issues/42,
    // https://github.com/sbt/sbt-pgp/issues/36
    publishTo := None
  )

}

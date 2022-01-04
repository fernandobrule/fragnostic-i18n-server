import Dependencies._
import com.jsuereth.sbtpgp.PgpKeys

val unusedOptions = Seq("-Ywarn-unused:imports")

lazy val fragnosticI18nServerSettings = Seq(
  organization := "com.fragnostic",
  assembly / assemblyJarName := s"fragnostic-i18n-server-${version.value}.jar",
  assembly / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  },
  Test / fork := true,
  Test / baseDirectory := (ThisBuild / baseDirectory).value,
  crossScalaVersions := Seq("2.12.15", "2.13.7", "3.1.1-RC2"),
  scalaVersion := crossScalaVersions.value.head,
  libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % "always",
  allDependencies := {
    val values = allDependencies.value
    // workaround for
    // "Modules were resolved with conflicting cross-version suffixes"
    // "   org.scala-lang.modules:scala-xml _3.0.0-RC1, _2.13"
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((3, _)) =>
        values.map(
          _.exclude("org.scala-lang.modules", "scala-xml_2.13")
            .exclude("org.scala-lang.modules", "scala-parser-combinators_2.13")
        )
      case _ =>
        values
    }
  },
  scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, _)) =>
        unusedOptions ++ Seq(
          "-target:jvm-1.8",
          "-Xlint",
          "-Xcheckinit",
        )
      case _ =>
        Seq(
          "-source",
          "3.0-migration",
        )
    }
  },
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    /*"-Yinline-warnings",*/
    "-encoding", "utf8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:existentials"
  ),
  manifestSetting,
) ++ Seq(Compile, Test).flatMap(c =>
  c / console / scalacOptions --= unusedOptions
)

lazy val manifestSetting = packageOptions += {
  Package.ManifestAttributes(
    "Created-By" -> "Simple Build Tool",
    "Built-By" -> System.getProperty("user.name"),
    "Build-Jdk" -> System.getProperty("java.version"),
    "Specification-Title" -> name.value,
    "Specification-Version" -> version.value,
    "Specification-Vendor" -> organization.value,
    "Implementation-Title" -> name.value,
    "Implementation-Version" -> version.value,
    "Implementation-Vendor-Id" -> organization.value,
    "Implementation-Vendor" -> organization.value
  )
}

lazy val doNotPublish = Seq(publish := {}, publishLocal := {}, PgpKeys.publishSigned := {}, PgpKeys.publishLocalSigned := {})

lazy val fragnosticI18nServerProject = Project(
  id = "fragnostic-i18n-server-project",
  base = file(".")).settings(
  fragnosticI18nServerSettings ++ Seq(
    libraryDependencies ++= Seq(
      finagleHttp,
      jacksonDatabind,
      json4sCore,
      json4sJackson,
      json4sNative,
      logbackClassic,
      fragnosticConfFacade,
      fragnosticI18nImpl
    ),
    name := "fragnostic i18n server project",
    artifacts := Classpaths.artifactDefs(Seq(Compile / packageDoc, Compile / makePom)).value,
    packagedArtifacts := Classpaths.packaged(Seq(Compile / packageDoc, Compile / makePom)).value,
    description := "A Fragnostic I18n Server",
    shellPrompt := { state =>
      s"sbt:${Project.extract(state).currentProject.id}" + Def.withColor("> ", Option(scala.Console.CYAN))
    }
  )).aggregate(
  //,
).enablePlugins()


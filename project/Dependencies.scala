import sbt._

object Dependencies {

  lazy val fragnosticI18nImpl         = "com.fragnostic"        % "fragnostic-i18n-impl_2.13"           % "0.2.5-SNAPSHOT"
  lazy val fragnosticConfFacade       = "com.fragnostic"        % "fragnostic-conf-facade_2.13"         % "0.2.6-SNAPSHOT"

  lazy val finagleHttp                = "com.twitter"          %% "finagle-http"                        % "20.12.0"
  lazy val jacksonDatabind            = "com.fasterxml.jackson.core" %  "jackson-databind"              % "2.11.4"
  lazy val json4sCore                 = "org.json4s"           %% "json4s-core"                         % "3.7.0-M7"
  lazy val json4sJackson              = "org.json4s"           %% "json4s-jackson"                      % "3.7.0-M7"
  lazy val json4sNative               = "org.json4s"           %% "json4s-native"                       % "3.7.0-M7"
  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                     % "1.3.0-alpha12" % "runtime"

}

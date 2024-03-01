import sbt._

object Dependencies {

  lazy val finagleHttp                = "com.twitter"          %% "finagle-http"                        % "23.11.0"
  lazy val fragnosticConfEnv          = "com.fragnostic"        %  "fragnostic-conf-env_2.13"           % "0.1.12"
  lazy val fragnosticConfFacade       = "com.fragnostic"        % "fragnostic-conf-facade_2.13"         % "0.2.9"
  lazy val fragnosticI18nApi          = "com.fragnostic"        % "fragnostic-i18n-api_2.13"            % "0.1.4"
  lazy val fragnosticI18nImpl         = "com.fragnostic"        % "fragnostic-i18n-impl_2.13"           % "0.2.8"
  lazy val fragnosticSupport          = "com.fragnostic"        % "fragnostic-support_2.13"             % "0.1.19"
  lazy val jacksonDatabind            = "com.fasterxml.jackson.core" %  "jackson-databind"              % "2.14.3"
  lazy val json4sCore                 = "org.json4s"           %% "json4s-core"                         % "4.0.7"
  lazy val json4sJackson              = "org.json4s"           %% "json4s-jackson"                      % "4.0.7"
  lazy val json4sNative               = "org.json4s"           %% "json4s-native"                       % "4.0.7"
  lazy val logbackClassic             = "ch.qos.logback"        % "logback-classic"                     % "1.5.0" % "runtime"
  lazy val slf4jApi                   = "org.slf4j"             % "slf4j-api"                           % "2.0.12"

}

package models

case class Configuration(
    version: String,
    styleName: String,
    scriptName: String) {

}

object Configuration {
  def apply(conf: play.api.Configuration) = {
    new Configuration(
      version = conf.getString("application.version").getOrElse(""),
      styleName = conf.getString("application.files.style").getOrElse(""),
      scriptName = conf.getString("application.files.script").getOrElse("")
    )
  }
}

package com.tsunderebug.p2pclient

import javafx.{scene => jfxs}

import scalafx.application
import scalafx.application.JFXApp
import scalafx.scene.{Parent, Scene}
import scalafxml.core.{DependenciesByType, FXMLLoader, FXMLView}

object Main extends JFXApp {

  stage = new application.JFXApp.PrimaryStage {
    title = "P2P Client"
    scene = new Scene(
      new jfxs.Scene(
        FXMLView(
          getClass.getResource("/fxml/main_menu.fxml"),
          new DependenciesByType(
            Map()
          )
        )
      )
    )
  }

}

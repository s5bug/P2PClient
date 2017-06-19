package com.tsunderebug.p2pclient

import javafx.{scene => jfxs}

import com.jfoenix.controls.{JFXButton, JFXListView, JFXTextField}
import com.tsunderebug.p2pclient.control.Connection

import scala.reflect.runtime.universe._
import scalafx.application
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{DependenciesByType, FXMLView}

object Main extends JFXApp {

  stage = new application.JFXApp.PrimaryStage {
    title = "P2P Client"
    scene = new Scene(
      new jfxs.Scene(
        FXMLView(
          getClass.getResource("/fxml/main_menu.fxml"),
          new DependenciesByType(
            Map(
              typeOf[JFXButton] -> new JFXButton(),
              typeOf[JFXTextField] -> new JFXTextField(),
              typeOf[JFXListView[Connection]] -> new JFXListView[Connection]()
            )
          )
        )
      )
    )
  }

}

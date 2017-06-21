package com.tsunderebug.p2pclient.controller

import java.net.InetAddress
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane

import com.jfoenix.controls.JFXDialog.DialogTransition
import com.jfoenix.controls.{JFXButton, JFXDialog, JFXListView, JFXTextField}
import com.tsunderebug.p2pclient.control.Connection
import com.tsunderebug.p2pclient.{messaging => msg}

import scalafxml.core.macros.sfxml

@sfxml(additionalControls=List("com.jfoenix.controls"))
class MainMenu(
                @FXML private val rootpane: StackPane,
                @FXML private val list: JFXListView[Connection],
                @FXML private val ip: JFXTextField,
                @FXML private val port: JFXTextField,
                @FXML private val connect: JFXButton,
                @FXML private val changeport: JFXButton,
                @FXML private val iptext: Label,
                @FXML private val dialog: JFXDialog,
                @FXML private val pchange: JFXTextField,
                @FXML private val accept: JFXButton
              ) {


  rootpane.getChildren.remove(dialog)
  connect.setDisable(true)

  Seq(port, pchange) foreach(_.textProperty.addListener((observable, oldValue, _) => {
    if (!observable.asInstanceOf[StringProperty].get().matches("""(6553[0-5]|655[0-2]\d|65[0-4]\d{2}|6[0-4]\d{3}|[0-5]\d{4}|\d{0,4})""")) {
      observable.asInstanceOf[StringProperty].setValue(oldValue)
    }
  }))
  ip.textProperty.addListener((observable, oldValue, _) => {
    if(!observable.asInstanceOf[StringProperty].get().matches("""(25[0-5]|2[0-4]\d|[0-1]?\d{0,2})(\.(25[0-5]|2[0-4]\d|[0-1]?\d{0,2})){0,3}""".stripMargin)) {
      observable.asInstanceOf[StringProperty].setValue(oldValue)
    }
    connect.setDisable(!observable.asInstanceOf[StringProperty].get().matches("""(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"""))
  })
  accept.setOnMouseClicked((_: MouseEvent) => {
    msg.Connection.setPort(pchange.getText.toInt)
    iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + msg.Connection.getPort)
    rootpane.getChildren.remove(dialog)
  })

  iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + msg.Connection.getPort)
  changeport.setOnMouseClicked((_: MouseEvent) => {
    dialog.setTransitionType(DialogTransition.CENTER)
    dialog.show(rootpane)
  })

}

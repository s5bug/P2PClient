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

@sfxml
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


  rootpane.getChildren.remove()

  pchange.textProperty.addListener((observable, oldValue, _) => {
    if (!observable.asInstanceOf[StringProperty].get().matches("\\d{,5}")) {
      observable.asInstanceOf[StringProperty].setValue(oldValue)
    }
  })
  accept.setOnMouseClicked((_: MouseEvent) => {
    msg.Connection.setPort(pchange.getText.toInt)
  })

  iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + msg.Connection.getPort)
  changeport.setOnMouseClicked((_: MouseEvent) => {
    println("pls")
    dialog.setTransitionType(DialogTransition.CENTER)
    dialog.show(rootpane)
  })
  println(changeport.getText)

}

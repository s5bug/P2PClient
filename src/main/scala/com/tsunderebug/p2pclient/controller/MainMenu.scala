package com.tsunderebug.p2pclient.controller

import java.net.InetAddress
import javafx.beans.property.StringProperty
import javafx.event.{ActionEvent, EventHandler}
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{BorderPane, StackPane, VBox}

import com.jfoenix.controls.JFXDialog.DialogTransition
import com.jfoenix.controls.{JFXButton, JFXDialog, JFXListView, JFXTextField}
import com.tsunderebug.p2pclient.control.Connection
import com.tsunderebug.p2pclient.{messaging => msg}

import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class MainMenu(@FXML private val rootpane: BorderPane,
               @FXML private val list: JFXListView[Connection],
               @FXML private val ip: JFXTextField,
               @FXML private val port: JFXTextField,
               @FXML private val connect: JFXButton,
               @FXML private val changeport: JFXButton,
               @FXML private val iptext: Text) {


  val changePortDialog = new JFXDialog()
  val t = new JFXTextField(msg.Connection.getPort.toString)
  t.textProperty.addListener((observable, oldValue, _) => {
    if (!observable.asInstanceOf[StringProperty].get().matches("\\d{,5}")) {
      observable.asInstanceOf[StringProperty].setValue(oldValue)
    }
  })
  val b = new JFXButton()
  b.addEventFilter(MouseEvent.MOUSE_PRESSED, (event: MouseEvent) => {
    msg.Connection.setPort(t.getText.toInt)
  })
  val c = new VBox(
    t,
    b
  )
  c.alignmentProperty().setValue(Pos.CENTER)
  changePortDialog.setContent(c)

  iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + msg.Connection.getPort)
  changeport.addEventHandler(ActionEvent.ACTION, (event: ActionEvent) => {
    println("pls")
    changePortDialog.setTransitionType(DialogTransition.CENTER)
    changePortDialog.show(rootpane.asInstanceOf[StackPane])
  })

}

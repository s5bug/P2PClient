package com.tsunderebug.p2pclient.controller

import java.net.InetAddress
import java.util
import java.util.Collections
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.{scene => jfxs}

import com.jfoenix.controls.JFXDialog.DialogTransition
import com.jfoenix.controls.{JFXButton, JFXDialog, JFXListView, JFXTextField}
import com.sun.javafx.collections.ObservableListWrapper
import com.tsunderebug.p2pclient.control.Connection
import com.tsunderebug.p2pclient.{Main, client}

import scala.collection.JavaConverters._
import scala.reflect.runtime.universe._
import scalafx.scene.Scene
import scalafxml.core.macros.sfxml
import scalafxml.core.{DependenciesByType, FXMLView}

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
    client.Connection.setPort(if (pchange.getText == "") client.Connection.defaultPort else pchange.getText.toInt)
    iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + client.Connection.getPort)
    rootpane.getChildren.remove(dialog)
  })

  iptext.setText("Your local IP is: " + InetAddress.getLocalHost.getHostAddress + ":" + client.Connection.getPort)
  changeport.setOnMouseClicked((_: MouseEvent) => {
    dialog.setTransitionType(DialogTransition.CENTER)
    dialog.show(rootpane)
  })

  private lazy val c: Array[Connection] = client.Connection.connections.map((c: client.Connection) => new Connection(c.name, c.members.map(_.ip).mkString("\n")))
  if(c.length > 0) {
    list.setItems(new ObservableListWrapper[Connection](c.toList.asJava))
  }

  connect.setOnMouseClicked((_: MouseEvent) => {
    if(c.length == 0) {
      list.setItems(new ObservableListWrapper[Connection](Collections.emptyList()))
    }
    val l = new util.ArrayList[Connection](list.getItems)
    val conn = new Connection(ip.getText + ":" + (if (port.getText == "") client.Connection.defaultPort.toString else port.getText), ip.getText + ":" + (if (port.getText == "") client.Connection.defaultPort.toString else port.getText))
    conn.setOnMouseClicked((_: MouseEvent) => {
      Main.stage.scene = new Scene(
        new jfxs.Scene(
          FXMLView(
            getClass.getResource("/fxml/conversation.fxml"),
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
    })
    l.add(conn)
    list.setItems(new ObservableListWrapper[Connection](l))
  })

}

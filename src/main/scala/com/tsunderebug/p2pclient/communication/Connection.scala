package com.tsunderebug.p2pclient.communication

import java.io.{BufferedReader, InputStreamReader, PrintStream}
import java.net.Socket

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Connection(ips: Array[String]) {

  val sockets: Map[String, Socket] = ips.zip(ips.map((s: String) => {
    val host = s.substring(0, s.indexOf(':'))
    val port = s.substring(s.indexOf(':') + 1).toInt
    new Socket(host, port)
  })).toMap

  private val listeners: mutable.Map[String, ListBuffer[Listener]] = mutable.Map[String, ListBuffer[Listener]]()

  /**
    * Format is [NAME] [totalips] [content]
    *
    * @param name Command name
    * @param toIPs Where the message should be sent
    * @param content Content of the message
    */
  def write(name: String, toIPs: Array[String], content: String): Unit = {
    val str = name.toUpperCase + " " + ips.mkString(",") + " " + content
    sockets.keys.filter(toIPs.contains).foreach((s: String) => {
      val socket = sockets(s)
      val os = new PrintStream(socket.getOutputStream)
      os.println(str)
    })
  }

  def addListener(command: String, listener: Listener): Unit = {
    listeners.getOrElseUpdate(command.toUpperCase, ListBuffer[Listener]()) += listener
  }

  def removeListener(listener: Listener): Unit = {
    listeners.keys.foreach(listeners(_) -= listener)
  }

  private var closeRequested = false

  sockets.values.foreach((s: Socket) => {
    val br = new BufferedReader(new InputStreamReader(s.getInputStream))
    while (!closeRequested) {
      val line = br.readLine()
      val cmd = line.substring(0, line.indexOf(' ')).toUpperCase
      listeners(cmd).foreach(_.handle(new Event {

        override def ipsIn: Array[String] = ips

        override def content: String = line.split(' ').drop(2).mkString(" ")

        override def name: String = cmd

      }))
    }
    br.close()
    s.close()
  })

  def close(): Unit = {
    closeRequested = true
  }

}

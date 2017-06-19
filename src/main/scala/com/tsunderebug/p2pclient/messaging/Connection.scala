package com.tsunderebug.p2pclient.messaging

import java.io.File

object Connection {

  val defaultPort: Int = 15138

  def apply(ips: Array[String]): Connection = {
    val d = new File(System.getProperty("user.home"), ".p2pclient/" + ips.mkString(" "))
    d.mkdirs()
    val f = new File(d, "connection.json")
    f.createNewFile()
  }

}

class Connection private {

  val members: Array[Member] = null
  val name: String = null
  @transient lazy val messages: Array[Message] = {
    val d = new File(System.getProperty("user.home"), ".p2pclient/" + members.map(_.ip).mkString(" "))
    d.mkdirs()
    val f = new File(d, "messages.json")
    f.createNewFile()
  }

}

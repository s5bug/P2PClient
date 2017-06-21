package com.tsunderebug.p2pclient.messaging

import java.io.{File, FileWriter}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.tsunderebug.p2pclient.Resource

object Connection {

  val defaultPort: Int = 15138

  def getPort: Int = {
    val d = new File(System.getProperty("user.home"), ".p2pclient")
    d.mkdirs()
    val f = new File(d, "port.json")
    if (!f.exists()) {
      f.createNewFile()
      val w = new FileWriter(f)
      w.write(defaultPort.toString)
      w.close()
    }
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[Int](f)
  }

  def setPort(v: Int): Unit = {
    val d = new File(System.getProperty("user.home"), ".p2pclient")
    d.mkdirs()
    val f = new File(d, "port.json")
    if (!f.exists()) {
      f.createNewFile()
    }
    val w = new FileWriter(f)
    w.write(v.toString)
    w.close()
  }

  def apply(ips: Array[String]): Connection = {
    val d = new File(System.getProperty("user.home"), ".p2pclient/" + ips.mkString(" "))
    d.mkdirs()
    val f = new File(d, "connection.json")
    f.createNewFile()
    if (!f.exists()) {
      f.createNewFile()
      val m = new ObjectMapper()
      m.registerModule(DefaultScalaModule)
      m.writeValue(f, new Connection(ips))
    }
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[Connection](f)
  }

}

class Connection private(ips: Array[String]) {

  val members: Array[Member] = ips.map(Member.apply)
  val name: String = members.mkString(", ")
  @transient lazy val messages: Array[Message] = {
    val d = Resource.getDataFolder(members.map(_.ip))
    val f = new File(d, "messages.json")
    if (!f.exists()) {
      f.createNewFile()
      val w = new FileWriter(f)
      w.write("[]")
      w.close()
    }
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[Array[Message]](f)
  }

}

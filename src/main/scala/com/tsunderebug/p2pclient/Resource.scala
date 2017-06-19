package com.tsunderebug.p2pclient

import java.io.File

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.collection.mutable

object Resource {

  def nickMap(): mutable.Map[String, String] = {
    val d = new File(System.getProperty("user.home"), ".p2pclient")
    d.mkdirs()
    val f = new File(d, "nicks.json")
    f.createNewFile()
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.readValue[mutable.Map[String, String]](f)
  }

  def writeNicks(map: mutable.Map[String, String]): Unit = {
    val d = new File(System.getProperty("user.home"), ".p2pclient")
    d.mkdirs()
    val f = new File(d, "nicks.json")
    f.createNewFile()
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.writeValue(f, map)
  }

  def getDataFolder(ips: Array[String]): File = {
    val f = new File(System.getProperty("user.home"), ".p2pclient/" + ips.mkString(","))
    f.mkdirs()
    f
  }

}

package com.tsunderebug.p2pclient.communication

trait Event {

  def ipsIn: Array[String]
  def name: String
  def content: String

}

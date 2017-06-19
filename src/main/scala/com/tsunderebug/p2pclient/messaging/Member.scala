package com.tsunderebug.p2pclient.messaging

import com.tsunderebug.p2pclient.Resource

import scala.collection.mutable

object Member {

  val ipToNick: mutable.Map[String, String] = Resource.nickMap()

  def apply(ip: String) = new Member(ip, Resource.nickMap().getOrElse(ip, ip))

}

class Member private (val ip: String, private var _nick: String) {

  def nick: String = _nick

  def nick_=(s: String): Unit = {
    _nick = s
    val m = Resource.nickMap()
    m += ip -> s
    Resource.writeNicks(m)
  }

}

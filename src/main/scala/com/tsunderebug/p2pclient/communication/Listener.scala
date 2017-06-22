package com.tsunderebug.p2pclient.communication

trait Listener {

  def handle(e: Event): Unit

}

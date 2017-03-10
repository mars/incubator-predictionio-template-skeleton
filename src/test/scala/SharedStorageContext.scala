package org.template.vanilla

import org.scalatest.{BeforeAndAfterAll, Suite}

import org.h2.tools.Server
import org.apache.predictionio.tools.console.{App, AppArgs, ConsoleArgs}

trait SharedStorageContext extends BeforeAndAfterAll {
  this: Suite =>

  override def beforeAll() {
    val _h2Server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers" )
    _h2Server.start
    App.create(ConsoleArgs(app = AppArgs(
      id = Some(1),
      name = "test",
      description = Option("temporary database for unit tests"))))

    super.beforeAll()
  }

  override def afterAll() {
    super.afterAll()

    App.delete(ConsoleArgs(app = AppArgs(id = Some(1))))
    //_h2Server.stop
  }
}
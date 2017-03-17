package org.template.vanilla

import org.scalatest.{BeforeAndAfterAll, Suite}

import org.h2.tools.Server
import org.apache.predictionio.data.storage.Storage
import org.apache.predictionio.tools.console.{App, AppArgs, ConsoleArgs}

trait SharedStorageContext extends BeforeAndAfterAll {
  this: Suite =>

  override def beforeAll() {
    val _h2Server = Server.createTcpServer("-tcpPort", "9092")
    _h2Server.start

    App.create(ConsoleArgs(app = AppArgs(
      id = Some(1),
      name = "INVALID_APP_NAME",
      description = Option("temporary database for unit tests"))))

    // Hack to initialize connection; avoids "Connection pool is not yet initialized" error.
    Storage.getPEvents()

    super.beforeAll()
  }

  override def afterAll() {
    super.afterAll()

    App.delete(ConsoleArgs(app = AppArgs(id = Some(1))))
    //_h2Server.stop
  }
}